package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemForItemRequestDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;


class ItemRequestMapperImplTest {

    ItemRequestMapperImpl itemRequestMapper = new ItemRequestMapperImpl();

    @Test
    void fromItemRequestDto_shouldReturnNullWhenItemRequestDtoEqualsNull() {
        ItemRequest itemRequest = itemRequestMapper.fromItemRequestDto(null);

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

        ItemRequest itemRequest = itemRequestMapper.fromItemRequestDto(itemRequestDto);

        Assertions.assertEquals(itemRequest.getId(), itemRequestDto.getId());
        Assertions.assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        Assertions.assertEquals(itemRequest.getRequestor().getId(), itemRequestDto.getRequestor().getId());
        Assertions.assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
    }

    @Test
    void toItemRequestDto_shouldReturnNullWhenItemRequestEqualsNull() {
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(null);

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

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest);

        Assertions.assertEquals(itemRequest.getId(), itemRequestDto.getId());
        Assertions.assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        Assertions.assertEquals(itemRequest.getRequestor().getId(), itemRequestDto.getRequestor().getId());
        Assertions.assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
    }

    @Test
    void toItemRequestDtoTwoParam_shouldReturnNullWhenItemRequestAndItemsEqualsNull() {
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(null, null);

        Assertions.assertNull(itemRequestDto);
    }

    @Test
    void toItemRequestDtoTwoParam_shouldReturnItemRequestDto() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription("Отзыв");
        User user = new User();
        user.setId(1L);
        itemRequest.setRequestor(user);
        itemRequest.setCreated(LocalDateTime.now());

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest, List.of(new ItemForItemRequestDto()));

        Assertions.assertEquals(itemRequest.getId(), itemRequestDto.getId());
        Assertions.assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        Assertions.assertEquals(itemRequest.getRequestor().getId(), itemRequestDto.getRequestor().getId());
        Assertions.assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
        Assertions.assertNotNull(itemRequestDto.getItems());
    }

    @Test
    void toItemRequestDtoTwoParam_shouldReturnItemRequestDtoAndItemsEqualsNull() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription("Отзыв");
        User user = new User();
        user.setId(1L);
        itemRequest.setRequestor(user);
        itemRequest.setCreated(LocalDateTime.now());

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest, null);

        Assertions.assertEquals(itemRequest.getId(), itemRequestDto.getId());
        Assertions.assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        Assertions.assertEquals(itemRequest.getRequestor().getId(), itemRequestDto.getRequestor().getId());
        Assertions.assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
        Assertions.assertNull(itemRequestDto.getItems());
    }

    @Test
    void toItemRequestDtoList_shouldReturnNullWhenitemRequestListEqualsNull() {
        List<ItemRequestDto> itemRequestDtoList = itemRequestMapper.toItemRequestDtoList(null);

        Assertions.assertNull(itemRequestDtoList);
    }

    @Test
    void toItemRequestDtoList_shouldReturnItemRequestDtoList() {
        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setId(1L);
        itemRequest1.setDescription("Отзыв");
        User user1 = new User();
        user1.setId(1L);
        itemRequest1.setRequestor(user1);
        itemRequest1.setCreated(LocalDateTime.now());

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setId(1L);
        itemRequest2.setDescription("Отзыв");
        User user2 = new User();
        user2.setId(1L);
        itemRequest2.setRequestor(user2);
        itemRequest2.setCreated(LocalDateTime.now());
        List<ItemRequest> itemRequestList = List.of(itemRequest1, itemRequest2);

        List<ItemRequestDto> itemRequestDtoList = itemRequestMapper.toItemRequestDtoList(itemRequestList);

        Assertions.assertEquals(itemRequestDtoList.get(0).getId(), itemRequestList.get(0).getId());
        Assertions.assertEquals(itemRequestDtoList.get(0).getDescription(), itemRequestList.get(0).getDescription());
        Assertions.assertEquals(itemRequestDtoList.get(0).getRequestor().getId(), itemRequestList.get(0).getRequestor().getId());
        Assertions.assertEquals(itemRequestDtoList.get(0).getCreated(), itemRequestList.get(0).getCreated());
        Assertions.assertEquals(itemRequestDtoList.size(), itemRequestList.size());
    }

    @Test
    void userToUserDto_shouldReturnNullWhenUserEqualsNull() {
        UserDto userDto = itemRequestMapper.userToUserDto(null);

        Assertions.assertNull(userDto);
    }

    @Test
    void userToUserDto_shouldReturnUserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email");

        UserDto userDto = itemRequestMapper.userToUserDto(user);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void userDtoToUser_shouldReturnNullWhenUserDtoEqualsNull() {
        User user = itemRequestMapper.userDtoToUser(null);

        Assertions.assertNull(user);
    }

    @Test
    void userDtoToUser_shouldReturnUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("name");
        userDto.setEmail("email");

        User user = itemRequestMapper.userDtoToUser(userDto);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }
}