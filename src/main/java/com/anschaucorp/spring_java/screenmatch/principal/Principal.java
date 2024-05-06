package com.anschaucorp.spring_java.screenmatch.principal;

import com.anschaucorp.spring_java.screenmatch.model.DadosEpisodio;
import com.anschaucorp.spring_java.screenmatch.model.DadosSerie;
import com.anschaucorp.spring_java.screenmatch.model.DadosTemporada;
import com.anschaucorp.spring_java.screenmatch.model.Episode;
import com.anschaucorp.spring_java.screenmatch.service.ConsumoAPI;
import com.anschaucorp.spring_java.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=e2ab1d32";

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConverteDados converteDados = new ConverteDados();

    private Scanner leitura = new Scanner(System.in);

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca:");
        var nomeSerie = leitura.nextLine();
        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+")+API_KEY);
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dadosSerie.totalSeasons(); i++){
			json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+")+"&season="+i+API_KEY);
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for( int i = 0; i < dadosSerie.totalSeasons(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodioList();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).title());
//            }
//        }

        temporadas.forEach(t -> t.episodioList().forEach(e -> System.out.println(e.title())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodioList().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episodes");
        dadosEpisodios.stream()
                .filter(e -> !e.rate().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::rate).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = temporadas.stream()
                .flatMap(t -> t.episodioList().stream()
                        .map(d -> new Episode(t.season(), d)))
                        .collect(Collectors.toList());

        episodes.forEach(System.out::println);

    }
}
