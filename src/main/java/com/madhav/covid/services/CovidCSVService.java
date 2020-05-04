package com.madhav.covid.services;

import com.madhav.covid.models.CovidCSV;
import com.madhav.covid.repositories.CovidCSVRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Service
public class CovidCSVService {

    @Autowired
    private CovidCSVRepository repository;

    private String COVID_URL  = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    public void getCovidData() throws IOException {

        URL url = new URL(COVID_URL);
        InputStream is = url.openStream();
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        // System.out.println("value :" + result);

        Reader in = new StringReader(result);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            String latitude = record.get("Lat");
            String longitude = record.get("Long");
            String confirmedCases = record.get("4/26/20");

            CovidCSV covidCSV = CovidCSV.builder()
                    .state(state)
                    .country(country)
                    .latitude(latitude)
                    .longitude(longitude)
                    .confirmedCases(Integer.parseInt(confirmedCases)).build();

            repository.saveAndFlush(covidCSV);

        }




    }

    public List<CovidCSV> getData(){
        return repository.findAll();
    }
}
