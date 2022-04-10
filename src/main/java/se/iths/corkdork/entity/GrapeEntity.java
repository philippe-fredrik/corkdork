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

    public void addWines(WineEntity wine) {
        wines.add(wine);
        wine.setGrape(this);
    }

    public Long getId() {
        return id;
    }

    public GrapeEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GrapeEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getColor() {
        return color;
    }

    public GrapeEntity setColor(String color) {
        this.color = color;
        return this;
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

    public GrapeEntity setCountry(CountryEntity country) {
        this.country = country;
        return this;
    }
}
