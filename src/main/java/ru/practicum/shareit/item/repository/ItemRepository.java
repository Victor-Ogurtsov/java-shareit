package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addItem(Item item);

    Item updateItem(Item item);

    Item getItemById(Long id);

    List<Item> getItemListFromUser(Long userId);

    List<Item> getItemListBySearch(String searchQuery);
}
