package entidades;

public class DadosHistoricosSelecoes {

	private String nome;
	private int participacoes;
	private int jogos;
	private int pontos;
	private int vitorias;
	private int empates;
	private int derrotas;
	private double aproveitamento;
	private int golsPro;
	private int golsContra;
	private int titulos;

	public DadosHistoricosSelecoes(String nome, int participacoes, int pontos, int jogos, int vitorias, int empates,
			int derrotas, double aproveitamento, int golsPro, int golsContra, int titulos) {
		this.nome = nome;
		this.participacoes = participacoes;
		this.jogos = jogos;
		this.pontos = pontos;
		this.vitorias = vitorias;
		this.empates = empates;
		this.derrotas = derrotas;
		this.aproveitamento = aproveitamento;
		this.golsPro = golsPro;
		this.golsContra = golsContra;
		this.titulos = titulos;
	}

	public String getNome() {
		return nome;
	}

	public int getParticipacoes() {
		return participacoes;
	}

	public int getPontos() {
		return pontos;
	}

	public int getJogos() {
		return jogos;
	}

	public int getVitorias() {
		return vitorias;
	}

	public int getEmpates() {
		return empates;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public double getAproveitamento() {
		return aproveitamento;
	}

	public int getGolsPro() {
		return golsPro;
	}

	public int getGolsContra() {
		return golsContra;
	}

	public int getTitulos() {
		return titulos;
	}

	public int compraraDados(DadosHistoricosSelecoes desafiante, int comparador) {

		switch (comparador) {
		case 1:
			return Integer.compare(this.getParticipacoes(), desafiante.getParticipacoes());
		case 2:
			return Integer.compare(this.getPontos(), desafiante.getPontos());
		case 3:
			return Integer.compare(this.getJogos(), desafiante.getJogos());
		case 4:
			return Integer.compare(this.getVitorias(), desafiante.getVitorias());
		case 5:
			return Integer.compare(this.getEmpates(), desafiante.getEmpates());
		case 6:
			return Integer.compare(this.getDerrotas(), desafiante.getDerrotas()) * -1;
		case 7:
			return Double.compare(this.getAproveitamento(), desafiante.getAproveitamento());
		case 8:
			return Integer.compare(this.getGolsPro(), desafiante.getGolsPro());
		case 9:
			return Integer.compare(this.getGolsContra(), desafiante.getGolsContra()) * -1;
		case 10:
			return Integer.compare(this.getTitulos(), desafiante.getTitulos());
		default:
			throw new IllegalArgumentException("Valor não aceito para comparação: " + comparador);
		}
	}

}
