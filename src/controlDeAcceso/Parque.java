package controlDeAcceso;

import java.util.concurrent.Semaphore;

public class Parque {

	static int cuenta = 0;
    static Semaphore semaforoMutex = new Semaphore(1);
    
	public static void main(String[] args) {
		
		Torno torno1 = new Torno("Torno 1");
		Torno torno2 = new Torno("Torno 2");
		Torno torno3 = new Torno("Torno 3");

		torno1.start();
        torno2.start();
        torno3.start();
        
        System.out.println("Han entrado "+cuenta+" visitantes");
	}

}
