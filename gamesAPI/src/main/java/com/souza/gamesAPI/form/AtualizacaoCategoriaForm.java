package com.souza.gamesAPI.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.souza.gamesAPI.model.Categoria;
import com.souza.gamesAPI.model.Jogo;
import com.souza.gamesAPI.repository.CategoriaRepository;
import com.souza.gamesAPI.repository.JogoRepository;

public class AtualizacaoCategoriaForm {

	@NotNull 
	@NotBlank
	private String nome;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria atualizar(Long id, CategoriaRepository categoriaRepository) {
		Categoria categoria = categoriaRepository.getById(id);
		categoria.setNome(this.nome);
		return categoria;
	}
	
}
