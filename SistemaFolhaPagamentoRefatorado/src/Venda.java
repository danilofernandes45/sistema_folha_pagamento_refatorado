import java.util.Date;

public class Venda
{
	private Date data;
	private float valor;
	private float comissao;
	
	public Venda() {
		this.data = new Date();
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getComissao() {
		return comissao;
	}

	public void setComissao(float comissao) {
		this.comissao = comissao;
	}
	
	public float getValueCommission() {
		return valor*comissao;		
	}
}