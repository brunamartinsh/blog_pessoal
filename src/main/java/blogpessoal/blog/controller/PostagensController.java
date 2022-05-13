package blogpessoal.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogpessoal.blog.model.Postagem;
import blogpessoal.blog.repository.PostagensRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagensController {

	@Autowired
	private PostagensRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<Postagem> GetById (@PathVariable long id){
		return repository.findById(id).
				map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
		
	}
}