package modelos;

import java.time.LocalDate;

import bbdd.BD_Transportes;

public class Abono {

	BD_Transportes bd=new BD_Transportes("transportes");
	private int numero;
	private LocalDate fecha;
	private int viajes;
	protected String nif;
	private double dineroGastado;
	private static double dineroGastadoFN;
	
	public Abono(int numero, LocalDate fecha, int viajes, String nif) {
		super();
		this.numero = numero;
		this.fecha = fecha;
		this.viajes = viajes;
		this.nif = nif;
	}

	public int getNumero() {
		return numero;
	}
	
	public int getViajes() {
		return this.viajes;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public void setDineroGastado(double d) {
		this.dineroGastado = this.dineroGastado+d;
	}

	public void setViajes(int viajes) {
		this.viajes = viajes;
	}

	public boolean realizarViaje() {
		
		if(this.viajes==0) return false;
		else {
			try {
				this.viajes--;
				bd.restarViaje(this);
			} catch (Exception e) {
				System.err.println("Error: "+e.getMessage());
			}
			return true;
		}
	}

	@Override
	public String toString() {
		return "[numero=" + numero + ", fecha=" + fecha + ", viajes=" + viajes + ", nif=" + nif + "]";
	}
	
}
