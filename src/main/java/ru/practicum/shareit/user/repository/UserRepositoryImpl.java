package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        Long newId = 1 + users.keySet().stream().mapToLong(value -> value).max().orElse(0);
        user.setId(newId);
        users.put(newId, user);
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User user) {
        if (user.getName() != null) {
            users.get(user.getId()).setName(user.getName());
        }
        if (user.getEmail() != null) {
            users.get(user.getId()).setEmail(user.getEmail());
        }
        return users.get(user.getId());
    }

    @Override
    public User getUserByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public void deleteUser(Long userId) {
        users.remove(userId);
    }
}
