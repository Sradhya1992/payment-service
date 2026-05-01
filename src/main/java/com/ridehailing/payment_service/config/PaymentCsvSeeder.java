package com.ridehailing.payment_service.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ridehailing.payment_service.entity.Payment;
import com.ridehailing.payment_service.repo.PaymentRepository;

@Component
public class PaymentCsvSeeder implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(PaymentCsvSeeder.class);
	private static final DateTimeFormatter CSV_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final String SEED_FILE = "data/ride_trip_payments.csv";

	private final PaymentRepository paymentRepository;
	private final JdbcTemplate jdbcTemplate;

	public PaymentCsvSeeder(PaymentRepository paymentRepository, JdbcTemplate jdbcTemplate) {
		this.paymentRepository = paymentRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		if (paymentRepository.count() > 0) {
			log.info("Payment seed skipped because payment table already has records");
			return;
		}

		ClassPathResource resource = new ClassPathResource(SEED_FILE);
		if (!resource.exists()) {
			log.warn("Payment seed skipped because {} was not found", SEED_FILE);
			return;
		}

		int inserted = 0;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) {
					continue;
				}

				Payment payment = parsePayment(line);
				if (paymentRepository.findByReference(payment.getReference()).isEmpty()) {
					paymentRepository.save(payment);
					inserted++;
				}
			}
		}

		jdbcTemplate.execute("""
				SELECT setval(
					pg_get_serial_sequence('ride_trip_payments', 'payment_id'),
					COALESCE((SELECT MAX(payment_id) FROM ride_trip_payments), 1)
				)
				""");
		log.info("Payment seed completed from {} with {} records inserted", SEED_FILE, inserted);
	}

	private Payment parsePayment(String line) {
		String[] columns = line.split(",", -1);
		if (columns.length != 7) {
			throw new IllegalArgumentException("Invalid payment CSV row: " + line);
		}

		String datasetPaymentId = columns[0].trim();
		Payment payment = new Payment();
		payment.setPaymentId(Long.parseLong(datasetPaymentId));
		payment.setTripId(columns[1].trim());
		payment.setAmount(new BigDecimal(columns[2].trim()));
		payment.setMethod(columns[3].trim());
		payment.setStatus(columns[4].trim());
		payment.setReference(columns[5].trim());
		payment.setCreatedAt(LocalDateTime.parse(columns[6].trim(), CSV_TIMESTAMP));
		return payment;
	}
}
