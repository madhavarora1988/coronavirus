package com.madhav.covid.services;

import com.madhav.covid.models.Covid;
import com.madhav.covid.models.CovidCSV;
import com.madhav.covid.repositories.CovidCSVRepository;
import com.madhav.covid.repositories.CovidRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CovidCSVService {

    @Autowired
    private CovidCSVRepository repository;

    @Autowired
    private CovidRepository covidRepository;

    private String COVID_URL  = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    public void getCovidData() throws IOException {

        URL url = new URL(COVID_URL);
        InputStream is = url.openStream();
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        String currentDate = getCurrentDate();

        List<CovidCSV> covidCSVList = new ArrayList<>();
        Reader in = new StringReader(result);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            String latitude = record.get("Lat");
            String longitude = record.get("Long");
            String confirmedCases = record.get(currentDate);

            CovidCSV covidCSV = CovidCSV.builder()
                    .state(state)
                    .country(country)
                    .latitude(latitude)
                    .longitude(longitude)
                    .confirmedCases(Integer.parseInt(confirmedCases)).build();

            covidCSVList.add(covidCSV);
            repository.saveAndFlush(covidCSV);

        }

        aggregateConfirmedCases(covidCSVList);
    }

    private void aggregateConfirmedCases(List<CovidCSV> covidCSVList){

        Map<String, Covid> covidMap = new HashMap<>();

        covidCSVList.forEach(value -> {
            String country = value.getCountry();

            if(!covidMap.containsKey(country)){

                covidMap.put(country, Covid.builder()
                .country(country)
                .latitude(value.getLatitude())
                .longitude(value.getLongitude())
                .confirmedCases(value.getConfirmedCases())
                .build());
            }else{
                Covid existingCovid = covidMap.get(country);
                existingCovid.setConfirmedCases(existingCovid.getConfirmedCases() +  value.getConfirmedCases());
            }
        });

        covidMap.values().forEach(covidRepository::saveAndFlush);
    }

    public List<Covid> getData(){
        return covidRepository.findAll();
    }


    private String getCurrentDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        return dateTimeFormatter.format(now);
    }
}
