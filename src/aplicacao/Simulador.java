package aplicacao;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import entidades.DadosHistoricosSelecoes;
import entidades.Grupo;
import entidades.Selecao;
import servico.Partida;

public class Simulador {

	private static Random random = new SecureRandom();

	public List<Grupo> geraGrupos(List<Selecao> selecoes) {

		List<Selecao> listaMutavel = new ArrayList<>();
		listaMutavel.addAll(selecoes);

		short numerador = 65;
		char alfabeto = (char) numerador;

		List<Grupo> result = new ArrayList<>();

		List<Selecao> grupo = new ArrayList<>();
		for (int i = 0; i < selecoes.size(); i++) {

			Selecao selecao = listaMutavel.get(random.nextInt(listaMutavel.size()));
			listaMutavel.remove(selecao);
			grupo.add(selecao);

			if (grupo.size() == 4) {

				alfabeto = (char) numerador++;
				Grupo esteGrupo = new Grupo("Grupo " + alfabeto, grupo);
				result.add(esteGrupo);
				grupo = new ArrayList<>();
			}
		}

		return result;
	}

	public List<Grupo> geraGruposReais(List<Selecao> selecoes) {

		List<Grupo> result = new ArrayList<>();

		Grupo grupoA = new Grupo("Grupo A", Arrays.asList(selecoes.get(0), selecoes.get(1), selecoes.get(2), selecoes.get(3)));
		result.add(grupoA);
		Grupo grupoB = new Grupo("Grupo B", Arrays.asList(selecoes.get(4), selecoes.get(5), selecoes.get(6), selecoes.get(7)));
		result.add(grupoB);
		Grupo grupoC = new Grupo("Grupo C", Arrays.asList(selecoes.get(8), selecoes.get(9), selecoes.get(10), selecoes.get(11)));
		result.add(grupoC);
		Grupo grupoD = new Grupo("Grupo D", Arrays.asList(selecoes.get(12), selecoes.get(13), selecoes.get(14), selecoes.get(15)));
		result.add(grupoD);
		Grupo grupoE = new Grupo("Grupo E", Arrays.asList(selecoes.get(16), selecoes.get(17), selecoes.get(18), selecoes.get(19)));
		result.add(grupoE);
		Grupo grupoF = new Grupo("Grupo F", Arrays.asList(selecoes.get(20), selecoes.get(21), selecoes.get(22), selecoes.get(23)));
		result.add(grupoF);
		Grupo grupoG = new Grupo("Grupo G", Arrays.asList(selecoes.get(24), selecoes.get(25), selecoes.get(26), selecoes.get(27)));
		result.add(grupoG);
		Grupo grupoH = new Grupo("Grupo H", Arrays.asList(selecoes.get(28), selecoes.get(29), selecoes.get(30), selecoes.get(31)));
		result.add(grupoH);

		return result;

	}

	private List<Grupo> geraLadoEsquerdo(List<Grupo> todosOsGrupos) {

		List<Grupo> result = new ArrayList<>();

		for (int i = 0; i < todosOsGrupos.size(); i++) {
			if (i % 2 == 0) {
				result.add(todosOsGrupos.get(i));
			}
		}
		return result;
	}

	private List<Grupo> geraLadoDireito(List<Grupo> todosOsGrupos) {

		List<Grupo> result = new ArrayList<>();

		for (int i = 0; i < todosOsGrupos.size(); i++) {
			if (i % 2 != 0) {
				result.add(todosOsGrupos.get(i));
			}
		}
		return result;
	}

