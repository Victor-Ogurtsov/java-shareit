package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * TODO Sprint add-bookings.
 */
@Setter
@Getter
@ToString
public class ResponseBookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDto item;
    private UserDto booker;
    private BookingStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseBookingDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStart(), that.getStart()) && Objects.equals(getEnd(), that.getEnd()) && Objects.equals(getItem(), that.getItem()) && Objects.equals(getBooker(), that.getBooker()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStart(), getEnd(), getItem(), getBooker(), getStatus());
    }
}
