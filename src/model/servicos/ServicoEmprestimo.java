package model.servicos;

import java.util.Calendar;
import java.util.Date;

import model.entities.Emprestimo;
import model.entities.ParcelasEmprestimo;

public class ServicoEmprestimo {

	private ServicoTaxaMensalEmprestimo servicoTaxaMensalEmprestimo;

	public ServicoEmprestimo(ServicoTaxaMensalEmprestimo servicoTaxaMensalEmprestimo) {
		this.servicoTaxaMensalEmprestimo = servicoTaxaMensalEmprestimo;
	}

	public void processarEmprestimo(Emprestimo emprestimo, int meses) {
		double valorParcelaSemJuros = emprestimo.getValorDoEmprestimo() / meses;
		for (int i = 1; i <= meses; i++) {
			Date data = addMeses(emprestimo.getDataInicial(), i);
			double valorAtualizadoComJuros = valorParcelaSemJuros + servicoTaxaMensalEmprestimo.taxaDeJurosMensal(valorParcelaSemJuros, i);
			emprestimo.addParcelasEmprestimo(new ParcelasEmprestimo(data, valorAtualizadoComJuros));
		}
	}

	private Date addMeses(Date data, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

}