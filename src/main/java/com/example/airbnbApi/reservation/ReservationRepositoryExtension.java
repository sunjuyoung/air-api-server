package com.example.airbnbApi.reservation;

import com.example.airbnbApi.reservation.dto.ReservationResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReservationRepositoryExtension {

     List<ReservationResponseDTO> getReservationsByUser(Integer userId);


}
