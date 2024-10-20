package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDatesDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {

    private static final String REQUEST_HEADER_VALUE = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос на добавление вещи у пользователя с userId = {}: {}", userId, itemDto);

        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Positive @PathVariable Long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("Запрос на обновлении вещи itemId = {} у пользователя userId = {}, обновляются свойства: {}",
                itemId, userId, itemDto);
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemWithBookingDto getItem(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @Positive @PathVariable Long itemId) {
        log.info("Запрос на получение вещи itemId = {}, у пользователя: {}", itemId, userId);
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemWithBookingDatesDto> getItemListFromUser(@RequestHeader(REQUEST_HEADER_VALUE) Long userId) {
        log.info("Запрос на получение списка вещей у пользователя: {}", userId);
        return itemService.getItemListFromUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemListBySearch(@RequestHeader(REQUEST_HEADER_VALUE) Long userId,
                                             @RequestParam(name = "text") String searchQuery) {
        log.info("Запрос на получение списка вещей у пользователя: {} по ключевому запросу text = {}", userId, searchQuery);
        return itemService.getItemListBySearch(userId, searchQuery);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(REQUEST_HEADER_VALUE) Long userId, @PathVariable Long itemId,
                                 @Valid @RequestBody Comment comment) {
        log.info("Запрос на добавление комментария пользователем" +
                " с userId = {} к вещи с itemId = {} c текстом comment = {}", userId, itemId, comment);
        return itemService.addComment(userId, itemId, comment);
    }
}
