package br.com.pessoas;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pessoas.model.Pessoa;
import br.com.pessoas.repository.PessoaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JPAUnitTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	PessoaRepository repository;
	
	@Test
	public void should_find_no_pessoas_if_repository_is_empty() {
		Iterable pessoas = repository.findAll();
		
		assertThat(pessoas).isEmpty();
	}
	
	@Test
	public void should_store_a_pessoa() {
		Pessoa pessoa = repository
				.save(new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true));
		
		assertThat(pessoa).hasFieldOrPropertyWithValue("nome", "Renato Silva");
		assertThat(pessoa).hasFieldOrPropertyWithValue("dataNasc", "09/03/1983");
		assertThat(pessoa).hasFieldOrPropertyWithValue("logradouro", "Rua Professor Henrique Costa");
		assertThat(pessoa).hasFieldOrPropertyWithValue("cep", "22.770-233");
		assertThat(pessoa).hasFieldOrPropertyWithValue("num", 188);
		assertThat(pessoa).hasFieldOrPropertyWithValue("cidade", "Rio de Janeiro");
		assertThat(pessoa).hasFieldOrPropertyWithValue("enderecoPrincipal", true);
	}
	
	@Test
	public void should_find_all_pessoas() {
		Pessoa pessoa1 = new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa1);
		
		Pessoa pessoa2 = new Pessoa(
				"Joana Tavares Cristiano", 
				"03/10/2019", 
				"Rua Ituverava",
		        "22.700-200",
		        634,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa2);
		
		Pessoa pessoa3 = new Pessoa(
				"Ana Luisa Barbosa", 
				"18/10/1945", 
				"Avenida Epitácio Pessoa",
		        "20.150-300",
		        10500,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa3);
		
		Iterable pessoas = repository.findAll();
		
		assertThat(pessoas).hasSize(3).contains(pessoa1, pessoa2, pessoa3);
		
	}
	
	@Test
	public void should_find_pessoa_by_id() {
		Pessoa pessoa1 = new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa1);
		
		Pessoa pessoa2 = new Pessoa(
				"Joana Tavares Cristiano", 
				"03/10/2019", 
				"Rua Ituverava",
		        "22.700-200",
		        634,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa2);
		
		Pessoa foundPessoa = repository.findById(pessoa2.getId()).get();
		
		assertThat(foundPessoa).isEqualTo(pessoa2);
	}
	
	@Test
	public void should_find_pessoas_by_nome_containing_string() {
		Pessoa pessoa1 = new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa1);
		
		Pessoa pessoa2 = new Pessoa(
				"Joana Tavares Cristiano", 
				"03/10/2019", 
				"Rua Ituverava",
		        "22.700-200",
		        634,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa2);
		
		Pessoa pessoa3 = new Pessoa(
				"Ana Luisa Barbosa", 
				"18/10/1945", 
				"Avenida Epitácio Pessoa",
		        "20.150-300",
		        10500,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa3);
		
		Iterable pessoas = repository.findByNomeContainingIgnoreCase("Joana");
		
		assertThat(pessoas).hasSize(1).contains(pessoa2);
	}
	
	@Test
	public void should_update_pessoa_by_id() {
		Pessoa pessoa1 = new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa1);
		
		Pessoa pessoa2 = new Pessoa(
				"Joana Tavares Cristiano", 
				"03/10/2019", 
				"Rua Ituverava",
		        "22.700-200",
		        634,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa2);
		
		Pessoa updatedPessoa = new Pessoa(
				"updated Joana Tavares Cristiano", 
				"updated 03/10/2019", 
				"updated Rua Ituverava",
		        "updated 22.700-200",
		        634,
		        "updated Rio de Janeiro",
		        true);
		
		Pessoa pessoa = repository.findById(pessoa2.getId()).get();
		pessoa.setNome(updatedPessoa.getNome());
		pessoa.setDataNasc(updatedPessoa.getDataNasc());
		pessoa.setLogradouro(updatedPessoa.getLogradouro());
		pessoa.setCep(updatedPessoa.getCep());
		pessoa.setNum(updatedPessoa.getNum());
		pessoa.setCidade(updatedPessoa.getCidade());
		pessoa.setEnderecoPrincipal(updatedPessoa.isEnderecoPrincipal());
		repository.save(pessoa);
		
		Pessoa checkPessoa = repository.findById(pessoa2.getId()).get();
		
		assertThat(checkPessoa.getId()).isEqualTo(pessoa2.getId());
		assertThat(checkPessoa.getNome()).isEqualTo(updatedPessoa.getNome());
		assertThat(checkPessoa.getDataNasc()).isEqualTo(updatedPessoa.getDataNasc());
		assertThat(checkPessoa.getLogradouro()).isEqualTo(updatedPessoa.getLogradouro());
		assertThat(checkPessoa.getCep()).isEqualTo(updatedPessoa.getCep());
		assertThat(checkPessoa.getNum()).isEqualTo(updatedPessoa.getNum());
		assertThat(checkPessoa.getCidade()).isEqualTo(updatedPessoa.getCidade());
		assertThat(checkPessoa.isEnderecoPrincipal()).isEqualTo(updatedPessoa.isEnderecoPrincipal());
	}
		
	@Test
	public void should_delete_pessoa_by_id() {
		Pessoa pessoa1 = new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa1);
		
		Pessoa pessoa2 = new Pessoa(
				"Joana Tavares Cristiano", 
				"03/10/2019", 
				"Rua Ituverava",
		        "22.700-200",
		        634,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa2);
		
		Pessoa pessoa3 = new Pessoa(
				"Ana Luisa Barbosa", 
				"18/10/1945", 
				"Avenida Epitácio Pessoa",
		        "20.150-300",
		        10500,
		        "Rio de Janeiro",
		        true);
		entityManager.persist(pessoa3);
		
		repository.deleteById(pessoa2.getId());
		
		Iterable pessoas = repository.findAll();
		
		assertThat(pessoas).hasSize(2).contains(pessoa1, pessoa3);
	}
	
	@Test
	public void should_delete_all_pessoas() {
		entityManager.persist(new Pessoa(
				"Renato Silva", 
				"09/03/1983", 
				"Rua Professor Henrique Costa",
		        "22.770-233",
		        188,
		        "Rio de Janeiro",
		        true));
		
		entityManager.persist(new Pessoa(
				"Joana Tavares Cristiano", 
				"03/10/2019", 
				"Rua Ituverava",
		        "22.700-200",
		        634,
		        "Rio de Janeiro",
		        true));
		
		repository.deleteAll();
		
		assertThat(repository.findAll()).isEmpty();
	}
	
	 @Test
	 public void should_find_enderecos_principais() {
		 Pessoa pessoa1 = new Pessoa(
					"Renato Silva", 
					"09/03/1983", 
					"Rua Professor Henrique Costa",
			        "22.770-233",
			        188,
			        "Rio de Janeiro",
			        true);
			entityManager.persist(pessoa1);
			
			Pessoa pessoa2 = new Pessoa(
					"Joana Tavares Cristiano", 
					"03/10/2019", 
					"Rua Ituverava",
			        "22.700-200",
			        634,
			        "Rio de Janeiro",
			        false);
			entityManager.persist(pessoa2);
			
			Pessoa pessoa3 = new Pessoa(
					"Ana Luisa Barbosa", 
					"18/10/1945", 
					"Avenida Epitácio Pessoa",
			        "20.150-300",
			        10500,
			        "Rio de Janeiro",
			        true);
			entityManager.persist(pessoa3);

	    Iterable pessoas = repository.findByEnderecoPrincipal(true);

	    assertThat(pessoas).hasSize(2).contains(pessoa1, pessoa3);
	  }

}