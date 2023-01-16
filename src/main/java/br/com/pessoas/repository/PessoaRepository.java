package br.com.pessoas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pessoas.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	List<Pessoa> findByNomeContainingIgnoreCase(String nome);
	
	List<Pessoa> findByEnderecoPrincipal(boolean enderecoPrincipal);

}
