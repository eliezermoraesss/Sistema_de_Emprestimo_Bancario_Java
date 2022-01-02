package model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprestimo {

	private Integer codigoEmprestimo;
	private Integer numeroDeParcelas;
	private Double valorDoEmprestimo;
	private Date dataInicial;

	List<ParcelasEmprestimo> parcelasEmprestimo = new ArrayList<>();
	
	public Emprestimo() {
	}

	public Emprestimo(Integer codigoEmprestimo, Integer numeroDeParcelas, Double valorDoEmprestimo, Date dataInicial) {
		this.codigoEmprestimo = codigoEmprestimo;
		this.numeroDeParcelas = numeroDeParcelas;
		this.valorDoEmprestimo = valorDoEmprestimo;
		this.dataInicial = dataInicial;
	}

	public Integer getCodigoEmprestimo() {
		return codigoEmprestimo;
	}

	public void setCodigoEmprestimo(Integer codigoEmprestimo) {
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
	
	public List<ParcelasEmprestimo> getParcelasEmprestimo() {
		return parcelasEmprestimo;
	}
	
	public void addParcelasEmprestimo(ParcelasEmprestimo parcelaEmprestimo) {
		parcelasEmprestimo.add(parcelaEmprestimo);
		
	}
	
	public void removeParcelasEmprestimo(ParcelasEmprestimo parcelaEmprestimo) {
		parcelasEmprestimo.remove(parcelaEmprestimo);
	}
}
