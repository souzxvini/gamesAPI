package com.souza.gamesAPI.dto;

import org.springframework.data.domain.Page;

import com.souza.gamesAPI.model.Categoria;
import com.souza.gamesAPI.model.Jogo;

public class JogoDto {

	private Long id;
	private String nome;
	private String descricao;
	private Categoria categoria;
	
	public JogoDto(Jogo jogo) {
		this.nome = jogo.getNome();
		this.descricao = jogo.getDescricao();
		this.categoria = jogo.getCategoria();
		this.id = jogo.getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public static Page<JogoDto> converter(Page<Jogo> jogos) {
		return jogos.map(JogoDto::new);
	}
}
