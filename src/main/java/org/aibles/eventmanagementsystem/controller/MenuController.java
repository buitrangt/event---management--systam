package org.aibles.eventmanagementsystem.controller;

import jakarta.validation.Valid;
import org.aibles.eventmanagementsystem.dto.request.MenuRequest;
import org.aibles.eventmanagementsystem.dto.response.MenuResponse;
import org.aibles.eventmanagementsystem.entity.Menu;
import org.aibles.eventmanagementsystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(@Valid @RequestBody MenuRequest menuRequest) {
        MenuResponse createdMenu = menuService.createMenu(menuRequest);
        return ResponseEntity.ok(createdMenu);
    }

    @GetMapping
    public ResponseEntity<Page<MenuResponse>> getAllMenus(Pageable pageable) {
        Page<MenuResponse> menus = menuService.getAllMenus(pageable);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getMenuById(@PathVariable String id) {
        MenuResponse menu = menuService.getMenuById(id);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable String id, @Valid @RequestBody MenuRequest menuRequest) {
        MenuResponse updatedMenu = menuService.updateMenu(id, menuRequest);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuResponse>> searchMenus(@RequestParam String keyword) {
        List<MenuResponse> menus = menuService.searchMenus(keyword);
        return ResponseEntity.ok(menus);
    }
}
