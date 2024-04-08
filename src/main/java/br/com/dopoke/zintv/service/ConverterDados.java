package br.com.dopoke.zintv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados implements IConverteDados {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> T obterDados(String json, Class<T> classe) {
		T retorno = null;
		try {
			retorno = mapper.readValue(json, classe);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return retorno;
	}
}
