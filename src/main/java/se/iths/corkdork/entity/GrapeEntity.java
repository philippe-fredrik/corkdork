package se.iths.corkdork.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GrapeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "grape",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<WineEntity> wines = new HashSet<>();

    @ManyToOne
    CountryEntity country;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<WineEntity> getWines() {
        return wines;
    }

    public void setWines(Set<WineEntity> wines) {
        this.wines = wines;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}
