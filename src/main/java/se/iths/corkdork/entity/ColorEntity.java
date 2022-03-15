package se.iths.corkdork.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String color;

    @OneToOne
    WineEntity wine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public WineEntity getWine() {
        return wine;
    }

    public void setWine(WineEntity wine) {
        this.wine = wine;
    }


}
