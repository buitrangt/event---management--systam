package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.MenuRequest;
import org.aibles.eventmanagementsystem.dto.response.MenuResponse;
import org.aibles.eventmanagementsystem.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    Page<MenuResponse> getAllMenus(Pageable pageable);

    List<MenuResponse> searchMenus(String keyword);

    MenuResponse createMenu(MenuRequest request);

    MenuResponse getMenuById(String id);

    MenuResponse updateMenu(String id, MenuRequest request);

    void deleteMenu(String id);
}
