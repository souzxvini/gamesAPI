package com.souza.gamesAPI.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.souza.gamesAPI.model.Categoria;
import com.souza.gamesAPI.model.Jogo;
import com.souza.gamesAPI.repository.CategoriaRepository;

public class JogoForm {

	@NotNull 
	@NotBlank
	private String nome;
	
	@NotNull 
	@NotBlank
	private String descricao;
	
	@NotNull 
	@NotBlank
	private String nomeCategoria;
	
	@NotNull 
	private double preco;
	
	@NotNull 
	@NotBlank
	private String empresa;
	
	@NotNull 
	@NotBlank
	private String anoLancamento;
	
	public String getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(String anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	public Jogo converter(CategoriaRepository categoriaRepository) {
		Categoria categoria = categoriaRepository.findByNome(nomeCategoria);
		
		return new Jogo(nome, descricao, preco, empresa, categoria, anoLancamento);
	}
	
	
	
}
