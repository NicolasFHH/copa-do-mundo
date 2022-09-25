package entidades;

public class Selecao implements Comparable<Selecao> {

	private String nome;
	private int vitorias = 0;
	private int empates = 0;
	private int derrotas = 0;
	private int golsFeitos = 0;
	private int golsSofridos = 0;
	private int saldoDeGols = 0;
	private int pontuacao = 0;
	private DadosHistoricosSelecoes dados;

	public Selecao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void adicionaVitoria() {
		vitorias++;
		pontuacao += 3;
	}

	public int getEmpates() {
		return empates;
	}

	public void adicionaEmpate() {
		empates++;
		pontuacao++;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public void adicionaDerrota() {
		derrotas++;
	}

	public int getGolsFeitos() {
		return golsFeitos;
	}

	public void adicionaGolsFeitos(int golsFeitos) {
		this.golsFeitos += golsFeitos;
		saldoDeGols += golsFeitos;
	}

	public int getGolsSofridos() {
		return golsSofridos;
	}

	public void adicionaGolsSofridos(int golsSofridos) {
		this.golsSofridos += golsSofridos;
		saldoDeGols -= golsSofridos;
	}

	public int getSaldoDeGols() {
		return saldoDeGols;
	}

	@Override
	public String toString() {
		return nome;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public DadosHistoricosSelecoes getDadosHistoricos() {
		return dados;
	}

	public void setDados(DadosHistoricosSelecoes dados) {
		this.dados = dados;
	}

	@Override
	public int compareTo(Selecao outroSelecao) {
		if (pontuacao != outroSelecao.getPontuacao()) {
			return Integer.compare(pontuacao, outroSelecao.getPontuacao());
		}
		if (saldoDeGols != outroSelecao.getSaldoDeGols()) {
			return Integer.compare(saldoDeGols, outroSelecao.getSaldoDeGols());
		}
		if (golsFeitos != outroSelecao.getGolsFeitos()) {
			return Integer.compare(golsFeitos, outroSelecao.getGolsFeitos());
		}
		return 1;
	}

}
