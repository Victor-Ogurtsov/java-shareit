package ru.practicum.shareit.booking.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingServiceImplTest {

    private final EntityManager entityManager;
    private final BookingService bookingService;

    @Test
    void addBooking_shouldReturnResponseBookingDto() {
        RequestBookingDto requestBookingDto = new RequestBookingDto();
        requestBookingDto.setStart(LocalDateTime.now());
        requestBookingDto.setEnd(LocalDateTime.now().plusDays(1L));
        requestBookingDto.setItemId(1L);

        ResponseBookingDto responseBookingDto = bookingService.addBooking(2L, requestBookingDto);

        assertThat(responseBookingDto.getId(), notNullValue());
        assertThat(responseBookingDto.getStart(), is(requestBookingDto.getStart()));
        assertThat(responseBookingDto.getEnd(), is(requestBookingDto.getEnd()));
        assertThat(responseBookingDto.getItem().getId(), is(requestBookingDto.getItemId()));
        assertThat(responseBookingDto.getBooker().getId(), is(2L));
        assertThat(responseBookingDto.getStatus(), is(BookingStatus.WAITING));
    }

    @Test
    void addBookingStatus_shouldReturnResponseBookingDtoWithStatusApproved() {
        ResponseBookingDto responseBookingDto = bookingService.addBookingStatus(1L, 1L, true);

        assertThat(responseBookingDto.getId(), is(1L));
        assertThat(responseBookingDto.getItem().getId(), is(1L));
        assertThat(responseBookingDto.getStatus(), is(BookingStatus.APPROVED));
    }

    @Test
    void getBookingById_shouldReturnResponseBookingDtoWithIdOne() {
        ResponseBookingDto responseBookingDto = bookingService.getBookingById(1L, 1L);

        assertThat(responseBookingDto.getId(), is(1L));
        assertThat(responseBookingDto.getItem().getId(), is(1L));
        assertThat(responseBookingDto.getBooker().getId(), is(2L));
        assertThat(responseBookingDto.getStatus(), is(BookingStatus.APPROVED));
    }

    @Test
    void getBookingListByBookerAndState_shouldReturnResponseBookingDtoListWithBookerIdOne() {
        List<ResponseBookingDto> responseBookingDtoList = bookingService.getBookingListByBookerAndState(1L, "ALL");

        assertThat(responseBookingDtoList.get(0).getId(), is(2L));
        assertThat(responseBookingDtoList.get(0).getBooker().getId(), is(1L));
        assertThat(responseBookingDtoList.size(), is(1));
    }

    @Test
    void getBookingListByOwnerAndState() {
        List<ResponseBookingDto> responseBookingDtoList = bookingService.getBookingListByOwnerAndState(1L, "ALL");

        assertThat(responseBookingDtoList.get(0).getId(), is(3L));
        assertThat(responseBookingDtoList.get(1).getId(), is(1L));
        assertThat(responseBookingDtoList.get(0).getItem().getOwner().getId(), is(1L));
        assertThat(responseBookingDtoList.get(1).getItem().getOwner().getId(), is(1L));
        assertThat(responseBookingDtoList.size(), is(2));

    }
}