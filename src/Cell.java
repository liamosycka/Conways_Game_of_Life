import java.util.LinkedList;

public class Cell {
	private boolean actualState, nextState;
	private LinkedList<Cell> neighbors;
	public Cell() {
		this.actualState = false;
		this.nextState=actualState;
		neighbors = new LinkedList<Cell>();
	}

	public void asignarVecino(Cell cellVecina) {
		neighbors.add(cellVecina);

	}
	public int getVecinos() {
		
		int cantVivas = 0;
		for (int i = 0; i < neighbors.size(); i++) {
			// veo si la celula vecina esta viva
			if (neighbors.get(i).getActualState()) {
				cantVivas++;
			}
		}
		return cantVivas;
	}

	public void determineNextState() {
		
		int cantVivas = 0;
		for (int i = 0; i < neighbors.size(); i++) {
			// veo si la celula vecina esta viva
			if (neighbors.get(i).getActualState()) {
				cantVivas++;
			}
		}
		// primero veo en que estado esta la celula
		if (actualState) {
			/*
			 * si la celula esta viva, veo si tiene 2 o 3 celulas vecinas vivas. Si esto
			 * ocurre, la celula sigue viviendo, sino muere
			 */
			if (cantVivas == 2 || cantVivas == 3) {
				
			} else {
				// asigno como proximo estado a la celula como MUERTA.
				this.setNextState(false);
			
			}
		} else {

			/*
			 * si la celula esta muerta, veo si tiene exactamente 3 celulas vecinas vivas.
			 * Si esto ocurre, la celula revive-
			 */
			if (cantVivas == 3) {

				this.setNextState(true);
			}
		}
	}

	public void setActualState(boolean actualState) {
		this.actualState = actualState;
		this.nextState=actualState;
	}

	public void setNextState(boolean newState) {
		this.nextState = newState;
	}

	public void updateState() {
		this.actualState = nextState;
	}

	public boolean getActualState() {
		return this.actualState;
	}

}
