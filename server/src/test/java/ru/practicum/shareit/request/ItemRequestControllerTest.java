package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ItemRequestService itemRequestService;

    @Autowired
    MockMvc mockMvc;

    private ItemRequestDto getItemRequestDto(Long i) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(i);
        itemRequestDto.setDescription("description " + i);
        itemRequestDto.setRequestor(new UserDto());
        itemRequestDto.setCreated(LocalDateTime.now());
        itemRequestDto.setItems(new ArrayList<>());
        return itemRequestDto;
    }

    @Test
    void addItemRequest_shouldReturnItemRequestDto() throws Exception {
        ItemRequestDto itemRequestDto = getItemRequestDto(1L);

        when(itemRequestService.addItemRequest(any(), any()))
                .thenReturn(itemRequestDto);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemRequestDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemRequestDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", Matchers.is(itemRequestDto.getDescription())))
                .andExpect(jsonPath("$.requestor", Matchers.notNullValue()))
                .andExpect(jsonPath("$.created", Matchers.containsString(itemRequestDto.getCreated().toString().substring(0, 24))))
                .andExpect(jsonPath("$.items", Matchers.notNullValue()));
    }

    @Test
    void getItemRequestById_shouldReturnItemRequestDto() throws Exception {
        ItemRequestDto itemRequestDto = getItemRequestDto(1L);

        when(itemRequestService.getItemRequestById(any(), any()))
                .thenReturn(itemRequestDto);

        mockMvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemRequestDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", Matchers.is(itemRequestDto.getDescription())))
                .andExpect(jsonPath("$.requestor", Matchers.notNullValue()))
                .andExpect(jsonPath("$.created", Matchers.containsString(itemRequestDto.getCreated().toString().substring(0, 24))))
                .andExpect(jsonPath("$.items", Matchers.notNullValue()));
    }

    @Test
    void getAllItemRequestForRequestor_shouldReturnItemRequestDtoList() throws Exception {
        List<ItemRequestDto> itemRequestDtoList = List.of(getItemRequestDto(1L), getItemRequestDto(2L));

        when(itemRequestService.getAllItemRequestForRequestor(any()))
                .thenReturn(itemRequestDtoList);

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemRequestDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[0].description", Matchers.is(itemRequestDtoList.get(0).getDescription())))
                .andExpect(jsonPath("$[0].requestor", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemRequestDtoList.get(1).getId()), Long.class))
                .andExpect(jsonPath("$[1].description", Matchers.is(itemRequestDtoList.get(1).getDescription())))
                .andExpect(jsonPath("$[1].requestor", Matchers.notNullValue()));
    }

    @Test
    void getAllItemRequest_shouldReturnItemRequestDtoList() throws Exception {
        List<ItemRequestDto> itemRequestDtoList = List.of(getItemRequestDto(1L), getItemRequestDto(2L));

        when(itemRequestService.getAllItemRequest(any()))
                .thenReturn(itemRequestDtoList);

        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemRequestDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[0].description", Matchers.is(itemRequestDtoList.get(0).getDescription())))
                .andExpect(jsonPath("$[0].requestor", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemRequestDtoList.get(1).getId()), Long.class))
                .andExpect(jsonPath("$[1].description", Matchers.is(itemRequestDtoList.get(1).getDescription())))
                .andExpect(jsonPath("$[1].requestor", Matchers.notNullValue()));
    }
}