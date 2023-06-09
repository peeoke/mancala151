package mancala_proj;

/**
 * This class defines the dark mode style of the Mancala board
 * @author pebbles (Sandra Le, Dat Tri Tat, Ysabella Dela Cruz)
 */

import java.awt.Color;

/*
 * Defines the name and colors of the night style.
 */
public class NightStyle implements StyleManager{
	public static final Color VERY_DARK_BLUE = new Color(0,0,153);
	
	/**
	 * Returns the name of this style.
	 * @return the name of this style
	 */
	public String getName() {
		return "Night";
	}

	/**
	 * Returns the pit color of this style.
	 * @return the pit color of this style
	 */
	public Color getPitColor() {
		return VERY_DARK_BLUE;
	}

	/**
	 * Returns the board color of this style.
	 * @return the board color of this style
	 */
	public Color getBoardColor() {
		return Color.BLACK;
	}
	
	/**
	 * Returns the pit label font color of this style.
	 * @return the pit label font color of this style.
	 */
	public Color getFontColor() {
		return Color.LIGHT_GRAY;
	}

}
