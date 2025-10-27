package ru.itmo.inf_sec.lab1.server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.inf_sec.lab1.dto.LoginRequest;
import ru.itmo.inf_sec.lab1.dto.RegisterRequest;
import ru.itmo.inf_sec.lab1.dto.TokenDto;
import ru.itmo.inf_sec.lab1.dto.UserDto;
import ru.itmo.inf_sec.lab1.entity.User;
import ru.itmo.inf_sec.lab1.exception.IncorrectAuthDataException;
import ru.itmo.inf_sec.lab1.exception.PasswordsNotEqualException;
import ru.itmo.inf_sec.lab1.exception.UsernameAlreadyExistException;
import ru.itmo.inf_sec.lab1.repository.UserRepository;
import ru.itmo.inf_sec.lab1.security.JwtUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream().map(user -> new UserDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public TokenDto register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new PasswordsNotEqualException("Пароли не совпадают");
        }
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException("Пользователь уже существует");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return new TokenDto(registerRequest.getUsername(), jwtUtils.generateJwtToken(registerRequest.getUsername()));
    }

    public TokenDto login(LoginRequest loginRequest) {
        var userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()) {
            throw new IncorrectAuthDataException("Неправильные имя пользователя или пароль");
        }
        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IncorrectAuthDataException("Неправильные имя пользователя или пароль");
        }
        return new TokenDto(loginRequest.getUsername(), jwtUtils.generateJwtToken(loginRequest.getUsername()));
    }
}
