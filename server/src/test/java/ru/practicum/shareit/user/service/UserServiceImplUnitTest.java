package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationArgException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;


    @Test
    void checkEmail_shouldThrowValidationArgException() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findByEmail(any())).thenReturn(new User());

        Assertions.assertThrows(ValidationArgException.class, () -> userServiceImpl.checkEmail("email"));
    }

    @Test
    void checkName_shouldThrowValidationArgException() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);

        Assertions.assertThrows(ValidationArgException.class, () -> userServiceImpl.checkName("   "));
    }

    @Test
    void addUser_shouldThrowValidationArgException() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findByEmail(any())).thenReturn(new User());
        when(userMapper.fromUserDto(any())).thenReturn(new User());

        Assertions.assertThrows(ValidationArgException.class, () -> userServiceImpl.addUser(new UserDto()));
    }

    @Test
    void updateUser_shouldThrowNotFoundExceptionWhenNotFoundUserById() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userServiceImpl.updateUser(1L, new UserDto()));
    }

    @Test
    void updateUser_shouldThrowValidationArgExceptionWhenCheckEmail() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail(any())).thenReturn(new User());
        UserDto userDto = new UserDto();
        userDto.setEmail("email");

        Assertions.assertThrows(ValidationArgException.class, () -> userServiceImpl.updateUser(1L, userDto));
    }

    @Test
    void updateUser_shouldThrowValidationArgExceptionWhenCheckName() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail(any())).thenReturn(null);
        UserDto userDto = new UserDto();
        userDto.setEmail("email");
        userDto.setName("   ");

        Assertions.assertThrows(ValidationArgException.class, () -> userServiceImpl.updateUser(1L, userDto));
    }

    @Test
    void getUser_shouldThrowNotFoundExceptionWhenNotFoundUserById() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userServiceImpl.getUser(1L));
    }

    @Test
    void deleteUser_shouldThrowNotFoundExceptionWhenNotFoundUserById() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userServiceImpl.deleteUser(1L));
    }
}
/*
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
 */