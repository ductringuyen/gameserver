package de.tuberlin.sese.swtpp.gameserver.model.ploy;
import java.util.ArrayList;
import java.util.Iterator;
import de.tuberlin.sese.swtpp.gameserver.model.ploy.GamePoint;
import de.tuberlin.sese.swtpp.gameserver.model.ploy.PloySetup;
public class PloyPieceState {
	
	private int rowCount;

	private int colCount;

	private int pieceCount;

	private ArrayList pieceArray;
	
	PloyPiece[] pieces;

	public PloyPieceState(PloySetup ploySetup, int rowCount, int colCount) {
		setPieces(ploySetup.getPieces());
		this.rowCount = rowCount;
		this.colCount = colCount;
	}

	public int getCols() {
		return colCount;
	}

	public void setCols(int cols) {
		this.colCount = cols;
	}

	public int getRows() {
		return rowCount;
	}

	public void setRows(int rows) {
		this.rowCount = rows;
	}
	
	/**
	 * @param rowColumnPoint
	 * @return boolean - returns true, if the specified GamePoint is within the game space, false otherwise.
	 */
	public boolean permissableRowColumn(GamePoint rowColumnPoint) {
		int col = rowColumnPoint.getCol();
		int row = rowColumnPoint.getRow();
		return ((col >= 1 && col <= colCount) && (row >= 1 && row <= rowCount));
	}

	/**
	 * Method for determining if pieces exist between two game points
	 * In Ploy, pieces may not be jumped over, regardless of the piece's team.
	 * @param rowColumn1
	 * @param rowColumn2
	 * @return int
	 */
	public int piecesBetweenRowColumns(GamePoint rowColumn1,
			GamePoint rowColumn2) {
		int ret = 0;
		//
		// delta
		//
		int dx = rowColumn2.getCol() - rowColumn1.getCol();
		int dy = rowColumn2.getRow() - rowColumn1.getRow();
		
		//
		// sign
		//
		int ax = 0;
		int ay = 0;
		if (dx != 0) {
			ax = (int) (dx / Math.abs(dx));
		}
		if (dy != 0) {
			ay = (int) (dy / Math.abs(dy));
		}
		
		//
		// iterate
 		//
		int maxi = (int) Math.max(Math.abs(dx), Math.abs(dy));
		GamePoint rcPoint = new GamePoint(0, 0);
		for (int i = 1; i < maxi; i++) {
			rcPoint.setCol((int) (rowColumn1.getCol() + i * ax));
			rcPoint.setRow((int) (rowColumn1.getRow() + i * ay));
			if (getPieceAtRowColumn(rcPoint) != null) {
				ret++;
			}
		}
		return ret;
	}
	
	/**
	 * @param rcPoint GamePoint
	 * @return PloyPiece the piece at the given GamePoint
	 */
	public PloyPiece getPieceAtRowColumn(GamePoint rcPoint) {
		for (int i = 0; i < pieceCount; i++) {
			if (pieces[i].isShown() && pieces[i].atRowColumn(rcPoint)) {
				return pieces[i];
			}
		}
		return null;
	}

	public int getPieceCount() {
		return pieceCount;
	}

	public void setPieceCount(int pieceCount) {
		this.pieceCount = pieceCount;
	}
	
	/**
	 * Returns the piece contained by the given screen coordinates.
	 * 
	 * @param x - location in screen pixels
	 * @param y - location in screen pixels
	 * @return PloyPiece
	 */
	public PloyPiece getPieceAtXY(int x, int y) {
		// reverse loop starts with the front-most object
		for (int i = pieceCount-1; i >=0; i--) {
			if (pieces[i].containsXY(x, y)) {
				return pieces[i];
			}
		}
		return null;
	}
	
	/**
	 * Return the pieces for a given player.
	 * @param player
	 * @return the pieces for the given player.
	 */
	public PloyPiece[] getPlayerPieces(int player) {
		ArrayList list = new ArrayList();
		for (int i=0; i<pieceCount; i++) {
			if (pieces[i].getPlayer() == player)
				list.add(pieces[i]);
		}
		PloyPiece[] ret = new PloyPiece[list.size()];
		for (int i=0; i<list.size(); i++)
			ret[i] = (PloyPiece)list.get(i);
		return ret;
	}

	/**
	 * Return the active pieces for a given player.
	 * @param player
	 * @return the active pieces for the given player.
	 */
	public PloyPiece[] getActivePlayerPieces(int player) {
		ArrayList list = new ArrayList();
		for (int i=0; i<pieceCount; i++) {
			if (pieces[i].getPlayer() == player && pieces[i].isShown())
				list.add(pieces[i]);
		}
		PloyPiece[] ret = new PloyPiece[list.size()];
		for (int i=0; i<list.size(); i++)
			ret[i] = (PloyPiece)list.get(i);
		return ret;
	}
	
	
	/**
	 * Reassign player pieces to capturing players. 
	 * 
	 * @param capturedPlayer
	 * @param capturingPlayer
	 * @param capturingTeam
	 */
	public void reassignPieces(int capturedPlayer, int capturingPlayer, int capturingTeam) {
		PloyPiece[] pieces = getPlayerPieces(capturedPlayer);
		int piecesLength = pieces.length;
		for (int piece = 0; piece < piecesLength; piece++) {
			PloyPiece ployPiece = pieces[piece];
			if (PloyPieceType.isCommander(ployPiece)) {
				ployPiece.hide();
			} else {
				ployPiece.setPlayer(capturingPlayer);
				ployPiece.setTeam(capturingTeam);
			}
		}
	}
	
	/**
	 * Return whether the given player still has a commander.
	 * 
	 * @param player
	 * @return whether the given player still has a commander
	 */
	public boolean playerHasCommander(int player) {
		boolean found = false;
		PloyPiece[] pieces = getPlayerPieces(player);
		int pieceLength = pieces.length;
		for (int i=0; i<pieceLength && !found; i++) {
			if (pieces[i].isShown()) {
				found |= PloyPieceType.isCommander(pieces[i].getPieceType());
			}
		}
		return found;
	}
	
	/**
	 * Return whether the given player still has pieces other than a commander
	 * 
	 * @param player
	 * @return whether the given player still has pieces other than a commander
	 */
	public boolean playerHasPiecesOtherThanCommander(int player) {
		boolean found = false;
		PloyPiece[] pieces = getPlayerPieces(player);
		int pieceLength = pieces.length;
		for (int i=0; i<pieceLength && !found; i++) {
			if (pieces[i].isShown()) {
				int pieceType = pieces[i].getPieceType();
				found |= PloyPieceType.isShield(pieceType);
				found |= PloyPieceType.isProbe(pieceType);
				found |= PloyPieceType.isLance(pieceType);
			}
		}
		return found;
	}

	public PloyPiece[] getPieces() {
		return pieces;
	}

	public void setPieces(PloyPiece[] pieces) {
		this.pieces = pieces;
		pieceCount = pieces.length;
		pieceArray = new ArrayList();
		for (int i=0; i<pieceCount; i++) {
			pieces[i].setPieceState(this);
			pieceArray.add(pieces[i]);
		}
	}
	
	public ArrayList getPieceArray() {
		return pieceArray;
	}
	
	public Iterator pieceIterator() {
		return getPieceArray().iterator();
	}
}