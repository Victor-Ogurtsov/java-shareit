package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BookingService bookingService;
    @Autowired
    MockMvc mockMvc;

    private ResponseBookingDto getResponseBookingDto(Long i) {
        ResponseBookingDto responseBookingDto = new ResponseBookingDto();
        responseBookingDto.setId(i);
        responseBookingDto.setStart(LocalDateTime.now().plusMinutes(i));
        responseBookingDto.setEnd(LocalDateTime.now().plusHours(i));
        responseBookingDto.setItem(new ItemDto());
        responseBookingDto.setBooker(new UserDto());
        responseBookingDto.setStatus(BookingStatus.WAITING);
        return  responseBookingDto;
    }

    @Test
    void addBooking_shouldReturnBooking() throws Exception {
        ResponseBookingDto responseBookingDto = getResponseBookingDto(1L);

        when(bookingService.addBooking(any(), any()))
                .thenReturn(responseBookingDto);

                mockMvc.perform(post("/bookings")
                                .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new RequestBookingDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", Matchers.is(responseBookingDto.getId()), Long.class))
                        .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                        .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                        .andExpect(jsonPath("$.status", Matchers.is(responseBookingDto.getStatus().name())))
                        .andExpect(jsonPath("$.start", Matchers.containsString(responseBookingDto.getStart().toString().substring(0, 24))))
                        .andExpect(jsonPath("$.end", Matchers.containsString(responseBookingDto.getEnd().toString().substring(0, 24))));
    }

    @Test
    void addBookingStatus_shouldReturnBookingWithApprovedStatus() throws Exception {
        ResponseBookingDto responseBookingDto = getResponseBookingDto(1L);
        responseBookingDto.setStatus(BookingStatus.APPROVED);

        when(bookingService.addBookingStatus(1L, responseBookingDto.getId(), true))
                .thenReturn(responseBookingDto);

        mockMvc.perform(patch("/bookings/" + responseBookingDto.getId() + "?approved=true")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(responseBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(responseBookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(responseBookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(responseBookingDto.getEnd().toString().substring(0, 24))));
    }

    @Test
    void getBookingById_shouldReturnBooking() throws Exception {
        ResponseBookingDto responseBookingDto = getResponseBookingDto(1L);

        when(bookingService.getBookingById(any(), any()))
                .thenReturn(responseBookingDto);

        mockMvc.perform(get("/bookings/" + responseBookingDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(responseBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(responseBookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(responseBookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(responseBookingDto.getEnd().toString().substring(0, 24))));
    }

    @Test
    void getBookingListByBookerAndState_shouldReturnBookingList() throws Exception {
        List<ResponseBookingDto> responseBookingDtoList = List.of(getResponseBookingDto(1L), getResponseBookingDto(2L));

        when(bookingService.getBookingListByBookerAndState(any(), any()))
                .thenReturn(responseBookingDtoList);

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(responseBookingDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(responseBookingDtoList.get(1).getId()), Long.class));
    }

    @Test
    void getBookingListByOwnerAndState_shouldReturnBookingList() throws Exception {
        List<ResponseBookingDto> responseBookingDtoList = List.of(getResponseBookingDto(1L), getResponseBookingDto(2L));

        when(bookingService.getBookingListByOwnerAndState(any(), any()))
                .thenReturn(responseBookingDtoList);

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(responseBookingDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(responseBookingDtoList.get(1).getId()), Long.class));
    }
}