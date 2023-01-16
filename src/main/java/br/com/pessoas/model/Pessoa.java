package br.com.pessoas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pessoas")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "Nome")
	private String nome;
	
	@Column(name = "Data de nascimento")
	private String dataNasc;
	
	@Column(name = "Logradouro")
	private String logradouro;
	
	@Column(name = "CEP")
	private String cep;
	
	@Column(name = "Número")
	private int num;
	
	@Column(name = "Cidade")
	private String cidade;
	
	@Column(name = "Endereço Principal")
	private boolean enderecoPrincipal;

	
	public Pessoa() {
		
	}
	
	public Pessoa (String nome, String dataNasc, String logradouro, String cep, int num, String cidade, boolean enderecoPrincipal) {
		this.nome = nome;
		this.dataNasc = dataNasc;
		this.logradouro = logradouro;
		this.cep = cep;
		this.num = num;
		this.cidade = cidade;
		this.enderecoPrincipal = enderecoPrincipal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public boolean isEnderecoPrincipal() {
		return enderecoPrincipal;
	}

	public void setEnderecoPrincipal(boolean enderecoPrincipal) {
		this.enderecoPrincipal = enderecoPrincipal;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", dataNasc=" + dataNasc + ", logradouro=" + logradouro
				+ ", cep=" + cep + ", num=" + num + ", cidade=" + cidade + ", enderecoPrincipal=" + enderecoPrincipal + "]";
	}
	
	

	
	
	
	

}
