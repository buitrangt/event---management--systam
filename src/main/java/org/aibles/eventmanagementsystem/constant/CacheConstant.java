package org.aibles.eventmanagementsystem.constant;

import java.time.Duration;

public class CacheConstant {

    private CacheConstant() {}

    public static final String OTP_ACTIVE_ACCOUNT_KEY = "OTP_ACTIVE_ACCOUNT_KEY";
    public static final String FAILED_OTP_ATTEMPT_KEY = "FAILED_OTP_ATTEMPT_KEY";
    public static final String OTP_TTL_KEY = "OTP_TTL_KEY";
    public static final int OTP_TTL_MINUTES = 3;
    public static final long LOCK_TIME = Duration.ofMinutes(1).getSeconds(); //


    public static final String UNLOCK_TIME_KEY = "unlockTime:";


    public static final String FAILED_PASSWORD_ATTEMPT_KEY = "failedPasswordAttempt:";


    public static final String ACCESS_TOKEN_KEY = "accessToken";


    public static final String REFRESH_TOKEN_KEY = "refreshToken";
    public static final String OTP_HASH_KEY = "active_otp";
    public static final String BAD_REQUEST = "invalid_request";
    public static final String ACCESS_TOKEN_HASH_KEY = "accessTokenHashKey";
    public static final String REFRESH_TOKEN_HASH_KEY = "refreshTokenHashKey";


    public static final int FIVE_FAILED_ATTEMPTS = 5;
    public static final int TEN_FAILED_ATTEMPTS = 10;
    public static final int FIFTEEN_FAILED_ATTEMPTS = 15;


    public static final long FIVE_MINUTES = 5 * 60;
    public static final long TEN_MINUTES = 10 * 60;
    public static final long FIFTEEN_MINUTES = 15 * 60;// 15 phút


    public static final String OTP_KEY = "OTP_KEY";
    public static final String OTP_FAILED_UNLOCK_TIME_KEY ="OTP_FAILED_UNLOCK_TIME_KEY";

    public static final String RESET_PASSWORD_OTP_KEY ="RESET_PASSWORD_OTP_KEY";
    public static final String RESET_PASSWORD_KEY = "RESET_PASSWORD_KEY";


}
