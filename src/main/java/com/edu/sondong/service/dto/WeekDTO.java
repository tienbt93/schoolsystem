package com.edu.sondong.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Week entity.
 */
public class WeekDTO implements Serializable {

    private Long id;

    private String weekName;

    private Long semesterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeekDTO weekDTO = (WeekDTO) o;
        if(weekDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weekDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeekDTO{" +
            "id=" + getId() +
            ", weekName='" + getWeekName() + "'" +
            "}";
    }
}
