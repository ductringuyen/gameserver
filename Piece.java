package de.tuberlin.sese.swtpp.gameserver.model.ploy;
import de.tuberlin.sese.swtpp.gameserver.model.ploy.GamePoint; 

import java.awt.Rectangle;

public class Piece {

	public boolean drawFill = true;

	public boolean selected = false;

	public int col = 0;

	public int row = 0;

	public Rectangle rectangle = null;

	public int player = 0;

	public int team = 0;

	public boolean shown = true;

	public int shadowOffsetPixels = 2;

	/**
	 * @param pt
	 * @return boolean
	 */
	public boolean atRowColumn(GamePoint pt) {
		return getRowColumn().equals(pt);
	}

	/**
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean containsXY(int x, int y) {
		if (isShown() && (rectangle != null)) {
			return rectangle.contains(x, y);
		}
		return false;
	}

	public void deselect() {
		selected = false;
	}

	/**
	 * @return int
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return int
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @return GamePoint
	 */
	public GamePoint getRowColumn() {
		return (new GamePoint(col, row));
	}

	/**
	 * @return int
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return int
	 */
	public int getTeam() {
		return team;
	}

	public void hide() {
		shown = false;
	}

	/**
	 * @param rcPoint
	 */
	public void moveToRowColumn(GamePoint rcPoint) {
		col = rcPoint.getCol();
		row = rcPoint.getRow();
	}

	public void select() {
		selected = true;
	}

	/**
	 * @param thePlayer
	 */
	public void setPlayer(int thePlayer) {
		player = thePlayer;
	}

	/**
	 * @param r
	 * @param c
	 */
	public void setRowColumn(int r, int c) {
		row = r;
		col = c;
	}

	/**
	 * @param theTeam
	 */
	public void setTeam(int theTeam) {
		team = theTeam;
	}

	/**
	 * @return boolean
	 */
	public boolean isShown() {
		return shown;
	}

	/**
	 * @param shown
	 */
	public void setShown(boolean shown) {
		this.shown = shown;
	}


	/**
	 * @return shadowOffsetPixels
	 */
	public int getShadowOffsetPixels() {
		return shadowOffsetPixels;
	}

	/**
	 * @param shadowOffsetPixels
	 */
	public void setShadowOffsetPixels(int shadowOffsetPixels) {
		this.shadowOffsetPixels = shadowOffsetPixels;
	}
}
