package servico;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entidades.DadosHistoricosSelecoes;
import entidades.Selecao;

public class Partida {

	private static Random random = new SecureRandom();

	public static Map<Selecao, String> partidaDeGrupo(Selecao principal, Selecao desafiante) {

		Integer golsSelecaoPrincipal = 0;
		Integer golsSelecaoDesafiante = 0;
		List<Integer> gols = new ArrayList<>();
		gols.add(golsSelecaoPrincipal);
		gols.add(golsSelecaoDesafiante);
		int golsDaPartida = golsPorPartida();
		while (golsDaPartida > 0) {
			distribuindoGolsNaPartida(principal, desafiante, gols);
			golsDaPartida--;
		}

		Map<Selecao, String> result = new HashMap<>();
		result.put(principal, gols.get(0).toString());
		result.put(desafiante, gols.get(1).toString());

		return result;
	}

	private static int golsPorPartida() {

		int numero = random.nextInt(1000) + 1;

		if (numero <= 67) {
			return 0;
		} else if (numero > 67 && numero <= 232) {
			return 1;
		} else if (numero > 232 && numero <= 479) {
			return 2;
		} else if (numero > 479 && numero <= 704) {
			return 3;
		} else if (numero > 704 && numero <= 856) {
			return 4;
		} else if (numero > 856 && numero <= 949) {
			return 5;
		} else if (numero > 949 && numero <= 981) {
			return 6;
		} else if (numero > 981 && numero <= 996) {
			return 7;
		} else if (numero > 996 && numero <= 998) {
			return 8;
		} else {
			return 9;
		}
	}

	private static void distribuindoGolsNaPartida(Selecao principal, Selecao desafiante, List<Integer> gols) {

		int numeroSorteado = random.nextInt(12) + 1;

		if (numeroSorteado <= 10) {
			comparaDadoHistorico(principal.getDadosHistoricos(), desafiante.getDadosHistoricos(), numeroSorteado, gols);
		} else {
			comparaTodosOsDadosHistoricosSelecoes(principal.getDadosHistoricos(), desafiante.getDadosHistoricos(), gols);
		}
	}

	private static void comparaDadoHistorico(DadosHistoricosSelecoes principal, DadosHistoricosSelecoes desafiante, int numeroSorteado,
			List<Integer> gols) {
		int resultado = principal.compraraDados(desafiante, numeroSorteado);
		if (resultado == 1) {
			gols.set(0, gols.get(0) + 1);
		} else if (resultado == -1) {
			gols.set(1, gols.get(1) + 1);
		}
	}

	private static void comparaTodosOsDadosHistoricosSelecoes(DadosHistoricosSelecoes principal, DadosHistoricosSelecoes desafiante,
			List<Integer> gols) {

		int contagemPrincipal = 0;
		int contagemDesafiante = 0;

		for (int i = 1; i <= 10; i++) {
			var resultado = principal.compraraDados(desafiante, i);

			if (resultado == 1) {
				contagemPrincipal++;
			} else if (resultado == -1) {
				contagemDesafiante++;
			}
		}

		if (contagemPrincipal > contagemDesafiante) {
			gols.set(1, gols.get(1) + 1);
		} else if (contagemPrincipal < contagemDesafiante) {
			gols.set(0, gols.get(0) + 1);
		}
	}

	public static Map<Selecao, String> partidaEliminatoria(Selecao principal, Selecao desafiante) {

		Map<Selecao, String> result = partidaDeGrupo(principal, desafiante);

		if (result.get(principal).equals(result.get(desafiante))) {
			Map<Selecao, String> penaltis = penalti(principal, desafiante);

			String novoResultadoPrincipal = result.get(principal) + penaltis.get(principal);
			String novoResultadoDesafiante = result.get(desafiante) + penaltis.get(desafiante);

			result.replace(principal, novoResultadoPrincipal);
			result.replace(desafiante, novoResultadoDesafiante);
		}
		return result;
	}

	private static Map<Selecao, String> penalti(Selecao principal, Selecao desafiante) {

		Integer golsSelecaoPrincipal = 0;
		Integer golsSelecaoDesafiante = 0;

		for (int i = 1; i <= 5; i++) {

			double chuteGol = random.nextDouble();
			double chanceDefesa = 0.35;

			if (chuteGol > chanceDefesa) {
				golsSelecaoPrincipal++;
			}

			chuteGol = random.nextDouble();

			if (chuteGol > chanceDefesa) {
				golsSelecaoDesafiante++;
			}

			if (golsSelecaoPrincipal - golsSelecaoDesafiante > 5 - i || golsSelecaoDesafiante - golsSelecaoPrincipal > 5 - i) {
				break;
			}

		}
		while (golsSelecaoPrincipal == golsSelecaoDesafiante) {
			double chuteGol = random.nextDouble();
			double chanceDefesa = 0.35;

			if (chuteGol > chanceDefesa) {
				golsSelecaoPrincipal++;
			}

			chuteGol = random.nextDouble();

			if (chuteGol > chanceDefesa) {
				golsSelecaoDesafiante++;
			}

		}

		Map<Selecao, String> result = new HashMap<>();
		result.put(principal, "( " + golsSelecaoPrincipal + " x " + golsSelecaoDesafiante + " )");
		result.put(desafiante, "( " + golsSelecaoDesafiante + " x " + golsSelecaoPrincipal + " )");

		return result;
	}
}
