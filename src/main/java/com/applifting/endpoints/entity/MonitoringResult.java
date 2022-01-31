package com.applifting.endpoints.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
public class MonitoringResult implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Timestamp checkDate;
    private Integer httpStatusCode;
    private String payload;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private MonitoredEndpoint endpoint;

    public MonitoringResult() {
        this.checkDate = new Timestamp(new Date().getTime());
    }

    public MonitoringResult(Integer httpStatusCode, final String payload) {
        this.checkDate = new Timestamp(new Date().getTime());
        this.httpStatusCode = httpStatusCode;
        this.payload = payload;
    }

    public MonitoringResult(Integer id, Integer httpStatusCode, final String payload) {
        this.id = id;
        this.checkDate = new Timestamp(new Date().getTime());
        this.httpStatusCode = httpStatusCode;
        this.payload = payload;
    }

    public MonitoringResult(Integer httpStatusCode, final String payload, final MonitoredEndpoint endpoint) {
        this.checkDate = new Timestamp(new Date().getTime());
        this.httpStatusCode = httpStatusCode;
        this.payload = payload;
        this.endpoint = endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MonitoringResult that = (MonitoringResult) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public Timestamp getCheckDate() {
        return checkDate;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getPayload() {
        return payload;
    }

    public MonitoredEndpoint getEndpoint() {
        return endpoint;
    }

    public void setParent(final MonitoredEndpoint endpoint) {
        this.endpoint = endpoint;
    }
}
