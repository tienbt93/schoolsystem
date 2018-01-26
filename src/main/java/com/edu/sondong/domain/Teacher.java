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

import com.edu.sondong.domain.enumeration.TrainTitle;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @Column(name = "salary_rate")
    private Long salaryRate;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "train_title")
    private TrainTitle trainTitle;

    @ManyToOne
    private School school;

    @OneToMany(mappedBy = "teacher")
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

    public String getFirstName() {
        return firstName;
    }

    public Teacher firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Teacher lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Teacher email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Teacher phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public Teacher hireDate(Instant hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalaryRate() {
        return salaryRate;
    }

    public Teacher salaryRate(Long salaryRate) {
        this.salaryRate = salaryRate;
        return this;
    }

    public void setSalaryRate(Long salaryRate) {
        this.salaryRate = salaryRate;
    }

    public String getTitle() {
        return title;
    }

    public Teacher title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TrainTitle getTrainTitle() {
        return trainTitle;
    }

    public Teacher trainTitle(TrainTitle trainTitle) {
        this.trainTitle = trainTitle;
        return this;
    }

    public void setTrainTitle(TrainTitle trainTitle) {
        this.trainTitle = trainTitle;
    }

    public School getSchool() {
        return school;
    }

    public Teacher school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Teacher lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Teacher addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setTeacher(this);
        return this;
    }

    public Teacher removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setTeacher(null);
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
        Teacher teacher = (Teacher) o;
        if (teacher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salaryRate=" + getSalaryRate() +
            ", title='" + getTitle() + "'" +
            ", trainTitle='" + getTrainTitle() + "'" +
            "}";
    }
}
