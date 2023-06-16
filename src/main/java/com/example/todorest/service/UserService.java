package com.example.todorest.service;

import com.example.todorest.dto.CreateUserRequestDto;
import com.example.todorest.dto.UserAuthRequestDto;
import com.example.todorest.dto.UserAuthResponseDto;
import com.example.todorest.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserAuthResponseDto> auth(UserAuthRequestDto userAuthRequestDto);

    public ResponseEntity<UserDto> register(CreateUserRequestDto createUserRequestDto);


}

