package se.iths.corkdork.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "country",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<WineEntity> wines = new HashSet<>();

    @OneToMany(mappedBy = "country",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private Set<GrapeEntity> grapes = new HashSet<>();


    public void addWine(WineEntity wine) {
        wines.add(wine);
        wine.setCountry(this);
    }

    public void addGrape(GrapeEntity grape) {
        grapes.add(grape);
        grape.setCountry(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return name;
    }

    public void setCountryName(String name) {
        this.name = name;
    }

    public Set<WineEntity> getWines() {
        return wines;
    }

    public void setWines(Set<WineEntity> wines) {
        this.wines = wines;
    }

    public Set<GrapeEntity> getGrapes() {
        return grapes;
    }

    public void setGrapes(Set<GrapeEntity> grapes) {
        this.grapes = grapes;
    }


}
