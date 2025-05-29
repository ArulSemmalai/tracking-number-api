package com.my.logistics.tracking.api.service;

import com.my.logistics.tracking.api.dto.TrackingNumberRequest;
import com.my.logistics.tracking.api.dto.TrackingNumberResponse;

public interface TrackingNumberService {
    public TrackingNumberResponse generateTrackingNumber(TrackingNumberRequest trackingNumberRequest);
}