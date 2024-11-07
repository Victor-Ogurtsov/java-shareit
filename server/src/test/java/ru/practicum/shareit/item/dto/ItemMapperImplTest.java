package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperImplTest {

    ItemMapperImpl itemMapper =  new ItemMapperImpl();

    @Test
    void toItemDto_shouldReturnNullWhenItemEqualsNull() {
        ItemDto itemDto = itemMapper.toItemDto(null);

        Assertions.assertNull(itemDto);
    }

    @Test
    void toItemDto_shouldReturnItemDto() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Название");
        item.setDescription("Описание");
        item.setAvailable(true);
        User user = new User();
        user.setId(1L);
        item.setOwner(user);
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        item.setRequest(itemRequest);

        ItemDto itemDto = itemMapper.toItemDto(item);
        System.out.println("itemDto : " + itemDto);

        Assertions.assertEquals(itemDto.getId(), item.getId());
        Assertions.assertEquals(itemDto.getName(), item.getName());
        Assertions.assertEquals(itemDto.getDescription(), item.getDescription());
        Assertions.assertEquals(itemDto.getAvailable(), item.getAvailable());
        Assertions.assertEquals(itemDto.getOwner().getId(), item.getOwner().getId());
        Assertions.assertEquals(itemDto.getRequestId(), item.getRequest().getId());
    }


    @Test
    void fromItemDto_shouldReturnNullWhenItemDtoEqualsNull() {
        Item item = itemMapper.fromItemDto(null);

        Assertions.assertNull(item);
    }

    @Test
    void itemDtoToItem_shouldReturnItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Название");
        itemDto.setDescription("Описание");
        itemDto.setAvailable(true);
        Item item = itemMapper.fromItemDto(itemDto);

        Assertions.assertEquals(item.getName(), itemDto.getName());
        Assertions.assertEquals(item.getDescription(), itemDto.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemDto.getAvailable());
    }

    @Test
    void toUserDto_shouldReturnNullWhenUserEqualsNull() {
        UserDto userDto = itemMapper.toUserDto(null);

        Assertions.assertNull(userDto);
    }

    @Test
    void toUserDto_shouldReturnUserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email");

        UserDto userDto = itemMapper.toUserDto(user);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void fromUserDto_shouldReturnNullWhenUserDtoEqualsNull() {
        User user = itemMapper.fromUserDto(null);

        Assertions.assertNull(user);
    }

    @Test
    void fromUserDto_shouldReturnUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("name");
        userDto.setEmail("email");

        User user = itemMapper.fromUserDto(userDto);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void fromItemRequestDto_shouldReturnNullWhenItemRequestDtoEqualsNull() {
        ItemRequest itemRequest = itemMapper.fromItemRequestDto(null);

        Assertions.assertNull(itemRequest);
    }

    @Test
    void fromItemRequestDto_shouldReturnItemRequest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        itemRequestDto.setDescription("Отзыв");
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        itemRequestDto.setRequestor(userDto);
        itemRequestDto.setCreated(LocalDateTime.now());

        ItemRequest itemRequest = itemMapper.fromItemRequestDto(itemRequestDto);

        Assertions.assertEquals(itemRequest.getId(), itemRequestDto.getId());
        Assertions.assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        Assertions.assertEquals(itemRequest.getRequestor().getId(), itemRequestDto.getRequestor().getId());
        Assertions.assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
    }

    @Test
    void toItemRequestDto_shouldReturnNullWhenItemRequestEqualsNull() {
        ItemRequestDto itemRequestDto = itemMapper.toItemRequestDto(null);

        Assertions.assertNull(itemRequestDto);
    }

    @Test
    void toItemRequestDto_shouldReturnItemRequestDto() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription("Отзыв");
        User user = new User();
        user.setId(1L);
        itemRequest.setRequestor(user);
        itemRequest.setCreated(LocalDateTime.now());

        ItemRequestDto itemRequestDto = itemMapper.toItemRequestDto(itemRequest);

        Assertions.assertEquals(itemRequest.getId(), itemRequestDto.getId());
        Assertions.assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        Assertions.assertEquals(itemRequest.getRequestor().getId(), itemRequestDto.getRequestor().getId());
        Assertions.assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
    }

    @Test
    void toItemListDto() {
    }
}