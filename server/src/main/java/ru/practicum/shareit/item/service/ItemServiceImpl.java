package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStartComparator;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exception.InvalidRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.dto.CommentDtoMapper;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ItemWithBookingDatesDtoMapper itemWithBookingDatesDtoMapper;
    private final ItemWithBookingDtoMapper itemWithBookingDtoMapper;
    private final CommentRepository commentRepository;
    private final CommentDtoMapper commentDtoMapper;
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        userService.checkUserById(userId);
        Item item = itemMapper.fromItemDto(itemDto);
        item.setOwner(new User());
        item.getOwner().setId(userId);
        if (itemDto.getRequestId() != null) {
            Long requestId = itemDto.getRequestId();
            ItemRequest itemRequest = itemRequestRepository.findItemRequestById(requestId).
                    orElseThrow(() -> new NotFoundException("Не найден запрос вещи с requestId = " + requestId));
            item.setRequest(itemRequest);
        }
        Item addedItem = itemRepository.save(item);
        return itemMapper.toItemDto(addedItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item itemFromRepository = getItemOrThrowNotFoundException(itemId);
        if (!Objects.equals(userId, itemFromRepository.getOwner().getId())) {
            throw new NotFoundException("Пользователь с userId = " + userId + "не владелец вещи с itemId = " + itemId);
        }
        Item item = itemMapper.fromItemDto(itemDto);
        if (item.getName() != null) {
            itemFromRepository.setName(item.getName());
        }
        if (item.getDescription() != null) {
            itemFromRepository.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            itemFromRepository.setAvailable(item.getAvailable());
        }
        Item updatedItem = itemRepository.save(itemFromRepository);
        return itemMapper.toItemDto(updatedItem);
    }

    @Override
    public ItemWithBookingDto getItem(Long userId, Long itemId) {
        userService.checkUserById(userId);
        Item item = getItemOrThrowNotFoundException(itemId);
        Booking lastBooking = null;
        Booking nextBooking = null;
        if (userId.equals(item.getOwner().getId())) {
            List<Booking> bookingList = bookingRepository.getBookingListByItemId(itemId);
            lastBooking = bookingList.stream()
                    .filter(booking -> LocalDateTime.now().isAfter(booking.getStart()))
                    .sorted(new BookingStartComparator().reversed()).findFirst().orElse(null);
            nextBooking = bookingList.stream()
                    .filter(booking -> LocalDateTime.now().isBefore(booking.getStart()))
                    .sorted(new BookingStartComparator()).findFirst().orElse(null);
        }
        List<Comment> commentList = commentRepository.getCommentListByItemId(itemId);

        return itemWithBookingDtoMapper.toItemWithBookingDto(item, lastBooking, nextBooking, commentList);
    }

    @Override
    public List<ItemWithBookingDatesDto> getItemListFromUser(Long userId) {
        userService.checkUserById(userId);
        List<Item> items = itemRepository.findAllItemsByUserId(userId);
        List<Long> itemsId = items.stream().map(Item::getId).toList();
        List<Booking> bookingList = bookingRepository.getBookingListByItemIdList(itemsId);
        List<Comment> commentList = commentRepository.getCommentListByItemIdList(itemsId);
        List<ItemWithBookingDatesDto> itemWithBookingDatesDtoList = new ArrayList<>();
        for (Item item : items) {
            Booking lastBooking = bookingList.stream().filter(booking -> booking.getItem().getId().equals(item.getId()))
                    .filter(booking -> LocalDateTime.now().isAfter(booking.getStart()))
                    .sorted(new BookingStartComparator().reversed()).findFirst().orElse(null);
            Booking nextBooking = bookingList.stream().filter(booking -> booking.getItem().getId().equals(item.getId()))
                    .filter(booking -> LocalDateTime.now().isBefore(booking.getStart()))
                    .sorted(new BookingStartComparator()).findFirst().orElse(null);
            List<Comment> commentListByItem = commentList.stream()
                    .filter(comment -> comment.getItem().getId().equals(item.getId())).toList();

            ItemWithBookingDatesDto itemWithBookingDatesDto = itemWithBookingDatesDtoMapper
                    .toItemWithBookingDatesDto(item, lastBooking, nextBooking, commentListByItem);
            itemWithBookingDatesDtoList.add(itemWithBookingDatesDto);
        }

        return itemWithBookingDatesDtoList;
    }

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        List<Booking> bookingList = bookingRepository.getBookingListByBookerIdAndItemIdAndState(userId, itemId,
                new ArrayList<>(List.of(BookingStatus.APPROVED, BookingStatus.CANCELED)));
        if (bookingList.isEmpty()) {
            throw new InvalidRequestException("Пользователь с userId = " + userId + " не брал вещь с itemId = " + itemId);
        }
        if (bookingList.stream()
                .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now())).toList().isEmpty()) {
            throw new InvalidRequestException("Пользователь с userId = " + userId + " не закончил аренду вещи с itemId = " + itemId);
        }

        Optional<User> author = userRepository.findById(userId);
        if (author.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + userId);
        }
        Item item = getItemOrThrowNotFoundException(itemId);
        Comment comment = commentDtoMapper.fromCommentDto(commentDto);
        comment.setAuthor(author.get());
        comment.setItem(item);
        Comment addedComment = commentRepository.save(comment);
        return commentDtoMapper.toCommentDto(addedComment);
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

    public Item getItemOrThrowNotFoundException(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findItemById(itemId);
        if (optionalItem.isEmpty()) {
            throw new NotFoundException("Не найдена вещь с itemId = " + itemId);
        }
        return optionalItem.get();
    }
}
