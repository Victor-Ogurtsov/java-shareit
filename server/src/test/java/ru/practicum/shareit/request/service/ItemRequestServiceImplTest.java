package ru.practicum.shareit.request.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestServiceImplTest {

    private final ItemRequestService itemRequestService;
    private final EntityManager entityManager;

    private ItemRequestDto getItemRequestDto(Long i) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("description " + i);
        itemRequestDto.setRequestor(new UserDto());
        itemRequestDto.setCreated(LocalDateTime.now());
        itemRequestDto.setItems(new ArrayList<>());
        return itemRequestDto;
    }

    @Test
    void addItemRequest_shouldReturnAddedItemRequest() {
        ItemRequestDto itemRequestDto = getItemRequestDto(1L);
        itemRequestService.addItemRequest(1L, itemRequestDto);

        TypedQuery<ItemRequest> typedQuery = entityManager.createQuery("Select ir from ItemRequest ir where ir.description = :description", ItemRequest.class);
        typedQuery.setParameter("description", itemRequestDto.getDescription());
        ItemRequest itemRequest = typedQuery.getSingleResult();

        assertThat(itemRequest.getId(), notNullValue());
        assertThat(itemRequest.getDescription(), is(itemRequestDto.getDescription()));
        assertThat(itemRequest.getCreated(), is(itemRequestDto.getCreated()));
    }

    @Test
    void getItemRequestById_shouldReturnItemRequestFromDb() {
        TypedQuery<ItemRequest> typedQuery = entityManager.createQuery("Select ir from ItemRequest ir where ir.id = 1", ItemRequest.class);
        ItemRequest itemRequest = typedQuery.getSingleResult();

        ItemRequestDto itemRequestDto = itemRequestService.getItemRequestById(1L, 1L);

        assertThat(itemRequestDto.getId(), is(1L));
        assertThat(itemRequestDto.getDescription(), is(itemRequest.getDescription()));
        assertThat(itemRequestDto.getRequestor().getId(), is(itemRequestDto.getRequestor().getId()));
    }

    @Test
    void getAllItemRequestForRequestor_shouldReturnAllItemRequestWithRequestorId() {
        Long requestorId = 1L;

        TypedQuery<ItemRequest> typedQuery = entityManager
                .createQuery("Select ir from ItemRequest ir JOIN ir.requestor as u where u.id = :id", ItemRequest.class);
        typedQuery.setParameter("id", requestorId);
        List<ItemRequest> itemRequestList = typedQuery.getResultList();

        List<ItemRequestDto> itemRequestDtoList = itemRequestService.getAllItemRequestForRequestor(requestorId);

        assertThat(itemRequestDtoList.size(), is(itemRequestList.size()));
    }

    @Test
    void getAllItemRequest_shouldReturnAllItemRequest() {
        TypedQuery<ItemRequest> typedQuery = entityManager
                .createQuery("Select ir from ItemRequest ir", ItemRequest.class);
        List<ItemRequest> itemRequestList = typedQuery.getResultList();

        List<ItemRequestDto> itemRequestDtoList = itemRequestService.getAllItemRequest(1L);

        assertThat(itemRequestDtoList.size(), is(itemRequestList.size()));
    }
}