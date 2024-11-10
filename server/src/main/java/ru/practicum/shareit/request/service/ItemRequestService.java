package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addItemRequest(Long userId, ItemRequestDto itemRequestDto);

    ItemRequestDto getItemRequestById(Long userId, Long requestId);

    List<ItemRequestDto> getAllItemRequestForRequestor(Long userId);

    List<ItemRequestDto> getAllItemRequest(Long userId);
}
