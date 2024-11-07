package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.CommentDto;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {

    private static final String REQUEST_HEADER_VALUE = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос на добавление вещи у пользователя с userId = {}: {}", userId, itemDto);

        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Positive @PathVariable Long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("Запрос на обновлении вещи itemId = {} у пользователя userId = {}, обновляются свойства: {}",
                itemId, userId, itemDto);
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Positive @PathVariable Long itemId) {
        log.info("Запрос на получение вещи itemId = {}, у пользователя: {}", itemId, userId);
        return itemClient.getItem(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemListFromUser(@RequestHeader(REQUEST_HEADER_VALUE) Long userId) {
        log.info("Запрос на получение списка вещей у пользователя: {}", userId);
        return itemClient.getItemListFromUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemListBySearch(@RequestHeader(REQUEST_HEADER_VALUE) Long userId,
                                             @RequestParam(name = "text") String text) {
        log.info("Запрос на получение списка вещей у пользователя: {} по ключевому запросу text = {}", userId, text);
        return itemClient.getItemListBySearch(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long itemId,
                                             @Valid @RequestBody CommentDto commentDto) {
        log.info("Запрос на добавление комментария пользователем" +
                " с userId = {} к вещи с itemId = {} c текстом comment = {}", userId, itemId, commentDto);
        return itemClient.addComment(userId, itemId, commentDto);
    }
}