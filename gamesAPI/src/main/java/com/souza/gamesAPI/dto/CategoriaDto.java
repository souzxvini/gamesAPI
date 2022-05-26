package com.souza.gamesAPI.dto;

import org.springframework.data.domain.Page;

import com.souza.gamesAPI.model.Categoria;

public class CategoriaDto {

	private Long id;
	
	private String nome;
	
	public CategoriaDto(Categoria categoria) {
		this.nome = categoria.getNome();
		this.id = categoria.getId();
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

	public static Page<CategoriaDto> converter(Page<Categoria> categorias) {
		return categorias.map(CategoriaDto::new);
	}
}
