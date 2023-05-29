package com.example.airbnbApi.reservation;

import com.example.airbnbApi.reservation.dto.ReservationDTO;
import com.example.airbnbApi.reservation.dto.ReservationResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO reservationDTO){
        reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getReservationByUser(@PathVariable Integer userId){
        List<ReservationResponseDTO> result =  reservationService.getReservationByUser(userId);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{reservationId}/{userId}")
    public ResponseEntity<?> deleteReservation(@PathVariable Integer reservationId,
                                               @PathVariable Integer userId){
        reservationService.deleteReservation(reservationId,userId);
        return ResponseEntity.ok().body("success");
    }
}
