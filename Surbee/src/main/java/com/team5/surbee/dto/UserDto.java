package com.team5.surbee.dto;

import com.team5.surbee.entity.User;

import java.time.LocalDateTime;

public record UserDto(
        Integer id,
        String username,
        String email,
        String provider,
        String providerId,
        LocalDateTime createdAt
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProvider(),
                user.getProviderId(),
                user.getCreatedAt()
        );
    }

    public User toEntity() {
        return User.of(username, email, provider, providerId);
    }
}
