package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class RequestBookingDto {
    private LocalDateTime start;
    private LocalDateTime end;
    Long itemId;
}
