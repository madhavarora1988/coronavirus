package com.madhav.covid.controllers;

import com.madhav.covid.models.CovidCSV;
import com.madhav.covid.services.CovidCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CovidController {

    @Autowired
    private CovidCSVService service;

    @GetMapping("/covid-data")
    public List<CovidCSV> getCovidData(){

        return service.getData();
    }
}
