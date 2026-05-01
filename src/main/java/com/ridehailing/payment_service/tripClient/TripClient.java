package com.ridehailing.payment_service.tripClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service", url = "${trip-service.url}")
public interface TripClient {

    @GetMapping("/{id}/status")
    String getTripStatus(@PathVariable("id") String tripId);
}
