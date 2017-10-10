package com.affinitas.filter.model.internal;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "people")
public class Person implements Serializable {

    private String id;
    private Integer age;
    private String jobTitle;
    private Integer heightInCm;
    private String mainPhoto;
    private Float compatibilityScore;
    private Integer contactsExchanged;
    private Boolean favourite;
    private String religion;
    private String displayName;
    private City city;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(Integer heightInCm) {
        this.heightInCm = heightInCm;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public Float getCompatibilityScore() {
        return compatibilityScore;
    }

    public void setCompatibilityScore(Float compatibilityScore) {
        this.compatibilityScore = compatibilityScore;
    }

    public Integer getContactsExchanged() {
        return contactsExchanged;
    }

    public void setContactsExchanged(Integer contactsExchanged) {
        this.contactsExchanged = contactsExchanged;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(age, person.age) &&
                Objects.equals(jobTitle, person.jobTitle) &&
                Objects.equals(heightInCm, person.heightInCm) &&
                Objects.equals(mainPhoto, person.mainPhoto) &&
                Objects.equals(compatibilityScore, person.compatibilityScore) &&
                Objects.equals(contactsExchanged, person.contactsExchanged) &&
                Objects.equals(favourite, person.favourite) &&
                Objects.equals(religion, person.religion) &&
                Objects.equals(displayName, person.displayName) &&
                Objects.equals(city, person.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, jobTitle, heightInCm, mainPhoto, compatibilityScore, contactsExchanged, favourite, religion, displayName, city);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", jobTitle='" + jobTitle + '\'' +
                ", heightInCm=" + heightInCm +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", compatibilityScore=" + compatibilityScore +
                ", contactsExchanged=" + contactsExchanged +
                ", favourite=" + favourite +
                ", religion='" + religion + '\'' +
                ", displayName='" + displayName + '\'' +
                ", city=" + city +
                '}';
    }
}