	public List<Grupo> geraEliminatorias(List<Grupo> chaveDeGrupos) {

		short numeroDoGrupo = 1;
		List<Grupo> semiResult = new ArrayList<>();
		List<Selecao> selecoesCampeoes = new ArrayList<>();
		List<Selecao> selecoesSegundos = new ArrayList<>();

		for (Grupo grupo : chaveDeGrupos) {
			selecoesCampeoes.add(grupo.getSelecoes().get(0));
			selecoesSegundos.add(grupo.getSelecoes().get(1));
		}

		for (int i = 0; i < selecoesCampeoes.size(); i += 2) {

			Selecao principal = selecoesCampeoes.get(i);
			Selecao desafiante = selecoesSegundos.get(i + 1);

			Grupo grupo = new Grupo("Grupo " + numeroDoGrupo, List.of(principal, desafiante));
			numeroDoGrupo++;
			semiResult.add(grupo);

			Selecao principalB = selecoesCampeoes.get(i + 1);
			Selecao desafianteB = selecoesSegundos.get(i);

			Grupo grupoB = new Grupo("Grupo " + numeroDoGrupo, List.of(principalB, desafianteB));
			numeroDoGrupo++;
			semiResult.add(grupoB);

		}

		List<Grupo> ladoA = geraLadoEsquerdo(semiResult);
		List<Grupo> ladoB = geraLadoDireito(semiResult);
		List<Grupo> result = new ArrayList<>();
		result.addAll(ladoA);
		result.addAll(ladoB);

		return result;
	}

	private List<Grupo> avancaEliminatorias(List<Selecao> selecoes) {

		List<Selecao> listaMutavel = new ArrayList<>();
		listaMutavel.addAll(selecoes);

		short numeroDoGrupo = 1;
		List<Grupo> result = new ArrayList<>();

		for (int i = 0; i < selecoes.size(); i += 2) {

			Selecao principal = selecoes.get(i);
			Selecao desafiante = selecoes.get(i + 1);

			Grupo grupo = new Grupo("Grupo " + numeroDoGrupo, List.of(principal, desafiante));
			numeroDoGrupo++;
			result.add(grupo);
		}

		return result;

	}

	public void geraJogos(List<Grupo> grupos) {
		grupos.stream().forEach(grupo -> geraJogosDaChave(grupo));
	}

	public List<Grupo> geraJogosDecisivos(List<Grupo> grupos) {

		List<Selecao> result = grupos.stream().map(grupo -> geraJogosEliminatorios(grupo)).collect(Collectors.toList());

		return avancaEliminatorias(result);
	}
	
	public Selecao geraFinal(List<Grupo> selecoesDaFinal) {
		return geraJogosEliminatorios(selecoesDaFinal.get(0));
	}

	private void geraJogosDaChave(Grupo grupo) {

		for (int i = 0; i < grupo.getSelecoes().size(); i++) {
			for (int j = i; j < grupo.getSelecoes().size() - 1; j++) {
				Selecao selecaoPrincipal = grupo.getSelecoes().get(i);
				Selecao selecaoDesafiante = grupo.getSelecoes().get(j + 1);

				Map<Selecao, String> result = Partida.partidaDeGrupo(selecaoPrincipal, selecaoDesafiante);
				atualizaEstatisticas(result, selecaoPrincipal, selecaoDesafiante);
				
				System.out.println(selecaoPrincipal + " " + result.get(selecaoPrincipal) + "x" + result.get(selecaoDesafiante) + " " + selecaoDesafiante);
			}

		}
	}
	
	private void imprimeResultado(Map<Selecao, String> result, Selecao selecaoPrincipal, Selecao selecaoDesafiante) {
		
		boolean tevePenalti = result.get(selecaoPrincipal).length() > 1;
		
		if(!tevePenalti) {
			System.out.println(selecaoPrincipal.getNome() + " " + result.get(selecaoPrincipal) + "x" + result.get(selecaoDesafiante) + " " + selecaoDesafiante.getNome());
		} else {
			String resultadoPrincipal = result.get(selecaoPrincipal);
			String resultadoDesafiante = result.get(selecaoDesafiante);
			System.out.println(selecaoPrincipal.getNome() + " " + resultadoPrincipal.substring(0, 1) + " (" + resultadoPrincipal.substring(3, 5).trim() + ")x(" +
								 resultadoDesafiante.substring(3,  5).trim() + ") " + resultadoDesafiante.substring(0, 1) + " " + selecaoDesafiante.getNome()); 
		}
	}
	
