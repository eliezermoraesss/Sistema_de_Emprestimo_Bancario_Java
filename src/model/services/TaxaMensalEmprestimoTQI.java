package model.services;

public class TaxaMensalEmprestimoTQI implements ServicoTaxaMensalEmprestimo {
	
	private static final double PORCENTAGEM_JUROS_MENSAL = 0.027; // 2.70% am

	@Override
	public double taxaDeJurosMensal(double valorParcela) {
		return valorParcela * PORCENTAGEM_JUROS_MENSAL;
	}
}
