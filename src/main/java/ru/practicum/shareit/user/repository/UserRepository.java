package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

public interface UserRepository {
    User addUser(User user);

    User getUserByEmail(String email);

    User updateUser(User user);

    User getUserById(Long id);

    void deleteUser(Long userId);
}
