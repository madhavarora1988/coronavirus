package com.madhav.covid.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CovidCSV {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String state;
    private String country;
    private String latitude;
    private String longitude;
    private Integer confirmedCases;

}
