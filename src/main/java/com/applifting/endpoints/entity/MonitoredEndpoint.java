package com.applifting.endpoints.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.sql.Timestamp;
import java.util.stream.Collectors;

import static java.lang.Integer.max;

@Entity
public class MonitoredEndpoint implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;
    private Timestamp creationDate;
    private Timestamp latestCheckDate;

    @OneToMany(mappedBy = "endpoint", cascade = CascadeType.ALL)
    private Collection<MonitoringResult> results;

    public MonitoredEndpoint() {
        this.creationDate = new Timestamp(new Date().getTime());
        this.latestCheckDate = null;
        this.results = new HashSet<>();
    }

    public MonitoredEndpoint(final String name, final String url) {
        this.name = name;
        this.url = url;
        this.creationDate = new Timestamp(new Date().getTime());
        this.latestCheckDate = null;
        this.results = new HashSet<>();
    }

    public MonitoredEndpoint(Integer id, final String name, final String url) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.creationDate = new Timestamp(new Date().getTime());
        this.latestCheckDate = null;
        this.results = new HashSet<>();
    }


    public MonitoredEndpoint(final String name, final String url, final Collection<MonitoringResult> results) {
        this.name = name;
        this.url = url;
        this.creationDate = new Timestamp(new Date().getTime());
        this.latestCheckDate = null;
        this.results = new HashSet<>(results);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MonitoredEndpoint that = (MonitoredEndpoint) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Timestamp getLatestCheckDate() {
        return latestCheckDate;
    }

    public Collection<MonitoringResult> getResults() {
        return results;
    }

    public Collection<MonitoringResult> listLastTenResults() {
        return results.stream().skip(max(results.size() - 10, 0)).collect(Collectors.toList());
    }

    public void setLatestCheckDate(Timestamp latestCheckDate) {
        this.latestCheckDate = latestCheckDate;
    }

    public void addResult(final MonitoringResult result) {
        result.setParent(this);
        results.add(result);
    }
}
