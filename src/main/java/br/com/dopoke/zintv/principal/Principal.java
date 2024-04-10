package br.com.dopoke.zintv.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.dopoke.zintv.model.DadosSeries;
import br.com.dopoke.zintv.model.DadosTemporada;
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
	}

}
