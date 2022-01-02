package model.servicos;

public class TaxaMensalEmprestimoTQI implements ServicoTaxaMensalEmprestimo {
	
	private static final double PORCENTAGEM_JUROS_MENSAL = 0.027;

	@Override
	public double taxaDeJurosMensal(double valorParcela, int meses) {
		return valorParcela * PORCENTAGEM_JUROS_MENSAL * meses;
	}

}
