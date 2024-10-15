package com.eCart.eCart.services.auth;

import com.eCart.eCart.dto.SignupRequest;
import com.eCart.eCart.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
}
