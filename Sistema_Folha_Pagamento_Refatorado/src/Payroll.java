import java.util.ArrayList;
import java.util.Date;

public class Payroll {

	private static Payroll payroll = new Payroll();;
	private Database database = Database.getInstance();;
	private static final int MILLISECONDS_PER_HOUR = 3600000;
	
	private Payroll() {}
	
	public static Payroll getInstance() {
		return payroll;
	}
	
	public void run(Date today) {
		
		ArrayList<Empregado> employees = database.getEmployees();
		
		float amountCommision;
		float amountSalary;
		float amountHourly;
		float grossSalary;
		float liquidSalary;
		float sindRate;
		
		float total = 0;
		
		for(Empregado emp : employees) {
			
			if(emp.isDayToPay(today)) {
				
				amountCommision = 0;
				amountSalary = 0;
				amountHourly = 0;
				
				for(Venda sale : emp.getSales()) {
					amountCommision += sale.getValueCommission();
				}
				
				emp.resetSales();
				
				ArrayList<Point> cartao = emp.getCartao();
				int sizePoints = cartao.size();
				for(int i=0; i<sizePoints; i++ ) {
					
					Point point = cartao.get(i);
					if( point.isHourly() ) {
						
						amountHourly += 1;
						if( ( i - 8 ) >= 0 && isSameDay( cartao.get( i - 8 ), point ) ) 
							amountHourly += 0.5;
					} else {
						amountSalary += 1;
					}
					
				}
				
				grossSalary = amountCommision + amountHourly * emp.getSalarioHora() + amountSalary * avarageSalary( emp );
				sindRate = emp.getTaxa() + emp.getTaxaServicos();
				liquidSalary = grossSalary - sindRate;
				total += grossSalary;
				
				emp.resetPointsCard();
				emp.setUltimoPagamento( today );
				
				System.out.println("---------------------------------------------");
				System.out.printf("[%d] %s\n"
								+ "     Salario: R$ %.2f\n"
								+ "     Descontos sindicais: R$ %.2f\n"
								+ "     Salario liquido: R$ %.2f\n", grossSalary, sindRate, liquidSalary);
				System.out.println("---------------------------------------------");
				
			}
			
		}
		
		System.out.printf("TOTAL : R$ %.2f\n", total);
		database.setEmployees(employees);
		
	}
	
	private float avarageSalary( Empregado emp ) {
		return emp.getSalarioFixo() / 25;		
	}
	
	private boolean isSameDay(Point p1, Point p2) {
		
		long hoursDistance = ( p1.getDate().getTime() - p2.getDate().getTime() ) / MILLISECONDS_PER_HOUR;
		if( hoursDistance < 24 )
			return true;
		return false;
		
	}
	
}