	private Selecao geraJogosEliminatorios(Grupo grupo) {

		Selecao selecaoPrincipal = grupo.getSelecoes().get(0);
		Selecao selecaoDesafiante = grupo.getSelecoes().get(1);

		Map<Selecao, String> result = Partida.partidaEliminatoria(selecaoPrincipal, selecaoDesafiante);
		atualizaEstatisticas(result, selecaoPrincipal, selecaoDesafiante);
		
		imprimeResultado(result, selecaoPrincipal, selecaoDesafiante);

		return encontraVencedor(result, selecaoPrincipal, selecaoDesafiante);
	}

	private Selecao encontraVencedor(Map<Selecao, String> result, Selecao selecaoPrincipal, Selecao selecaoDesafiante) {

		boolean tevePenalti = result.get(selecaoPrincipal).length() > 1;

		int golsSelecao1 = 0;
		int golsSelecao2 = 0;

		if (tevePenalti) {
			List<Integer> golsDePenalti = encontraGolsDePenalti(result.get(selecaoPrincipal), golsSelecao1, golsSelecao2);
			golsSelecao1 = golsDePenalti.get(0);
			golsSelecao2 = golsDePenalti.get(1);
		} else {
			golsSelecao1 = Integer.parseInt(result.get(selecaoPrincipal).substring(0, 1));
			golsSelecao2 = Integer.parseInt(result.get(selecaoDesafiante).substring(0, 1));
		}

		return golsSelecao1 > golsSelecao2 ? selecaoPrincipal : selecaoDesafiante;

	}

	private List<Integer> encontraGolsDePenalti(String resultado, int golsSelecao1, int golsSelecao2) {

		golsSelecao1 = Integer.parseInt(resultado.substring(3, 5).trim());
		if (golsSelecao1 <= 9) {
			golsSelecao2 = Integer.parseInt(resultado.substring(7, 9).trim());
		} else {
			golsSelecao2 = Integer.parseInt(resultado.substring(8, 10).trim());
		}
		return Arrays.asList(golsSelecao1, golsSelecao2);
	}

	private void atualizaEstatisticas(Map<Selecao, String> result, Selecao selecaoPrincipal, Selecao selecaoDesafiante) {
		int golsSelecaoPrincipal = Integer.parseInt(result.get(selecaoPrincipal).substring(0, 1));
		int golsSelecaoDesafiante = Integer.parseInt(result.get(selecaoDesafiante).substring(0, 1));

		selecaoPrincipal.adicionaGolsFeitos(golsSelecaoPrincipal);
		selecaoPrincipal.adicionaGolsSofridos(golsSelecaoDesafiante);

		selecaoDesafiante.adicionaGolsFeitos(golsSelecaoDesafiante);
		selecaoDesafiante.adicionaGolsSofridos(golsSelecaoPrincipal);

		if (golsSelecaoPrincipal > golsSelecaoDesafiante) {
			selecaoPrincipal.adicionaVitoria();
			selecaoDesafiante.adicionaDerrota();
		} else if (golsSelecaoPrincipal == golsSelecaoDesafiante) {
			selecaoPrincipal.adicionaEmpate();
			selecaoDesafiante.adicionaEmpate();
		} else {
			selecaoPrincipal.adicionaDerrota();
			selecaoDesafiante.adicionaVitoria();
		}
	}

	public List<Grupo> geraGrupoDeTerceiroLugar(List<Grupo> quartas, List<Grupo> semifinal) {

		List<Selecao> selecoes = new ArrayList<>();
		quartas.stream().forEach(grupo -> selecoes.addAll(grupo.getSelecoes()));

		Grupo grupo = new Grupo("Grupo 1", selecoes.stream().filter(selecao -> !semifinal.get(0).getSelecoes().contains(selecao))
				.collect(Collectors.toList()));

		return List.of(grupo);
	}
	
	public Selecao getSegundo(Selecao vencedor, List<Grupo> semifinal) {

		return semifinal.get(0).getSelecoes().stream().filter(selecao -> !selecao.getNome().equals(vencedor.getNome()))
				.findFirst().get();
	}

	public void organizaGrupos(List<Grupo> chaveDeGrupos) {
		chaveDeGrupos.stream().forEach(grupo -> Collections.sort(grupo.getSelecoes(), Collections.reverseOrder()));
	}

