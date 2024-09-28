package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        userService.checkUserById(userId);
        Item item = itemMapper.fromItemDto(itemDto);
        item.setOwner(userRepository.getUserById(userId));
        Item addedItem = itemRepository.addItem(item);
        return itemMapper.toItemDto(addedItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        if (itemRepository.getItemById(itemId) == null) {
            throw new NotFoundException("Не найдена вещь с itemId = " + itemId);
        } else if (!Objects.equals(userId, itemRepository.getItemById(itemId).getOwner().getId())) {
            throw new NotFoundException("Пользователь с userId = " + userId + "не владелец вещи с itemId = " + itemId);
        }
        Item item = itemMapper.fromItemDto(itemDto);
        item.setId(itemId);
        Item updatedItem = itemRepository.updateItem(item);
        return itemMapper.toItemDto(updatedItem);
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        Item item = itemRepository.getItemById(itemId);
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getItemListFromUser(Long userId) {
        userService.checkUserById(userId);
        List<Item> items = itemRepository.getItemListFromUser(userId);

        return itemMapper.toItemListDto(items);
    }

    @Override
    public List<ItemDto> getItemListBySearch(Long userId, String searchQuery) {
        userService.checkUserById(userId);
        List<Item> items;
        if (searchQuery.isEmpty()) {
            return new ArrayList<ItemDto>();
        }
        items = itemRepository.getItemListBySearch(searchQuery);
        return itemMapper.toItemListDto(items);
    }
}
