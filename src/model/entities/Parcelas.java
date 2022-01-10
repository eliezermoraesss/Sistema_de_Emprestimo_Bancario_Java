package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Parcelas {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private Date dataDeVencimento;
	private Double valorDaParcela;

	public Parcelas() {
	}

	public Parcelas(Date dataDeVencimento, Double valorDaParcela) {
		this.dataDeVencimento = dataDeVencimento;
		this.valorDaParcela = valorDaParcela;
	}

	public Date getDataDeVencimento() {
		return dataDeVencimento;
	}

	public void setDataDeVencimento(Date dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}

	public Double getValorDaParcela() {
		return valorDaParcela;
	}

	public void setValorDaParcela(Double valorDaParcela) {
		this.valorDaParcela = valorDaParcela;
	}

	@Override
	public String toString() {
		return sdf.format(dataDeVencimento) + " - " + String.format("%.2f", valorDaParcela);
	}
}