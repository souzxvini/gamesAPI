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

import com.souza.gamesAPI.dto.CategoriaDto;
import com.souza.gamesAPI.form.AtualizacaoCategoriaForm;
import com.souza.gamesAPI.form.CategoriaForm;
import com.souza.gamesAPI.model.Categoria;
import com.souza.gamesAPI.repository.CategoriaRepository;
import com.souza.gamesAPI.repository.JogoRepository;

@RestController
@RequestMapping(value = "categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeCategorias")
	public Page<CategoriaDto> listar(@RequestParam(required = false) String nome,
			@PageableDefault(sort= "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao){
		Page<Categoria> categorias = categoriaRepository.findAll(paginacao);
		return CategoriaDto.converter(categorias);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeCategorias", allEntries = true)
	public ResponseEntity<CategoriaDto> cadastrar(@RequestBody @Valid CategoriaForm categoriaForm, UriComponentsBuilder uriBuilder){
		
		Categoria categoria = categoriaForm.converter();
		categoriaRepository.save(categoria);
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> detalhar(@PathVariable Long id){
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		if(categoria.isPresent()) {
			return ResponseEntity.ok(new CategoriaDto(categoria.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCategorias", allEntries = true)
	public ResponseEntity<CategoriaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCategoriaForm atualizacaoCategoriaForm){
		
		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		if(optional.isPresent()) {
			Categoria categoria = atualizacaoCategoriaForm.atualizar(id, categoriaRepository);
			return ResponseEntity.ok(new CategoriaDto(categoria));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCategorias", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id){

		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		if(optional.isPresent()) {
			categoriaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		 
	}
	
}
