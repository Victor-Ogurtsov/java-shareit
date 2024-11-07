package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@Positive @PathVariable Long userId) {
        log.info("Запрос на получение User с id = : {}", userId);
        return userClient.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<Object>  addUser(@Validated(UserDto.AddUser.class) @RequestBody UserDto userDto) {
        log.info("Запрос на добавление пользователя: {}", userDto);
        return userClient.addUser(userDto);
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@Positive @PathVariable Long userId, @Validated(UserDto.UpdateUser.class) @RequestBody UserDto userDto) {
        log.info("Запрос на обновление пользователя с id = {} и свойствами: {}", userId, userDto);
        return userClient.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@Positive @PathVariable Long userId) {
        log.info("Запрос на удаление пользователя с id = {}", userId);
        userClient.deleteUser(userId);
    }
}