	public void adicionaDadosHistoricosSelecoes(List<Selecao> selecoes) {

		Map<String, DadosHistoricosSelecoes> listaDeDados = cargaDeDadosHistoricosSelecoes();

		selecoes.stream().forEach(selecao -> selecao.setDados(listaDeDados.getOrDefault(selecao.getNome(),
				new DadosHistoricosSelecoes(selecao.getNome(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))));
	}

	private Map<String, DadosHistoricosSelecoes> cargaDeDadosHistoricosSelecoes() {

		Map<String, DadosHistoricosSelecoes> result = new HashMap<>();

		DadosHistoricosSelecoes dadosDoBrasil = new DadosHistoricosSelecoes("Brasil", 21, 237, 109, 73, 18, 18, 0.7248, 229, 105, 5);
		result.put("Brasil", dadosDoBrasil);
		DadosHistoricosSelecoes dadosDaAlemanha = new DadosHistoricosSelecoes("Alemanha", 19, 221, 109, 67, 20, 22, 0.6758, 226, 125, 4);
		result.put("Alemanha", dadosDaAlemanha);
		DadosHistoricosSelecoes dadosDaArgentina = new DadosHistoricosSelecoes("Argentina", 17, 144, 81, 43, 15, 23, 0.5926, 137, 93, 2);
		result.put("Argentina", dadosDaArgentina);
		DadosHistoricosSelecoes dadosDaFranca = new DadosHistoricosSelecoes("França", 15, 115, 66, 34, 13, 19, 0.5808, 120, 77, 2);
		result.put("França", dadosDaFranca);
		DadosHistoricosSelecoes dadosDaInglaterra = new DadosHistoricosSelecoes("Inglaterra", 15, 108, 69, 29, 21, 19, 0.5217, 91, 64, 1);
		result.put("Inglaterra", dadosDaInglaterra);
		DadosHistoricosSelecoes dadosDaEspanha = new DadosHistoricosSelecoes("Espanha", 15, 105, 63, 30, 15, 18, 0.5556, 99, 72, 1);
		result.put("Espanha", dadosDaEspanha);
		DadosHistoricosSelecoes dadosDaHolanda = new DadosHistoricosSelecoes("Holanda", 10, 93, 50, 27, 12, 11, 0.62, 86, 48, 0);
		result.put("Holanda", dadosDaHolanda);
		DadosHistoricosSelecoes dadosDoUruguai = new DadosHistoricosSelecoes("Uruguai", 13, 84, 56, 24, 12, 20, 0.5, 87, 74, 2);
		result.put("Uruguai", dadosDoUruguai);
		DadosHistoricosSelecoes dadosDaBelgica = new DadosHistoricosSelecoes("Bélgica", 13, 69, 48, 20, 9, 19, 0.4792, 68, 72, 0);
		result.put("Bélgica", dadosDaBelgica);
		DadosHistoricosSelecoes dadosDaServia = new DadosHistoricosSelecoes("Sérvia", 12, 62, 46, 18, 8, 20, 0.4493, 66, 63, 0);
		result.put("Sérvia", dadosDaServia);
		DadosHistoricosSelecoes dadosDoMexico = new DadosHistoricosSelecoes("México", 16, 62, 57, 16, 14, 27, 0.3626, 60, 98, 0);
		result.put("México", dadosDoMexico);
		DadosHistoricosSelecoes dadosDaPolonia = new DadosHistoricosSelecoes("Polônia", 8, 53, 34, 16, 5, 13, 0.5196, 46, 45, 0);
		result.put("Polônia", dadosDaPolonia);
		DadosHistoricosSelecoes dadosDePortugal = new DadosHistoricosSelecoes("Portugal", 7, 48, 30, 14, 6, 10, 0.5333, 49, 35, 0);
		result.put("Portugal", dadosDePortugal);
		DadosHistoricosSelecoes dadosDaSuica = new DadosHistoricosSelecoes("Suíça", 11, 44, 37, 12, 8, 17, 0.3964, 50, 64, 0);
		result.put("Suíça", dadosDaSuica);
		DadosHistoricosSelecoes dadosDaCroacia = new DadosHistoricosSelecoes("Croácia", 5, 37, 23, 11, 4, 8, 0.5362, 35, 26, 0);
		result.put("Croácia", dadosDaCroacia);
		DadosHistoricosSelecoes dadosDaDinamarca = new DadosHistoricosSelecoes("Dinamarca", 5, 32, 20, 9, 5, 6, 0.5333, 30, 26, 0);
		result.put("Dinamarca", dadosDaDinamarca);
		DadosHistoricosSelecoes dadosDosEstadosUnidos = new DadosHistoricosSelecoes("Estados Unidos", 10, 30, 33, 8, 6, 19, 0.303, 37, 62, 0);
		result.put("Estados Unidos", dadosDosEstadosUnidos);
		DadosHistoricosSelecoes dadosDaCoreiaDoSul = new DadosHistoricosSelecoes("Coreia do Sul", 10, 27, 34, 6, 9, 19, 0.2647, 34, 70, 0);
		result.put("Coreia do Sul", dadosDaCoreiaDoSul);
		DadosHistoricosSelecoes dadosDoJapao = new DadosHistoricosSelecoes("Japão", 6, 20, 21, 5, 5, 11, 0.3175, 20, 29, 0);
		result.put("Japão", dadosDoJapao);
		DadosHistoricosSelecoes dadosDaCostaRica = new DadosHistoricosSelecoes("Costa Rica", 5, 20, 18, 5, 5, 8, 0.3704, 19, 28, 0);
		result.put("Costa Rica", dadosDaCostaRica);
		DadosHistoricosSelecoes dadosDosCamaroes = new DadosHistoricosSelecoes("Camarões", 7, 19, 23, 4, 7, 12, 0.2754, 18, 43, 0);
		result.put("Camarões", dadosDosCamaroes);
		DadosHistoricosSelecoes dadosDeGana = new DadosHistoricosSelecoes("Gana", 3, 15, 12, 4, 3, 5, 0.4167, 13, 16, 0);
		result.put("Gana", dadosDeGana);
		DadosHistoricosSelecoes dadosDoEquador = new DadosHistoricosSelecoes("Equador", 3, 13, 10, 4, 1, 5, 0.4333, 10, 11, 0);
		result.put("Equador", dadosDoEquador);
		DadosHistoricosSelecoes dadosDoSenegal = new DadosHistoricosSelecoes("Senegal", 2, 12, 8, 3, 3, 2, 0.5, 11, 10, 0);
		result.put("Senegal", dadosDoSenegal);
		DadosHistoricosSelecoes dadosDoMarrocos = new DadosHistoricosSelecoes("Marrocos", 5, 11, 16, 2, 5, 9, 0.2292, 14, 22, 0);
		result.put("Marrocos", dadosDoMarrocos);
		DadosHistoricosSelecoes dadosDaArabiaSaudita = new DadosHistoricosSelecoes("Arábia Saudita", 5, 11, 16, 3, 2, 11, 0.2292, 11, 39, 0);
		result.put("Arábia Saudita", dadosDaArabiaSaudita);
		DadosHistoricosSelecoes dadosDaTunisia = new DadosHistoricosSelecoes("Tunísia", 5, 10, 15, 2, 4, 9, 0.2222, 13, 25, 0);
		result.put("Tunísia", dadosDaTunisia);
		DadosHistoricosSelecoes dadosDoIra = new DadosHistoricosSelecoes("Irã", 5, 10, 15, 2, 4, 9, 0.2222, 9, 24, 0);
		result.put("Irã", dadosDoIra);
		DadosHistoricosSelecoes dadosDaAustralia = new DadosHistoricosSelecoes("Austrália", 5, 10, 16, 2, 4, 10, 0.2083, 13, 31, 0);
		result.put("Austrália", dadosDaAustralia);
		DadosHistoricosSelecoes dadosDoPaisDeGales = new DadosHistoricosSelecoes("País de Gales", 1, 6, 5, 1, 3, 1, 0.4, 4, 4, 0);
		result.put("País de Gales", dadosDoPaisDeGales);
		DadosHistoricosSelecoes dadosDoCanada = new DadosHistoricosSelecoes("Canadá", 1, 0, 3, 0, 0, 3, 0.0, 0, 5, 0);
		result.put("Canadá", dadosDoCanada);

		return result;
	}
}