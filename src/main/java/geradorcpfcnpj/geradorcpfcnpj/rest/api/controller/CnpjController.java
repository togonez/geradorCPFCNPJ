package geradorcpfcnpj.geradorcpfcnpj.rest.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CnpjController {

	@GetMapping(path = "/api/gerar-cnpj/{filial}/{pontuacao}")
	public Map<String, String> gerarCNPJ(@PathVariable("filial") String filial,
			@PathVariable("pontuacao") String pontuacao) {

		if (filial.equals("1")) { // filial 1 - matriz
			if (pontuacao.equals("SIM")) {

				String aux_cnpj = gerarRadical();
				String aux_cnpj1 = aux_cnpj + Long.toString(gerarDV(Long.valueOf(aux_cnpj)));
				String aux_cnpj2 = aux_cnpj1 + Long.toString(gerarDV(Long.valueOf(aux_cnpj1)));
				String cnpj = gerarPontuacao(String.valueOf(aux_cnpj2), pontuacao);

				Map<String, String> map = new HashMap<>();

				map.put("cnpj", String.valueOf(cnpj));
				map.put("status", "Válido");
				map.put("pontuacao", pontuacao);
				map.put("filial", filial + " - Matriz");
				return map;

			} else if (pontuacao.equals("NAO")) {

				String aux_cnpj = gerarRadical();
				String aux_cnpj1 = aux_cnpj + Long.toString(gerarDV(Long.valueOf(aux_cnpj)));
				String aux_cnpj2 = aux_cnpj1 + Long.toString(gerarDV(Long.valueOf(aux_cnpj1)));
				String cnpj = gerarPontuacao(String.valueOf(aux_cnpj2), pontuacao);

				Map<String, String> map = new HashMap<>();
				map.put("status", "filial 1 e parametro NAO");
				map.put("cnpj", String.valueOf(cnpj));
				return map;

			} else {

				Map<String, String> map = new HashMap<>();
				map.put("status", "Parâmetro [pontuacao] informado inválido");
				return map;
			}
		} else { // filial > 1 - filial
			if (pontuacao.equals("SIM")) {

				String filialUser = filial;
				String filialSys = String.format("%04d", Integer.parseInt(filialUser));

				if(filialUser.length() > 4) {
					Map<String, String> map = new HashMap<>();

					map.put("erro", "Filial só pode ter 4 dígitos");
					return map;
				} else if(filialUser.equals("0")) {
					Map<String, String> map = new HashMap<>();

					map.put("erro", "Filial não pode ser zero");
					return map;
				}
				
				Random gerador = new Random();
				int cnpj1 = gerador.nextInt(9);
				int cnpj2 = gerador.nextInt(9);
				int cnpj3 = gerador.nextInt(9);
				int cnpj4 = gerador.nextInt(9);
				int cnpj5 = gerador.nextInt(9);
				int cnpj6 = gerador.nextInt(9);
				int cnpj7 = gerador.nextInt(9);
				int cnpj8 = gerador.nextInt(9);

				String aux_cnpj = Integer.toString(cnpj1) + Integer.toString(cnpj2) + Integer.toString(cnpj3)
						+ Integer.toString(cnpj4) + Integer.toString(cnpj5) + Integer.toString(cnpj6)
						+ Integer.toString(cnpj7) + Integer.toString(cnpj8) + filialSys;

				String aux_cnpj1 = aux_cnpj + Long.toString(gerarDV(Long.valueOf(aux_cnpj)));
				String aux_cnpj2 = aux_cnpj1 + Long.toString(gerarDV(Long.valueOf(aux_cnpj1)));

				String cnpj = gerarPontuacao(String.valueOf(aux_cnpj2), pontuacao);

				Map<String, String> map = new HashMap<>();

				map.put("cnpj", String.valueOf(cnpj));
				map.put("status", "Válido");
				map.put("pontuacao", pontuacao);
				map.put("filial", filial + " - Filial");
				return map;

			} else if (pontuacao.equals("NAO")) {
				String filialUser = filial;
				String filialSys = String.format("%04d", Integer.parseInt(filialUser));

				if(filialUser.length() > 4) {
					Map<String, String> map = new HashMap<>();

					map.put("erro", "Filial só pode ter 4 dígitos");
					return map;
				} else if(filialUser.equals("0")) {
					Map<String, String> map = new HashMap<>();

					map.put("erro", "Filial não pode ser zero");
					return map;
				}

				Random gerador = new Random();
				int cnpj1 = gerador.nextInt(9);
				int cnpj2 = gerador.nextInt(9);
				int cnpj3 = gerador.nextInt(9);
				int cnpj4 = gerador.nextInt(9);
				int cnpj5 = gerador.nextInt(9);
				int cnpj6 = gerador.nextInt(9);
				int cnpj7 = gerador.nextInt(9);
				int cnpj8 = gerador.nextInt(9);

				String aux_cnpj = Integer.toString(cnpj1) + Integer.toString(cnpj2) + Integer.toString(cnpj3)
						+ Integer.toString(cnpj4) + Integer.toString(cnpj5) + Integer.toString(cnpj6)
						+ Integer.toString(cnpj7) + Integer.toString(cnpj8) + filialSys;

				String aux_cnpj1 = aux_cnpj + Long.toString(gerarDV(Long.valueOf(aux_cnpj)));
				String aux_cnpj2 = aux_cnpj1 + Long.toString(gerarDV(Long.valueOf(aux_cnpj1)));

				String cnpj = gerarPontuacao(String.valueOf(aux_cnpj2), pontuacao);

				Map<String, String> map = new HashMap<>();

				map.put("cnpj", String.valueOf(cnpj));
				map.put("status", "Válido");
				map.put("pontuacao", pontuacao);
				map.put("filial", filial + " - Filial");
				return map;
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("status", "Parâmetro informado inválido");
				return map;
			}
		}

	}

	@GetMapping(path = "/api/validar-cnpj/{cnpj}")
	public Map<String, String> validarCNPJ(@PathVariable("cnpj") String cnpj) throws Exception {

		String scnpj = String.valueOf(cnpj).replaceAll("\\p{Punct}", "");
		char[] ccnpj = scnpj.toCharArray();

		if (ccnpj.length < 14) {
			Map<String, String> map = new HashMap<>();
			map.put("CNPJ", String.valueOf(cnpj));
			map.put("erro", "CNPJ informado não tem 14 dígitos.");
			System.out.println("Ponto: 11");
			return map;
		} else if (ccnpj.length > 14) {
			Map<String, String> map = new HashMap<>();
			map.put("CNPJ", String.valueOf(cnpj));
			map.put("erro", "CNPJ informado tem mais que 14 dígitos.");
			System.out.println("Ponto: 12");
			return map;
		} else if (ccnpj.toString() == "") {
			Map<String, String> map = new HashMap<>();
			map.put("CNPJ", String.valueOf(cnpj));
			map.put("erro", "CNPJ não informado");
			System.out.println("Ponto: 13");
			return map;
		} else {
			if (validadorCNPJ(cnpj, "NAO").equals("OK")) {
				Map<String, String> map = new HashMap<>();
				map.put("CNPJ", String.valueOf(cnpj));
				map.put("status", "CNPJ válido");
				System.out.println("Ponto: 14");
				return map;
			} else if (validadorCNPJ(cnpj, "NAO").equals("NOK")) {
				Map<String, String> map = new HashMap<>();
				map.put("CNPJ", String.valueOf(cnpj));
				map.put("status", "CNPJ inválido");
				System.out.println("Ponto: 15");
				return map;
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("erro", "Erro interno");
				System.out.println("Ponto: 16");
				return map;
			}
		}
	}

	// funções

	private String validadorCNPJ(String cnpj, String pontuacao) throws Exception {
		try {
			String cnpj_compare1 = cnpj.replaceAll("\\p{Punct}", "");
			String cnpj_compare = cnpj_compare1.substring(0, 12);

			String aux_cnpj = cnpj_compare;
			String aux_cnpj1 = aux_cnpj + Long.toString(gerarDV(Long.valueOf(aux_cnpj)));
			String aux_cnpj2 = aux_cnpj1 + Long.toString(gerarDV(Long.valueOf(aux_cnpj1)));
			String cnpj1 = gerarPontuacao(String.valueOf(aux_cnpj2), pontuacao);

			if (cnpj1.equals("00000000000000") || cnpj1.equals("11111111111111") || cnpj1.equals("22222222222222")
					|| cnpj1.equals("33333333333333") || cnpj1.equals("44444444444444")
					|| cnpj1.equals("55555555555555") || cnpj1.equals("66666666666666")
					|| cnpj1.equals("77777777777777") || cnpj1.equals("88888888888888")
					|| cnpj1.equals("99999999999999")) {
				return "NOK";
			} else if (cnpj1.equals(cnpj_compare1)) {
				return "OK";
			} else {
				return "NOK";
			}

		} catch (Exception e) {
			String exceptionData = e.getMessage();
			return exceptionData;
		}
	}

	private String gerarRadical() {
		Random gerador = new Random();
		int cnpj1 = gerador.nextInt(9);
		int cnpj2 = gerador.nextInt(9);
		int cnpj3 = gerador.nextInt(9);
		int cnpj4 = gerador.nextInt(9);
		int cnpj5 = gerador.nextInt(9);
		int cnpj6 = gerador.nextInt(9);
		int cnpj7 = gerador.nextInt(9);
		int cnpj8 = gerador.nextInt(9);
		int cnpj9 = 0;
		int cnpj10 = 0;
		int cnpj11 = 0;
		int cnpj12 = 1;

		String aux_cnpj = Integer.toString(cnpj1) + Integer.toString(cnpj2) + Integer.toString(cnpj3)
				+ Integer.toString(cnpj4) + Integer.toString(cnpj5) + Integer.toString(cnpj6) + Integer.toString(cnpj7)
				+ Integer.toString(cnpj8) + Integer.toString(cnpj9) + Integer.toString(cnpj10)
				+ Integer.toString(cnpj11) + Integer.toString(cnpj12);

		return aux_cnpj;
	}

	private int gerarDV(long cnpj) {
		int tamanho = String.valueOf(cnpj).length();

		if (tamanho == 12) {
			String parte1 = Long.toString(cnpj).substring(0, 4);
			char[] char_parte1 = parte1.toCharArray();
			int tamanho1 = parte1.length();
			String parte2 = Long.toString(cnpj).substring(4);
			char[] char_parte2 = parte2.toCharArray();
			int tamanho2 = parte2.length();

			int multiplicador1 = 5;
			int multiplicador2 = 9;
			int soma = 0;

			for (int i = 0; i < tamanho1; i++) {
				soma += Character.getNumericValue(char_parte1[i]) * multiplicador1;
				multiplicador1--;
			}

			for (int i = 0; i < tamanho2; i++) {
				soma += Character.getNumericValue(char_parte2[i]) * multiplicador2;
				multiplicador2--;
			}

			int resto = soma % 11;
			return resto > 1 ? 11 - resto : 0;

		} else if (tamanho == 13) {
			String parte1 = Long.toString(cnpj).substring(0, 5);
			char[] char_parte1 = parte1.toCharArray();
			int tamanho1 = parte1.length();
			String parte2 = Long.toString(cnpj).substring(5);
			char[] char_parte2 = parte2.toCharArray();
			int tamanho2 = parte2.length();

			int multiplicador1 = 6;
			int multiplicador2 = 9;
			int soma = 0;

			for (int i = 0; i < tamanho1; i++) {
				soma += Character.getNumericValue(char_parte1[i]) * multiplicador1;
				multiplicador1--;
			}

			for (int i = 0; i < tamanho2; i++) {
				soma += Character.getNumericValue(char_parte2[i]) * multiplicador2;
				multiplicador2--;
			}

			int resto = soma % 11;
			return resto > 1 ? 11 - resto : 0;
		} else {
			return 0;
		}
	}

	private String gerarPontuacao(String cnpj, String pontuacao) {
		String string_cnpj = String.valueOf(cnpj);
		char[] char_cnpj = string_cnpj.toCharArray();
		String cnpj1 = "";

		if (pontuacao.equals("SIM")) {
			for (int i = 0; i < char_cnpj.length; i++) {
				if (i == 2) {
					cnpj1 += "." + char_cnpj[i];
				} else if (i == 5) {
					cnpj1 += "." + char_cnpj[i];
				} else if (i == 8) {
					cnpj1 += "/" + char_cnpj[i];
				} else if (i == 12) {
					cnpj1 += "-" + char_cnpj[i];
				} else {
					cnpj1 += char_cnpj[i];
				}
			}
			return cnpj1;

		} else if (pontuacao.equals("NAO")) {
			for (int i = 0; i < char_cnpj.length; i++) {
				cnpj1 += char_cnpj[i];
			}
			return cnpj1;
		} else {
			return "Erro";
		}
	}

}
