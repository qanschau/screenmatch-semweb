package com.anschaucorp.spring_java.screenmatch.principal;

import com.anschaucorp.spring_java.screenmatch.model.DadosEpisodio;
import com.anschaucorp.spring_java.screenmatch.model.DadosSerie;
import com.anschaucorp.spring_java.screenmatch.model.DadosTemporada;
import com.anschaucorp.spring_java.screenmatch.model.Episode;
import com.anschaucorp.spring_java.screenmatch.service.ConsumoAPI;
import com.anschaucorp.spring_java.screenmatch.service.ConverteDados;
import org.springframework.format.annotation.DateTimeFormat;

import javax.sound.midi.Soundbank;
import javax.swing.text.DateFormatter;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e2ab1d32";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    public void exibeMenu(){
        var option = -1;
        while (option != 0) {
            var menu = """
                    ++++++++++<<<< MENU >>>>++++++++++
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries pesquisadas
                                    
                    0 - Sair
                    """;

            System.out.println(menu);
            option = leitura.nextInt();
            leitura.nextLine();

            switch (option) {
                case 1:
                    searchSeriesWeb();
                    break;
                case 2:
                    searchEpisodePerSeries();
                    break;
                case 3:
                    listarSeries();
                    break;
                case 0:
                    System.out.println("Byeeeeee!");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private DadosSerie getDadosSerie(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoAPI.obterDados(URL+ nomeSerie.replace(" ","+") + API_KEY);
        DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void searchSeriesWeb(){
        DadosSerie dadosSerie = getDadosSerie();
        dadosSeries.add(dadosSerie);
        System.out.println(dadosSerie);
    }

    private void searchEpisodePerSeries(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalSeasons(); i++){
            var json = consumoAPI.obterDados(URL + dadosSerie.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeries(){
        dadosSeries.forEach(System.out::println);
    }
}

//OLD CODING - STREAMS AND LISTS
//System.out.println("Digite o nome da série para busca:");
//        var nomeSerie = leitura.nextLine();
//        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+")+API_KEY);
//        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
//        System.out.println(dadosSerie);
//
//        List<DadosTemporada> temporadas = new ArrayList<>();
//
//		for (int i = 1; i<=dadosSerie.totalSeasons(); i++){
//			json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+")+"&season="+i+API_KEY);
//			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
//			temporadas.add(dadosTemporada);
//		}
//		temporadas.forEach(System.out::println);
//
////        for( int i = 0; i < dadosSerie.totalSeasons(); i++){
////            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodioList();
////            for (int j = 0; j < episodiosTemporada.size(); j++){
////                System.out.println(episodiosTemporada.get(j).title());
////            }
////        }
//
//        temporadas.forEach(t -> t.episodioList().forEach(e -> System.out.println(e.title())));
//
//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodioList().stream())
//                .collect(Collectors.toList());
//
////        System.out.println("\nTop 10 episodes");
////        dadosEpisodios.stream()
////                .filter(e -> !e.rate().equalsIgnoreCase("N/A"))
////                //.peek(e -> System.out.println("Filter 1(N/A): " + e))
////                .sorted(Comparator.comparing(DadosEpisodio::rate).reversed())
////                //.peek(e -> System.out.println("Sort: " + e))
////                .limit(10)
////                //.peek(e -> System.out.println("Limit: " + e))
////                .map(e -> e.title().toUpperCase())
////                //.peek(e -> System.out.println("Map:" + e))
////                .forEach(System.out::println);
//
//        List<Episode> episodes = temporadas.stream()
//                .flatMap(t -> t.episodioList().stream()
//                        .map(d -> new Episode(t.season(), d)))
//                        .collect(Collectors.toList());
//
//        episodes.forEach(System.out::println);
//
////        System.out.println("Digite uma parte to title do episodio: ");
////
////        var partOfTitle = leitura.nextLine();
////
////        Optional<Episode> searchedEpisode = episodes.stream()
////                .filter(e -> e.getTitle().toUpperCase().contains(partOfTitle.toUpperCase()))
////                .findFirst();
////        if(searchedEpisode.isPresent()){
////            System.out.println("Episodio encontrado");
////            System.out.println("Temporada " + searchedEpisode.get().getSeason());
////        } else {
////            System.out.println("Episodio não encontrado");
////        }
////
////        System.out.println("A partir de que ano você quer ver os episódios: ");
////        var ano = leitura.nextInt();
////        leitura.nextLine();
////
////        LocalDate dataBusca = LocalDate.of(ano,1,1);
////
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////
////        episodes.stream()
////                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(dataBusca))
////                .forEach(e -> System.out.println(
////                        "Sesson: " + e.getSeason() +
////                                " - Episode: " + e.getEpisode() +
////                                " - Release date: " + e.getReleaseDate().format(formatter)
////                ));
//
//        Map<Integer, Double> rateBySeason = episodes.stream()
//                .filter(e -> e.getRate() > 0.0)
//                .collect(Collectors.groupingBy(Episode::getSeason,
//                        Collectors.averagingDouble(Episode::getRate)));
//        System.out.println(rateBySeason);
//
//        DoubleSummaryStatistics est = episodes.stream()
//                .filter(e -> e.getRate() > 0.0)
//                .collect(Collectors.summarizingDouble(Episode::getRate));
//        System.out.println(est);
//
//        System.out.println("Average: " + est.getAverage());
//        System.out.println("Melhor avaliação: " + est.getMax() );
//        episodes.stream().filter(e -> e.getRate() == est.getMax()).forEach(e -> System.out.println(e.getTitle()));
//        System.out.println("Pior avaliação: " + est.getMin());
//        episodes.stream().filter(e -> e.getRate() == est.getMin()).forEach(e -> System.out.println(e.getTitle()));
//        System.out.println("Quantidade: " + est.getCount());
//        System.out.println("----------------");