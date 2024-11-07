package ru.practicum.shareit.item.comment.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentDtoMapper {

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "itemDto", source = "item")
    CommentDto toCommentDto(Comment comment);

    Comment fromCommentDto(CommentDto commentDto);

    @Mapping(target = "requestId", source = "request.id")
    ItemDto itemToItemDto(Item item);

    Item itemDtoToItem(ItemDto itemDto);
}

