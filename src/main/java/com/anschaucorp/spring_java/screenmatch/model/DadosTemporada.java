package com.anschaucorp.spring_java.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer season,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodioList) {
}
