package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.RequestBookingDto;




@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;
	private static final String REQUEST_HEADER_VALUE = "X-Sharer-User-Id";

	@GetMapping
	public ResponseEntity<Object> getBookingListByBookerAndState(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @RequestParam(defaultValue = "ALL") BookingState state) {
		log.info("Запрос списка бронирований пользователя  с userId = {} и состоянием  state = {} ", userId, state);
		return bookingClient.getBookings(userId, state);
	}

	@PostMapping
	public ResponseEntity<Object> addBooking(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Valid @RequestBody RequestBookingDto requestBookingDto) {
		log.info("Запрос на добавление бронирования у пользователя с userId = {}: вещи = {}", userId, requestBookingDto);
		return bookingClient.bookItem(userId, requestBookingDto);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBookingById(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long bookingId) {
		log.info("Запрос на информацию по бронированию с bookingId = {} от пользователя с userId = {} ", bookingId, userId);
		return bookingClient.getBooking(userId, bookingId);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> addBookingStatus(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long bookingId, @RequestParam Boolean approved) {
		log.info("Запрос на изменение статуса approved = {} для бронирования с bookingId = {} пользователем с userId = {} ", approved, bookingId, userId);
		return bookingClient.addBookingStatus(userId, bookingId, approved);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getBookingListByOwnerAndState(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @RequestParam(defaultValue = "ALL") String state) {
		log.info("Запрос списка бронирований владельцем с userId = {} и состоянием бронирований state = {} ", userId, state);
		return bookingClient.getBookingListByOwnerAndState(userId, state);
	}
}
