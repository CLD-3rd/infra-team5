package com.team5.surbee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessionUser {
    private String username;
    private String email;
    private int id;
    public SessionUser(String username, String email, int id) {
        this.username = username;
        this.email = email;
        this.id = id;
    }
    public static SessionUser of(String username,String email, int id) {
        return new SessionUser(username,email,id);
    }
    public static SessionUser of(String username,int id){
        return new SessionUser(username,null,id);
    }
}