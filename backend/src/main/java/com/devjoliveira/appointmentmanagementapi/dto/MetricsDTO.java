package com.devjoliveira.appointmentmanagementapi.dto;

import java.math.BigDecimal;

public record MetricsDTO(
    Integer todayAppointments,
    Integer weekAppointments,
    BigDecimal todayRevenue,
    Integer totalAppointments

) {

}
