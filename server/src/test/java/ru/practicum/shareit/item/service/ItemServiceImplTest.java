package ru.practicum.shareit.item.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDatesDto;
import ru.practicum.shareit.item.dto.ItemWithBookingDto;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemServiceImplTest {

    private final EntityManager entityManager;
    private final ItemServiceImpl itemService;

    private ItemDto getItemDto(Long i) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("name" + i);
        itemDto.setDescription("description " + i);
        itemDto.setAvailable(true);
        itemDto.setRequestId(2L);
        return itemDto;
    }

    @Test
    void addItem_shouldReturnAddedItem() {
        ItemDto itemDto = getItemDto(3L);
        ItemDto addedItemDto = itemService.addItem(1L, itemDto);

        TypedQuery<Item> typedQuery = entityManager.createQuery("Select i from Item i where i.id = :id", Item.class);
        typedQuery.setParameter("id", addedItemDto.getId());
        Item item = typedQuery.getSingleResult();

        assertThat(addedItemDto.getId(), is(item.getId()));
        assertThat(addedItemDto.getName(), is(item.getName()));
        assertThat(addedItemDto.getDescription(), is(item.getDescription()));
        assertThat(addedItemDto.getAvailable(), is(item.getAvailable()));
        assertThat(addedItemDto.getOwner().getId(), is(item.getOwner().getId()));
        assertThat(addedItemDto.getOwner().getId(), is(item.getOwner().getId()));
        assertThat(addedItemDto.getRequestId(), is(item.getRequest().getId()));
    }

    @Test
    void updateItem_shouldReturnUpdatedItem() {
        ItemDto itemDto = getItemDto(3L);
        ItemDto updatedItemDto = itemService.updateItem(1L, 1L, itemDto);

        TypedQuery<Item> typedQuery = entityManager.createQuery("Select i from Item i where i.id = :id", Item.class);
        typedQuery.setParameter("id", updatedItemDto.getId());
        Item item = typedQuery.getSingleResult();

        assertThat(updatedItemDto.getId(), is(item.getId()));
        assertThat(updatedItemDto.getName(), is(item.getName()));
        assertThat(updatedItemDto.getDescription(), is(item.getDescription()));
        assertThat(updatedItemDto.getAvailable(), is(item.getAvailable()));
        assertThat(updatedItemDto.getOwner().getId(), is(item.getOwner().getId()));
        assertThat(updatedItemDto.getOwner().getId(), is(item.getOwner().getId()));
        assertThat(updatedItemDto.getRequestId(), is(item.getRequest().getId()));
    }

    @Test
    void getItem_shouldReturnItemWithBookingDto() {
        ItemWithBookingDto itemWithBookingDto = itemService.getItem(1L, 1L);

        assertThat(itemWithBookingDto.getId(), is(1L));
        assertThat(itemWithBookingDto.getName(), is("Название 1"));
        assertThat(itemWithBookingDto.getAvailable(), is(true));
        assertThat(itemWithBookingDto.getOwner().getId(), is(1L));
        assertThat(itemWithBookingDto.getLastBooking().getId(), is(1L));
        assertThat(itemWithBookingDto.getNextBooking().getId(), is(3L));
        assertThat(itemWithBookingDto.getComments().size(), is(1));
    }

    @Test
    void getItemListFromUser_shouldReturnItemWithBookingDatesDtoList() {
        List<ItemWithBookingDatesDto> itemWithBookingDatesDtoList = itemService.getItemListFromUser(1L);

        System.out.println("Получили: " + itemWithBookingDatesDtoList);

        assertThat(itemWithBookingDatesDtoList.size(), is(1));
        assertThat(itemWithBookingDatesDtoList.get(0).getId(), is(1L));
        assertThat(itemWithBookingDatesDtoList.get(0).getName(), is("Название 1"));
        assertThat(itemWithBookingDatesDtoList.get(0).getDescription(), is("Описание первой вещи"));
        assertThat(itemWithBookingDatesDtoList.get(0).getAvailable(), is(true));
        assertThat(itemWithBookingDatesDtoList.get(0).getOwner().getId(), is(1L));
        assertThat(itemWithBookingDatesDtoList.get(0).getRequest().getId(), is(2L));
        assertThat(itemWithBookingDatesDtoList.get(0).getRequest().getId(), is(2L));
        assertThat(itemWithBookingDatesDtoList.get(0).getLastBookingStart(), notNullValue());
        assertThat(itemWithBookingDatesDtoList.get(0).getLastBookingEnd(), notNullValue());
        assertThat(itemWithBookingDatesDtoList.get(0).getNextBookingStart(), notNullValue());
        assertThat(itemWithBookingDatesDtoList.get(0).getNextBookingEnd(), notNullValue());
        assertThat(itemWithBookingDatesDtoList.get(0).getComments().size(), is(1));
    }

    @Test
    void addComment_shouldReturnCommentDto() {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Текст комментария");
        commentDto.setCreated(LocalDateTime.now());
        Long userId = 2L;
        Long itemId = 1L;
        CommentDto addedCommentDto = itemService.addComment(userId, itemId, commentDto);

        assertThat(addedCommentDto.getId(), notNullValue());
        assertThat(addedCommentDto.getText(), is(commentDto.getText()));
        assertThat(addedCommentDto.getAuthorName(), is("Игорь"));
        assertThat(addedCommentDto.getCreated(), is(commentDto.getCreated()));
    }

    @Test
    void getItemListBySearch_shouldReturnListWithItemDtoMatchDescription() {
        List<ItemDto> itemDtoList = itemService.getItemListBySearch(2L, "пеРвой веЩи");

        assertThat(itemDtoList.get(0).getDescription(), is("Описание первой вещи"));
    }
}