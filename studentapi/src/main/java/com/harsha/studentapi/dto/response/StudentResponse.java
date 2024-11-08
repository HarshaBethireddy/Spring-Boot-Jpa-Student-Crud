package com.harsha.studentapi.dto.response;

import com.harsha.studentapi.dto.enums.DegreeStatus;

public record StudentResponse(Long id, String name, String email, String phoneNo, DegreeStatus degreeStatus) {
}
