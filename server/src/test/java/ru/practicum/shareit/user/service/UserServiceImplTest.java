package ru.practicum.shareit.user.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    private final EntityManager entityManager;
    private final UserService userService;

    private UserDto getUserDto(Long i) {
        UserDto userDto = new UserDto();
        userDto.setName("name " + i);
        userDto.setEmail("email" + i + "@mail.com");
        return userDto;
    }

    @Test
    void addUser_shouldReturnUserWithId() {
        UserDto userDto = getUserDto(1L);
        UserDto addedUserDto = userService.addUser(userDto);

        assertThat(addedUserDto.getId(), notNullValue());
        assertThat(addedUserDto.getName(), is(userDto.getName()));
        assertThat(addedUserDto.getEmail(), is(userDto.getEmail()));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        UserDto newUserDto = getUserDto(2L);
        UserDto updatedUser = userService.updateUser(1L, newUserDto);

        assertThat(updatedUser.getId(), is(1L));
        assertThat(updatedUser.getName(), is(newUserDto.getName()));
        assertThat(updatedUser.getEmail(), is(newUserDto.getEmail()));
    }

    @Test
    void getUser_shouldReturnUserById() {
        UserDto userDto = userService.getUser(1L);

        assertThat(userDto.getName(), is("Иван"));
        assertThat(userDto.getEmail(), is("ivan@mail.com"));
    }

    @Test
    void deleteUser_shouldReturnZero() {
        userService.deleteUser(3L);

        TypedQuery<User> userTypedQuery2 = entityManager.createQuery("Select u from User u", User.class);
        List<User> userList = userTypedQuery2.getResultList();

        assertThat(userList.size(), is(2));
    }

    @Test
    void checkUserById_shouldThrowNotFoundException() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.checkUserById(333L));
    }
}