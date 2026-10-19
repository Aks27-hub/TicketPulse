package com.ticketpulse.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TicketService {

    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Long> seatReservationScript;

    public TicketService(StringRedisTemplate redisTemplate, RedisScript<Long> seatReservationScript) {
        this.redisTemplate = redisTemplate;
        this.seatReservationScript = seatReservationScript;
    }

    public boolean bookSeat(String eventId, String seatId, String userId) {
        String key = "seat:" + eventId + ":" + seatId;
        
        // Execute the Lua script atomically
        Long result = redisTemplate.execute(
                seatReservationScript,
                Collections.singletonList(key),
                userId
        );

        // Returns 1 if successfully locked, 0 if conflict occurred
        return result != null && result == 1L;
    }
}