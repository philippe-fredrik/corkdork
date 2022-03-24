package se.iths.corkdork.dtos;

import se.iths.corkdork.entity.ColorEntity;
import se.iths.corkdork.entity.CountryEntity;
import se.iths.corkdork.entity.GrapeEntity;

public class Wine {

    private Long id;
    private String name;
    private CountryEntity country;
    private GrapeEntity grape;
    private ColorEntity color;

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

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public GrapeEntity getGrape() {
        return grape;
    }

    public void setGrape(GrapeEntity grape) {
        this.grape = grape;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }
}
