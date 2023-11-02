package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.dto.RegDto;
import ru.liga.service.UserService;

@RestController
@AllArgsConstructor
@Tag(name = "API аутентификации")
public class AuthenticationController {
    private final UserService userService;

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RegDto request) {
        return userService.createUser(request);
    }

}
