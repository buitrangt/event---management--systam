package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.dto.request.ItemRequest;
import org.aibles.eventmanagementsystem.dto.response.ItemResponse;
import org.aibles.eventmanagementsystem.entity.Item;

import org.aibles.eventmanagementsystem.exception.exception.ItemException;
import org.aibles.eventmanagementsystem.repository.ItemRepository;
import org.aibles.eventmanagementsystem.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Page<ItemResponse> getAllItems(Pageable pageable) {
        log.info("getAllItems with pagination and sorting");
        Page<Item> items = itemRepository.findAll(pageable);
        return items.map(this::convertToItemResponse);
    }

    @Override
    public ItemResponse createItem(ItemRequest request) {
        log.info("Creating new item: {}", request);
        Item item = convertToItem(request);
        item.setCreatedAt(LocalDateTime.now());
        item.setLastUpdatedAt(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        return convertToItemResponse(savedItem);
    }

    @Override
    public ItemResponse getItemById(String id) {
        log.info("Getting item by id: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemException("Item not found with id: " + id));
        return convertToItemResponse(item);
    }

    @Override
    public ItemResponse updateItem(String id, ItemRequest request) {
        log.info("Updating item with id: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemException("Item not found with id: " + id));
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setLastUpdatedAt(LocalDateTime.now());
        item.setLastUpdatedBy(request.getLastUpdatedBy());
        Item updatedItem = itemRepository.save(item);
        return convertToItemResponse(updatedItem);
    }

    @Override
    public void deleteItem(String id) {
        log.info("Deleting item with id: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemException("Item not found with id: " + id));
        itemRepository.delete(item);
    }

    @Override
    public List<ItemResponse> searchItems(String keyword) {
        log.info("Searching items with keyword: {}", keyword);
        List<Item> items = itemRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return items.stream()
                .map(this::convertToItemResponse)
                .toList();
    }



    private Item convertToItem(ItemRequest request) {
        Item item = new Item();
        item.setId(request.getId());
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setCreatedAt(request.getCreatedAt());
        item.setCreatedBy(request.getCreatedBy());
        item.setLastUpdatedAt(request.getLastUpdatedAt());
        item.setLastUpdatedBy(request.getLastUpdatedBy());
        return item;
    }

    private ItemResponse convertToItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setName(item.getName());
        itemResponse.setDescription(item.getDescription());
        itemResponse.setPrice(item.getPrice());
        itemResponse.setCreatedAt(item.getCreatedAt());
        itemResponse.setCreatedBy(item.getCreatedBy());
        itemResponse.setLastUpdatedAt(item.getLastUpdatedAt());
        itemResponse.setLastUpdatedBy(item.getLastUpdatedBy());
        return itemResponse;
    }
}
