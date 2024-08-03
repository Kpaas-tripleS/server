package com.tripleS.server.user.service;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}
