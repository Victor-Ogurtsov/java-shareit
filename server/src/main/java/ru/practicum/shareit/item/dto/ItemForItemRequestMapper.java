package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemForItemRequestMapper {

    @Mapping(target = "ownerId", source = "item.owner.id")
    ItemForItemRequestDto toItemForItemRequest(Item item);

    List<ItemForItemRequestDto> toItemForItemRequestList(List<Item> items);
}
