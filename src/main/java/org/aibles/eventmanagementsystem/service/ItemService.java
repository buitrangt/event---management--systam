package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.ItemRequest;
import org.aibles.eventmanagementsystem.dto.response.ItemResponse;
import org.aibles.eventmanagementsystem.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    Page<ItemResponse> getAllItems(Pageable pageable);

    List<ItemResponse> searchItems(String keyword);


    ItemResponse createItem(ItemRequest request);

    ItemResponse getItemById(String id);

    ItemResponse updateItem(String id, ItemRequest request);

    void deleteItem(String id);
}