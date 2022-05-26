package com.souza.gamesAPI.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.souza.gamesAPI.model.Usuario;

public class RegisterForm {
	
	@NotBlank @NotNull
	private String email;
	
	@NotBlank @NotNull
	private String senha;
	
	@NotBlank @NotNull
	private String nome;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String username) {
		this.email = username;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Usuario converter() {
		Usuario usuario = new Usuario();
		usuario.setSenha(senha);
		usuario.setEmail(email);
		usuario.setNome(nome);
		
		return usuario;
	}
}
