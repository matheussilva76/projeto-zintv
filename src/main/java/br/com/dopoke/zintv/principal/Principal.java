package br.com.dopoke.zintv.principal;

import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import br.com.dopoke.zintv.model.DadosEpisodio;
import br.com.dopoke.zintv.model.DadosSeries;
import br.com.dopoke.zintv.model.DadosTemporada;
import br.com.dopoke.zintv.model.Eps;
import br.com.dopoke.zintv.service.ConsumoAPI;
import br.com.dopoke.zintv.service.ConverterDados;

public class Principal {

	private Scanner leitura = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private ConverterDados conversor = new ConverterDados();

	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String SEASON = "&season=";
	private final String API_KEY = "&apikey=f165582f";

	public void exibeMenu() {

		System.out.println("Digite o nome da serie para busca: ");
		var nomeSerie = leitura.nextLine();

		var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
		
		DadosSeries dadosbb = conversor.obterDados(json, DadosSeries.class);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosbb.totalTemporadas(); i++) {
			json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + SEASON + i + API_KEY);
			DadosTemporada dadostp = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadostp);
		}
		temporadas.forEach(System.out::println);
		temporadas.forEach(t -> t.episodeos().forEach(e -> System.out.println(e.titulo())));

		/*List<String> nomes = Arrays.asList("Jacque", "Theuzindopoke");
		nomes.stream().sorted().limit(3).filter(s -> s.startsWith("T")).map(s -> s.toLowerCase()).forEach(System.out::println);*/

		List<DadosEpisodio> dadosEpisodios = temporadas.stream()
				.flatMap(t -> t.episodeos().stream())
				.collect(Collectors.toList());

		System.out.println("\n Top five episodeos");
		dadosEpisodios.stream()
				.filter(dadosEpisodio -> !dadosEpisodio.avaliacao().contains("N/A"))
				.sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
				.limit(5)
				.forEach(System.out::println);

		List<Eps> eps = temporadas.stream()
				.flatMap(t -> t.episodeos().stream()
						.filter(dadosEpisodio -> !dadosEpisodio.avaliacao().contains("N/A"))
						.map(dadosEpisodio -> new Eps(t.numero(), dadosEpisodio))
				).toList();

		eps.forEach(System.out::println);

		System.out.println("A partir de que ano você deseja ver os episodeos? ");
		var ano = leitura.next();
		leitura.nextLine();

		LocalDate dataBusca = LocalDate.of(Integer.parseInt(ano), 1, 1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		eps.stream()
				.filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
				.forEach(e -> System.out.println(
						"Temporada: " + e.getTemporada() +
								" Episodeo: " + e.getTitulo() +
								" Data de lançamento: " + e.getDataLancamento().format(dtf)

				));
	}
}
