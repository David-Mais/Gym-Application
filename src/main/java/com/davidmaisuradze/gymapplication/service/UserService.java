package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;

public interface UserService {
    boolean login(CredentialsDto credentialsDto);
    boolean changePassword(String username, PasswordChangeDto passwordChangeDto);
}
