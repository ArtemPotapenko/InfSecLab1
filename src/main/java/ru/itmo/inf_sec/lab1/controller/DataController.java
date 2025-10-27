package ru.itmo.inf_sec.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.inf_sec.lab1.dto.UserDto;
import ru.itmo.inf_sec.lab1.server.UserService;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
