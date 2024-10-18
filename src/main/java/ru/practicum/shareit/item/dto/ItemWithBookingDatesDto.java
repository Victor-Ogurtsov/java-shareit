package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ItemWithBookingDatesDto {

    private  Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private ItemRequest request;
    private LocalDateTime lastBookingStart;
    private LocalDateTime lastBookingEnd;
    private LocalDateTime nextBookingStart;
    private LocalDateTime nextBookingEnd;
    List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemWithBookingDatesDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getAvailable(), that.getAvailable()) && Objects.equals(getOwner(), that.getOwner()) && Objects.equals(getRequest(), that.getRequest()) && Objects.equals(getLastBookingStart(), that.getLastBookingStart()) && Objects.equals(getLastBookingEnd(), that.getLastBookingEnd()) && Objects.equals(getNextBookingStart(), that.getNextBookingStart()) && Objects.equals(getNextBookingEnd(), that.getNextBookingEnd()) && Objects.equals(getComments(), that.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAvailable(), getOwner(), getRequest(), getLastBookingStart(), getLastBookingEnd(), getNextBookingStart(), getNextBookingEnd(), getComments());
    }
}
