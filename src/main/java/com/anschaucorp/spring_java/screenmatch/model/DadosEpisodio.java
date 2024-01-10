package com.anschaucorp.spring_java.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.util.IgnorePropertiesUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String title,
                            @JsonAlias("Episode") Integer episode,
                            @JsonAlias("imdbRating") String rate,
                            @JsonAlias("Released") String releaseDate) {
}
