package com.team5.surbee.dto;

public record SessionUserDto(
        String username,
        String email,
        Integer id) {

    public static SessionUserDto of(String username, String email, Integer id) {
        return new SessionUserDto(username, email, id);
    }

    public static SessionUserDto of(String username, Integer id) {
        return new SessionUserDto(username, null, id);
    }
}
