package br.com.dopoke.zintv.principal;

import java.util.Scanner;

import br.com.dopoke.zintv.service.ConsumoAPI;

public class Principal {

	private Scanner leitura = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();

	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=f165582f";

	public void exibeMenu() {
		System.out.println("Digite o nome da serie para busca: ");
		var nomeSerie = leitura.nextLine();

		var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
	}

}
