package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.entities.Autenticacao;
import model.entities.Cliente;
import model.entities.Emprestimo;
import model.entities.Endereco;
import model.entities.Parcelas;
import model.exception.DomainException;
import model.services.ServicoEmprestimo;
import model.services.TaxaMensalEmprestimoTQI;
import model.util.DiferencaDatas;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataSistema = new Date();

		Autenticacao autenticacao = new Autenticacao();
		Map<String, Long> auth = new HashMap<>();

		boolean loop = true;
		Long codEmprestimo = 0L;
		Long idCliente = 0L;
		char resposta;
		Double renda;
		String email;

		try {
			while (loop) {
				System.out.println("********** Bem-vindo(a) ao servi�o de empr�stimos do TQIBank ********** "
						+ sdf2.format(dataSistema));
				System.out.println();
				System.out.print(
						"J� � cliente TQI?\nDigite 's' para acessar a �REA DO CLIENTE ou 'n' para CADASTRAR-SE: ");
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
					System.out.print("2. CPF (somente n�meros): ");
					String cpf = sc.nextLine();
					System.out.print("3. RG (somente n�meros): ");
					String rg = sc.nextLine();
					System.out.print("4. Qual a sua renda mensal? (somente n�meros) R$ ");
					renda = sc.nextDouble();
					sc.nextLine();
					System.out.print("5. Digite o seu melhor e-mail: ");
					email = sc.nextLine();
					System.out.print("6. Digite uma senha segura (digite no m�nimo 8 d�gitos): ");
					String senha = sc.nextLine();
					if (senha.length() < 8) {
						throw new DomainException("Digite no m�nimo 8 d�gitos.");
					}
					System.out.println();

					// Verifica se o e-mail j� existe, ou seja, se j� foi cadastrado.
					if (auth.containsKey(email)) {
						throw new DomainException(
								"********** E-MAIL J� CADASTRADO! :(\n********** TENTE NOVAMENTE. **********\n");
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

					idCliente++;
					Cliente cliente = new Cliente(idCliente, nome, email.toUpperCase(), cpf, rg, renda, senha,
							new Endereco(rua, numero, bairro, cidade, estado, pais));
					autenticacao.addClientes(cliente);

					auth.put(email.toUpperCase(), idCliente);

					System.out.println();
					System.out.println("********** CLIENTE CADASTRADO COM SUCESSO! :D **********\n");
					System.out.print("Deseja efetuar um empr�stimo?\nDigite 's' para SIM e 'n' para N�O: ");
					resposta = sc.next().charAt(0);
					sc.nextLine();
					System.out.println();

					while (resposta == 's') {
						System.out.println("********** �rea do cliente **********");
						System.out.println("********** Solicita��o de empr�stimo **********\n");
						System.out.println("---------- TAXA DE 2.77% am ----------\n");
						codEmprestimo++;
						int numeroDeParcelas = 0;
						boolean testeParcela = true;
						while (testeParcela) {
							System.out.print("Digite o n�mero de parcelas (somente n�meros): ");
							numeroDeParcelas = sc.nextInt();
							sc.nextLine();
							System.out.println();
							// L�gica da condi��o de quantidade m�xima de parcelas permitidas, conforme
							// regra de neg�cio
							testeParcela = (numeroDeParcelas <= 60) ? false : true;
							if (numeroDeParcelas > 60) {
								System.out.println("N�mero de parcelas excedido. Parcelamento de no m�ximo 60 vezes.");
							}
						}
						System.out.print("Digite o valor do empr�stimo (somente n�meros): R$ ");
						double valorDoEmprestimo = sc.nextDouble();
						System.out.println();
						
						DiferencaDatas data = new DiferencaDatas();
						System.out.print("Qual a data de vencimento da primeira parcela?\nDigite uma data entre "
								+ sdf.format(dataSistema) + " e " + sdf.format(data.dataLimite()) + ": ");
						Date dataMaximaParcela = sdf.parse(sc.next());
						System.out.println();
						
						// Classe utilit�ria para verificar a data da primeira parcela 3 meses ap�s a
						// data atual, conforme regra de neg�cio
						data.diferencaDatas(dataMaximaParcela);

						// Sobrecarga para gerar a lista de parcelas
						Emprestimo emprestimoParcelas = new Emprestimo(valorDoEmprestimo, dataMaximaParcela);
						Emprestimo emprestimo = new Emprestimo(codEmprestimo, numeroDeParcelas, valorDoEmprestimo,
								dataMaximaParcela, renda, email);

						cliente.addEmprestimo(emprestimo);

						ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(new TaxaMensalEmprestimoTQI());
						servicoEmprestimo.processarEmprestimo(emprestimoParcelas, numeroDeParcelas);

						// Imprimir lista de parcelas do empr�stimo efetuado
						System.out.println("********** EMPRESTIMO EFETUADO COM SUCESSO! **********\n");
						System.out.println("Parcelas a serem pagas: ");
						for (Parcelas p : emprestimoParcelas.getParcelas()) {
							System.out.println(p);
						}
						System.out.println();

						System.out.println("********** LISTAGEM DE EMPR�STIMOS REALIZADOS **********\n");
						for (Emprestimo e : cliente.getEmprestimo()) {
							System.out.println(e);
						}
						System.out.println();

						System.out.print("Deseja efetuar um novo empr�stimo?\nDigite 's' para SIM e 'n' para N�O: ");
						resposta = sc.next().charAt(0);
						sc.nextLine();
					}

				} else if (resposta == 's') {
					// Tela de autentica��o
					System.out.println("********** �rea do cliente **********\n");
					System.out.print("LOGIN (e-mail): ");
					String emailLogin = sc.nextLine();
					System.out.print("SENHA: ");
					String senha = sc.nextLine();
					System.out.println();

					Cliente cliente = new Cliente(emailLogin.toUpperCase(), senha);

					long posicao = 0L;
					String chaveValor = emailLogin;

					if (autenticacao.getClientes().contains(cliente)) {

						System.out.println("********** ACESSO EFETUADO COM SUCESSO! **********\n");

						System.out.println(
								"Deseja efetuar um NOVO EMPR�STIMO ou somente VISUALIZAR SUA LISTA DE EMPR�STIMO(S)?\n"
										+ "Digite 'e' para NOVO EMPR�STIMO ou 'v' para VISUALIZAR LISTA DE EMPR�STIMO(S): ");
						resposta = sc.next().charAt(0);
						sc.nextLine();
						while (loop) {
							if (resposta == 'e') {

								System.out.println("********** �rea do cliente **********");
								System.out.println("********** Solicita��o de empr�stimo **********\n");
								System.out.println("---------- TAXA DE 2.77% am ----------\n");
								codEmprestimo++;
								int numeroDeParcelas = 0;
								boolean testeParcela = true;
								while (testeParcela) {
									System.out.print("Digite o n�mero de parcelas (somente n�meros): ");
									numeroDeParcelas = sc.nextInt();
									sc.nextLine();

									// L�gica da condi��o de quantidade m�xima de parcelas permitidas, conforme
									// regra de neg�cio
									testeParcela = (numeroDeParcelas <= 60) ? false : true;
									if (numeroDeParcelas > 60) {
										System.out.println(
												"N�mero de parcelas excedido. Parcelamento de no m�ximo 60 vezes.");
									}
								}
								System.out.print("Digite o valor do empr�stimo (somente n�meros): R$ ");
								double valorDoEmprestimo = sc.nextDouble();

								DiferencaDatas data = new DiferencaDatas();
								System.out
										.print("Qual a data de vencimento da primeira parcela?\nDigite uma data entre "
												+ sdf.format(dataSistema) + " e " + sdf.format(data.dataLimite())
												+ ": ");
								Date dataMaximaParcela = sdf.parse(sc.next());

								// Classe utilit�ria para verificar a data da primeira parcela 3 meses ap�s a
								// data atual, conforme regra de neg�cio
								data.diferencaDatas(dataMaximaParcela);

								// Sobrecarga para gerar a lista de parcelas
								Emprestimo emprestimoParcelas = new Emprestimo(valorDoEmprestimo, dataMaximaParcela);
								Emprestimo emprestimo = new Emprestimo(codEmprestimo, numeroDeParcelas,
										valorDoEmprestimo, dataMaximaParcela, cliente.getRenda(), cliente.getEmail());

								autenticacao.getClientes().get((int) posicao).addEmprestimo(emprestimo);

								ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(
										new TaxaMensalEmprestimoTQI());
								servicoEmprestimo.processarEmprestimo(emprestimoParcelas, numeroDeParcelas);

								// Imprimir lista de parcelas do empr�stimo efetuado
								System.out.println("********** EMPRESTIMO EFETUADO COM SUCESSO! **********\n");
								System.out.println("Parcelas a serem pagas: ");
								for (Parcelas p : emprestimoParcelas.getParcelas()) {
									System.out.println(p);
								}
								System.out.println();

								System.out.print(
										"Deseja efetuar um novo empr�stimo?\nDigite 'e' para NOVO EMPR�STIMO ou 'v' para VISUALIZAR LISTA DE EMPR�STIMOS: ");
								resposta = sc.next().charAt(0);
								sc.nextLine();

							} else if (resposta == 'v') {

								posicao = auth.get(chaveValor.toUpperCase()) - 1L;
								System.out.println();
								
								for (Emprestimo e : autenticacao.getClientes().get((int) posicao).getEmprestimo()) {
									System.out.println(e);
								}
								
								loop = false;

							}
						}
						loop = true;
					} else {
						System.out.println("********** ACESSO NEGADO! E-MAIL E/OU SENHA INCORRETOS. **********\n");
					}
				}
				System.out.println();
			}
		} catch (ParseException e) {
			System.out.println("Formato de data incorreto. (Exemplo: 28/04/2022)");
		} catch (DomainException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("Erro inesperado!");
		} finally {
			sc.close();
		}
	}
}