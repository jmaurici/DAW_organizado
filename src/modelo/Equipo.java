package modelo;

public class Equipo implements Comparable<Equipo>{
	private String nombre;
	private int puntos;
	
	public Equipo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	@Override
	public int compareTo(Equipo equipo) {
		return this.getNombre().compareTo(equipo.getNombre());
	}
}
