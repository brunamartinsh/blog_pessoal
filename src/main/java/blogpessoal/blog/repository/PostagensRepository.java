package blogpessoal.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blogpessoal.blog.model.Postagem;

@Repository
public interface PostagensRepository extends JpaRepository <Postagem, Long> {
	public List<Postagem> findAllByTituloContainingIgnoreCase (String Titulo);
}
