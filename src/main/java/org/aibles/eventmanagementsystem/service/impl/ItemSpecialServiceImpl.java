package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.dto.request.ItemSpecialRequest;
import org.aibles.eventmanagementsystem.dto.response.ItemSpecialResponse;
import org.aibles.eventmanagementsystem.entity.ItemSpecial;

import org.aibles.eventmanagementsystem.exception.exception.ItemSpecialException;
import org.aibles.eventmanagementsystem.repository.ItemSpecialRepository;
import org.aibles.eventmanagementsystem.service.ItemSpecialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemSpecialServiceImpl implements ItemSpecialService {

    private final ItemSpecialRepository itemSpecialRepository;

    public ItemSpecialServiceImpl(ItemSpecialRepository itemSpecialRepository) {
        this.itemSpecialRepository = itemSpecialRepository;
    }

    @Override
    public Page<ItemSpecialResponse> getAllItemSpecials(Pageable pageable) {
        log.info("getAllItemSpecials with pagination and sorting");
        Page<ItemSpecial> itemSpecials = itemSpecialRepository.findAll(pageable);
        return itemSpecials.map(this::convertToItemSpecialResponse);
    }

    @Override
    public List<ItemSpecialResponse> searchItemSpecials(String keyword) {
        log.info("Searching item specials with keyword: {}", keyword);
        List<ItemSpecial> itemSpecials = itemSpecialRepository.findByItemIdContaining(keyword);
        return itemSpecials.stream()
                .map(this::convertToItemSpecialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemSpecialResponse createItemSpecial(ItemSpecialRequest request) {
        log.info("Creating new item special: {}", request);
        ItemSpecial itemSpecial = convertToItemSpecial(request);
        itemSpecial.setCreatedAt(LocalDateTime.now());
        itemSpecial.setLastUpdatedAt(LocalDateTime.now());
        ItemSpecial savedItemSpecial = itemSpecialRepository.save(itemSpecial);
        return convertToItemSpecialResponse(savedItemSpecial);
    }

    @Override
    public ItemSpecialResponse getItemSpecialById(String id) {
        log.info("Getting item special by id: {}", id);
        ItemSpecial itemSpecial = itemSpecialRepository.findById(id)
                .orElseThrow(() -> new ItemSpecialException("Item special not found with id: " + id));
        return convertToItemSpecialResponse(itemSpecial);
    }

    @Override
    public ItemSpecialResponse updateItemSpecial(String id, ItemSpecialRequest request) {
        log.info("Updating item special with id: {}", id);
        ItemSpecial itemSpecial = itemSpecialRepository.findById(id)
                .orElseThrow(() -> new  ItemSpecialException("Item special not found with id: " + id));
        itemSpecial.setItemId(request.getItemId());
        itemSpecial.setStartDate(request.getStartDate());
        itemSpecial.setEndDate(request.getEndDate());
        itemSpecial.setPrice(request.getPrice());
        itemSpecial.setLastUpdatedAt(LocalDateTime.now());
        itemSpecial.setLastUpdatedBy(request.getLastUpdatedBy());
        ItemSpecial updatedItemSpecial = itemSpecialRepository.save(itemSpecial);
        return convertToItemSpecialResponse(updatedItemSpecial);
    }

    @Override
    public void deleteItemSpecial(String id) {
        log.info("Deleting item special with id: {}", id);
        ItemSpecial itemSpecial = itemSpecialRepository.findById(id)
                .orElseThrow(() -> new  ItemSpecialException("Item special not found with id: " + id));
        itemSpecialRepository.delete(itemSpecial);
    }

    private ItemSpecial convertToItemSpecial(ItemSpecialRequest request) {
        ItemSpecial itemSpecial = new ItemSpecial();
        itemSpecial.setId(request.getId());
        itemSpecial.setItemId(request.getItemId());
        itemSpecial.setStartDate(request.getStartDate());
        itemSpecial.setEndDate(request.getEndDate());
        itemSpecial.setPrice(request.getPrice());
        itemSpecial.setCreatedAt(request.getCreatedAt());
        itemSpecial.setCreatedBy(request.getCreatedBy());
        itemSpecial.setLastUpdatedAt(request.getLastUpdatedAt());
        itemSpecial.setLastUpdatedBy(request.getLastUpdatedBy());
        return itemSpecial;
    }

    private ItemSpecialResponse convertToItemSpecialResponse(ItemSpecial itemSpecial) {
        ItemSpecialResponse itemSpecialResponse = new ItemSpecialResponse();
        itemSpecialResponse.setId(itemSpecial.getId());
        itemSpecialResponse.setItemId(itemSpecial.getItemId());
        itemSpecialResponse.setStartDate(itemSpecial.getStartDate());
        itemSpecialResponse.setEndDate(itemSpecial.getEndDate());
        itemSpecialResponse.setPrice(itemSpecial.getPrice());
        itemSpecialResponse.setCreatedAt(itemSpecial.getCreatedAt());
        itemSpecialResponse.setCreatedBy(itemSpecial.getCreatedBy());
        itemSpecialResponse.setLastUpdatedAt(itemSpecial.getLastUpdatedAt());
        itemSpecialResponse.setLastUpdatedBy(itemSpecial.getLastUpdatedBy());
        return itemSpecialResponse;
    }
}