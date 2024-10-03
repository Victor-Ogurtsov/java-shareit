package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserDto getUser(@Positive @PathVariable Long userId) {
        log.info("Запрос на получение User с id = : {}", userId);
        return userService.getUser(userId);
    }

    @PostMapping
    public UserDto addUser(@Validated(UserDto.AddUser.class) @RequestBody UserDto userDto) {
        log.info("Запрос на добавление пользователя: {}", userDto);
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Positive @PathVariable Long userId, @Validated(UserDto.UpdateUser.class) @RequestBody UserDto userDto) {
        log.info("Запрос на обновление пользователя с id = {} и свойствами: {}", userId, userDto);
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@Positive @PathVariable Long userId) {
        log.info("Запрос на удаление пользователя с id = {}", userId);
        userService.deleteUser(userId);
    }
}
