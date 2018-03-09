import java.util.ArrayList;
import java.util.Date;

public class Empregado 
{
	private int id;
	private String nome;
	private String endereco;
	private TypeEmp tipo;
	
	private float salarioHora;
	private float salarioFixo;
	private MethodPay metodoPagam;
	private Date ultimoPagamento; 
	
	private PaymentSchedule paymentSchedule;
	
	private boolean sindicalizado;
	private int codigoSindicato;
	private float taxaSind;
	private float taxaServicos;

	private ArrayList<Point> cartao;
	private ArrayList<Venda> sales;
	
	private boolean deleted;
	
	public Empregado() {
		this.setTaxaServicos(0);
		this.deleted = false;
		this.sindicalizado = false;
		this.cartao = new ArrayList<>();
		this.sales = new ArrayList<>();
	}
	
	public ArrayList<Point> getCartao() {
		return cartao;
	}

	public void setCartao(ArrayList<Point> cartao) {
		this.cartao = cartao;
	}

	public ArrayList<Venda> getSales() {
		return sales;
	}

	public void resetSales() {
		this.sales = new ArrayList<>();
	}
	
	public void resetPointsCard() {
		this.cartao = new ArrayList<>();
	}

	public void addPointCard() {
		
		Point point = new Point( this.tipo == TypeEmp.HOURLY );
		
		int size = cartao.size();
		if(size >= 8) {
			//Converte o intervalo de tempo de milisegundos para horas
			long time = (point.getDate().getTime() - cartao.get( size - 8 ).getDate().getTime()) / 3600000; 
			
			if(time < 24)
				point.setOvertime(true);
		}
		
		this.cartao.add(point);
	}
	
	public boolean isDayToPay(Date today) {
		return paymentSchedule.isDayToPay(today, ultimoPagamento);
	}
	
	public void addServiceRate(Float rate) {
		this.setTaxaServicos(this.getTaxaServicos() + rate);
	}
	
	public void addSale(Venda sale) {
		sales.add(sale);
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getNome()
	{
		return this.nome;
	}
	
	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}
	public String getEndereco()
	{
		return this.endereco;
	}
	
	public void setTipo(TypeEmp type)
	{
		this.tipo = type;
		
	}
	public TypeEmp getTipo()
	{
		return this.tipo;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setIdSindicado(int id)
	{
		this.codigoSindicato = id;
	}
	public int getIdSindicato()
	{
		return this.codigoSindicato;
	}

	public void setTaxaSind(float rate)
	{
		this.taxaSind = rate;
	}
	public float getTaxa()
	{
		return this.taxaSind;
	}

	public void setSindicalizado(boolean sind)
	{
		this.sindicalizado = sind;	
	}
	public boolean getSindicalizado()
	{
		return this.sindicalizado;
	}

	public void setMetodo(MethodPay method)
	{
		
		this.metodoPagam = method;
		
	}
	public MethodPay getMetodo()
	{
		return this.metodoPagam;
	}

	public void setSalarioHora(float salario)
	{
		this.salarioHora = salario;
	}
	public float getSalarioHora()
	{
		return this.salarioHora;
	}
	
	public void setSalarioFixo(float salary)
	{
		this.salarioFixo = salary;
	}
	public float getSalarioFixo()
	{
		return this.salarioFixo;
	}

	public void setUltimoPagamento(Date ultimo)
	{
		this.ultimoPagamento = ultimo;
	}
	public Date getUltimoPagamento()
	{
		return this.ultimoPagamento;
	}
	
	public float getTaxaServicos() {
		return taxaServicos;
	}

	public void setTaxaServicos(float taxaServicos) {
		this.taxaServicos = taxaServicos;
	}
}