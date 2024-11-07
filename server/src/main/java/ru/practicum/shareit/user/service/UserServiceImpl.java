package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationArgException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userMapper.fromUserDto(userDto);
        checkEmail(user.getEmail());
        User addedUser = userRepository.save(user);

        return userMapper.toUserDto(addedUser);
    }

    @Transactional
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
        User updatedUser = userRepository.save(user);

        return userMapper.toUserDto(updatedUser);
    }

    @Override
    public UserDto getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + userId);
        }
        return userMapper.toUserDto(optionalUser.get());
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        checkUserById(userId);
        userRepository.deleteById(userId);
    }

    public void checkEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new ValidationArgException("Пользователь с таким email уже зарегистрирован!");
        }
    }

    public void checkName(String name) {
        if (name.trim().isBlank()) {
            throw new ValidationArgException("Свойство name у Пользователя не может быть пустым!");
        }
    }

    public void checkUserById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + id);
        }
    }
}
