package com.my.logistics.tracking.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingNumberRequest {
    @NotBlank(message = "Origin country ID is required.")
    private String origin_country_id;
    @NotBlank(message = "Destination country ID is required.")
    private String destination_country_id;
    @NotNull(message = "Weight is required.")
    @Positive(message = "Weight must be greater than 0.")
    @Digits(integer = 10, fraction = 3, message = "Weight must have up to 3 decimal places.")
    private BigDecimal weight;
    @NotNull(message = "Created at timestamp is required.")
    private ZonedDateTime created_at;
    @NotBlank(message = "Customer ID is required.")
    private String customer_id;
    @NotBlank(message = "Customer name is required.")
    private String customer_name;
    @NotBlank(message = "Customer slug is required.")
    private String customer_slug;
}
