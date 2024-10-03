package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationArgException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userMapper.fromUserDto(userDto);
        checkEmail(user.getEmail());
        User addedUser = userRepository.addUser(user);

        return userMapper.toUserDto(addedUser);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        checkUserById(userId);
        if (userDto.getEmail() != null) {
            checkEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            checkName(userDto.getName());
        }
        User user = userMapper.fromUserDto(userDto);
        user.setId(userId);
        User updatedUser = userRepository.updateUser(user);

        return userMapper.toUserDto(updatedUser);
    }

    @Override
    public UserDto getUser(Long userId) {
        checkUserById(userId);
        User user = userRepository.getUserById(userId);
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        checkUserById(userId);
        userRepository.deleteUser(userId);
    }

    public void checkEmail(String email) {
        if (userRepository.getUserByEmail(email) != null) {
            throw new ValidationArgException("Пользователь с таким email уже зарегистрирован!");
        }
    }

    public void checkName(String name) {
        if (name.trim().isBlank()) {
            throw new ValidationArgException("Свойство name у Пользователя не может быть пустым!");
        }
    }

    public void checkUserById(Long id) {
        if (userRepository.getUserById(id) == null) {
            throw new NotFoundException("Не найден пользователь с id = " + id);
        }
    }
}
