package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import exception.DomainException;

public class DiferencaDatas {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public DiferencaDatas() {
	}

	public long diferencaDatas(Date dataMaximaParcela) {

		Date dataSistema = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(dataSistema);
		cal.add(Calendar.MONTH, 3);
		dataSistema = cal.getTime();

		// System.out.println(sdf.format(dataSistema)); // Data atual acrescida de 3 meses.

		long dif = dataMaximaParcela.getTime() - dataSistema.getTime();

		TimeUnit time = TimeUnit.DAYS;
		long diferenca = time.convert(dif, TimeUnit.MILLISECONDS);
		
		//System.out.println(diferenca + 1);

		if ((diferenca + 1) <= 0) {
			return diferenca + 1;
		} else {
			throw new DomainException("Data limite da primeira parcela excedida. A data deve ser menor que " + sdf.format(dataSistema) + ".");
		}
	}
}
