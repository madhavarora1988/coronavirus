package com.madhav.covid.repositories;

import com.madhav.covid.models.CovidCSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidCSVRepository  extends JpaRepository<CovidCSV, Integer> {

}
