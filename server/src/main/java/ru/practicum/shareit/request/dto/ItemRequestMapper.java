package ru.practicum.shareit.request.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.dto.ItemForItemRequestDto;
import ru.practicum.shareit.request.ItemRequest;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemRequestMapper {

    ItemRequest fromItemRequestDto(ItemRequestDto itemRequestDto);


    ItemRequestDto toItemRequestDto(ItemRequest itemRequest);

    ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<ItemForItemRequestDto> items);

    List<ItemRequestDto> toItemRequestDtoList(List<ItemRequest> itemRequestList);
}
