package com.harsha.studentapi.dto.request;

import java.time.LocalDate;

public record StudentRequest(String name, String email, String phoneNo, LocalDate joiningDate, LocalDate relievingDate) {
}
