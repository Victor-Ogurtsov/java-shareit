package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.UserDto;


import java.time.LocalDateTime;

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
}
