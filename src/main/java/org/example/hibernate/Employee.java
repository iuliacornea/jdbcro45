package org.example.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Employee {


    @Id
    private Integer id;

    private String name;

    private String occupation;

    private Date birthdate;



}
