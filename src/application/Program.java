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
		Map<String, Long> auth = new HashMap<>(); // Declarado Map<> para lógica de autenticação.
		
		//Declaração de variáveis globais.
		boolean loop = true;
		Long codEmprestimo = 0L;
		Long idCliente = 0L;
		char resposta;
		Double renda;
		String email;

		try {
			while (loop) {
				System.out.println("********** Bem-vindo(a) ao serviço de empréstimos do TQIBank ********** "
						+ sdf2.format(dataSistema));
				System.out.println("---------- Desenvolvedor: Eliezer Moraes Silva ----------");
				System.out.println();
				System.out.print(
						"Já é cliente TQI?\nDigite 's' para acessar a ÁREA DO CLIENTE ou 'n' para CADASTRAR-SE: ");
				resposta = sc.next().charAt(0);
				sc.nextLine();
				if (resposta != 's' && resposta != 'n') {
					System.out.println("Resposta inválida. Digite 's' para SIM e 'n' para NÃO.");
				}
				System.out.println();

				if (resposta == 'n') {
					System.out.println("********** Cadastro de novo cliente **********\n");
					System.out.print("1. Nome completo: ");
					String nome = sc.nextLine();
					System.out.print("2. CPF (somente números): ");
					String cpf = sc.nextLine();
					System.out.print("3. RG (somente números): ");
					String rg = sc.nextLine();
					System.out.print("4. Qual a sua renda mensal? (R$ 0000,00) R$ ");
					renda = sc.nextDouble();
					sc.nextLine();
					System.out.print("5. Digite o seu melhor e-mail: ");
					email = sc.nextLine();
					System.out.print("6. Digite uma senha segura (digite no mínimo 8 dígitos): ");
					String senha = sc.nextLine();
					if (senha.length() < 8) {
						throw new DomainException("Digite no mínimo 8 dígitos.");
					}
					System.out.println();

					// Verifica se o e-mail já existe, ou seja, se já foi cadastrado.
					if (auth.containsKey(email)) {
						throw new DomainException(
								"********** E-MAIL JÁ CADASTRADO! :(\n********** TENTE NOVAMENTE. **********\n");
					}

					System.out.println("Endereço");
					System.out.print("1. Rua: ");
					String rua = sc.nextLine();
					System.out.print("2. Número: ");
					int numero = sc.nextInt();
					sc.nextLine();
					System.out.print("3. Bairro: ");
					String bairro = sc.nextLine();
					System.out.print("4. Cidade: ");
					String cidade = sc.nextLine();
					System.out.print("5. Estado: ");
					String estado = sc.nextLine();
					System.out.print("6. País: ");
					String pais = sc.nextLine();
					
					//Incrementar ID da classe cliente para uso futuro. Obter posição na lista de Clientes.
					idCliente++;
					//Instanciação do objeto Cliente
					Cliente cliente = new Cliente(idCliente, nome, email.toUpperCase(), cpf, rg, renda, senha,
							new Endereco(rua, numero, bairro, cidade, estado, pais));
					//Adicionar o objeto Cliente na lista Cliente da classe Autenticação.
					autenticacao.addClientes(cliente);
					
					//Adicionar os atributos email em letra maíuscula para o login e ID cliente para relacionar a lista de clientes.
					auth.put(email.toUpperCase(), idCliente);

					System.out.println();
					System.out.println("********** CLIENTE CADASTRADO COM SUCESSO! :D **********\n");
					System.out.print("Deseja efetuar um empréstimo?\nDigite 's' para SIM e 'n' para NÃO: ");
					resposta = sc.next().charAt(0);
					sc.nextLine();
					System.out.println();

					while (resposta == 's') {
						System.out.println("********** Área do cliente **********");
						System.out.println("********** Solicitação de empréstimo **********\n");
						System.out.println("---------- TAXA DE 2,77% am ----------\n");
						codEmprestimo++;
						int numeroDeParcelas = 0;
						boolean testeParcela = true;
						while (testeParcela) {
							System.out.print("Digite o número de parcelas (somente números): ");
							numeroDeParcelas = sc.nextInt();
							sc.nextLine();
							System.out.println();
							// Lógica da condição de quantidade máxima de parcelas permitidas, conforme REGRA DE NEGÓCIO.
							testeParcela = (numeroDeParcelas <= 60) ? false : true;
							if (numeroDeParcelas > 60) {
								System.out.println("Número de parcelas excedido. Parcelamento de no máximo 60 vezes.");
							}
						}
						System.out.print("Digite o valor do empréstimo (R$ 0000,00): R$ ");
						double valorDoEmprestimo = sc.nextDouble();
						System.out.println();
						
						//Instanciação de objeto para chamada de método para cálculo de datas. 
						DiferencaDatas data = new DiferencaDatas();
						System.out.print("Qual a data de vencimento da primeira parcela?\nDigite uma data entre "
								+ sdf.format(dataSistema) + " e " + sdf.format(data.dataLimite()) + ": ");
						Date dataMaximaParcela = sdf.parse(sc.next());
						System.out.println();

						// Classe utilitária para verificar a data da primeira parcela 3 meses após a data atual, conforme REGRA DE NEGÓCIO.
						data.diferencaDatas(dataMaximaParcela);

						// Sobrecarga para gerar a lista de parcelas
						Emprestimo emprestimoParcelas = new Emprestimo(valorDoEmprestimo, dataMaximaParcela);
						Emprestimo emprestimo = new Emprestimo(codEmprestimo, numeroDeParcelas, valorDoEmprestimo,
								dataMaximaParcela, renda, email);
						
						//Adicionar o objeto Empréstimo na lista de empréstimo da classe Cliente.
						cliente.addEmprestimo(emprestimo);
						
						//Camada de serviço. Responsável pelos cálculos do empréstimo obedecendo as regras de négocio impostas.
						ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(new TaxaMensalEmprestimoTQI());
						servicoEmprestimo.processarEmprestimo(emprestimoParcelas, numeroDeParcelas);

						// Imprimir lista de parcelas do empréstimo efetuado
						System.out.println("********** EMPRESTIMO EFETUADO COM SUCESSO! **********\n");
						System.out.println("Parcelas a serem pagas: ");
						for (Parcelas p : emprestimoParcelas.getParcelas()) {
							System.out.println(p);
						}
						System.out.println();
						
						//Imprimir listagem de empréstimos realizados utilizando forEach()
						System.out.println("********** LISTAGEM DE EMPRÉSTIMOS REALIZADOS **********\n");
						for (Emprestimo e : cliente.getEmprestimo()) {
							System.out.println(e);
						}
						System.out.println();

						System.out.print("Deseja efetuar um novo empréstimo?\nDigite 's' para SIM e 'n' para NÃO: ");
						resposta = sc.next().charAt(0);
						sc.nextLine();
					}
				//AUTENTICAÇÃO
				} else if (resposta == 's') {
					System.out.println("********** Área do cliente **********\n");
					System.out.print("LOGIN (e-mail): ");
					String emailLogin = sc.nextLine();
					System.out.println();
					System.out.print("SENHA: ");
					String senha = sc.nextLine();
					System.out.println();
					
					Cliente cliente = new Cliente(emailLogin.toUpperCase(), senha);

					long posicao = 0L;
					String chaveValor = emailLogin;
					
					//Verificar se na lista de clientes contem o e-mail e senha digitados
					if (autenticacao.getClientes().contains(cliente)) {

						System.out.println("********** ACESSO EFETUADO COM SUCESSO! **********\n");
						
						// Cria novo empréstimo ou exibe a listagem de contrato de empréstimos solicitados pelo cliente, conforme REGRA DE NEGÓCIO
						System.out.println("Digite 'e' para efetuar um NOVO EMPRÉSTIMO ou 'v' para visualizar sua LISTA DE EMPRÉSTIMO(S): ");
						resposta = sc.next().charAt(0);
						sc.nextLine();
						System.out.println();
						while (loop) {
							if (resposta == 'e') {
								
								//Uso do Map() para obter o valor (posicao na lista) através da chave (que é o e-mail digitado)
								posicao = auth.get(chaveValor.toUpperCase()) - 1L;

								System.out.println("********** Área do cliente **********");
								System.out.println("********** Solicitação de empréstimo **********\n");
								System.out.println("---------- TAXA DE 2,77% am ----------\n");
								codEmprestimo++;
								int numeroDeParcelas = 0;
								boolean testeParcela = true;
								while (testeParcela) {
									System.out.print("Digite o número de parcelas (somente números): ");
									numeroDeParcelas = sc.nextInt();
									sc.nextLine();

									// Lógica da condição de quantidade máxima de parcelas permitidas, conforme REGRA DE NEGÓCIO
									testeParcela = (numeroDeParcelas <= 60) ? false : true; //operação ternária
									if (numeroDeParcelas > 60) {
										System.out.println(
												"Número de parcelas excedido. Parcelamento de no máximo 60 vezes.");
									}
								}
								System.out.print("Digite o valor do empréstimo (somente números): R$ ");
								double valorDoEmprestimo = sc.nextDouble();

								DiferencaDatas data = new DiferencaDatas();
								System.out
										.print("Qual a data de vencimento da primeira parcela?\nDigite uma data entre "
												+ sdf.format(dataSistema) + " e " + sdf.format(data.dataLimite())
												+ ": ");
								Date dataMaximaParcela = sdf.parse(sc.next());

								// Classe utilitária para verificar a data da primeira parcela 3 meses após a data atual, conforme REGRA DE NEGÓCIO
								data.diferencaDatas(dataMaximaParcela);

								// Sobrecarga para gerar a lista de parcelas
								Emprestimo emprestimoParcelas = new Emprestimo(valorDoEmprestimo, dataMaximaParcela);
								Emprestimo emprestimo = new Emprestimo(codEmprestimo, numeroDeParcelas,
										valorDoEmprestimo, dataMaximaParcela,
										autenticacao.getClientes().get((int) posicao).getRenda(), // Navegação utilizando a programação orientada a objetos a nosso favor
										autenticacao.getClientes().get((int) posicao).getEmail()); // Navegação utilizando a programação orientada a objetos a nosso favor
								
								// Adicionar o novo empréstimo para o cliente que está logado!
								autenticacao.getClientes().get((int) posicao).addEmprestimo(emprestimo); // Navegação utilizando a programação orientada a objetos a nosso favor
								
								// Uso de Interfaces para deixar facilitar a manutenção do código e permitir modificações futuras ou incluir novas taxas, se necessário
								ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(
										new TaxaMensalEmprestimoTQI());
								servicoEmprestimo.processarEmprestimo(emprestimoParcelas, numeroDeParcelas);

								// Imprimir lista de parcelas do empréstimo efetuado
								System.out.println("********** EMPRESTIMO EFETUADO COM SUCESSO! **********\n");
								System.out.println("Parcelas a serem pagas: ");
								for (Parcelas p : emprestimoParcelas.getParcelas()) {
									System.out.println(p);
								}
								System.out.println();

								System.out.print("Digite 'e' para efetuar um NOVO EMPRÉSTIMO ou 'v' para visualizar sua LISTA DE EMPRÉSTIMO(S): ");
								resposta = sc.next().charAt(0);
								sc.nextLine();
								System.out.println();

							} else if (resposta == 'v') {

								posicao = auth.get(chaveValor.toUpperCase()) - 1L;
								System.out.println();
								
								int posicaoDetalhe = 0;
								//forEach() para percorrer a lista Empréstimo e imprimir os valores conforme a posição do cliente logado
								for (Emprestimo e : autenticacao.getClientes().get((int) posicao).getEmprestimo()) {
									System.out.println("POSIÇÃO: " + posicaoDetalhe + " -----> " + e);
									posicaoDetalhe++;
								}
								System.out.println();
								
								//DETALHAMENTO DA LISTA DE EMPRÉSTIMO conforme selecionado pelo usuário. REGRA DE NEGÓCIO.
								System.out.print("Para mais DETALHES, digite a POSIÇÃO do contrato de empréstimo: ");
								int cod = sc.nextInt();
								sc.nextLine();
								System.out.println();
								
								// Imprimir os detalhes do empréstimo selecionado
								System.out.println(autenticacao.getClientes().get((int) posicao).getEmprestimo().get(cod));
								System.out.println("Renda: " + autenticacao.getClientes().get((int) posicao).getRenda());
								System.out.println("E-mail: " + autenticacao.getClientes().get((int) posicao).getEmail());
								
								// Sair do while(loop)
								loop = false;
							// Sair do while(loop) caso seja digitado caracteres inválidos
							} else if (resposta != 'e' && resposta != 'v') {
								System.out.println("DIGITO INVÁLIDO! ");
								loop = false;
							}
						}
						// Entrar no loop da tela de login "ÁREA DO CLIENTE"
						loop = true;
					} else {
						// Mensagem para e-mail e/ou senha incorretos na tela de login
						System.out.println("********** ACESSO NEGADO! E-MAIL E/OU SENHA INCORRETOS. **********\n");
					}
				}
				System.out.println();
			}
		// TRATAMENTO DE EXCEÇÕES. Criado exceção personalizada.
		} catch (ParseException e) {
			System.out.println("Formato de data incorreto! (Exemplo: 28/04/2022)");
		} catch (DomainException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("Erro inesperado!");
		} finally {
			// Fechar o Scanner aberto no início através do bloco Finally
			sc.close();
		}
	}
}