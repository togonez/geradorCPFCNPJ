package geradorcpfcnpj.geradorcpfcnpj.rest.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CpfController {

	@GetMapping(path = "/api/status")
	public String status() {
		String status = "online";
		System.out.println("Ponto: 0");
		return status;
	}

	@GetMapping(path = "/api/gerar-cpf/{uf}/{pontuacao}")
	public Map<String, String> gerarCPF(@PathVariable("uf") String uf, @PathVariable("pontuacao") String pontuacao) {

		if (uf.equals("ZZ")) {
			if (pontuacao.equals("SIM")) {

				String aux_cpf = gerarRadical();
				String aux_cpf1 = aux_cpf + Long.toString(gerarDV(Long.valueOf(aux_cpf)));
				String aux_cpf2 = aux_cpf1 + Long.toString(gerarDV(Long.valueOf(aux_cpf1)));
				String cpf = gerarPontuacao(String.valueOf(aux_cpf2), pontuacao);

				Map<String, String> map = retornoSemUF(pontuacao, cpf);

				System.out.println("Ponto: 1");

				return map;
			} else if (pontuacao.equals("NAO")) {
				String aux_cpf = gerarRadical();
				String aux_cpf1 = aux_cpf + Long.toString(gerarDV(Long.valueOf(aux_cpf)));
				String aux_cpf2 = aux_cpf1 + Long.toString(gerarDV(Long.valueOf(aux_cpf1)));
				String cpf = gerarPontuacao(String.valueOf(aux_cpf2), pontuacao);

				Map<String, String> map = retornoSemUF(pontuacao, cpf);

				System.out.println("Ponto: 2");

				return map;
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("erro", "Parâmetro 'pontuacao' informado não encontrado");

				System.out.println("Ponto: 3");

				return map;
			}

		} else {
			String aux_uf = verificaUF(uf);
			if (aux_uf.equals("Erro")) {
				Map<String, String> map = new HashMap<>();
				map.put("erro", "Parâmetro [uf] informado não encontrado");

				System.out.println("Ponto: 4");

				return map;
			}
			String aux_cpf = gerarRadicalUF(Integer.valueOf(aux_uf));
			String aux_cpf1 = aux_cpf + Long.toString(gerarDV(Long.valueOf(aux_cpf)));
			String aux_cpf2 = aux_cpf1 + Long.toString(gerarDV(Long.valueOf(aux_cpf1)));
			String cpf = gerarPontuacao(String.valueOf(aux_cpf2), pontuacao);

			Map<String, String> map = retornoComUF(pontuacao, cpf, uf);

			System.out.println("Ponto: 5");

			return map;
		}
	}

	@GetMapping(path = "/api/validar-cpf/{cpf}")
	public Map<String, String> validarCPF(@PathVariable("cpf") String cpf) throws Exception {
		String scpf = String.valueOf(cpf).replaceAll("\\p{Punct}", "");
		char[] ccpf = scpf.toCharArray();
		

		if (ccpf.length < 11) {
			Map<String, String> map = new HashMap<>();
			map.put("erro", "CPF informado não tem 11 dígitos.");
			System.out.println("Ponto: 6");
			return map;
		} else if (ccpf.length > 11) {
			Map<String, String> map = new HashMap<>();
			map.put("erro", "CPF informado tem mais que 11 dígitos.");
			System.out.println("Ponto: 7");
			return map;
		} else if (ccpf.toString() == "") {
			Map<String, String> map = new HashMap<>();
			map.put("erro", "CPF não informado");
			System.out.println("Ponto: 8");
			return map;
		} else {
			if (validadorCPF(cpf, "NAO").equals("OK")) {
				Map<String, String> map = new HashMap<>();
				map.put("status", "CPF válido");
				System.out.println("Ponto: 9");
				return map;
			} else if (validadorCPF(cpf, "NAO").equals("NOK")) {
				Map<String, String> map = new HashMap<>();
				map.put("status", "CPF inválido");
				System.out.println("Ponto: 10");
				return map;
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("erro", "Erro interno");
				System.out.println("Ponto: 11");
				return map;
				
			}
		}
	}

	// funções
	private String validadorCPF(String cpf, String pontuacao) throws Exception {
		try {
			String cpf_compare1 = cpf.replaceAll("\\p{Punct}", "");		
			String cpf_compare = cpf_compare1.substring(0,9);
			
			String aux_cpf = cpf_compare;
			String aux_cpf1 = aux_cpf + Integer.toString(gerarDV(Integer.valueOf(aux_cpf)));
			String aux_cpf2 = aux_cpf1 + Integer.toString(gerarDV(Integer.valueOf(aux_cpf1)));
			String cpf1 = gerarPontuacao(String.valueOf(aux_cpf2), pontuacao);
			
			if (cpf1.equals("00000000000") || cpf1.equals("11111111111") || cpf1.equals("22222222222") ||cpf1.equals("33333333333") ||cpf1.equals("44444444444") ||cpf1.equals("55555555555") || cpf1.equals("66666666666") || cpf1.equals("77777777777") || cpf1.equals("88888888888") || cpf1.equals("99999999999")) {
				return "NOK";
			} else if (cpf1.equals(cpf_compare1)) {
				return "OK";
			} else {
				return "NOK";
			}

		} catch (Exception e) {
			String exceptionData = e.getMessage();
			return exceptionData;
		}
	}

	private Map<String, String> retornoSemUF(String pontuacao, String cpf) {
		Map<String, String> map = new HashMap<>();
		map.put("cpf", String.valueOf(cpf));
		map.put("status", "Válido");
		map.put("pontuacao", pontuacao);
		map.put("uf", "Infiferente");
		return map;
	}

	private Map<String, String> retornoComUF(String pontuacao, String cpf, String uf) {
		Map<String, String> map = new HashMap<>();
		map.put("cpf", String.valueOf(cpf));
		map.put("status", "Válido");
		map.put("pontuacao", pontuacao);
		map.put("uf", uf);
		return map;
	}

	private String verificaUF(String uf) {
		if (uf.equals("RS")) {
			int dv = 0;
			return Integer.toString(dv);
		} else if (uf.equals("DF") || uf.equals("GO") || uf.equals("MS") || uf.equals("TO")) {
			int dv = 1;
			return Integer.toString(dv);
		} else if (uf.equals("PA") || uf.equals("AM") || uf.equals("AC") || uf.equals("AP") || uf.equals("RO")
				|| uf.equals("RR")) {
			int dv = 2;
			return Integer.toString(dv);
		} else if (uf.equals("CE") || uf.equals("MA") || uf.equals("PI")) {
			int dv = 3;
			return Integer.toString(dv);
		} else if (uf.equals("PE") || uf.equals("RN") || uf.equals("PB") || uf.equals("AL")) {
			int dv = 4;
			return Integer.toString(dv);
		} else if (uf.equals("BA") || uf.equals("SE")) {
			int dv = 5;
			return Integer.toString(dv);
		} else if (uf.equals("MG")) {
			int dv = 6;
			return Integer.toString(dv);
		} else if (uf.equals("RJ") || uf.equals("ES")) {
			int dv = 7;
			return Integer.toString(dv);
		} else if (uf.equals("SP")) {
			int dv = 8;
			return Integer.toString(dv);
		} else if (uf.equals("PR") || uf.equals("SC")) {
			int dv = 9;
			return Integer.toString(dv);
		} else {
			return "Erro";
		}
	}

	private String gerarPontuacao(String cpf, String pontuacao) {
		String scpf = String.valueOf(cpf);
		char[] ccpf = scpf.toCharArray();
		String cpf1 = "";

		if (pontuacao.equals("SIM")) {
			for (int i = 0; i < ccpf.length; i++) {
				if (i == 3) {
					cpf1 += "." + ccpf[i];
				} else if (i == 6) {
					cpf1 += "." + ccpf[i];
				} else if (i == 9) {
					cpf1 += "-" + ccpf[i];
				} else {
					cpf1 += ccpf[i];
				}
			}
			return cpf1;

		} else if (pontuacao.equals("NAO")) {
			for (int i = 0; i < ccpf.length; i++) {
				cpf1 += ccpf[i];
			}
			return cpf1;
		} else {
			return "Erro";
		}
	}

	private int gerarDV(long cpf) {
		String scpf = String.valueOf(cpf);
		char[] ccpf = scpf.toCharArray();

		int tamanho = String.valueOf(cpf).length();
		int multiplicador = tamanho + 1;
		int soma = 0;

		for (int i = 0; i < ccpf.length; i++) {
			soma += Character.getNumericValue(ccpf[i]) * multiplicador;
			multiplicador--;
		}

		int resto = soma % 11;
		return resto > 1 ? 11 - resto : 0;
	}

	private String gerarRadical() {
		Random gerador = new Random();
		int cpf1 = gerador.nextInt(9);
		int cpf2 = gerador.nextInt(9);
		int cpf3 = gerador.nextInt(9);
		int cpf4 = gerador.nextInt(9);
		int cpf5 = gerador.nextInt(9);
		int cpf6 = gerador.nextInt(9);
		int cpf7 = gerador.nextInt(9);
		int cpf8 = gerador.nextInt(9);
		int cpf9 = gerador.nextInt(9);

		String aux_cpf = Integer.toString(cpf1) + Integer.toString(cpf2) + Integer.toString(cpf3)
				+ Integer.toString(cpf4) + Integer.toString(cpf5) + Integer.toString(cpf6) + Integer.toString(cpf7)
				+ Integer.toString(cpf8) + Integer.toString(cpf9);

		return aux_cpf;
	}

	private String gerarRadicalUF(int uf) {
		Random gerador = new Random();
		int cpf1 = gerador.nextInt(9);
		int cpf2 = gerador.nextInt(9);
		int cpf3 = gerador.nextInt(9);
		int cpf4 = gerador.nextInt(9);
		int cpf5 = gerador.nextInt(9);
		int cpf6 = gerador.nextInt(9);
		int cpf7 = gerador.nextInt(9);
		int cpf8 = gerador.nextInt(9);
		int cpf9 = uf;

		String aux_cpf = Integer.toString(cpf1) + Integer.toString(cpf2) + Integer.toString(cpf3)
				+ Integer.toString(cpf4) + Integer.toString(cpf5) + Integer.toString(cpf6) + Integer.toString(cpf7)
				+ Integer.toString(cpf8) + Integer.toString(cpf9);

		return aux_cpf;
	}
}
