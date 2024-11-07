package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDatesDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ItemService itemService;
    @Autowired
    MockMvc mockMvc;

    private ItemDto getItemDto(Long i) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(i);
        itemDto.setName("name" + i);
        itemDto.setDescription("description " + i);
        itemDto.setAvailable(true);
        itemDto.setOwner(new UserDto());
        itemDto.setRequestId(i);
        return itemDto;
    }

    private ItemWithBookingDto getItemWithBookingDto(Long i) {
        ItemWithBookingDto itemWithBookingDto = new ItemWithBookingDto();
        itemWithBookingDto.setId(i);
        itemWithBookingDto.setName("name" + i);
        itemWithBookingDto.setDescription("description " + i);
        itemWithBookingDto.setAvailable(true);
        itemWithBookingDto.setOwner(new UserDto());
        itemWithBookingDto.setRequest(new ItemRequest());
        itemWithBookingDto.setLastBooking(new Booking());
        itemWithBookingDto.setNextBooking(new Booking());
        itemWithBookingDto.setComments(new ArrayList<>());
        return itemWithBookingDto;
    }

    private ItemWithBookingDatesDto getItemWithBookingDatesDto(Long i) {
        ItemWithBookingDatesDto itemWithBookingDatesDto = new ItemWithBookingDatesDto();
        itemWithBookingDatesDto.setId(i);
        itemWithBookingDatesDto.setName("name" + i);
        itemWithBookingDatesDto.setDescription("description " + i);
        itemWithBookingDatesDto.setAvailable(true);
        itemWithBookingDatesDto.setOwner(new UserDto());
        itemWithBookingDatesDto.setRequest(new ItemRequest());
        itemWithBookingDatesDto.setLastBookingStart(LocalDateTime.now());
        itemWithBookingDatesDto.setLastBookingEnd(LocalDateTime.now().plusHours(i));
        itemWithBookingDatesDto.setNextBookingStart(LocalDateTime.now().plusHours(i));
        itemWithBookingDatesDto.setNextBookingEnd(LocalDateTime.now().plusDays(i));
        itemWithBookingDatesDto.setComments(new ArrayList<>());
        return itemWithBookingDatesDto;
    }

    private CommentDto getCommentDto(Long i) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(i);
        commentDto.setText("text" + i);
        commentDto.setItemDto(new ItemDto());
        commentDto.setAuthorName("name " + i);
        commentDto.setCreated(LocalDateTime.now());
        return commentDto;
    }

    @Test
    void addItem_shouldReturnItemDto() throws Exception {
        ItemDto itemDto = getItemDto(1L);

        when(itemService.addItem(any(), any()))
                .thenReturn(itemDto);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemDto.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.requestId", Matchers.is(itemDto.getRequestId()), Long.class));

    }

    @Test
    void updateItem_shouldReturnItemDto() throws Exception {
        ItemDto itemDto = getItemDto(1L);

        when(itemService.updateItem(any(), any(), any()))
                .thenReturn(itemDto);

        mockMvc.perform(patch("/items/" + itemDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemDto.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.requestId", Matchers.is(itemDto.getRequestId()), Long.class));
    }

    @Test
    void getItem_shouldReturnItemWithBookingDto() throws Exception {
        ItemWithBookingDto itemWithBookingDto = getItemWithBookingDto(1L);

        when(itemService.getItem(any(), any()))
                .thenReturn(itemWithBookingDto);

        mockMvc.perform(get("/items/" + itemWithBookingDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemWithBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemWithBookingDto.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemWithBookingDto.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemWithBookingDto.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.request", Matchers.notNullValue()))
                .andExpect(jsonPath("$.lastBooking", Matchers.notNullValue()))
                .andExpect(jsonPath("$.nextBooking", Matchers.notNullValue()))
                .andExpect(jsonPath("$.comments", Matchers.notNullValue()));
    }

    @Test
    void getItemListFromUser_shouldReturnItemWithBookingDatesDtoList() throws Exception {
        List<ItemWithBookingDatesDto> itemWithBookingDatesDtoList = List.of(getItemWithBookingDatesDto(1L),
                getItemWithBookingDatesDto(2L));

        when(itemService.getItemListFromUser(any()))
                .thenReturn(itemWithBookingDatesDtoList);

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemWithBookingDatesDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemWithBookingDatesDtoList.get(1).getId()), Long.class))
                .andExpect(jsonPath("$[0].description", Matchers.is(itemWithBookingDatesDtoList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].description", Matchers.is(itemWithBookingDatesDtoList.get(1).getDescription())));
    }

    @Test
    void getItemListBySearch_shouldReturnItemDtoList() throws Exception {
        List<ItemDto> itemDtoList = List.of(getItemDto(1L), getItemDto(2L));

        when(itemService.getItemListBySearch(any(),any()))
                .thenReturn(itemDtoList);

        String content = mockMvc.perform(get("/items/search?text=text")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        List<ItemDto> itemDtoListResult = objectMapper.readValue(content, new TypeReference<List<ItemDto>>(){});

        assertThat(itemDtoList.get(0), samePropertyValuesAs(itemDtoListResult.get(0)));
        assertThat(itemDtoList.get(1), samePropertyValuesAs(itemDtoListResult.get(1)));
    }

    @Test
    void addComment_shouldReturnCommentDto() throws Exception {
        CommentDto commentDto = getCommentDto(1L);

        when(itemService.addComment(any(), any(), any()))
                .thenReturn(commentDto);

        mockMvc.perform(post("/items/1/comment")
                .header("X-Sharer-User-Id", 1)
                .content(objectMapper.writeValueAsString(new CommentDto()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", Matchers.is(commentDto.getText())))
                .andExpect(jsonPath("$.itemDto", Matchers.notNullValue()))
                .andExpect(jsonPath("$.authorName", Matchers.is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.created", Matchers.containsString(commentDto.getCreated().toString().substring(0, 25))));

    }
}