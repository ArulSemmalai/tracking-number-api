package com.my.logistics.tracking.api.controller;

import com.my.logistics.tracking.api.dto.TrackingNumberRequest;
import com.my.logistics.tracking.api.dto.TrackingNumberResponse;
import com.my.logistics.tracking.api.service.TrackingNumberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrackingNumberApiController {

    private final TrackingNumberService trackingNumberService;

    public TrackingNumberApiController(TrackingNumberService trackingNumberService) {
        this.trackingNumberService = trackingNumberService;
    }
    @PostMapping("/next-tracking-number")
    public ResponseEntity<TrackingNumberResponse> getTrackingNumber(@Valid @RequestBody TrackingNumberRequest trackingNumberRequest){
        return ResponseEntity.ok(trackingNumberService.generateTrackingNumber(trackingNumberRequest));
    }

    @GetMapping("/test")
    public String test(){
        log.info("inside testtt");
        return "test application success";
    }

}
