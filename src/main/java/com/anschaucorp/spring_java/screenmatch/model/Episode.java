package com.anschaucorp.spring_java.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episode;
    private Double rate;
    private LocalDate releaseDate;

    public Episode(Integer season, DadosEpisodio dadosEpisodio) {
        this.season = season;
        this.title = dadosEpisodio.title();
        this.episode = dadosEpisodio.episode();
        try{
            this.rate = Double.valueOf(dadosEpisodio.rate());
        } catch (NumberFormatException ex){
            this.rate = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(dadosEpisodio.releaseDate());
        } catch (DateTimeParseException ex) {
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return
                "season=" + season +
                ", title='" + title + '\'' +
                ", episode=" + episode +
                ", rate=" + rate +
                ", releaseDate=" + releaseDate
               ;
    }
}
