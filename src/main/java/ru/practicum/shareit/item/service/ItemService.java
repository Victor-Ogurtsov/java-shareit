package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDatesDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addItem(Long userId, ItemDto item);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    ItemWithBookingDto getItem(Long userId, Long itemId);

    List<ItemWithBookingDatesDto> getItemListFromUser(Long userId);

    List<ItemDto> getItemListBySearch(Long userId, String searchQuery);

    Item getItemOrThrowNotFoundException(Long itemId);

    CommentDto addComment(Long userId, Long itemId, Comment comment);
}
