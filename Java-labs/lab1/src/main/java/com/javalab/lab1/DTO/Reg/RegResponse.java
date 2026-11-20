package com.javalab.lab1.DTO.Reg;

public record RegResponse(
    Long id,
    String name,
    String email,
    String role
) {}
