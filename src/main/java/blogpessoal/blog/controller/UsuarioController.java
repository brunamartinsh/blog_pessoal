package blogpessoal.blog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import blogpessoal.blog.model.Usuario;
import blogpessoal.blog.model.UsuarioLogin;
import blogpessoal.blog.repository.UsuarioRepository;
import blogpessoal.blog.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired 
	private UsuarioRepository repository;

	@GetMapping("/all")
	public ResponseEntity <List<Usuario>> getAll() {
	        return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id) {
	        return repository.findById(id)
		        .map(resp -> ResponseEntity.ok(resp))
		        .orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){		
	        return usuarioService.atualizarUsuario(usuario)
	                .map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
	                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autentication(@Valid @RequestBody Optional <UsuarioLogin> user){
	        return usuarioService.logarUsuario(user).map(resp -> ResponseEntity.ok(resp))
			        .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Optional<Usuario>> post(@Valid @RequestBody Usuario usuario){
		
	        return ResponseEntity.status(HttpStatus.CREATED)
			        .body(usuarioService.cadastrarUsuario(usuario));
	}


}