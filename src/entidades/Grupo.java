package entidades;

import java.util.ArrayList;
import java.util.List;

public class Grupo {

	private static final List<Integer> POSSIVEIS_TAMANHOS = List.of(2, 4);
	private String nome;
	private List<Selecao> selecoes = new ArrayList<>();

	public Grupo(String nome, List<Selecao> selecoes) {
		if (!POSSIVEIS_TAMANHOS.contains(selecoes.size())) {
			throw new IllegalAccessError("Não é possível criar um grupo com este numero de Seleções");
		}
		this.nome = nome;
		this.selecoes = selecoes;
	}

	public String getNome() {
		return nome;
	}

	public List<Selecao> getSelecoes() {
		return selecoes;
	}

}
