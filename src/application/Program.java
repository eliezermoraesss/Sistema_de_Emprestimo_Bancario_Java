package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.entities.Cliente;
import model.entities.Emprestimo;
import model.entities.Endereco;
import model.entities.ParcelasEmprestimo;
import model.servicos.ServicoEmprestimo;
import model.servicos.TaxaMensalEmprestimoTQI;

public class Program {

	public static void main(String[] args) throws ParseException {

		boolean loop = true;

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Set<Cliente> listaDeClientes = new HashSet<>();

		while (loop) {
			System.out.println("---------- Bem-vindo(a) ao servi�o de empr�stimos do TQIBank ----------\n");
			System.out.print("J� � nosso cliente? (s/n) ");
			char resposta = sc.next().charAt(0);
			sc.nextLine();

			if (resposta == 'n') {
				System.out.println("---------- Cadastro de novo cliente ----------\n");
				System.out.print("Digite o seu nome completo: ");
				String nome = sc.nextLine();
				System.out.print("Digite o seu CPF (somente n�meros): ");
				String cpf = sc.nextLine();
				System.out.print("Digite o seu RG (somente n�meros): ");
				String rg = sc.nextLine();
				System.out.print("Qual a sua renda mensal? ");
				double renda = sc.nextDouble();
				sc.nextLine();
				System.out.print("Digite o seu melhor e-mail: ");
				String email = sc.nextLine();
				System.out.print("Digite uma senha (m�nimo 6 d�gitos): ");
				String senha = sc.nextLine();
				System.out.println();
				System.out.println("Endere�o");
				System.out.print("Rua: ");
				String rua = sc.nextLine();
				System.out.print("N�mero: ");
				int numero = sc.nextInt();
				sc.nextLine();
				System.out.print("Bairro: ");
				String bairro = sc.nextLine();
				System.out.print("Cidade: ");
				String cidade = sc.nextLine();
				System.out.print("Estado: ");
				String estado = sc.nextLine();
				System.out.print("Pa�s: ");
				String pais = sc.nextLine();

				Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, pais);

				Cliente cliente = new Cliente(nome, email, cpf, rg, renda, senha, endereco);

				listaDeClientes.add(cliente);

				System.out.println("Cliente cadastrado com sucesso!");

				for (Cliente cli : listaDeClientes) {
					System.out.println(cli);
				}

			} else if (resposta == 's') {

				// AUTENTICACAO

				System.out.print("E-mail: ");
				String email = sc.nextLine();
				System.out.print("Senha: ");
				String senha = sc.nextLine();

				Cliente cliente = new Cliente(email, senha);

				if (listaDeClientes.contains(cliente)) {

					System.out.println("---------- �rea do cliente ----------\n");
					System.out.println("---------- Solicita��o de empr�stimo ----------\n");

					int numeroDeParcelas = 0;
					// while(numeroDeParcelas < 60) {
					System.out.print("Digite o n�mero de parcelas (m�ximo 60x): ");
					numeroDeParcelas = sc.nextInt();
					sc.nextLine();
					// System.out.println((numeroDeParcelas > 60) ? "" : "N�mero de parcelas
					// excedido. Digite um valor menor ou igual � 60.");
					// }
					System.out.print("Digite o valor do empr�stimo: ");
					double valorDoEmprestimo = sc.nextDouble();
					System.out.print("Qual a data da primeira parcela? (dd/MM/yyyy) ");
					Date dataInicial = sdf.parse(sc.next());

					Emprestimo emprestimo = new Emprestimo(valorDoEmprestimo, dataInicial);

					ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(new TaxaMensalEmprestimoTQI());

					servicoEmprestimo.processarEmprestimo(emprestimo, numeroDeParcelas);

					System.out.println("Parcelas: ");
					for (ParcelasEmprestimo x : emprestimo.getParcelasEmprestimo()) {
						System.out.println(x);
					}
				}
				else {
					System.out.println("E-mail e/ou senha incorretos!");
				}
			}
			System.out.println();
			// sc.close();
		}
	}
}
