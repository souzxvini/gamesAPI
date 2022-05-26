package com.souza.gamesAPI.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.souza.gamesAPI.dto.JogoDto;
import com.souza.gamesAPI.form.AtualizacaoJogoForm;
import com.souza.gamesAPI.form.JogoForm;
import com.souza.gamesAPI.model.Jogo;
import com.souza.gamesAPI.repository.CategoriaRepository;
import com.souza.gamesAPI.repository.JogoRepository;

@RestController
@RequestMapping(value = "jogos")
public class JogoController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeJogos")
	public Page<JogoDto> listar(@RequestParam(required = false) String nome,
			@PageableDefault(sort= "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao){
		Page<Jogo> jogos = jogoRepository.findAll(paginacao);
		return JogoDto.converter(jogos);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeJogos", allEntries = true)
	public ResponseEntity<JogoDto> cadastrar(@RequestBody @Valid JogoForm jogoForm, UriComponentsBuilder uriBuilder){
		
		Jogo jogo = jogoForm.converter(categoriaRepository);
		
		jogoRepository.save(jogo);
		
		URI uri = uriBuilder.path("/jogos/{id}").buildAndExpand(jogo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new JogoDto(jogo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<JogoDto> detalhar(@PathVariable Long id){
		
		Optional<Jogo> jogo = jogoRepository.findById(id);
		
		if(jogo.isPresent()) {
			return ResponseEntity.ok(new JogoDto(jogo.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeJogos", allEntries = true)
	public ResponseEntity<JogoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoJogoForm jogoForm){
		
		Optional<Jogo> optional = jogoRepository.findById(id);
		
		if(optional.isPresent()) {
			Jogo jogo = jogoForm.atualizar(id, jogoRepository, categoriaRepository);
			return ResponseEntity.ok(new JogoDto(jogo));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeJogos", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id){

		Optional<Jogo> jogo = jogoRepository.findById(id);
		
		if(jogo.isPresent()) {
			jogoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		 
	}
	
	
}
