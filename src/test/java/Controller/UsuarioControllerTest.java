	package Controller;

	import static org.junit.jupiter.api.Assertions.assertEquals;

	import java.util.Optional;



	import org.junit.jupiter.api.BeforeAll;
	import org.junit.jupiter.api.DisplayName;
	import org.junit.jupiter.api.MethodOrderer;
	import org.junit.jupiter.api.Order;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.TestInstance;
	import org.junit.jupiter.api.TestMethodOrder;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
	import org.springframework.boot.test.web.client.TestRestTemplate;
	import org.springframework.http.HttpEntity;
	import org.springframework.http.HttpMethod;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;

	import com.blogpessoal.blogpessoal.model.Usuario;
	import com.blogpessoal.blogpessoal.model.UsuarioLogin;
	import com.blogpessoal.blogpessoal.repository.UsuarioRepository;
	import com.blogpessoal.blogpessoal.service.UsuarioService;

	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class UsuarioControllerTest {

		
		@Autowired
		private TestRestTemplate testRestTemplate;

		@Autowired
		private UsuarioService usuarioService;

		@Autowired
		private UsuarioRepository usuarioRepository;

		@BeforeAll
		void start(){

			usuarioRepository.deleteAll();

		}

		@Test
		@Order(1)
		@DisplayName("Cadastrar Um Usuário")
		public void deveCriarUmUsuario() {

		
			HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Jonatas Marques", "jonatasmarques@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg","normail"));

			
			ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

			
			assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

			assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());

			
			assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
		}

		@Test
		@Order(2)
		@DisplayName("Não deve permitir duplicação do Usuário")
		public void naoDeveDuplicarUsuario() {

			
			usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Maria clara", "mariaclara@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg","adm"));

			
			HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Maria clara", "mariaclara@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg","normal"));

			ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

			
			assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
		}

		@Test
		@Order(3)
		@DisplayName("Atualizar um Usuário")
		public void deveAtualizarUmUsuario() {

			
			Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Bruna Maria", "brunamaria@email.com.br", "bruna123", "https://i.imgur.com/yDRVeK7.jpg","ong"));
			
			Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), 
				"Juliana Amaral", "julianaamaral@email.com.br", "juliana123" , "https://i.imgur.com/yDRVeK7.jpg","adm");
		
			HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

			
			ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

			
			assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

			
			assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());

			
			assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
		}

		@Test
		@Order(4)
		@DisplayName("Listar todos os Usuários")
		public void deveMostrarTodosUsuarios() {

			
			usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Clara Moraes", "claramoraes@email.com.br", "clara123", "https://i.imgur.com/5M2p5Wb.jpg","normal"));
			
			usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Kamila", "kamila@email.com.br", "kamila123", "https://i.imgur.com/Sk5SjWE.jpg","normal"));

			ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

			

		}

	
		@Test
		@Order(5)
		@DisplayName("Listar Um Usuário Específico")
		public void deveListarApenasUmUsuario() {
			
			Optional<Usuario> usuarioBusca = usuarioService.cadastrarUsuario(new Usuario(0L, 
					"Ivo", "Ivo@email.com.br", "Ivo12345", "https://i.imgur.com/EcJG8kB.jpg","adm"));
				
			
			ResponseEntity<String> resposta = testRestTemplate
					.withBasicAuth("root", "root")
					.exchange("/usuarios/" + usuarioBusca.get().getId(), HttpMethod.GET, null, String.class);
			
			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			
		}

		@Test
		@Order(6)
		@DisplayName("Login do Usuário")
		public void deveAutenticarUsuario() {

			usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Maria Luísa", "marialuisa@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg","ong"));

			HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<UsuarioLogin>(new UsuarioLogin(0L, 
				"", "debora", "13465278", "", "",""));

			ResponseEntity<UsuarioLogin> corpoResposta = testRestTemplate
				.exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, UsuarioLogin.class);

			assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

		}
	}	
	
}
