package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Objects;


/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
@ToString
public class ItemDto {
    private  Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private UserDto owner;
    private ItemRequest request;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDto itemDto)) return false;
        return Objects.equals(getId(), itemDto.getId()) && Objects.equals(getName(), itemDto.getName()) && Objects.equals(getDescription(), itemDto.getDescription()) && Objects.equals(getAvailable(), itemDto.getAvailable()) && Objects.equals(getOwner(), itemDto.getOwner()) && Objects.equals(getRequest(), itemDto.getRequest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAvailable(), getOwner(), getRequest());
    }
}
