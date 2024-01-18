package controlDeAcceso;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Torno extends Thread {

	// Creamos el contador de visitantes que han pasado por cada uno de los tornos
	private int flujo = 0;

	////// AÑADIDO///////
	// Tambien creamos Queue<Visitante> para almacenar visitantes que están
	////// esperando para simular una "cola"
	private Queue<Visitante> cola = new LinkedList<>();

	public Torno(String nombre) {
		super(nombre);
	}

	public void setFlujo(int flujo) {
		this.flujo = flujo;
	}

	////// AÑADIDO///////
	// Hemos decidido crear un metodo que genere número aleatorios del 1 al 10,
	////// hacemos esto para simular la llegada de visitantes a los tornos de forma
	////// aleatoria. Así no entrará el mismo número de visitantes por un torno que
	////// por otro
	static int generarNumeroAleatorio() {
		Random random = new Random();
		int numeroAleatorio = random.nextInt(10) + 1;
		return numeroAleatorio;
	}

	public void run() {

		// Realizamos el bucle el numero de veces que se genere en la función.
		for (int i = 0; i < generarNumeroAleatorio(); i++) {
			try {

				////// AÑADIDO///////
				// Llamamos al semáfono para que uno a uno se vayan poninendo en la cola de
				////// entrada
				Parque.semaforoMutex.acquire();

				// Creamos un nuevo visitante
				Visitante visitante = new Visitante("Visitante");
				// Y lo agregregamos a la cola
				cola.add(visitante);

				System.out.println("El visitante " + visitante.getNumeroVisitantes() + " está en la cola.");

				// Liberamos el semáforo para permitir que otros visitantes accedan
				Parque.semaforoMutex.release();

				// Y añadimos un sleep para simular la espera en la cola antes de entrar
				sleep(1000);

				// Verificamos si hay visitantes en la cola antes de pasar por el torno
				if (!cola.isEmpty()) {
					// Llamamos de nuevo al semaforo esta vez para dejar pasar de uno en uno a los
					// visitantes que ya están en la cola
					Parque.semaforoMutex.acquire();

					// Incrementamos el contador de flujo
					flujo++;

					// Guardamos el visitante que está pimero en la cola y lo borramos de esta con
					// poll()
					Visitante visitanteEnCola = cola.poll();

					// Indicamos por qué torno está pasando qué visitante
					System.out.println("\n__" + getName() + " : " + visitanteEnCola.getName() + " "
							+ visitanteEnCola.getNumeroVisitantes() + "__");

					// Y dejamos libre el torno
					Parque.semaforoMutex.release();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		////// AÑADIDO///////
		// Por último mostramos el número de visitantes que han entrado por el torno en
		////// concreto.

		System.out.println("\n - Ha entrado " + flujo + " visitantes por el " + getName());

	}
}
