package ru.practicum.shareit.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    @NotBlank(groups = AddUser.class)
    private String name;
    @Email(groups = {AddUser.class, UpdateUser.class})
    @NotNull(groups = AddUser.class)
    private String email;

    /**
     * группа проверок для сохранения
     */
    public interface AddUser {
    }

    /**
     * группа проверок для обновления
     */
    public interface UpdateUser {
    }
}