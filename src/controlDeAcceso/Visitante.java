package controlDeAcceso;

public class Visitante extends Thread {

	// contador de todas las visitas que acceden al parque
	private static int contadorGlobal = 0;

	// Número específico de este visitante
	private int numeroVisitantes;

	public Visitante(String nombre) {
		super(nombre);

		// Incrementamos el contador global y asignamos el valor al número del visitante
		this.numeroVisitantes = ++contadorGlobal;
	}

	public int getNumeroVisitantes() {
		return numeroVisitantes;
	}

	public static int getContadorGlobal() {
		return contadorGlobal;
	}
}
