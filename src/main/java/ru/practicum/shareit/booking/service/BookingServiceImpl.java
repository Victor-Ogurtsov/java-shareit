package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.RequestBookingDtoMapper;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDtoMapper;
import ru.practicum.shareit.exception.InvalidRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final RequestBookingDtoMapper requestBookingDtoMapper;
    private final ResponseBookingDtoMapper responseBookingDtoMapper;

    @Override
    public ResponseBookingDto addBooking(Long userId, RequestBookingDto requestBookingDto) {
        userService.checkUserById(userId);
        if (!requestBookingDto.getEnd().isAfter(requestBookingDto.getStart())) {
            throw new InvalidRequestException("Момент окончания бронирования должен быть после момента начала бронирования.");
        }
        Booking booking = requestBookingDtoMapper.fromRequestBookingDto(requestBookingDto);
        Item itemFromRepository = itemService.getItemOrThrowNotFoundException(booking.getItem().getId());
        if (itemFromRepository.getAvailable() == false) {
            throw new InvalidRequestException("Не найдена среди доступных вещь с itemId = " + itemFromRepository.getId());
        }
        booking.setItem(itemFromRepository);
        booking.setBooker(new User());
        booking.getBooker().setId(userId);
        return responseBookingDtoMapper.toResponseBookingDto(bookingRepository.save(booking));
    }

    @Override
    public ResponseBookingDto addBookingStatus(Long userId, Long bookingId, Boolean approved) {
        Optional<Booking> optionalBooking = bookingRepository.findBookingById(bookingId);
        checkBookingId(optionalBooking, bookingId);
        Booking booking = optionalBooking.get();
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new InvalidRequestException("Отвечать на запрос бронирования может только собственник вещи");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return responseBookingDtoMapper.toResponseBookingDto(bookingRepository.save(booking));
    }

    @Override
    public ResponseBookingDto getBookingById(Long userId, Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findBookingById(bookingId);
        checkBookingId(optionalBooking, bookingId);
        Booking booking = optionalBooking.get();
        if (!Objects.equals(userId, booking.getItem().getOwner().getId())
                && !Objects.equals(userId, booking.getBooker().getId())) {
            throw new NotFoundException("Не найден запрос на бронирование с bookingId = " + bookingId +
                    " для пользователя с userId = " + userId);
        }
        return responseBookingDtoMapper.toResponseBookingDto(booking);
    }

    @Override
    public List<ResponseBookingDto> getBookingListByBookerAndState(Long userId, String state) {
        List<BookingStatus> statusList = getBookingStatusList(state);
        List<Booking> bookingList = bookingRepository.getBookingListByBookerIdAndState(userId, statusList);
        bookingList = getFilteredBookingListByState(bookingList, state);
        return responseBookingDtoMapper.toResponseBookingDtoList(bookingList);
    }

    @Override
    public List<ResponseBookingDto> getBookingListByOwnerAndState(Long userId, String state) {
        List<BookingStatus> statusList = getBookingStatusList(state);
        List<Booking> bookingList = bookingRepository.getBookingListByOwnerIdAndState(userId, statusList);
        if (bookingList.isEmpty()) {
            throw new NotFoundException("Нет предоставленных вещей для аренды от пользователя с userId = " + userId);
        }
        bookingList = getFilteredBookingListByState(bookingList, state);
        return responseBookingDtoMapper.toResponseBookingDtoList(bookingList);
    }

    void checkBookingId(Optional<Booking> optionalBooking, Long bookingId) {
        if (optionalBooking.isEmpty()) {
            throw new NotFoundException("Не найден запрос на бронирование с bookingId = "
                    + bookingId);
        }
    }

    List<BookingStatus> getBookingStatusList(String state) {
        List<BookingStatus> statusList = new ArrayList<>(List.of(BookingStatus.APPROVED, BookingStatus.WAITING,
                BookingStatus.REJECTED, BookingStatus.CANCELED));
        if (state.equals("CURRENT") || state.equals("FUTURE")) {
            statusList = new ArrayList<>(List.of(BookingStatus.APPROVED));
        } else if (state.equals("WAITING")) {
            statusList = new ArrayList<>(List.of(BookingStatus.WAITING));
        } else if (state.equals("REJECTED")) {
            statusList = new ArrayList<>(List.of(BookingStatus.REJECTED));
        } else if (state.equals("PAST")) {
            statusList = new ArrayList<>(List.of(BookingStatus.CANCELED));
        } else if (!state.equals("ALL")) {
            throw new InvalidRequestException("Параметр state должен быть из: CURRENT, FUTURE, WAITING, PAST, REJECTED");
        }
        return statusList;
    }

    List<Booking> getFilteredBookingListByState(List<Booking> bookingList, String state) {
        if (state.equals("CURRENT")) {
            bookingList = bookingList.stream().filter(booking -> LocalDateTime.now().isBefore(booking.getEnd())).toList();
        }
        if (state.equals("FUTURE")) {
            bookingList = bookingList.stream().filter(booking -> LocalDateTime.now().isBefore(booking.getStart())).toList();
        }

        return bookingList;
    }
}
