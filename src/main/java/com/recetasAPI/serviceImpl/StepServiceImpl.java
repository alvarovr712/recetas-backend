package com.recetasAPI.serviceImpl;

import com.recetasAPI.repository.StepRepository;
import com.recetasAPI.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;
}
