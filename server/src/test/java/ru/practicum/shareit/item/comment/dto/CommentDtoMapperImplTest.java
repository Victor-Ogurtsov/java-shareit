package ru.practicum.shareit.item.comment.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

class CommentDtoMapperImplTest {

    CommentDtoMapperImpl commentDtoMapper = new CommentDtoMapperImpl();

    @Test
    void toCommentDto_shouldReturnCommentDto() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("text");
        Item item = new Item();
        item.setId(1L);
        comment.setItem(item);
        User user = new User();
        user.setId(1L);
        user.setName("name");
        comment.setAuthor(user);

        CommentDto commentDto = commentDtoMapper.toCommentDto(comment);

        Assertions.assertEquals(commentDto.getId(), comment.getId());
        Assertions.assertEquals(commentDto.getText(), comment.getText());
        Assertions.assertEquals(commentDto.getItemDto().getId(), comment.getItem().getId());
        Assertions.assertEquals(commentDto.getAuthorName(), comment.getAuthor().getName());
        Assertions.assertEquals(commentDto.getCreated(), comment.getCreated());
    }

    @Test
    void toCommentDto_shouldReturnNullWhenCommentEqualsNull() {
        CommentDto commentDto = commentDtoMapper.toCommentDto(null);

        Assertions.assertNull(commentDto);
    }

    @Test
    void fromCommentDto_shouldReturnComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("text");

        Comment comment = commentDtoMapper.fromCommentDto(commentDto);

        Assertions.assertEquals(commentDto.getText(), comment.getText());
    }

    @Test
    void fromCommentDto_shouldReturnNullWhenCommentDtoEqualsNull() {
        Comment comment = commentDtoMapper.fromCommentDto(null);

        Assertions.assertNull(comment);
    }

    @Test
    void itemToItemDto_shouldReturnNullWhenItemEqualsNull() {
        ItemDto itemDto = commentDtoMapper.itemToItemDto(null);

        Assertions.assertNull(itemDto);
    }

    @Test
    void itemToItemDto_shouldReturnItemDto() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Название");
        item.setDescription("Описание");
        item.setAvailable(true);
        User user = new User();
        user.setId(1L);
        item.setOwner(user);
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        item.setRequest(itemRequest);

        ItemDto itemDto = commentDtoMapper.itemToItemDto(item);
        System.out.println("itemDto : " + itemDto);

        Assertions.assertEquals(itemDto.getId(), item.getId());
        Assertions.assertEquals(itemDto.getName(), item.getName());
        Assertions.assertEquals(itemDto.getDescription(), item.getDescription());
        Assertions.assertEquals(itemDto.getAvailable(), item.getAvailable());
        Assertions.assertEquals(itemDto.getOwner().getId(), item.getOwner().getId());
        Assertions.assertEquals(itemDto.getRequestId(), item.getRequest().getId());
    }

    @Test
    void itemDtoToItem_shouldReturnNullWhenItemDtoEqualsNull() {
        Item item = commentDtoMapper.itemDtoToItem(null);

        Assertions.assertNull(item);
    }

    @Test
    void itemDtoToItem_shouldReturnItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Название");
        itemDto.setDescription("Описание");
        itemDto.setAvailable(true);
        Item item = commentDtoMapper.itemDtoToItem(itemDto);

        Assertions.assertEquals(item.getName(), itemDto.getName());
        Assertions.assertEquals(item.getDescription(), itemDto.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemDto.getAvailable());
    }

    @Test
    void userToUserDto_shouldReturnNullWhenUserEqualsNull() {
        UserDto userDto = commentDtoMapper.userToUserDto(null);

        Assertions.assertNull(userDto);
    }

    @Test
    void userToUserDto_shouldReturnUserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email");

        UserDto userDto = commentDtoMapper.userToUserDto(user);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void userDtoToUser_shouldReturnNullWhenUserDtoEqualsNull() {
        User user = commentDtoMapper.userDtoToUser(null);

        Assertions.assertNull(user);
    }

    @Test
    void userDtoToUser_shouldReturnUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("name");
        userDto.setEmail("email");

        User user = commentDtoMapper.userDtoToUser(userDto);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }
}