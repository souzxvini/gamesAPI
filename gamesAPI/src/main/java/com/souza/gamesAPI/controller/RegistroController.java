package com.souza.gamesAPI.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.souza.gamesAPI.form.RegisterForm;
import com.souza.gamesAPI.model.Perfil;
import com.souza.gamesAPI.model.Usuario;
import com.souza.gamesAPI.repository.PerfilRepository;
import com.souza.gamesAPI.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "registro")
public class RegistroController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid RegisterForm registerForm, BindingResult result){
		
		Usuario user = registerForm.converter();
		List<Perfil> perfis = perfilRepository.findByNome("ROLE_ADM");
	
		String senhaNaoCriptografada = user.getSenha();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(senhaNaoCriptografada);
		user.setSenha(encodedPassword);
		user.setPerfis(perfis);
		Usuario novo = usuarioRepository.save(user);
		
		return ResponseEntity.ok(novo);
		
	}
	
}
