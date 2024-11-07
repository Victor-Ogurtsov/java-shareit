package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemForItemRequestDto;
import ru.practicum.shareit.item.dto.ItemForItemRequestMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestMapper itemRequestMapper;
    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final ItemForItemRequestMapper itemForItemRequestMapper;

    @Override
    public ItemRequestDto addItemRequest(Long userId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId));
        ItemRequest itemRequest = itemRequestMapper.fromItemRequestDto(itemRequestDto);
        itemRequest.setRequestor(user);
        ItemRequest addedItemRequest = itemRequestRepository.save(itemRequest);
        return itemRequestMapper.toItemRequestDto(addedItemRequest);
    }

    @Override
    public ItemRequestDto getItemRequestById(Long userId, Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findItemRequestById(requestId)
                .orElseThrow(() -> new NotFoundException("Не найден запрос на вещь с requestId = " + requestId));
        List<Item> itemList = itemRepository.findAllItemsByRequestId(requestId);
        List<ItemForItemRequestDto> itemForItemRequestDtoList = itemForItemRequestMapper.toItemForItemRequestList(itemList);
        log.info("Сервис: itemForItemRequestDtoList : {}", itemForItemRequestDtoList);

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest, itemForItemRequestDtoList);
        return itemRequestDto;
    }

    @Override
    public List<ItemRequestDto> getAllItemRequestForRequestor(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId));
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllItemRequestByUserId(userId);
        List<Long> itemRequestIdList = itemRequestList.stream().map(itemRequest -> itemRequest.getId()).toList();
        List<Item> itemList = itemRepository.findAllItemsByRequestIdList(itemRequestIdList);
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        for (ItemRequest itemRequest : itemRequestList) {
            Long id = itemRequest.getId();
            List<Item> itemListByRequestId = itemList.stream().filter(item -> Objects.equals(item.getRequest().getId(), id)).toList();
            List<ItemForItemRequestDto> itemForItemRequestDtoList = itemForItemRequestMapper.toItemForItemRequestList(itemListByRequestId);
            ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest, itemForItemRequestDtoList);
            itemRequestDtoList.add(itemRequestDto);
        }
        return itemRequestDtoList;
    }

    @Override
    public List<ItemRequestDto> getAllItemRequest(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId));
        return itemRequestMapper.toItemRequestDtoList(itemRequestRepository.findAllSortedItemRequest());
    }
}
