package util.Document;
import java.awt.Color;
import java.awt.Component;

public interface HTMLStyle<T, F extends Component> {
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