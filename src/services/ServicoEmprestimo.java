package services;

import java.util.Calendar;
import java.util.Date;

import entities.Emprestimo;
import entities.Parcelas;

public class ServicoEmprestimo {

	private ServicoTaxaMensalEmprestimo servicoTaxaMensalEmprestimo;

	public ServicoEmprestimo(ServicoTaxaMensalEmprestimo servicoTaxaMensalEmprestimo) {
		this.servicoTaxaMensalEmprestimo = servicoTaxaMensalEmprestimo;
	}

	public void processarEmprestimo(Emprestimo emprestimo, int meses) {
		double valorParcelaSemJuros = emprestimo.getValorDoEmprestimo() / meses;
		for (int i = 1; i <= meses; i++) {
			Date data = addMeses(emprestimo.getDataInicial(), i);
			double valorAtualizadoComJuros = valorParcelaSemJuros + servicoTaxaMensalEmprestimo.taxaDeJurosMensal(valorParcelaSemJuros);
			emprestimo.addParcelas(new Parcelas(data, valorAtualizadoComJuros));
		}
	}

	private Date addMeses(Date data, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}
}