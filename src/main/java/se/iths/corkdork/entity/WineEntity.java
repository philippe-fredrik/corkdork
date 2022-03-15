package se.iths.corkdork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
public class WineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String wineName;

    @ManyToOne
    private CountryEntity country;

    @ManyToOne
    private GrapeEntity grape;

    @OneToOne
    ColorEntity color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }

    @JsonIgnore
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
