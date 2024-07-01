package org.aibles.eventmanagementsystem.controller;

import jakarta.validation.Valid;
import org.aibles.eventmanagementsystem.dto.request.ItemSpecialRequest;
import org.aibles.eventmanagementsystem.dto.response.ItemSpecialResponse;
import org.aibles.eventmanagementsystem.service.ItemSpecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item-specials")
public class ItemSpecialController {

    private final ItemSpecialService itemSpecialService;

    @Autowired
    public ItemSpecialController(ItemSpecialService itemSpecialService) {
        this.itemSpecialService = itemSpecialService;
    }

    @PostMapping
    public ResponseEntity<ItemSpecialResponse> createItemSpecial(@Valid @RequestBody ItemSpecialRequest itemSpecialRequest) {
        ItemSpecialResponse createdItemSpecial = itemSpecialService.createItemSpecial(itemSpecialRequest);
        return ResponseEntity.ok(createdItemSpecial);
    }

    @GetMapping
    public ResponseEntity<Page<ItemSpecialResponse>> getAllItemSpecials(Pageable pageable) {
        Page<ItemSpecialResponse> itemSpecials = itemSpecialService.getAllItemSpecials(pageable);
        return ResponseEntity.ok(itemSpecials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemSpecialResponse> getItemSpecialById(@PathVariable String id) {
        ItemSpecialResponse itemSpecial = itemSpecialService.getItemSpecialById(id);
        return ResponseEntity.ok(itemSpecial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemSpecialResponse> updateItemSpecial(@PathVariable String id, @Valid @RequestBody ItemSpecialRequest itemSpecialRequest) {
        ItemSpecialResponse updatedItemSpecial = itemSpecialService.updateItemSpecial(id, itemSpecialRequest);
        return ResponseEntity.ok(updatedItemSpecial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemSpecial(@PathVariable String id) {
        itemSpecialService.deleteItemSpecial(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemSpecialResponse>> searchItemSpecials(@RequestParam String keyword) {
        List<ItemSpecialResponse> itemSpecials = itemSpecialService.searchItemSpecials(keyword);
        return ResponseEntity.ok(itemSpecials);
    }
}