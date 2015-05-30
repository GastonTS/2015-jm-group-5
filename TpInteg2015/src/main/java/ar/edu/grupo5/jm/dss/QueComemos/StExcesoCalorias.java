package ar.edu.grupo5.jm.dss.QueComemos;

public class StExcesoCalorias implements StFiltro {
	public boolean filtrar(Receta unaReceta, Usuario unUsuario) {
		return unaReceta.getCantCaloriasTotales() < 500;
	}
}
