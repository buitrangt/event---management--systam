package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.dto.request.MenuRequest;
import org.aibles.eventmanagementsystem.dto.response.MenuResponse;
import org.aibles.eventmanagementsystem.entity.Menu;
import org.aibles.eventmanagementsystem.exception.exception.MenuException;

import org.aibles.eventmanagementsystem.repository.MenuRepository;
import org.aibles.eventmanagementsystem.service.MenuService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Page<MenuResponse> getAllMenus(Pageable pageable) {
        log.info("getAllMenus with pagination and sorting");
        Page<Menu> menus = menuRepository.findAll(pageable);
        return menus.map(this::convertToMenuResponse);
    }

    @Override
    public List<MenuResponse> searchMenus(String keyword) {
        log.info("Searching menus with keyword: {}", keyword);
        List<Menu> menus = menuRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return menus.stream()
                .map(this::convertToMenuResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuResponse createMenu(MenuRequest request) {
        log.info("Creating new menu: {}", request);
        Menu menu = convertToMenu(request);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setLastUpdatedAt(LocalDateTime.now());
        Menu savedMenu = menuRepository.save(menu);
        return convertToMenuResponse(savedMenu);
    }

    @Override
    public MenuResponse getMenuById(String id) {
        log.info("Getting menu by id: {}", id);
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuException("Menu not found with id: " + id));
        return convertToMenuResponse(menu);
    }

    @Override
    public MenuResponse updateMenu(String id, MenuRequest request) {
        log.info("Updating menu with id: {}", id);
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuException("Menu not found with id: " + id));
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setLastUpdatedAt(LocalDateTime.now());
        menu.setLastUpdatedBy(request.getLastUpdatedBy());
        Menu updatedMenu = menuRepository.save(menu);
        return convertToMenuResponse(updatedMenu);
    }

    @Override
    public void deleteMenu(String id) {
        log.info("Deleting menu with id: {}", id);
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuException("Menu not found with id: " + id));
        menuRepository.delete(menu);
    }

    private Menu convertToMenu(MenuRequest request) {
        Menu menu = new Menu();
        menu.setId(request.getId());
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setCreatedAt(request.getCreatedAt());
        menu.setCreatedBy(request.getCreatedBy());
        menu.setLastUpdatedAt(request.getLastUpdatedAt());
        menu.setLastUpdatedBy(request.getLastUpdatedBy());
        return menu;
    }

    private MenuResponse convertToMenuResponse(Menu menu) {
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setDescription(menu.getDescription());
        menuResponse.setCreatedAt(menu.getCreatedAt());
        menuResponse.setCreatedBy(menu.getCreatedBy());
        menuResponse.setLastUpdatedAt(menu.getLastUpdatedAt());
        menuResponse.setLastUpdatedBy(menu.getLastUpdatedBy());
        return menuResponse;
    }
}