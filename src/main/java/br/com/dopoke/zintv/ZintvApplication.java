package br.com.dopoke.zintv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.dopoke.model.DadosSeries;
import br.com.dopoke.zintv.service.ConsumoAPI;
import br.com.dopoke.zintv.service.ConverterDados;

@SpringBootApplication
public class ZintvApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ZintvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?i=tt2467372&apikey=f165582f");
		
		System.out.println(json);
		
		ConverterDados conversor = new ConverterDados();
		DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
		
		System.out.println(dados);
		
	}

}
