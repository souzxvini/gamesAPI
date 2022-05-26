package com.souza.gamesAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souza.gamesAPI.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	List<Perfil> findByNome(String string);

}
