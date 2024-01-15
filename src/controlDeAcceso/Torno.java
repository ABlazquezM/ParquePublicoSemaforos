package controlDeAcceso;

import java.util.Random;

public class Torno extends Thread {
	
	private int flujo=0;
    
	public Torno(String nombre) {
		super(nombre);
    }

	 public void setFlujo(int flujo) {
		this.flujo = flujo;
	}

	 static int generarNumeroAleatorio() {
	        Random random = new Random();
	        int numeroAleatorio = random.nextInt(10) + 1;
	        return numeroAleatorio;
	    }
	 

	public void run() {
         for(int i = 0;i<generarNumeroAleatorio();i++) {
             try {
                 // Incrementa el contador de visitantes de manera segura con el semáforo
                 Parque.semaforoMutex.acquire();
                 flujo++;
                 Parque.cuenta++;

                 // Crea un nuevo visitante y muestra el mensaje
                 Visitante visitante = new Visitante("Visitante");
                 System.out.println(getName()+" : " + visitante.getName() + " ha entrado. Total: " + Parque.cuenta);
                 Parque.semaforoMutex.release();

             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         
         System.out.println("\nHa entrado "+flujo+" visitantes por el "+getName());
     
     }
    
 
}
