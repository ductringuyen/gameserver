package de.tuberlin.sese.swtpp.gameserver.model.ploy;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Game point has extended utilities above and beyond a standard Point.
 * s
 * @author Jeff Conrad
 */
public class GamePoint {

	private Point point;
	
	/**
	 * A class for storing a coordinate in the game space.
	 */
	public GamePoint(int col, int row) {
		super();
		point = new Point(col, row);
	}
	
	/**
	 * A class for storing a coordinate in the game space.
	 */
	public GamePoint(Dimension d) {
		super();
		int width = (int)Math.round(d.getWidth());
		int height = (int)Math.round(d.getHeight());
		point = new Point(width, height);
	}
	
	public int getCol()
	{
		return point.x;
	}

	public int getRow()
	{
		return point.y;
	}
	
	public void setCol(int col) {
		point.x = col;
	}
	
	public void setRow(int row) {
		point.y = row;
	}
	
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (!(o instanceof GamePoint))
			return false;
		GamePoint ogp = (GamePoint)o;
		if (this.getCol() != ogp.getCol())
			return false;
		if (this.getRow() != ogp.getRow())
			return false;
		return true;
	}
	
	public double pointToRadians(Point p) {
		double radians = 0.0d;
		
		double xc = (double) (this.getCol() / 2);
		double yc = (double) (this.getRow() / 2);
		double xs = (double) (p.getX() - xc);
		double ys = (double) (-p.getY() + yc);

		// currentFacing in radians
		if (xs > 0.0 && ys > 0.0) {
			radians = Math.atan(ys / xs);
		} else if (xs < 0.0 && ys > 0.0) {
			radians = Math.PI + Math.atan(ys / xs);
		} else if (xs < 0.0 && ys < 0.0) {
			radians = Math.PI + Math.atan(ys / xs);
		} else if (xs > 0.0 && ys < 0.0) {
			radians = 2 * Math.PI + Math.atan(ys / xs);
		} else {
			if (xs == 0.0 && ys >= 0.0)
				radians = Math.PI / 2;
			else if (xs == 0.0 && ys < 0.0)
				radians = 3 * Math.PI / 2;
			else if (xs >= 0.0 && ys == 0.0)
				radians = 0.0;
			else if (xs < 0.0 && ys == 0.0)
				radians = Math.PI;
		}
		return radians;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName());
		sb.append(";col=" + getCol());
		sb.append(";row=" + getRow());
		return sb.toString();
	}
}
