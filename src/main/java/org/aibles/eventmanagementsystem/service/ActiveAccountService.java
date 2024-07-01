package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.ActiveAccountRequest;
import org.aibles.eventmanagementsystem.dto.response.ActiveAccountResponse;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;

public interface ActiveAccountService {
   ActiveAccountResponse  activeAccount(ActiveAccountRequest request);
}
