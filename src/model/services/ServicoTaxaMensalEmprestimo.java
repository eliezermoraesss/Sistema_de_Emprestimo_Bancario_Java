package model.services;

// INTERFACE para cálculo do juros mensal. Facilita a manutenção do código e permite implementação de outros valores e tipos de juros, se necessário no futuro.
public interface ServicoTaxaMensalEmprestimo {
	
	double taxaDeJurosMensal(double valorParcela);

}
