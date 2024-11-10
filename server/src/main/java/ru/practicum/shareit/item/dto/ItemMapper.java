package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    @Mapping(target = "requestId", source = "request.id")
    ItemDto toItemDto(Item item);

    Item fromItemDto(ItemDto itemDto);

    UserDto toUserDto(User user);

    User fromUserDto(UserDto userDto);

    ItemRequestDto toItemRequestDto(ItemRequest itemRequest);

    ItemRequest fromItemRequestDto(ItemRequestDto itemRequestDto);

    List<ItemDto> toItemListDto(List<Item> itemList);
}
