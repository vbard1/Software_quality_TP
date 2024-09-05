package org.example.sqbackend.services;

import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.models.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ResponseService {

    List<Response> getResponseByChoice(Choice choice);
}
