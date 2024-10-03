package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
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

    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос на добавление вещи у пользователя с userId = {}: {}", userId, itemDto);

        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Positive @PathVariable Long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("Запрос на обновлении вещи itemId = {} у пользователя userId = {}, обновляются свойства: {}",
                itemId, userId, itemDto);
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Positive @PathVariable Long itemId) {
        log.info("Запрос на получение вещи itemId = {}, у пользователя: {}", itemId, userId);
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getItemListFromUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение списка вещей у пользователя: {}", userId);
        return itemService.getItemListFromUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemListBySearch(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(name = "text") String searchQuery) {
        log.info("Запрос на получение списка вещей у пользователя: {} по ключевому запросу text = {}", userId, searchQuery);
        return itemService.getItemListBySearch(userId, searchQuery);
    }
}