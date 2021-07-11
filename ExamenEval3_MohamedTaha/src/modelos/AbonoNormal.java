package modelos;

import java.time.LocalDate;

public class AbonoNormal extends Abono {

	public AbonoNormal(int numero, LocalDate fecha, int viajes, String nif) {
		super(numero, fecha, viajes, nif);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AbonoNormal "+super.toString();
	}
	
}
