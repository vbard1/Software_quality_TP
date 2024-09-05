package org.example.sqbackend.services.impl;

import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.repositories.ResponseRepository;
import org.example.sqbackend.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;

    public ResponseServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Response> getResponseByChoice(Choice choice) {
        return responseRepository.findAllByChoice(choice);
    }
}
