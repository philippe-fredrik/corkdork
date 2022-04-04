package se.iths.corkdork.dtos;

import javax.validation.constraints.NotBlank;

public class Wine {

    private Long id;
    @NotBlank(message = "name cannot be empty.")
    private String name;
    @NotBlank
    private Country country;
    @NotBlank
    private Grape grape;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Grape getGrape() {
        return grape;
    }

    public void setGrape(Grape grape) {
        this.grape = grape;
    }

}
