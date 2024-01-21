package controlDeAcceso;

import java.util.concurrent.Semaphore;

public class Parque {

	////// AÑADIDO///////
	/*
	 * Delimitamos la entrada al parque con dos factores, el tiempo que está abierto
	 * el parque y el aforo que tiene por torno, si aumentamos el tiempo aumentremos
	 * el aforo por torno este aforo se genera de manera aleatioria en cada torno ya
	 * que no controlamos la salida de los visitantes, de esta manera el parque se
	 * cierra por dos motivos, o bien por el tiempo o bien por que se ha completado
	 * el aforo de ese día
	 */

	// Creamos un semáforo para controlar el acceso a recursos compartidos
	//public static Semaphore semaforoMutex = new Semaphore(1);

	public static void main(String[] args) {
		// Determinamos el tiempo que el parque estará abierto
		final long tiempoLimite = System.currentTimeMillis() + 30000;

		// Crearmos instancias de los 3 tornos
		Torno torno1 = new Torno("Torno 1", tiempoLimite);
		Torno torno2 = new Torno("Torno 2", tiempoLimite);
		Torno torno3 = new Torno("Torno 3", tiempoLimite);

		// Iniciamos los hilos de los tornos
		torno1.start();
		torno2.start();
		torno3.start();

		try {
			// Esperamos a que el tiempo límite se alcance.
			while (System.currentTimeMillis() < tiempoLimite) {
				// Verificar si todos los tornos se han detenido pero el tiempo límite no ha
				// terminado
				if (!torno1.isAlive() && !torno2.isAlive() && !torno3.isAlive()
						&& (System.currentTimeMillis() < tiempoLimite)) {
					System.out.println("\n¡Aforo completo! Se cerraron los tornos antes de que terminara el tiempo.");
					// Si ya se ha cumplido el aforo salimos del bucle, el parque se cierra.
					break;
				}
				Thread.sleep(1000); // Espera de 1 segundo antes de volver a verificar
			}

			// Detenemos los tornos después de que ha pasado el tiempo límite
			torno1.detenerTorno();
			torno2.detenerTorno();
			torno3.detenerTorno();

			// Esperamos a que cada hilo de torno complete su ejecución
			torno1.join();
			torno2.join();
			torno3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Y por último imprimimos el total de visitantes cuando todos los tornos han
		// terminado

		System.out.println("\n¡Han entrado en total " + Visitante.getContadorGlobal() + " visitantes en el día de hoy!");

	}
}
