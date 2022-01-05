package model.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DiferencaDatas {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private Date dataMaximaParcela;
	
	public DiferencaDatas() {
	}
	
	public DiferencaDatas(Date dataMaximaParcela) {
		this.dataMaximaParcela = dataMaximaParcela;
	}
	
	public Date getDataMaximaParcela() {
		return dataMaximaParcela;
	}

	public void setDataMaximaParcela(Date dataMaximaParcela) {
		this.dataMaximaParcela = dataMaximaParcela;
	}

	public long diferencaDatas(Date dataMaximaParcela) {
		
		Date dataSistema = new Date();	
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataSistema);
		cal.add(Calendar.MONTH, 3);
		dataSistema = cal.getTime();

        long dif = dataMaximaParcela.getTime() - dataSistema.getTime();

        TimeUnit time = TimeUnit.DAYS; 
        long diferenca = time.convert(dif, TimeUnit.MILLISECONDS);
        
        return diferenca;
		
	}
}
