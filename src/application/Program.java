package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.entities.Cliente;
import model.entities.Emprestimo;
import model.entities.Endereco;
import model.entities.Parcelas;
import model.servicos.ServicoEmprestimo;
import model.servicos.TaxaMensalEmprestimoTQI;
import model.util.DiferencaDatas;

public class Program {

	public static void main(String[] args) throws ParseException {

		boolean loop = true;
		int codEmprestimo = 0;
		char resposta;

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataSistema = new Date();

		List<Cliente> listaDeClientes = new ArrayList<>();

		while (loop) {
			System.out.println("********** Bem-vindo(a) ao serviço de empréstimos do TQIBank ********** "
					+ sdf2.format(dataSistema));
			System.out.println();
			System.out.print("Já é cliente TQI? (s/n) ");
			resposta = sc.next().charAt(0);
			sc.nextLine();

			if (resposta == 'n') {
				System.out.println("********** Cadastro de novo cliente **********\n");
				System.out.print("Digite o seu nome completo: ");
				String nome = sc.nextLine();
				System.out.print("Digite o seu CPF (somente números): ");
				String cpf = sc.nextLine();
				System.out.print("Digite o seu RG (somente números): ");
				String rg = sc.nextLine();
				System.out.print("Qual a sua renda mensal? ");
				double renda = sc.nextDouble();
				sc.nextLine();
				System.out.print("Digite o seu melhor e-mail: ");
				String email = sc.nextLine();
				System.out.print("Digite uma senha (mínimo 6 dígitos): ");
				String senha = sc.nextLine();
				System.out.println("Endereço");
				System.out.print("Rua: ");
				String rua = sc.nextLine();
				System.out.print("Número: ");
				int numero = sc.nextInt();
				sc.nextLine();
				System.out.print("Bairro: ");
				String bairro = sc.nextLine();
				System.out.print("Cidade: ");
				String cidade = sc.nextLine();
				System.out.print("Estado: ");
				String estado = sc.nextLine();
				System.out.print("País: ");
				String pais = sc.nextLine();

				Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, pais);
				Cliente cliente = new Cliente(nome, email, cpf, rg, renda, senha, endereco);

				listaDeClientes.add(cliente);
				System.out.println();
				System.out.println("Cliente cadastrado com sucesso!");
				System.out.println();
				System.out.println("Deseja efetuar um empréstimo? (s/n) ");
				resposta = sc.next().charAt(0);
				sc.nextLine();
				
				while (resposta == 's') {
					System.out.println("********** Área do cliente **********");
					System.out.println("********** Solicitação de empréstimo **********\n");

					codEmprestimo++;
					int numeroDeParcelas = 0;
					System.out.print("Digite o número de parcelas (máximo 60x): ");
					numeroDeParcelas = sc.nextInt();
					sc.nextLine();
					System.out.print("Digite o valor do empréstimo: ");
					double valorDoEmprestimo = sc.nextDouble();
					System.out.print("Qual a data da primeira parcela? (dd/MM/yyyy) (máximo de 3 meses após o dia atual - " + sdf.format(dataSistema) + ": ");
					Date dataMaximaParcela = sdf.parse(sc.next());
					
					DiferencaDatas diferencaDatas = new DiferencaDatas();
					System.out.println(diferencaDatas.diferencaDatas(dataMaximaParcela)); 

					Emprestimo emprestimoParcelas = new Emprestimo(valorDoEmprestimo, dataMaximaParcela); // Sobrecarga
					Emprestimo emprestimo = new Emprestimo(codEmprestimo, numeroDeParcelas, valorDoEmprestimo,
							dataMaximaParcela);

					cliente.addEmprestimo(emprestimo);

					ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(new TaxaMensalEmprestimoTQI());
					servicoEmprestimo.processarEmprestimo(emprestimoParcelas, numeroDeParcelas);

					System.out.println("Empréstimo efetuado com sucesso!");
					System.out.println("Parcelas a serem pagas: ");
					for (Parcelas p : emprestimoParcelas.getParcelas()) {
						System.out.println(p);
					}
			
					System.out.println();
					
					for (Emprestimo e : cliente.getEmprestimo()) {
						System.out.println(e);
					}
					System.out.println();
					
					System.out.println("Deseja efetuar um novo empréstimo? (s/n) ");
					resposta = sc.next().charAt(0);
					sc.nextLine();
				}
					
			} else if (resposta == 's') {
				// AUTENTICACAO
				System.out.print("E-mail: ");
				String email = sc.nextLine();
				System.out.print("Senha: ");
				String senha = sc.nextLine();

				Cliente cliente = new Cliente(email, senha);

				if (listaDeClientes.contains(cliente)) {
					
					System.out.println("Acesso permitido!");

				} else {
					System.out.println("E-mail e/ou senha incorretos!");
				}
			}
			System.out.println();
			// sc.close();
		}
	}
}