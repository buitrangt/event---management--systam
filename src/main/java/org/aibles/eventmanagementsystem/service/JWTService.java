package org.aibles.eventmanagementsystem.service;


import org.aibles.eventmanagementsystem.entity.Account;

public interface JWTService {
    String generateAccessToken(Account account);
    String generateRefreshToken(Account account);
    String parseToken(String token);
}
