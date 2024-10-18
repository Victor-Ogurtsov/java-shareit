package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
public class ItemWithBookingDto {
    private  Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private ItemRequest request;
    private Booking lastBooking;
    private Booking nextBooking;
    List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemWithBookingDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getAvailable(), that.getAvailable()) && Objects.equals(getOwner(), that.getOwner()) && Objects.equals(getRequest(), that.getRequest()) && Objects.equals(getLastBooking(), that.getLastBooking()) && Objects.equals(getNextBooking(), that.getNextBooking()) && Objects.equals(getComments(), that.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAvailable(), getOwner(), getRequest(), getLastBooking(), getNextBooking(), getComments());
    }
}
