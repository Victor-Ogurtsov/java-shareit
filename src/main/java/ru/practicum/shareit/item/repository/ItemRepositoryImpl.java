package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private Map<Long, Item> items = new HashMap<>();

    @Override
    public Item addItem(Item item) {
        Long newId = generateNewId();
        item.setId(newId);
        items.put(newId, item);
        return items.get(newId);
    }

    @Override
    public Item updateItem(Item item) {
        Item updatedItem = items.get(item.getId());
        if (item.getName() != null) {
            updatedItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updatedItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }
        return updatedItem;
    }

    @Override
    public Item getItemById(Long id) {
        return items.get(id);
    }

    @Override
    public List<Item> getItemListFromUser(Long userId) {
        return items.values().stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), userId))
                .toList();
    }

    @Override
    public List<Item> getItemListBySearch(String searchQuery) {
        return items.values().stream()
                .filter(item -> (item.getName().toLowerCase().contains(searchQuery.toLowerCase())
                        || item.getDescription().toLowerCase().contains(searchQuery.toLowerCase()))
                        && item.getAvailable().equals(true))
                .toList();
    }

    private Long generateNewId() {
        return items.keySet().stream()
                .mapToLong(value -> value).max().orElse(0) + 1;

    }
}
