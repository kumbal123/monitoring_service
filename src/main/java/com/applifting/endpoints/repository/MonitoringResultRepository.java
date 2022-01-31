package com.applifting.endpoints.repository;

import com.applifting.endpoints.entity.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Integer> {

}
