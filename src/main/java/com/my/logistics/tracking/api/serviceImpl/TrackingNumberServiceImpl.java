package com.my.logistics.tracking.api.serviceImpl;

import com.my.logistics.tracking.api.dto.TrackingNumberRequest;
import com.my.logistics.tracking.api.dto.TrackingNumberResponse;
import com.my.logistics.tracking.api.service.TrackingNumberService;
import com.my.logistics.tracking.api.exception.TrackingNumberGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrackingNumberServiceImpl implements TrackingNumberService {

    private static final String TRACKING_SET_KEY = "used_tracking_numbers";
    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<String> trackingNumberLuaScript;

    @Value("${tracking.number.max-attempts: }")
    private int maxAttempts;

    public TrackingNumberServiceImpl(
            RedisTemplate<String, String> redisTemplate,
            DefaultRedisScript<String> trackingNumberLuaScript
    ) {
        this.redisTemplate = redisTemplate;
        this.trackingNumberLuaScript = trackingNumberLuaScript;
    }

    @Override
    public TrackingNumberResponse generateTrackingNumber(TrackingNumberRequest request) {

        log.info("Entered generateTrackingNumber method in ServiceImpl");
        String cleanSlug = sanitize(request.getCustomer_slug()).toUpperCase();
        String customerId = sanitize(request.getCustomer_id().toUpperCase());

        String prefix = cleanSlug.substring(0, Math.min(4, cleanSlug.length()));
        String customerIdPart = customerId.substring(0, Math.min(6, customerId.length()));
        String timeSuffix = formatTime(request.getCreated_at());

        for (int i = 0; i < maxAttempts; i++) {
            String shuffled = charsShuffle(customerIdPart + timeSuffix);
            String candidate = prefix + shuffled;

            String result = redisTemplate.execute(
                    trackingNumberLuaScript,
                    Collections.singletonList(TRACKING_SET_KEY),
                    candidate
            );
            log.info("TrackingNo is generated successfully. : " + result);
            if (result != null) {
                return new TrackingNumberResponse(result, ZonedDateTime.now());
            }
        }
        throw new TrackingNumberGenerationException("Failed to generate a unique tracking number after 3 attempts.");
    }

    private String sanitize(String input) {
        return input == null ? "" : input.replaceAll("[^a-zA-Z0-9]", "");
    }

    private String formatTime(ZonedDateTime time) {
        String seconds = String.format("%02d", time.getSecond());
        String millis = String.format("%-4s", time.getNano() / 1_000_000).replace(' ', '0');
        return seconds + millis;
    }

    private String charsShuffle(String data) {
        List<Character> chars = data.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(chars, ThreadLocalRandom.current());
        return chars.stream().map(String::valueOf).collect(Collectors.joining());
    }
}
