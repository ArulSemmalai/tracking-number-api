package com.my.logistics.tracking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TrackingNumberResponse {
    private String tracking_number;
    private String created_at;

    public TrackingNumberResponse(String trackingNumber, ZonedDateTime createdAt) {
        this.tracking_number = trackingNumber;
        this.created_at = createdAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
