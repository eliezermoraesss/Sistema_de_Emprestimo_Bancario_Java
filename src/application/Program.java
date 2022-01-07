package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Cliente;
import entities.Emprestimo;
import entities.Endereco;
import entities.Parcelas;
import exception.DomainException;
import services.ServicoEmprestimo;
import services.TaxaMensalEmprestimoTQI;
import util.DiferencaDatas;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataSistema = new Date();

		List<Cliente> listaDeClientes = new ArrayList<>();
		
		boolean loop = true;
		int codEmprestimo = 0;
		char resposta;

		try {
			while (loop) {
				System.out.println("********** Bem-vindo(a) ao servi�o de empr�stimos do TQIBank ********** "
						+ sdf2.format(dataSistema));
				System.out.println();
				System.out.print("Se j� � nosso cliente, digite 's' para acessar a �rea do Cliente ou 'n' para Cadastrar-se! (s/n): ");
				resposta = sc.next().charAt(0);
				sc.nextLine();
				if (resposta != 's' && resposta != 'n') {
					System.out.println("Resposta inv�lida. Digite 's' para SIM e 'n' para N�O.");
				}
				System.out.println();

				if (resposta == 'n') {
					System.out.println("********** Cadastro de novo cliente **********\n");
					System.out.print("1. Nome completo: ");
					String nome = sc.nextLine();
					System.out.print("2. CPF: ");
					String cpf = sc.nextLine();
					System.out.print("3. Digite o seu RG: ");
					String rg = sc.nextLine();
					System.out.print("4. Qual a sua renda mensal? ");
					double renda = sc.nextDouble();
					sc.nextLine();
					System.out.print("5. Digite o seu melhor e-mail: ");
					String email = sc.nextLine();
					System.out.print("6. Digite uma senha segura (digite no m�nimo 8 d�gitos): ");
					String senha = sc.nextLine();
					if (senha.length() < 8) {
						throw new DomainException("Digite no m�nimo 8 d�gitos.");
					}
					System.out.println("Endere�o");
					System.out.print("1. Rua: ");
					String rua = sc.nextLine();
					System.out.print("2. N�mero: ");
					int numero = sc.nextInt();
					sc.nextLine();
					System.out.print("3. Bairro: ");
					String bairro = sc.nextLine();
					System.out.print("4. Cidade: ");
					String cidade = sc.nextLine();
					System.out.print("5. Estado: ");
					String estado = sc.nextLine();
					System.out.print("6. Pa�s: ");
					String pais = sc.nextLine();
					
					Cliente cliente = new Cliente(nome, email, cpf, rg, renda, senha, new Endereco(rua, numero, bairro, cidade, estado, pais));					
					listaDeClientes.add(cliente);

					System.out.println();
					System.out.println("********** Cliente cadastrado com sucesso! **********\n");
					System.out.print("Deseja efetuar um empr�stimo? (s/n) ");
					resposta = sc.next().charAt(0);
					sc.nextLine();
					System.out.println();

					while (resposta == 's') {
						System.out.println("********** �rea do cliente **********");
						System.out.println("********** Solicita��o de empr�stimo **********\n");
						codEmprestimo++;
						int numeroDeParcelas = 0;
						boolean testeParcela = true;
						while (testeParcela) {
							System.out.print("Digite o n�mero de parcelas: ");
							numeroDeParcelas = sc.nextInt();
							sc.nextLine();
							
							// L�gica da condi��o de quantidade m�xima de parcelas permitidas, conforme regra de neg�cio
							testeParcela = (numeroDeParcelas <= 60) ? false : true;
							if (numeroDeParcelas > 60) {
								System.out.println("N�mero de parcelas excedido. Parcelamento de no m�ximo 60 vezes.");
							}
						}
						System.out.print("Digite o valor do empr�stimo: ");
						double valorDoEmprestimo = sc.nextDouble();

						System.out.print("Qual a data da primeira parcela? (dd/MM/yyyy) (data m�xima de 3 meses ap�s o dia atual - "
										+ sdf.format(dataSistema) + ": ");
						Date dataMaximaParcela = sdf.parse(sc.next());
						
						// Classe utilit�ria para verificar a data da primeira parcela 3 meses ap�s a data atual, conforme regra de neg�cio
						DiferencaDatas data = new DiferencaDatas();
						data.diferencaDatas(dataMaximaParcela); 																																											 
						
						// Sobrecarga paragerar a lista de parcelas
						Emprestimo emprestimoParcelas = new Emprestimo(valorDoEmprestimo, dataMaximaParcela); 
						Emprestimo emprestimo = new Emprestimo(codEmprestimo, numeroDeParcelas, valorDoEmprestimo,
								dataMaximaParcela);

						cliente.addEmprestimo(emprestimo);

						ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(new TaxaMensalEmprestimoTQI());
						servicoEmprestimo.processarEmprestimo(emprestimoParcelas, numeroDeParcelas);

						// Imprimir lista de parcelas do empr�stimo efetuado
						System.out.println("********** Empr�stimo efetuado com sucesso! **********\n"); 
						System.out.println("Parcelas a serem pagas: ");
						for (Parcelas p : emprestimoParcelas.getParcelas()) {
							System.out.println(p);
						}
						System.out.println();
						
						System.out.println("********** Listagem de empr�stimos feitos pelo cliente **********");
						for (Emprestimo e : cliente.getEmprestimo()) { 
							System.out.println(e);
						}
						System.out.println();
						
						System.out.print("Deseja efetuar um novo empr�stimo? (s/n) ");
						resposta = sc.next().charAt(0);
						sc.nextLine();
					}

				} else if (resposta == 's') {
					// Tela de autentica��o
					System.out.println("********** �rea do cliente **********");
					System.out.println("********** Tela de Login **********\n");
					System.out.print("Digite seu e-mail: ");
					String email = sc.nextLine();
					System.out.print("Digite sua senha: ");
					String senha = sc.nextLine();

					Cliente cliente = new Cliente(email, senha);

					if (listaDeClientes.contains(cliente)) {
						
						System.out.println("********** Acesso permitido! **********\n");
						int posicao = listaDeClientes.size();

						List<List<Emprestimo>> lista = listaDeClientes.stream()
								.filter(f -> (f.getEmail() == email) && (f.getSenha() == senha))
								.map(m -> m.getEmprestimo()).collect(Collectors.toList());

						lista.forEach(System.out::println);

					} else {
						System.out.println("********** Acesso negado! E-mail e/ou senha incorretos. **********\n");
					}
				}
				System.out.println();
			}
		} catch (ParseException e) {
			System.out.println("Formato de data incorreto. (Exemplo: 28/04/2022)");
		} catch (DomainException e) {
			System.out.println("Erro de preenchimento. " + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("Erro de preenchimento. Digite um n�mero.");
		} finally {
			sc.close();
		}
	}
}