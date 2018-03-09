import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class UI 
{

	private Scanner input = new Scanner(System.in);
	private Database database = Database.getInstance();
	private Payroll payroll = Payroll.getInstance();
	private Calendar calendar = Calendar.getInstance();
	
	public void initialize() {
		
		PaymentSchedule pay = new PaymentSchedule();
		pay.setPaymentSchedule("mensal $");
		database.addSchedule( pay );
		
		pay = new PaymentSchedule();
		pay.setPaymentSchedule("semanal 1 sexta");
		database.addSchedule( pay );
		
		pay = new PaymentSchedule();
		pay.setPaymentSchedule("semanal 2 sexta");
		database.addSchedule( pay );
		
		showInitialMenu();
		
	}
	
	public void showInitialMenu() 
	{
		int continuar = 1;
		int realizar, escolha;
		while(continuar != 0)
		{
			System.out.println("Digite 1(empregado) 2(lancar) 3(folha)");
			realizar = input.nextInt();
			
			if(realizar == 1)
			{
				System.out.println("Digite 1(incluir) ou 2(editar) ou 3(remover)");
				escolha = input.nextInt();
						
				if(escolha == 1)	
					addEmployee();
				else if(escolha == 2)
					editEmployee();
				else if(escolha == 3)
					removeEmployee();
				else
				{
					System.out.println("Opcao invalida!!");
					continue;
				}
			}
			else if(realizar == 2)
			{
				System.out.println("Digite: 1(Cartao de Ponto) 2(Venda) 3(Taxa de servico)");
				int lancar = Integer.valueOf( input.nextLine() );
				
				System.out.println("Digite o id: ");
				Empregado emp = database.searchEmployee( Integer.valueOf( input.nextLine() ) );
				if( emp == null ) {
					System.out.println("Usuario inexistente!");
					continue;
				}
				
				if(lancar == 1)
					emp.addPointCard();
				else if(lancar == 2)
					lancarVenda( emp );
				else if(lancar == 3)
				{
					lancarTaxaServico( emp );
				}	
				else
					System.out.println("Numero digitado invalido!");
					continue;
			}
			else if(realizar == 3)
			{
				System.out.println("Digite: 1(Folha de hoje) 2(criacao de agenda)");
				int folha= Integer.valueOf( input.nextLine() );
				
				if(folha == 1)
				{
					System.out.println("Digite o dia: ");
					int day = Integer.valueOf( input.nextLine() );
					System.out.println("Digite o mes: ");
					int month = Integer.valueOf( input.nextLine() );
					System.out.println("Digite o ano: ");
					int year = Integer.valueOf( input.nextLine() );
					
					calendar.set(year, month - 1, day);
					
					payroll.run( calendar.getTime() );
				}
				else if(folha == 2)
				{
					criaAgenda();
				}
				else
				{
					System.out.println("Opcao invalida!!");
					continue;
				}
			}
			else
			{
				System.out.println("Opcao invalida.");
				continue;
			}
			
			System.out.println("Deseja: 0(desfazer) 1(refazer) 2(nenhuma alteracao) ");
			int transacao = input.nextInt();
			
			if(transacao == 0)
			{
				empregados.removeAll(empregados);
				
				empregados.addAll(historico);
				id = empregados.size();
				
				historico.removeAll(empregados);
				System.out.println("Deseja: 1(continuar) ou 0(encerrar) ");
				continuar = input.nextInt();
			}
			else if(transacao == 1)
			{
				empregados.remove(id);
				id = empregados.size();
				continuar = 1;
			}
			else
			{
				System.out.println("Deseja: 1(continuar) ou 0(encerrar) ");
				continuar = input.nextInt();
			}
			//listaEmpregados(empregados);
		}
		System.out.println("Programa encerrado.");
	}
	
	private void editEmployee() {
		
		System.out.println("Digite o id do empregado: ");
		int id =  Integer.valueOf( input.nextLine() );
		Empregado emp = database.searchEmployee(id);
		
		if( emp == null ) {
			System.out.println("Empregado inexistente!");
			return;
		}
		
		System.out.println("Digite 1 para editar as informacoes pessoais do empregado ou 0 para continuar:");
		int option = Integer.valueOf( input.nextLine() );
		if( option == 1 )
			emp = editPersonalInfo(emp);
		
		System.out.println("Digite 1 para editar as informacoes financeiras do empregado ou 0 para continuar:");
		option = Integer.valueOf( input.nextLine() );
		if( option == 1 )
			emp = editFinicialInfo(emp);
		
		System.out.println("Digite 1 para editar as informacoes sindicais do empregado ou 0 para continuar:");
		option = Integer.valueOf( input.nextLine() );
		if( option == 1 )
			emp = editSindicateInfo(emp);					
		
		database.editEmployee(emp);
		System.out.println("Feito!");
		
	}
	
	private void addEmployee() {
		
		Empregado emp = new Empregado();
		emp = editPersonalInfo(emp);
		emp = editFinicialInfo(emp);
		emp = editSindicateInfo(emp);					
		
		database.addEmployee(emp);
		System.out.println("Feito!");
		
	}
	
	private Empregado editPersonalInfo(Empregado emp) {
		
		System.out.println("Digite o nome do empregado:");
		input.nextLine();
		emp.setNome( input.nextLine() );
		
		System.out.println("Digite o endereco:");
		emp.setEndereco( input.nextLine() );
		
		return emp;
		
	}
	
	private Empregado editFinicialInfo(Empregado emp) {
		
		System.out.println("Digite 1(por hora) 2(assalariado) 3 (comissionado)");
		int tipo = Integer.valueOf( input.nextLine() );
		
		if(tipo == 1) {
			
			emp.setTipo(TypeEmp.HOURLY); 
			System.out.println("Valor da hora trabalhada:");	
			emp.setSalarioHora( Float.valueOf( input.nextLine() ) );
			
			
		} else {
		
			System.out.println("Digite o salario fixo: ");
			emp.setSalarioFixo( Float.valueOf( input.nextFloat() ) );
			
			if( tipo == 2 )
				emp.setTipo(TypeEmp.SALARIED); 					
			
			else
				emp.setTipo(TypeEmp.COMISSIONED); 

		}
			
		
		System.out.println("Digite 1(cheque) 2(conta bancaria): ");
		int metodo = Integer.valueOf( input.nextLine() );
		
		if(metodo == 1)
		{
			System.out.println("Receber por 1(correios) ou 2(em maos): ");
			int receber = Integer.valueOf( input.nextLine() );
			if(receber == 1)
				emp.setMetodo(MethodPay.MAIL);
			else if(receber == 2)
				emp.setMetodo(MethodPay.DIRECT);
			else
			{
				System.out.println("Metodo invalido!");
			}	
		}
		else if(metodo == 2)
			emp.setMetodo(MethodPay.ACCOUNT);
		else
		{
			System.out.println("Metodo invalido!");
		}
		
		return emp;
		
	}
	
	private Empregado editSindicateInfo(Empregado emp) {
		
		System.out.println("Pertence ao sindicato?");
		System.out.println("Digite 1(sim) ou 0(nao)");
		int sindicatoInt = Integer.valueOf( input.nextLine() );
		
		if(sindicatoInt == 1) {
			
			emp.setSindicalizado( true );
	
			System.out.println("Digite o id do empregado no sindicato:");
			emp.setIdSindicado( Integer.valueOf( input.nextLine() ) );
			
			System.out.println("Digite a taxa mensal do sindicato: ");
			 emp.setTaxaSind( Float.valueOf( input.nextLine() ) );
			
		}
		
		return emp;
		
	}
	
	private void removeEmployee() {
		
		System.out.println("Digite o id do empregado:");
		int id = input.nextInt();
		input.nextLine();
		if (database.removeEmployee(id) ) 
			System.out.println("Feito!");
		else
			System.out.println("Empregado inexistente!");
		
	}

	private static void cadastrado(ArrayList<Empregado> empregados,
									ArrayList<Venda> vendas, ArrayList<CartaoPonto> cartoes,
									ArrayList<Empregado> historico) 
	{
		Empregado emp1 = new Empregado(10, "Ana", "R.aaaaaa");
		emp1.setAll("assalariado" , 0, 1000f, 0 , "cheque em maos", true, 1, 2, 3);
		empregados.add(emp1);
		
		Empregado emp2 = new Empregado(11, "Fabio", "R. sssss");
		emp2.setAll("horista", 10, 0, 0, "conta bancaria", false, 0, 0, 0);
		Calendar entrada = Calendar.getInstance();
		Calendar saida = Calendar.getInstance();
		emp2.cartao = new CartaoPonto(11);
		
		entrada.set(2018, 2,10,  10, 0);
		saida.set(2018, 2, 10, 20, 0);
		emp2.cartao.entrada = entrada;
		emp2.cartao.saida = saida;
		emp2.PorHora(emp2.cartao);
		cartoes.add(emp2.cartao);
		empregados.add(emp2);
		
		Empregado emp3 = new Empregado(12, "Julia", "R.iiiii");
		emp3.setAll("horista" , 1, 0, 0, "cheque por correios", false, 0, 0, 0);
		emp3.cartao = new CartaoPonto(12);
		
		entrada.set(2018, 02, 16, 7, 30);
		saida.set(2018, 02, 16, 12, 0); 
		emp3.cartao.entrada = entrada;
		emp3.cartao.saida = saida;
		emp3.PorHora(emp3.cartao);
		cartoes.add(emp3.cartao);
		empregados.add(emp3);
		
		Empregado emp4 = new Empregado(13, "Maria", "R.ss.s.s.");
		emp4.setAll("comissionada", 1, 2f, 1000f, "cheque em maos", false, 0 , 0f, 0f);
		emp4.venda = new Venda(13, 17, 02, 2018, 100f, 1f);
		vendas.add(emp4.venda);
		empregados.add(emp4);	
	}

	private static void listaEmpregados(ArrayList<Empregado> empregados)
	{
		for(int i=0; i < empregados.size(); i++)
		{
			Empregado emp = empregados.get(i);
			System.out.printf("Id: %d Nome: %s Rua: %s Tipo: %s Salario total: %s",
					emp.id, emp.nome, emp.endereco, emp.tipo, emp.salarioTotal);
			System.out.println("Sindicalizado: " + emp.sindicalizado);
		}
	}

	private void criaAgenda()
	{		
		PaymentSchedule sched = new PaymentSchedule();
		System.out.println("Digite a nova agenda: ");
		boolean returned = sched.setPaymentSchedule( input.nextLine() );
		if(returned) {
			database.addSchedule(sched);
			System.out.println("Feito");
		} else {
			System.out.println("Entrada invalida");
		}
		
	}

	private static void rodaFolha(ArrayList<Empregado> empregados, ArrayList<Empregado> pagamento) 
	{
		Scanner input = new Scanner(System.in);
		int dia, mes, ano;
		System.out.println("Digite a data de validade do pagamento no formato dd mm yyyy:");
		dia = input.nextInt();
		mes = input.nextInt();
		ano = input.nextInt();
		
		System.out.printf("Pagamento ate %d/%d/%d:\n", dia, mes, ano);
		if(pagamento.size() == 0)
		{
			System.out.println("Sem empregados para serem pagos nesse dia.");
		}
		for(int i = 0; i < pagamento.size(); i++)
		{
			System.out.printf("Nome: %s Salario: %.2f Metodo: %s Tipo: %s Sindicalizado: ", pagamento.get(i).nome, 
					pagamento.get(i).salarioTotal, pagamento.get(i).metodoPagam, pagamento.get(i).tipo);
			System.out.print(pagamento.get(i).sindicalizado);
			System.out.println(" Taxas: "+ (pagamento.get(i).getTaxaServicos()+ pagamento.get(i).taxaSind));
			pagamento.remove(empregados.get(i));	
		}
		
	}

	private static void procuraComissionado(ArrayList<Empregado> empregados, 
			ArrayList<Empregado> pagamento, Date atual, ArrayList<Venda> vendas)
	{
		long dias = 0;
		for(int i =0; i < empregados.size(); i++)
		{
			if(empregados.get(i).primeiroPagamento  == true)
				 dias = ( atual.getTime() - empregados.get(i).ultimoPagamento.getTime()) / 86400000L;	
			
			for(int j=0; j < vendas.size(); j++)
			{
				float salario  = empregados.get(i).salarioFixo;
				if(vendas.get(j).id == empregados.get(i).id)
				{		
					if(empregados.get(i).primeiroPagamento == false)
					{
						salario += empregados.get(i).comissao*vendas.get(j).valor;
						salario -= (empregados.get(i).getTaxaServicos()+ empregados.get(i).taxaSind);
						empregados.get(i).setSalarioTotal(salario);
						empregados.get(i).setUltimoPagamento(atual);
						empregados.get(i).setPrimeiroPagamento(true);
						pagamento.add(empregados.get(i));
						vendas.remove(vendas.get(j));
					}
					else if(dias == 14)
					{
						salario += empregados.get(i).comissao*vendas.get(j).valor;
						salario -= (empregados.get(i).getTaxaServicos()+ empregados.get(i).taxaSind);
						empregados.get(i).setSalarioTotal(salario);
						empregados.get(i).setUltimoPagamento(atual);
						pagamento.add(empregados.get(i));
						vendas.remove(vendas.get(j));
					}
				}
			}	
		}
	}

	private static void procuraAssalariado(ArrayList<Empregado> empregados, Date atual, ArrayList<Empregado> pagamento)
	{	
		float salario;
		for(int i =0; i < empregados.size(); i++)
		{
			if(empregados.get(i).tipo.equals("assalariado"))
			{
				empregados.get(i).setUltimoPagamento(atual);
				salario = empregados.get(i).getSalarioFixo();
				salario -= (empregados.get(i).getTaxaServicos() + empregados.get(i).taxaSind); 
				empregados.get(i).setSalarioTotal(salario);
				pagamento.add(empregados.get(i));
			}
		}	
	}

	private static void procurarHorista(ArrayList<Empregado> empregados, ArrayList<Empregado> pagamento, Date atual, 
			ArrayList<CartaoPonto> cartoes)
	{
		for(int i =0; i < empregados.size(); i++)
		{
			float salario = 0;
			if(empregados.get(i).tipo.equals("horista"))
			{
				for(int j = 0; j < cartoes.size(); j++)
				{
					if(cartoes.get(j).id == empregados.get(i).id)
					{
						empregados.get(i).PorHora(cartoes.get(j));
						salario += cartoes.get(j).salarioDia;
						cartoes.remove(j);
					}
				}
				if(salario >0)
					salario -= (empregados.get(i).getTaxaServicos() + empregados.get(i).taxaSind); 
				empregados.get(i).setSalarioTotal(salario);
				pagamento.add(empregados.get(i));
				empregados.get(i).setUltimoPagamento(atual);
			}
		}
	}

	private void lancarTaxaServico(Empregado emp) 
	{
		System.out.println("Digite a taxa de servico: ");
		emp.addServiceRate( Float.valueOf( input.nextLine() ) );
	
	}

	private void lancarVenda(Empregado emp)
	{
		Venda sale = new Venda();
		
		System.out.println("Digite o valor da venda:");
		sale.setValor(Float.valueOf( input.nextLine() ) );
		System.out.println("Digite o valor da comissao [Ex.: Se for 10%, digite 0.1]: ");
		sale.setComissao(Float.valueOf( input.nextLine() );
		
		emp.addSale(sale);
		database.editEmployee(emp);
	}

	public void lancarCartao(int id, ArrayList<Empregado> empregados, ArrayList<CartaoPonto> cartoes)
	{
		int tem = 0;
		for(int i = 0; i < empregados.size(); i++)
		{
			if(id == empregados.get(i).id)
			{
				Calendar entrada = Calendar.getInstance();
		        Calendar saida = Calendar.getInstance();
		        
				int horas, minutos, dia, mes, ano; 
				System.out.println("Digite a hora de entrada no formato h min");
				horas = input.nextInt();
				minutos = input.nextInt();
				entrada.set(entrada.HOUR_OF_DAY, horas);
				entrada.set(entrada.MINUTE, minutos); 
				
				System.out.println("Digite a data de entrada no formato dd mm yyyy");
				dia = input.nextInt(); 
				mes = input.nextInt();
				ano = input.nextInt();
				entrada.set(Calendar.DATE, dia);
				entrada.set(Calendar.MONTH, mes);
				entrada.set(Calendar.YEAR, ano);
				empregados.get(i).cartao.entrada = Calendar.getInstance();
				empregados.get(i).cartao.entrada = entrada;
				
				System.out.println("Digite a hora de saida no formato h min: ");
				horas = input.nextInt();
				minutos = input.nextInt();
				saida.set(saida.HOUR_OF_DAY, horas);
				saida.set(saida.MINUTE, minutos); 
				System.out.println("Digite a data de saida no formato dd mm yyyy");
				dia = input.nextInt(); 
				mes = input.nextInt();
				ano = input.nextInt();
				saida.set(saida.DATE, dia);
				saida.set(saida.MONTH, mes);
				saida.set(saida.YEAR, ano);
				empregados.get(i).cartao.saida = Calendar.getInstance();
				empregados.get(i).cartao.saida = saida;
				
				cartoes.add(empregados.get(i).cartao);
				tem = 1;
			}
		}
		if(tem == 0)
			System.out.println("Codigo inexistente.");
	}
	
	private static void editando(ArrayList<Empregado> empregados)
	{
		Scanner input = new Scanner(System.in);
		
		int editar;
		System.out.println("Escolha o que deseja editar: ");
		System.out.println("Digite 1(nome) 2(endereco) 3(tipo) 4(metodo de pagamento)");
		System.out.println("5(sindicalizado) 6(id do sindicato) 7(taxa sindical)");
		editar = input.nextInt();
		
		System.out.println("Digite id:");
		int codigo = input.nextInt(); 
		
		if(editar == 1)
		{	
			System.out.println("Digite o novo nome: "); 
			input.nextLine();
			empregados.get(codigo).nome = input.nextLine();
		}
		else if(editar == 2)
		{
			System.out.println("Digite o novo endereco: ");
			input.nextLine();
			empregados.get(codigo).endereco = input.nextLine();
		}
		else if(editar == 3)
		{
			System.out.println("Digite o novo tipo: ");
			input.nextLine();
			empregados.get(codigo).tipo = input.nextLine();
		}
		else if(editar == 4)
		{
			System.out.println("Digite o novo metodo de pagamento");
			input.nextLine();
			empregados.get(codigo).tipo = input.nextLine();
		}
		else if(editar == 5)
		{
			System.out.println("Esta sindicalizado? 1(sim) 0(nao)");
			int resp = input.nextInt();
			
			if(resp == 1)
				empregados.get(codigo).sindicalizado = true;
			else
				empregados.get(codigo).sindicalizado = false;
		}
		else if(editar == 6)
		{
			System.out.println("Digite o novo id no sindicato: ");
			empregados.get(codigo).codigoSindicato = input.nextInt();
		}
		else if(editar == 7)
		{
			System.out.println("Digite a nova taxa sindical:");
			empregados.get(codigo).taxaSind = input.nextFloat();
		}	
		
	}

	private static void removerEmpregado(ArrayList<Empregado> empregados,  String nome) 
	{
		int tem = 0;
		for(int i = 0; i < empregados.size(); i++)
		{
			Empregado emp = empregados.get(i);
			
			if(empregados.get(i).nome.equals(nome))
			{
				empregados.remove(i);
				System.out.println("Removido com sucesso!");
				tem = 1;
				break;
			}
		}
		if(tem ==0)
			System.out.println("Nome inexistente.");
	}

}