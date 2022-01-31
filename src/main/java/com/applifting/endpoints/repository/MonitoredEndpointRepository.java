package com.applifting.endpoints.repository;

import com.applifting.endpoints.entity.MonitoredEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Integer> {

}
