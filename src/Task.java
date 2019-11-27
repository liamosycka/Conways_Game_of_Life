import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class Task implements Runnable {
	private int limHeight, limWidth, columnaInicial, columnaFinal;
	private int id;
	private Cell[][] board;
	private CyclicBarrier barrier;

	public Task(int id,int rows, int columnaInicial, int columnaFinal, Cell[][] board, CyclicBarrier barrier) {
		// this.limWidth=columns;
		this.columnaInicial = columnaInicial;
		this.columnaFinal = columnaFinal;
		this.limHeight = rows;
		this.board = board;
		// el valor del parametro de la barrera es la cantidad de submundos que se
		// deberan recorrer.
		this.barrier = barrier;
		this.id=id;
	}

	/*
	 * Como cada hilo tiene asignado una cantidad de columnas, recorro todas las
	 * filas del tablero de esas columnas. Como se divide en 2 procesos, la idea es
	 * que primero todos los hilos recorran las secciones asignadas y hagan que cada
	 * celula vea cual es su proximo estado y lo almacenen ( sin modificarlo ), al
	 * terminar de recorrer el espacio asignado chocan una cyclic barrier, a la
	 * espera de que se haya recorrido todo el tablero. Una vez que se rompa esta
	 * barrera, cada hilo volvera a recorrer su espacio asignado haciendo esta vez
	 * que cada celula cambie su estado.
	 */

	public void run() {
		/*
		 * recorro el submundo asignado para que cada celula determine su proximo
		 * estado.
		 */
		for (int i = 0; i < limHeight; i++) {
			for (int j = columnaInicial; j <= columnaFinal; j++) {
				
				board[i][j].determineNextState();
			}
		}
		try {
			barrier.await();

		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Vuelvo a recorrer el submundo asignado para que cada celula CAMBIE su estado
		 * actual.
		 */
		for (int i = 0; i < limHeight; i++) {
			for (int j = columnaInicial; j <= columnaFinal; j++) {
				
				board[i][j].updateState();
			}
		}

	}
}
