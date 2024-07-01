package org.aibles.eventmanagementsystem.controller;

import jakarta.validation.Valid;
import org.aibles.eventmanagementsystem.dto.request.ItemRequest;
import org.aibles.eventmanagementsystem.dto.response.ItemResponse;
import org.aibles.eventmanagementsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@Valid @RequestBody ItemRequest itemRequest) {
        ItemResponse createdItem = itemService.createItem(itemRequest);
        return ResponseEntity.ok(createdItem);
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAllItems(Pageable pageable) {
        Page<ItemResponse> items = itemService.getAllItems(pageable);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable String id) {
        ItemResponse item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable String id, @Valid @RequestBody ItemRequest itemRequest) {
        ItemResponse updatedItem = itemService.updateItem(id, itemRequest);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemResponse>> searchItems(@RequestParam String keyword) {
        List<ItemResponse> items = itemService.searchItems(keyword);
        return ResponseEntity.ok(items);
    }
}
