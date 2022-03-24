package se.iths.corkdork.mappers;

import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.WineEntity;

public class WineMapper {

    public static Wine wineEntityToWine(WineEntity wineEntity){

        var wine = new Wine();
        wine.setName(wineEntity.getName());
        wine.setCountry(wineEntity.getCountry());
        wine.setColor(wineEntity.getColor());
        wine.setGrape(wineEntity.getGrape());

        return wine;
    }

    public static WineEntity wineToWineEntity(Wine wine) {

        var wineE = new WineEntity();
        wineE.setName(wine.getName());
        wineE.setCountry(wine.getCountry());
        wineE.setGrape(wine.getGrape());
        wineE.setColor(wine.getColor());

        return wineE;
    }
}
