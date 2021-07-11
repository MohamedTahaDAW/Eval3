package modelos;

import java.time.LocalDate;

public class AbonoMensual extends Abono {
	
	private Usuario u;
	
	public AbonoMensual(int numero, LocalDate fecha, int viajes, String nif, Usuario u) {
		super(numero, fecha, viajes, nif);
		this.u = u;
	}

	public Usuario getU() {
		return u;
	}

	@Override
	public String toString() {
		return "AbonoMensual "+super.toString()+ " Usuario ["+ this.u.getNombre() + "]";
	}
	
}
