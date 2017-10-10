package com.affinitas.filter.model.api;

import java.util.Objects;

public class Person {

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

    //jackson
    public Person() {
    }


    private Person(Builder builder) {
        this.id = builder.id;
        this.age = builder.age;
        this.jobTitle = builder.jobTitle;
        this.heightInCm = builder.heightInCm;
        this.mainPhoto = builder.mainPhoto;
        this.compatibilityScore = builder.compatibilityScore;
        this.contactsExchanged = builder.contactsExchanged;
        this.favourite = builder.favourite;
        this.religion = builder.religion;
        this.displayName = builder.displayName;
        this.city = builder.city;
    }

    public String getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Integer getHeightInCm() {
        return heightInCm;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public Float getCompatibilityScore() {
        return compatibilityScore;
    }

    public Integer getContactsExchanged() {
        return contactsExchanged;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public String getReligion() {
        return religion;
    }

    public String getDisplayName() {
        return displayName;
    }

    public City getCity() {
        return city;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

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

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAge(Integer age) {
            this.age = age;
            return this;
        }

        public Builder withJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public Builder withHeightInCm(Integer heightInCm) {
            this.heightInCm = heightInCm;
            return this;
        }

        public Builder withMainPhoto(String mainPhoto) {
            this.mainPhoto = mainPhoto;
            return this;
        }

        public Builder withCompatibilityScore(Float compatibilityScore) {
            this.compatibilityScore = compatibilityScore;
            return this;
        }

        public Builder withContactsExchanged(Integer contactsExchanged) {
            this.contactsExchanged = contactsExchanged;
            return this;
        }

        public Builder withFavourite(Boolean favourite) {
            this.favourite = favourite;
            return this;
        }

        public Builder withReligion(String religion) {
            this.religion = religion;
            return this;
        }

        public Builder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder withCity(City city) {
            this.city = city;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

}
