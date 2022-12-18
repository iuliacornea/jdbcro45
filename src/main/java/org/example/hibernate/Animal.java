package org.example.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "animal")
public class Animal {

    @Id
    private int id;

    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    public Animal(int id, String name, String species, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.yearOfBirth = yearOfBirth;
    }
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Animal.class)
            .buildSessionFactory();

//        sessionFactory.close();

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}