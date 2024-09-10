package belajar.quarkus.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Person extends PanacheEntity {
    // public String name;
    // public int age;
     @Column(name = "name")
    public String name;
    @Column(name = "age")
    public int age;
}
