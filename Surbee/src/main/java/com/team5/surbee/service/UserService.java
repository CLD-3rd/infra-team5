package com.team5.surbee.service;

import com.team5.surbee.dto.UserDto;
import com.team5.surbee.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    @Transactional
    public UserDto getUser(Integer id){
        return UserDto.from(userRepository.findById(id).orElseThrow(()->new EntityNotFoundException()));
    }

    @Transactional
    public UserDto getUserByProvAndProvId(String provider,String providerId){
        return UserDto.from(userRepository.findByProviderAndProviderId(provider,providerId).orElseThrow(()->new EntityNotFoundException()));
    }
}
