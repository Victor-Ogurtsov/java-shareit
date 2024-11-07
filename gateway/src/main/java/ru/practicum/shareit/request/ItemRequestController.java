package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final ItemRequestClient itemRequestClient;


    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader(REQUEST_HEADER_VALUE) Long userId,
                                                 @Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Запрос вещи: {} у пользователя с userId = {}: ", itemRequestDto, userId);
        return itemRequestClient.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long requestId) {
        log.info("Запрос информации о запросе на вещь с requestId = {} от пользователя с userId = {}: ", requestId, userId);
        return itemRequestClient.getItemRequestById(userId, requestId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemRequestForRequestor(@RequestHeader(REQUEST_HEADER_VALUE) Long userId) {
        log.info("Запрос списка всех своих запросов от пользователя с userId = {}: ", userId);
        return itemRequestClient.getAllItemRequestForRequestor(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequest(@RequestHeader(REQUEST_HEADER_VALUE) Long userId) {
        log.info("Запрос списка всех запросов вещей от пользователя с userId = {}: ", userId);
        return itemRequestClient.getAllItemRequest(userId);
    }
}
