package br.com.dopoke.zintv;

import br.com.dopoke.zintv.model.DadosEpisodio;
import br.com.dopoke.zintv.model.DadosTemporada;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.dopoke.zintv.model.DadosSeries;
import br.com.dopoke.zintv.service.ConsumoAPI;
import br.com.dopoke.zintv.service.ConverterDados;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ZintvApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ZintvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();

		var json = consumoAPI.obterDados("https://www.omdbapi.com/?i=tt2467372&apikey=f165582f");
		var jsonbb = consumoAPI.obterDados("https://www.omdbapi.com/?i=tt0903747&apikey=f165582f");
		var jsonep = consumoAPI.obterDados("https://www.omdbapi.com/?i=tt0903747&season=1&episode=2&apikey=f165582f");
		var jsonss = consumoAPI.obterDados("https://www.omdbapi.com/?i=tt090377&season=1&episode=2&apikey=f165582f");
		
		ConverterDados conversor = new ConverterDados();

		DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
		DadosSeries dadosbb = conversor.obterDados(jsonbb, DadosSeries.class);
		DadosEpisodio dadosep = conversor.obterDados(jsonep, DadosEpisodio.class);
		DadosTemporada dadosss = conversor.obterDados(jsonss, DadosTemporada.class);

		System.out.println(dados);
		System.out.println(dadosbb);
		System.out.println(dadosep);

		List<DadosTemporada> temporadas = new ArrayList<>();


		for (int i = 1; i<=dadosbb.totalTemporadas(); i++) {
			json = consumoAPI.obterDados("https://www.omdbapi.com/?i=tt0903747&season=" +i+ "&apikey=f165582f");
			DadosTemporada dadostp = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadostp);
		}
		temporadas.forEach(System.out::println);
	}

}
