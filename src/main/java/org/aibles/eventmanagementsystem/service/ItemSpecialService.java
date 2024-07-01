package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.ItemSpecialRequest;
import org.aibles.eventmanagementsystem.dto.response.ItemSpecialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemSpecialService {
    Page<ItemSpecialResponse> getAllItemSpecials(Pageable pageable);

    List<ItemSpecialResponse> searchItemSpecials(String keyword);

    ItemSpecialResponse createItemSpecial(ItemSpecialRequest request);

    ItemSpecialResponse getItemSpecialById(String id);

    ItemSpecialResponse updateItemSpecial(String id, ItemSpecialRequest request);

    void deleteItemSpecial(String id);
}