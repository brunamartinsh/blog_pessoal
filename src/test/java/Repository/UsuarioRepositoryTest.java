	package Repository;

	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.junit.jupiter.api.Assertions.assertTrue;

	import java.util.List;
	import java.util.Optional;



	import org.junit.jupiter.api.AfterAll;
	import org.junit.jupiter.api.BeforeAll;
	import org.junit.jupiter.api.DisplayName;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.TestInstance;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

	import com.blogpessoal.blogpessoal.model.Usuario;


	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	public class UsuarioRepositoryTest {

	    @Autowired
		private UsuarioRepository usuarioRepository;
		
		@BeforeAll
		void start(){

			usuarioRepository.deleteAll();
			
			usuarioRepository.save(new Usuario(0L, "Bruna Maria", "brunamaria@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg","normal"));
			
			usuarioRepository.save(new Usuario(0L, "Ana Maria", "anamaria@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg","adm"));
			
			usuarioRepository.save(new Usuario(0L, "Chimamanda", "chimamanda@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg","ong"));

	        usuarioRepository.save(new Usuario(0L, "Maria luisa", "marialuisa@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg","normal"));

		}

		@Test
		@DisplayName("Retorna 1 usuario")
		public void deveRetornarUmUsuario() {

			
			Optional<Usuario> usuario = usuarioRepository.findByUsuario("brunamaria@email.com.br");

			
			assertTrue(usuario.get().getUsuario().equals("brunamaria@email.com.br"));
		}

		@Test
		@DisplayName("Retorna 3 usuarios")
		public void deveRetornarTresUsuarios() {

			
			List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");

			assertEquals(3, listaDeUsuarios.size());

			assertTrue(listaDeUsuarios.get(0).getNome().equals("Bruna Maria"));
			
			assertTrue(listaDeUsuarios.get(1).getNome().equals("Ana Maria"));

			assertTrue(listaDeUsuarios.get(2).getNome().equals("Maria luisa"));
			
		}

		@AfterAll
		public void end() {
			usuarioRepository.deleteAll();
		}
		
	}
