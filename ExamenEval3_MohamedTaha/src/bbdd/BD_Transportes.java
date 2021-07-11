
package bbdd;

import java.sql.*;
import java.util.*;

import modelos.Abono;
import modelos.AbonoMensual;
import modelos.AbonoNormal;
import modelos.Usuario;

public class BD_Transportes extends BD_Conector {
		private static Statement s;		
		private static ResultSet reg;
		private static Statement s1;		
		private static ResultSet reg1;
		
	public BD_Transportes(String bbdd){
		super (bbdd);
	}
	
public Vector<Abono> guardarAbonos() throws Exception{
		
		Vector<Abono> abonos=new Vector<Abono>();
		String cadena="SELECT * FROM abonos";
		Usuario u=null;
		
		try{
			this.abrir();
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			
			while (reg.next()) {
				
				if(reg.getString(4)==null) {
					abonos.add(new AbonoNormal(reg.getInt("numero"),reg.getDate(2).toLocalDate(),reg.getInt(3),reg.getString(4)));
				} else {
					
					try{
						u=getUsuario(reg.getString(4));
					}catch (Exception e) {
						System.err.println("Error: "+e.getMessage());
					}
					
					abonos.add(new AbonoMensual(reg.getInt("numero"),reg.getDate(2).toLocalDate(),reg.getInt(3),reg.getString(4),u));
				}
				
			}
			
			s.close();
			this.cerrar();
			return abonos;
		}
		catch (SQLException e){
			throw new Exception("Error al guardar los abonos en un vector");
		}

	}

	public Usuario getUsuario(String nif) throws Exception{
		
		String cadena="SELECT * FROM usuarios WHERE nif = '"+nif+"'";
		Usuario u=null;
		
		try{
			this.abrir();
			s1=c.createStatement();
			reg1=s1.executeQuery(cadena);
			
			if (reg1.next()) {
				u=new Usuario(reg1.getString(1), reg1.getString(2), reg1.getString(3),reg1.getDate(4).toLocalDate(),reg1.getInt(5));
			}
			
			s1.close();
			this.cerrar();
			
			if(u==null) throw new Exception("Error al obtener los datos del usuario del abono mensual para el vector de abonos");
			else return u;
		}
		catch (SQLException e){
			throw new Exception("Error al obtener los datos del usuario del abono mensual para el vector de abonos");
		}
	}
	
	public void restarViaje(Abono a) throws Exception {
		
		String cadena="UPDATE abonos SET viajes = "+a.getViajes()+" WHERE numero = "+a.getNumero(); 	
		
		try{
			this.abrir();
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
			this.cerrar();
		} catch (Exception e){
			this.cerrar();
			System.err.println("Error al restar el viaje");
		}
		
	}
}

