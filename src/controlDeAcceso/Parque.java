package controlDeAcceso;

import java.util.concurrent.Semaphore;

public class Parque {

	// Creamos un semaforo para controlar el acceso a recursos compartidos
	public static Semaphore semaforoMutex = new Semaphore(1);

	public static void main(String[] args) {

		// Crearmos instancias de los 3 tornos
		Torno torno1 = new Torno("Torno 1");
		Torno torno2 = new Torno("Torno 2");
		Torno torno3 = new Torno("Torno 3");

		// Iniciamos los hilos de los tornos
		torno1.start();
		torno2.start();
		torno3.start();

		try {
			// Esperamos a que cada hilo de torno complete su ejecución
			torno1.join();
			torno2.join();
			torno3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Y por último imprimimos el total de visitantes cuando todos los tornos han
		// terminado
		System.out
				.println("\n¡Han entrado en total " + Visitante.getContadorGlobal() + " visitantes en el día de hoy!");

	}
}
