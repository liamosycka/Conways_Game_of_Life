import java.util.LinkedList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class World {
	private Cell[][] board;
	private int height, width;
	private LinkedList<Task> listaTasks;
	private ExecutorService executor;
	private CyclicBarrier barrier;

	public World(int i, int j) {
		board = new Cell[i][j];
		this.height = i;
		this.width = j;
		barrier = new CyclicBarrier(3);
		this.crearCelulas();
		this.establecerVecinos();

		// establezo celulas vivas iniciales
		/*Snake pattern*/
		board[13][12].setActualState(true);
		board[13][13].setActualState(true);
		board[13][14].setActualState(true);
		board[13][15].setActualState(true);
		board[13][16].setActualState(true);
		board[13][17].setActualState(true);
		board[13][18].setActualState(true);
		board[13][19].setActualState(true);
		board[13][20].setActualState(true);
		board[13][21].setActualState(true);
		
		//exploder pattern
		/*board[13][12].setActualState(true);
		board[14][12].setActualState(true);
		board[15][12].setActualState(true);
		board[12][12].setActualState(true);
		board[11][12].setActualState(true);
		board[11][14].setActualState(true);
		board[11][16].setActualState(true);
		board[12][16].setActualState(true);
		board[13][16].setActualState(true);
		board[14][16].setActualState(true);
		board[15][16].setActualState(true);
		board[15][14].setActualState(true);*/
		
		

	}

	private void setExecutor() {
		int cantTasks = 3;
		executor = Executors.newFixedThreadPool(cantTasks);
		listaTasks = new LinkedList<Task>();
		int columnaInicial = 0;
		int cantCadaHilo = 27 / cantTasks;
		int columnaFinal = cantCadaHilo - 1;
		for (int i = 0; i < cantTasks; i++) {
			Task task = new Task(i, 27, columnaInicial, columnaFinal, board, barrier);
			columnaInicial += cantCadaHilo;
			columnaFinal += cantCadaHilo;
			listaTasks.add(task);
		}
	}

	public void startGame() {
		this.setExecutor();
		TurnsManager turnsManager = new TurnsManager(this, listaTasks, board);
		turnsManager.run();

	}

	public void passTurn() {

		for (int i = 0; i < listaTasks.size(); i++) {
			executor.execute(listaTasks.get(i));
		}

	}

	private void crearCelulas() {
		for (int k = 0; k < height; k++) {
			for (int m = 0; m < width; m++) {
				board[k][m] = new Cell();
			}
		}
	}

	private void establecerVecinos() {
		for (int k = 0; k < height; k++) {
			for (int m = 0; m < width; m++) {
				asignarVecinos(k, m);

			}
		}
	}

	private void asignarVecinos(int k, int m) {
		int i = k, j = m;
		Cell cellActual = board[k][m];
		// 1er vecino
		i = k - 1;
		if (i < 0) {
			i = height - 1;
		}

		cellActual.asignarVecino(board[i][j]);
		// 2do vecino
		i = k + 1;
		if (i >= height) {
			i = 0;
		}
		cellActual.asignarVecino(board[i][j]);
		// 3er vecino
		i = k;
		j = m - 1;
		if (j < 0) {
			j = width - 1;
		}
		cellActual.asignarVecino(board[i][j]);
		// 4to vecino
		j = m + 1;
		if (j >= width) {
			j = 0;
		}
		cellActual.asignarVecino(board[i][j]);
		// 5to vecino
		i = k - 1;
		j = m - 1;
		if (i < 0) {
			i = height - 1;
		}
		if (j < 0) {
			j = width - 1;
		}
		cellActual.asignarVecino(board[i][j]);
		// 6to vecino
		i = k + 1;
		j = m + 1;
		if (i >= height) {
			i = 0;
		}
		if (j >= width) {
			j = 0;
		}
		cellActual.asignarVecino(board[i][j]);
		// 7mo vecino
		i = k + 1;
		j = m - 1;
		if (i >= height) {
			i = 0;
		}
		if (j < 0) {
			j = width - 1;
		}
		cellActual.asignarVecino(board[i][j]);
		// 8vo vecino
		i = k - 1;
		if (i < 0) {
			i = height - 1;
		}
		j = m + 1;
		if (j >= width) {
			j = 0;
		}
		cellActual.asignarVecino(board[i][j]);

	}

	public String showWorld() {
		String cad = "";
		for (int k = 0; k < height; k++) {
			cad += "	|    ";
			for (int m = 0; m < width; m++) {
				// Verifico el estado de la celula (viva "O "/ muerta "-" )
				if (board[k][m].getActualState()) {
					cad += "O     ";
				} else {
					cad += ".     ";
				}
			}

			cad += "   |";
			cad += "\n";
		}
		return cad;
	}

}
