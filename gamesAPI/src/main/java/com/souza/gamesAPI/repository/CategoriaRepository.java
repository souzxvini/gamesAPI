package com.souza.gamesAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souza.gamesAPI.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Categoria findByNome(String nomeCategoria);

}
