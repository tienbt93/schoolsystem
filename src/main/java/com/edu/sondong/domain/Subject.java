package com.edu.sondong.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "total_hours")
    private Integer totalHours;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> lessons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Subject subjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public Subject totalHours(Integer totalHours) {
        this.totalHours = totalHours;
        return this;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Subject lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Subject addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setSubject(this);
        return this;
    }

    public Subject removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setSubject(null);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
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
        Subject subject = (Subject) o;
        if (subject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectName='" + getSubjectName() + "'" +
            ", totalHours=" + getTotalHours() +
            "}";
    }
}
