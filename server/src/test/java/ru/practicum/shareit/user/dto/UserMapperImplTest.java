package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    @Test
    void toUserDto_shouldReturnUserDto() {
        UserMapperImpl userMapper = new UserMapperImpl();

        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email");

        UserDto userDto = userMapper.toUserDto(user);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void fromUserDto_shouldReturnUser() {
        UserMapperImpl userMapper = new UserMapperImpl();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("name");
        userDto.setEmail("email");

        User user = userMapper.fromUserDto(userDto);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }
}