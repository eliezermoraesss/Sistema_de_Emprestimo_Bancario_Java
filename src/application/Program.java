package application;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println("---------- Bem-vindo(a) ao servi�o de empr�stimo do TQIBank ----------\n");
		System.out.print("J� � nosso cliente? (s/n)");
		char resposta = sc.next().charAt(0);
		if (resposta == 'n') {
			System.out.println("---------- Cadastro de novo cliente ----------\n");
			System.out.print("Nome completo: ");
			String nomeCompleto = sc.nextLine();
			
		}
			
		
	}
}
