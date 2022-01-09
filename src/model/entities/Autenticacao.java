package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Autenticacao {
	
	private String email;
	private String senha;
	
	private List<Cliente> clientes = new ArrayList<>();
	
	public Autenticacao() {
	}

	public Autenticacao(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public void addClientes(Cliente cliente) {
		clientes.add(cliente);
	}

	public void removeEmprestimo(Cliente cliente) {
		clientes.remove(cliente);
	}
}
