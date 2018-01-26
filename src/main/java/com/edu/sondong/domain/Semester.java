package com.edu.sondong.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Semester.
 */
@Entity
@Table(name = "semester")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "semester_name")
    private String semesterName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "total_week")
    private Integer totalWeek;

    @OneToMany(mappedBy = "semester")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Week> weeks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public Semester semesterName(String semesterName) {
        this.semesterName = semesterName;
        return this;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Semester startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Semester endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalWeek() {
        return totalWeek;
    }

    public Semester totalWeek(Integer totalWeek) {
        this.totalWeek = totalWeek;
        return this;
    }

    public void setTotalWeek(Integer totalWeek) {
        this.totalWeek = totalWeek;
    }

    public Set<Week> getWeeks() {
        return weeks;
    }

    public Semester weeks(Set<Week> weeks) {
        this.weeks = weeks;
        return this;
    }

    public Semester addWeek(Week week) {
        this.weeks.add(week);
        week.setSemester(this);
        return this;
    }

    public Semester removeWeek(Week week) {
        this.weeks.remove(week);
        week.setSemester(null);
        return this;
    }

    public void setWeeks(Set<Week> weeks) {
        this.weeks = weeks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Semester semester = (Semester) o;
        if (semester.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), semester.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Semester{" +
            "id=" + getId() +
            ", semesterName='" + getSemesterName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", totalWeek=" + getTotalWeek() +
            "}";
    }
}
