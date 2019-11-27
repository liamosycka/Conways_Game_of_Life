import java.util.LinkedList;

public class TurnsManager implements Runnable {
	private LinkedList<Task> lista;
	private Cell[][] board;
	private World world;

	public TurnsManager(World world, LinkedList<Task> lista, Cell[][] board) {
		this.world = world;
		this.lista = lista;
		this.board = board;
	}

	public void run() {

		while (true) {
			System.out.println(world.showWorld());
			world.passTurn();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
