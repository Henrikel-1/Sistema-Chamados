package com.example.base.service;

import com.example.base.dto.LoginReqDto;
import com.example.base.dto.LoginRespDto;

public interface AuthService {

    String login(LoginReqDto dto);
}
