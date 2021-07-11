package modelos;

import java.time.LocalDate;

public class Usuario {

	private String nif;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private int familiaNumerosa;
	
	public Usuario(String nif, String nombre, String apellido, LocalDate fechaNacimiento, int familiaNumerosa) {
		super();
		this.nif = nif;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.familiaNumerosa = familiaNumerosa;
	}

	public String getNif() {
		return nif;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public int getFamiliaNumerosa() {
		return familiaNumerosa;
	}
	
}
