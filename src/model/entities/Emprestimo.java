package model.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprestimo {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private Long codigoEmprestimo;
	private Integer numeroDeParcelas;
	private Double valorDoEmprestimo;
	private Date dataInicial;
	private Double renda;
	private String email;
	private Cliente cliente;
	private List<Parcelas> parcelas = new ArrayList<>();

	public Emprestimo() {
	}

	public Emprestimo(Long codigoEmprestimo, Integer numeroDeParcelas, Double valorDoEmprestimo, Date dataInicial,
			Double renda, String email) {
		this.codigoEmprestimo = codigoEmprestimo;
		this.numeroDeParcelas = numeroDeParcelas;
		this.valorDoEmprestimo = valorDoEmprestimo;
		this.dataInicial = dataInicial;
		this.renda = renda;
		this.email = email;
	}

	public Emprestimo(Double valorDoEmprestimo, Date dataInicial) {
		this.valorDoEmprestimo = valorDoEmprestimo;
		this.dataInicial = dataInicial;
	}

	public Long getCodigoEmprestimo() {
		return codigoEmprestimo;
	}

	public void setCodigoEmprestimo(Long codigoEmprestimo) {
		this.codigoEmprestimo = codigoEmprestimo;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Double getValorDoEmprestimo() {
		return valorDoEmprestimo;
	}

	public void setValorDoEmprestimo(Double valorDoEmprestimo) {
		this.valorDoEmprestimo = valorDoEmprestimo;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public Double getRenda() {
		return renda;
	}

	public void setRenda(Double renda) {
		this.renda = renda;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Parcelas> getParcelas() {
		return parcelas;
	}

	public void addParcelas(Parcelas parcela) {
		parcelas.add(parcela);
	}

	public void removeParcelas(Parcelas parcela) {
		parcelas.remove(parcela);
	}
	
	public String detalheEmprestimo() {		
		
		return "Código: ";
		/*+ codigoEmprestimo 
		+ " - Valor do emprestimo: " 
		+ String.format("%.2f", valorDoEmprestimo) 
		+ " - Quantidade de parcelas: " 
		+ numeroDeParcelas
		+ " - Primeira parcela: "
		+ sdf.format(dataInicial)
		+ " - Email: "
		+ email
		+ " - Renda: "
		+ String.format("%.2f", renda);*/
	}

	@Override
	public String toString() {
		return "Código: " + codigoEmprestimo + " - Valor do emprestimo: " + String.format("%.2f", valorDoEmprestimo) + " - Quantidade de parcelas: " + numeroDeParcelas;
	}
}
