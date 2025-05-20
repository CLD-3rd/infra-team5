package com.team5.surbee.dto;

import com.team5.surbee.entity.User;

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

    public static SessionUserDto from(User user) {
        return new SessionUserDto(user.getUsername(), user.getEmail(), user.getId());
    }
}
