package com.team5.surbee.dto;

import com.team5.surbee.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@ToString
@Getter
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String provider;
    private String providerId;
    private Timestamp createdAt;

    public static UserDto of(Integer id, String username, String email, String provider, String providerId){
        return new UserDto(id, username, email, provider, providerId,null);
    }

    public static UserDto of(Integer id, String username, String provider, String providerId){
        return UserDto.of(id, username, null, provider, providerId);
    }

    public static UserDto from(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProvider(),
                user.getProviderId(),
                user.getCreatedAt()
        );
    }

    public User toEntity(){
        return User.of(
                username,
                email,
                provider,
                providerId
        );
    }
}
