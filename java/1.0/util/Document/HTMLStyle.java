package util.Document;
import java.awt.Color;
import java.awt.Component;

public interface HTMLStyle<T, F extends Component> {
	/**
	 * Sets the size that the component occupies within the Fragment element.
	 * @param width width value consisting of px or %
	 * @param height height value consisting of px or %
	 * @return The method chain that allows you to edit other styles.
	 */
	public T size(String width, String height);
	/**
	 * Sets the size that the component occupies within the Fragment element.
	 * @param widthp percentage
	 * @param width pixel <b>= calc((widthp)% + (width)px)</b>
	 * @param heightp percentage
	 * @param height pixel <b>= calc((heightp)% + (height)px)</b>
	 * @return The method chain that allows you to edit other styles.
	 */
	public T size(int widthp, int width, int heightp, int height);
	/**
	 * Sets the position that the component occupies within the Fragment element.
	 * @param x x position value consisting of px or % only
	 * @param y y position value consisting of px or % only
	 * @return The method chain that allows you to edit other styles.
	 */
	public T position(String x, String y);
	/**
	 * Sets the position that the component occupies within the Fragment element.
	 * @param xp percentage
	 * @param x pixel <b>= calc((xp)% + (x)px)</b>
	 * @param yp percentage
	 * @param y pixel <b>= calc((yp)% + (y)px)</b>
	 * @return The method chain that allows you to edit other styles.
	 */
	public T position(int xp, int x, int yp, int y);
	/**
	 * Sets the background color of component
	 * @param color Color to set as background for Component
	 * @return The method chain that allows you to edit other styles.
	 */
	public T background(Color color);
	/**
	 * Close the method chain that edits the style, return the main component.
	 * @return main component
	 */
	public F end();
}