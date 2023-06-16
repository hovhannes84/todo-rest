package com.example.todorest.service.impl;

import com.example.todorest.dto.CreateUserRequestDto;
import com.example.todorest.dto.UserAuthRequestDto;
import com.example.todorest.dto.UserAuthResponseDto;
import com.example.todorest.dto.UserDto;
import com.example.todorest.entity.Type;
import com.example.todorest.entity.User;
import com.example.todorest.mapper.UserMapper;
import com.example.todorest.repository.UserRepository;
import com.example.todorest.service.UserService;
import com.example.todorest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;


    @Override
    public ResponseEntity<UserAuthResponseDto> auth(UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        UserAuthResponseDto userAuthResponseDto = new UserAuthResponseDto(token);

        return ResponseEntity.ok(userAuthResponseDto);

    }

    @Override
    public ResponseEntity<UserDto> register(CreateUserRequestDto createUserRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(createUserRequestDto.getEmail());
        if(byEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setType(Type.USER);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.mapToDto(user));

    }
}

