package se.iths.corkdork.dtos;

import se.iths.corkdork.entity.ColorEntity;
import se.iths.corkdork.entity.CountryEntity;
import se.iths.corkdork.entity.GrapeEntity;

public record Wine (Long id, String name, CountryEntity countryEntity, GrapeEntity grapeEntity, ColorEntity colorEntity) {}
