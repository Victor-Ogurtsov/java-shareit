package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;

import java.util.List;

public interface BookingService {
    ResponseBookingDto addBooking(Long userId, RequestBookingDto booking);

    ResponseBookingDto addBookingStatus(Long userId, Long bookingId, Boolean approved);

    ResponseBookingDto getBookingById(Long userId, Long bookingId);

    List<ResponseBookingDto> getBookingListByBookerAndState(Long userId, String state);

    List<ResponseBookingDto> getBookingListByOwnerAndState(Long userId, String state);
}
