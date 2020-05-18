package com.madhav.covid.repositories;

import com.madhav.covid.models.Covid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidRepository extends JpaRepository<Covid, Integer> {
}
