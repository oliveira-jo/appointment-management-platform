package com.devjoliveira.appointmentmanagementapi.dto;

import java.math.BigDecimal;

public record MetricsResponseDTO(
        Integer todayAppointments,
        Integer weekAppointments,
        BigDecimal todayRevenue,
        Integer totalAppointments

) {

}
