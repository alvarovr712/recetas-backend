package com.recetasAPI.serviceImpl;

import com.recetasAPI.repository.SessionRepository;
import com.recetasAPI.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;
}
