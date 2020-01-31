package de.tuberlin.sese.swtpp.gameserver.model.ploy;
import de.tuberlin.sese.swtpp.gameserver.model.ploy.PloyPiece;
import de.tuberlin.sese.swtpp.gameserver.model.ploy.PloyPieceState;
public class PloyPlayerState {
	
	PloyPlayer ployPlayers[];
	
	private PloyPieceState pieceState;
	
	public PloyPlayerState(PloyPlayer[] ployPlayers) {
		this.ployPlayers = ployPlayers;
	}
	
	/**
	 * If the players score is zero.
	 * 
	 * @param player
	 * @return true, if the score is zero
	 */
	public boolean isDeadPlayer(int player) {
		return getPlayerScore(player) == 0;
	}
	
	/**
	 * Return whether the given player is out of the game.
	 * 
	 * @param player
	 * @return boolean, whether the given player is out of the game.
	 */
	private boolean isPlayerOut(int player) {
		//
		// if the player has no pieces, they are out of the game
		//
		boolean hasCommander = getPieceState().playerHasCommander(player);
		boolean hasOtherThanCommander = getPieceState().playerHasPiecesOtherThanCommander(player);
		if (!(hasCommander || hasOtherThanCommander)) {
			return true;
		}

		//
		// otherwise, it is a function of the game setup.
		//
		int playersLength = ployPlayers.length;
		if (playersLength == 2) {
			if (!(hasCommander && hasOtherThanCommander)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Computes the total current score for the provided player
	 * 
	 * @param player
	 * @return total current score for the provided player
	 */
	public int getPlayerScore(int player) {
		int ret = 0;
		if (!isPlayerOut(player)) {
			PloyPiece[] pieces = getPieceState().getPlayerPieces(player);
			int piecesLength = pieces.length;
			for (int i=0; i < piecesLength; i++) {
				if (pieces[i].isShown()) {
					ret += pieces[i].getValue();
				}
			}
		}
		return ret;
	}
	
	/**
	 * For a two-player game, return the value of the other team index
	 * 
	 * @param player index
	 * @return the other player index
	 */
	public int otherPlayer(int player) {
		int ret = -1;
		switch (player) {
			case 0:
				ret = 2;
				break;
			case 1:
				ret = 3;
				break;
			case 2:
				ret = 0;
				break;
			case 3:
				ret = 1;
				break;
		}
		return ret;
	}
	
	/**
	 * For a two-player game, return the value of the other team index
	 * 
	 * @param team index
	 * @return the other team index
	 */
	public int otherTeam(int team) {
		if (team == 0)
			return 1;
		else
			return 0;
	}
	
	
	public PloyPlayer getPloyPlayer(int i) {
		return ployPlayers[i];
	}
	
	public PloyPlayer[] getPloyPlayers() {
		return ployPlayers;
	}

	public void setPloyPlayers(PloyPlayer[] ployPlayers) {
		this.ployPlayers = ployPlayers;
	}
	
	public PloyPieceState getPieceState() {
		return pieceState;
	}

	public void setPieceState(PloyPieceState pieceState) {
		this.pieceState = pieceState;
	}
}
