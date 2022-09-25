package aplicacao;

import java.util.List;

import entidades.Grupo;
import entidades.Selecao;

public class CopaDoMundo {

	public void geraCopaDoMundoReal() {
		geraCopaDoMundo(true);
	}
	
	public void geraCopaDoMundoRandomica() {
		geraCopaDoMundo(false);
	}
	
	private void geraCopaDoMundo(boolean gruposReais) {
		
		Simulador simulador = new Simulador();

		List<Selecao> selecoes = List.of(new Selecao("Catar"), new Selecao("Equador"), new Selecao("Holanda"), new Selecao("Senegal"),
				new Selecao("Estados Unidos"), new Selecao("Inglaterra"), new Selecao("Irã"), new Selecao("País de Gales"),
				new Selecao("Arábia Saudita"), new Selecao("Argentina"), new Selecao("México"), new Selecao("Polônia"),
				new Selecao("Austrália"), new Selecao("Dinamarca"), new Selecao("França"), new Selecao("Tunísia"),
				new Selecao("Alemanha"), new Selecao("Costa Rica"), new Selecao("Espanha"), new Selecao("Japão"),
				new Selecao("Bélgica"), new Selecao("Canadá"), new Selecao("Croácia"), new Selecao("Marrocos"),
				new Selecao("Brasil"),	new Selecao("Camarões"), new Selecao("Sérvia"), new Selecao("Suíça"),
				new Selecao("Coreia do Sul"), new Selecao("Gana"), new Selecao("Portugal"), new Selecao("Uruguai"));

		simulador.adicionaDadosHistoricosSelecoes(selecoes);
		List<Grupo> chaveDeGrupos = gruposReais?
				simulador.geraGruposReais(selecoes):simulador.geraGrupos(selecoes);

		for (Grupo grupo : chaveDeGrupos) {
			System.out.println(grupo.getNome());
			System.out.println(grupo.getSelecoes());
		}
		
		System.out.println();
		System.out.println("JOGOS DA FASE DE GRUPOS");
		System.out.println();
		simulador.geraJogos(chaveDeGrupos);
		simulador.organizaGrupos(chaveDeGrupos);
		
		String brancos = "               ";
		System.out.println();
		System.out.println("RESULTADO FINAL DA FASE DE GRUPOS");
		System.out.println();
		for (Grupo grupo : chaveDeGrupos) {
			System.out.println(grupo.getNome());
			System.out.println();
			for (Selecao selecao : grupo.getSelecoes()) {
				System.out.println(selecao.getNome() + brancos.substring(0, 15 - selecao.getNome().length()) + " - PTS:"
						+ selecao.getPontuacao() + " V:" + selecao.getVitorias() + " E:" + selecao.getEmpates() + " D:"
						+ selecao.getDerrotas() + " GF:" + selecao.getGolsFeitos() + " GS:" + selecao.getGolsSofridos() + " SG:"
						+ selecao.getSaldoDeGols());
			}
			System.out.println();
		}
		
		System.out.println("FASE FINAL - MATA-MATA");
		System.out.println();
		List<Grupo> selecoesVencedores = simulador.geraEliminatorias(chaveDeGrupos);
		
		System.out.println("OITAVAS-DE-FINAL");
		System.out.println();
		List<Grupo> oitavas = simulador.geraJogosDecisivos(selecoesVencedores);
		
		System.out.println();
		System.out.println("QUARTAS-DE-FINAL");
		System.out.println();
		List<Grupo> quartas = simulador.geraJogosDecisivos(oitavas);

		System.out.println();
		System.out.println("SEMIFINAIS");
		System.out.println();
		List<Grupo> semifinal = simulador.geraJogosDecisivos(quartas);
		
		System.out.println();
		System.out.println("DECISÃO DO TERCEIRO LUGAR");
		System.out.println();
		List<Grupo> perdedoresDaSemiFinal = simulador.geraGrupoDeTerceiroLugar(quartas, semifinal);
		Selecao terceiro = simulador.geraFinal(perdedoresDaSemiFinal);
		
		System.out.println();
		System.out.println("FINAL");
		System.out.println();
		Selecao vencedor = simulador.geraFinal(semifinal);
		System.out.println();
		System.out.println("CAMPEÃO = " + vencedor);
		
		Selecao segundo = simulador.getSegundo(vencedor, semifinal);
		System.out.println("SEGUNDO = " + segundo);
		System.out.println("TERCEIRO = " + terceiro);
	}
}
