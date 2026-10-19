package com.ticketpulse.controller;

import com.ticketpulse.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(
            @RequestParam String eventId,
            @RequestParam String seatId,
            @RequestParam String userId) {
        
        boolean success = ticketService.bookSeat(eventId, seatId, userId);
        
        if (success) {
            return ResponseEntity.ok("Success: Seat " + seatId + " temporarily locked for user " + userId);
        } else {
            return ResponseEntity.status(409).body("Conflict: Seat " + seatId + " is already taken.");
        }
    }
}