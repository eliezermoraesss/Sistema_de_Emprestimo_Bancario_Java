package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

	private String name;
	private String email;
	private String cpf;
	private String rg;
	private Double renda;
	private String senha;

	private Endereco endereco;

	List<Emprestimo> emprestimos = new ArrayList<>();

	public Cliente() {
	}

	public Cliente(String name, String email, String cpf, String rg, Double renda, String senha, Endereco endereco) {
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.rg = rg;
		this.renda = renda;
		this.senha = senha;
		this.endereco = endereco;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Double getRenda() {
		return renda;
	}

	public void setRenda(Double renda) {
		this.renda = renda;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Emprestimo> getEmprestimo() {
		return emprestimos;
	}

	public void addEmprestimo(Emprestimo emprestimo) {
		emprestimos.add(emprestimo);

	}

	public void removeEmprestimo(Emprestimo emprestimo) {
		emprestimos.remove(emprestimo);
	}
}
