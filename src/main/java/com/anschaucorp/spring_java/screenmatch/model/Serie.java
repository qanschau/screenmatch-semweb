package com.anschaucorp.spring_java.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Optional;
import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double imdbRating;
    private Category genre;
    private String actors;
    private String poster;
    private String plot;

    public Serie(DadosSerie dadosSerie){
        this.title = dadosSerie.title();
        this.totalSeasons = dadosSerie.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.valueOf(dadosSerie.imdbRating())).orElse(0);
        this.genre = Category.fromString(dadosSerie.genre().split(",")[0].trim());
        this.actors = dadosSerie.actors();
        this.poster = dadosSerie.poster();
        this.plot = dadosSerie.plot();
    }
}
