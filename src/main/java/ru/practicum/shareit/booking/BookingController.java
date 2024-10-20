package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private static final String REQUEST_HEADER_VALUE = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public ResponseBookingDto addBooking(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Valid @RequestBody RequestBookingDto requestBookingDto) {
        log.info("Запрос на добавление бронирования у пользователя с userId = {}: вещи с itemId = {}", userId, requestBookingDto.getItemId());
        return bookingService.addBooking(userId, requestBookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseBookingDto addBookingStatus(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long bookingId, @RequestParam Boolean approved) {
        log.info("Запрос на изменение статуса approved = {} для бронирования с bookingId = {} пользователем с userId = {} ", approved, bookingId, userId);
        return bookingService.addBookingStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseBookingDto getBookingById(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long bookingId) {
        log.info("Запрос на информацию по бронированию с bookingId = {} от пользователя с userId = {} ", bookingId, userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<ResponseBookingDto> getBookingListByBookerAndState(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("Запрос списка бронирований пользователя  с userId = {} и состоянием  state = {} ", userId, state);
        return bookingService.getBookingListByBookerAndState(userId, state);
    }

    @GetMapping("/owner")
    public List<ResponseBookingDto> getBookingListByOwnerAndState(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("Запрос списка бронирований владельцем с userId = {} и состоянием бронирований state = {} ", userId, state);
        return bookingService.getBookingListByOwnerAndState(userId, state);
    }
}
