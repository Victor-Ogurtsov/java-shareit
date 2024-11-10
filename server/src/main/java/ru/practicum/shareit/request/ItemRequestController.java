package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private static final String REQUEST_HEADER_VALUE = "X-Sharer-User-Id";
    private final ItemRequestService itemRequestService;


    @PostMapping
    public ItemRequestDto addItemRequest(@RequestHeader(REQUEST_HEADER_VALUE) Long userId,
                                         @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Запрос вещи: {} у пользователя с userId = {}: ", itemRequestDto, userId);
        return itemRequestService.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long requestId) {
        log.info("Запрос информации о запросе на вещь с requestId = {} от пользователя с userId = {}: ", requestId, userId);
        return itemRequestService.getItemRequestById(userId, requestId);
    }

    @GetMapping
    public List<ItemRequestDto> getAllItemRequestForRequestor(@RequestHeader(REQUEST_HEADER_VALUE) Long userId) {
        log.info("Запрос списка всех своих запросов вещей от пользователя с userId = {}: ", userId);
        return itemRequestService.getAllItemRequestForRequestor(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllItemRequest(@RequestHeader(REQUEST_HEADER_VALUE) Long userId) {
        log.info("Запрос списка всех запросов вещей от пользователя с userId = {}: ", userId);
        return itemRequestService.getAllItemRequest(userId);
    }
}
