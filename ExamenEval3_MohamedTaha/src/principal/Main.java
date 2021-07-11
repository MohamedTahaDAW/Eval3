package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Vector;

import bbdd.BD_Transportes;
import modelos.Abono;
import modelos.AbonoMensual;
import modelos.AbonoNormal;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BD_Transportes bd=new BD_Transportes("transportes");
		Scanner sc=new Scanner(System.in);
		Vector<Abono> abonos=new Vector<Abono>();
		//
		int menu=0;
		int numero=0, pos=0;
		LocalDate fecha;
		int opc=0;
		int numCargar = 0;
		double importe;
		// PROGRAMA
		
		try {
			abonos=bd.guardarAbonos();
		} catch (Exception e1) {
			System.err.println("ERROR: "+e1.getMessage());
		}
		
		for(int i=0; i<abonos.size(); i++) {
			System.out.println(abonos.get(i).toString());
		}
		
		while(menu!=-1) {
			
			mostrarMenu();
			try{
				menu=sc.nextInt();
			}catch(Exception e) {
				System.err.println("Error, opcion no valida");
				break;
			}
			
			switch(menu) {
			
			case 1:
				try {
					pos=buscarAbono(numero, abonos, sc);
				} catch (ErrorLeyendoAbono e) {
					System.err.println("Error: "+e.getMessage());
					break;
				}
				if(abonos.get(pos).realizarViaje()==false) {
					System.out.println("No se puede realizar el viaje, no quedan viajes disponibles.");
					System.out.println("¿Quieres cargar el abono? (1. SI, 2. NO)");
					try {
						opc=leerInt(sc);
					} catch (Exception e) {
						System.err.println("Error: "+e.getMessage());
					}
					
					if(opc==1) {
						
						System.out.println("Anota cuantos viajes quieres cargar, el numero anotado se multiplicara por 10");
						try {
							numCargar=leerInt(sc);
						} catch (Exception e) {
							System.err.println("Error: "+e.getMessage());
						}
						importe=calcImporte(abonos.get(pos), numCargar);
						numCargar=numCargar*10;
						System.out.println("Se han cargado "+numCargar+" viajes con un importe de "+importe);
						abonos.get(pos).setDineroGastado(importe);
						abonos.get(pos).setViajes(abonos.get(pos).getViajes()+numCargar);
					} else break;
					
					break;
				}
				System.out.println("Viaje realizado correctamente, 1 viaje restado de tu tarjeta");
				
				break;

			case 2:
				sc.nextLine();
				System.out.println("Introduce fecha en formato d/mm/yyyy");
				String fechaString=sc.nextLine();
				try {
					fecha=validarFecha(fechaString);
				} catch (Exception e) {
					System.err.println("Error: "+e.getMessage());
					break;
				}
				System.out.println("Fecha leida: "+fecha);
				
				int borrados=0;
				for(int j=0; j<abonos.size(); j++) {
					
					if(abonos.get(j).getViajes()==0 && abonos.get(j).getFecha().isBefore(fecha)) {
						abonos.remove(j);
						borrados++;
					}
				}
				System.out.println("Se han borrado "+borrados+" vectores. Quedan los siguientes:");
				for(int j=0; j<abonos.size(); j++) {
					
					System.out.println(abonos.get(j).toString());
				}
				break;

			case -1:
				break;

			default:
				System.out.println("Opcion no valida");
				break;
				
			}
			
		}
		
	}

	private static void mostrarMenu() {
		
		System.out.println("------------------- MENU -------------------");
		System.out.println("1. Buscar abono y devolver posicion");
		System.out.println("2. Eliminar abonos no recagrados y anteriores a una fecha");
		System.out.println("-1. Salir");
	}

	public static int buscarAbono (int numero, Vector <Abono> abonos, Scanner sc) throws ErrorLeyendoAbono {

		int intentos=3;
		boolean encontrado=false;
		
		while(intentos>0) {
			sc.nextLine();
			try{
				System.out.println("Introduce numero del abono a buscar");
				numero=sc.nextInt();
			}catch(Exception e) {
				intentos--;
				System.out.println("Dato introducido no valido, te quedan "+intentos+" intentos");
			}finally {
					for(int i=0; i<abonos.size(); i++) {
						if(abonos.get(i).getNumero()==numero) {
							return i;
						}
					}
					intentos--;
					System.out.println("No se ha encontrado ningun abono con ese numero, te quedan "+intentos+" intentos");
			}
		}
		
		throw new ErrorLeyendoAbono("No se ha encontrado ningun abono");
	}
	
	public static int leerInt(Scanner sc) throws Exception {

		int num=0;
		boolean correcto=false;
		do {
			try {
				num=sc.nextInt();
				correcto=true;
			}catch(Exception e) {
				sc.nextLine();
				System.out.println("Tienes que introducir un número");
			}
		}while(correcto==false);
		return num;
	}
	
	public static LocalDate validarFecha(String fechaString) throws Exception {

		LocalDate fecha;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/MM/yyyy");

		try {
			fecha = LocalDate.parse(fechaString, formato);
		}catch (Exception e) {
			throw new Exception("Error al validar la fecha");
		}
		return fecha;

	}
	
	public static double calcImporte(Abono a, int numCargar) {
		
		double importe=0;
		
		if(a instanceof AbonoNormal) {
			importe=12*numCargar;
		} else {
			return 20;
		}
		
		return importe;
	}
	
}
