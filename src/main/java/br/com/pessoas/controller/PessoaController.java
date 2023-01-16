package br.com.pessoas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pessoas.model.Pessoa;
import br.com.pessoas.repository.PessoaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PessoaController {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@GetMapping("/pessoas")
	public ResponseEntity<List<Pessoa>> getAllPessoas(@RequestParam(required = false) String nome) {
		try {
			List<Pessoa> pessoas = new ArrayList<Pessoa>();
			
			if (nome == null) {
				pessoaRepository.findAll().forEach(pessoas::add);
			} else {
				pessoaRepository.findByNomeContainingIgnoreCase(nome).forEach(pessoas::add);
			} 
			
			if (pessoas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> getPessoaById(@PathVariable("id") long id) {
		Optional<Pessoa> pessoaData = pessoaRepository.findById(id);
		
		if (pessoaData.isPresent()) {
			return new ResponseEntity<>(pessoaData.get(), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@PostMapping("/pessoas")
	public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
		try {
			Pessoa _pessoa = pessoaRepository
					.save(new Pessoa(pessoa.getNome(), pessoa.getDataNasc(), pessoa.getLogradouro(), 
							pessoa.getCep(), pessoa.getNum(), pessoa.getCidade(), false));
			return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@PutMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") long id, @RequestBody Pessoa pessoa) {
		Optional<Pessoa> pessoaData = pessoaRepository.findById(id);
		
		if (pessoaData.isPresent()) {
			
			Pessoa _pessoa = pessoaData.get();
			
			_pessoa.setNome(pessoa.getNome());
			_pessoa.setDataNasc(pessoa.getDataNasc());
			_pessoa.setLogradouro(pessoa.getLogradouro());
			_pessoa.setCep(pessoa.getCep());
			_pessoa.setNum(pessoa.getNum());
			_pessoa.setCidade(pessoa.getCidade());
			_pessoa.setEnderecoPrincipal(pessoa.isEnderecoPrincipal());
			
			return new ResponseEntity<>(pessoaRepository.save(_pessoa), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/pessoas/{id}")
	public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("id") long id) {
		try {
			pessoaRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/pessoas")
	public ResponseEntity<HttpStatus> deleteAllPessoas() {
		try {
			pessoaRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/pessoas/enderecoPrincipal")
	public ResponseEntity<List<Pessoa>> findByEnderecoPrincipal() {
		try {
			List<Pessoa> pessoas = pessoaRepository.findByEnderecoPrincipal(true);

			if (pessoas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
