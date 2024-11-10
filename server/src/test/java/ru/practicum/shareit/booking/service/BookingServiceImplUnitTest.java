package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.RequestBookingDtoMapper;
import ru.practicum.shareit.booking.dto.ResponseBookingDtoMapper;
import ru.practicum.shareit.exception.InvalidRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplUnitTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private RequestBookingDtoMapper requestBookingDtoMapper;
    @Mock
    private ResponseBookingDtoMapper responseBookingDtoMapper;
    BookingServiceImpl bookingService = new BookingServiceImpl(bookingRepository, userService, itemService,
            requestBookingDtoMapper, responseBookingDtoMapper);

    @Test
    void getFilteredBookingListByState_shouldReturnBookingListWithBookingWithEndAfterNowAndStartBeforeNow() {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setEnd(LocalDateTime.now().plusDays(1));
        booking1.setStart(LocalDateTime.now().minusHours(12));

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setEnd(LocalDateTime.now().plusHours(16));
        booking2.setStart(LocalDateTime.now().plusHours(12));

        List<Booking> bookingList = bookingService.getFilteredBookingListByState(List.of(booking1, booking2), "CURRENT");

        Assertions.assertEquals(bookingList.size(), 1);
        Assertions.assertEquals(bookingList.get(0).getId(), 1L);
    }

    @Test
    void getFilteredBookingListByState_shouldReturnBookingListWithBookingWithStartAfterNow() {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setEnd(LocalDateTime.now().plusDays(1));
        booking1.setStart(LocalDateTime.now().minusHours(12));

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setEnd(LocalDateTime.now().minusDays(1));
        booking2.setStart(LocalDateTime.now().plusHours(12));

        List<Booking> bookingList = bookingService.getFilteredBookingListByState(List.of(booking1, booking2), "FUTURE");

        Assertions.assertEquals(bookingList.size(), 1);
        Assertions.assertEquals(bookingList.get(0).getId(), 2L);
    }

    @Test
    void getBookingStatusList_shouldThrowInvalidRequestException() {
        Assertions.assertThrows(InvalidRequestException.class, () -> bookingService.getBookingStatusList("НепонятноеСостояние"));
    }

    @Test
    void getBookingStatusList_shouldReturnBookingStatusListWithAllStatus() {
        Assertions.assertEquals(bookingService.getBookingStatusList("ALL").size(), List.of(BookingStatus.APPROVED, BookingStatus.WAITING,
                BookingStatus.REJECTED, BookingStatus.CANCELED).size());
    }

    @Test
    void getBookingStatusList_shouldReturnBookingStatusListWithApprovedStatus() {
        Assertions.assertEquals(bookingService.getBookingStatusList("CURRENT").get(0),
                BookingStatus.APPROVED);
    }

    @Test
    void getBookingStatusList_shouldReturnBookingStatusListWithWaitingStatus() {
        Assertions.assertEquals(bookingService.getBookingStatusList("WAITING").get(0),
                BookingStatus.WAITING);
    }

    @Test
    void getBookingStatusList_shouldReturnBookingStatusListWithRejectedStatus() {
        Assertions.assertEquals(bookingService.getBookingStatusList("REJECTED").get(0),
                BookingStatus.REJECTED);
    }

    @Test
    void getBookingStatusList_shouldReturnBookingStatusListWithCanceledStatus() {
        Assertions.assertEquals(bookingService.getBookingStatusList("PAST").get(0),
                BookingStatus.CANCELED);
    }

    @Test
    void checkBookingId() {
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.checkBookingId(Optional.empty(), 1L));
    }
}