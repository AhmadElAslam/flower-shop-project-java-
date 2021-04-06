package server;
/**
 * This interface implements the abstract method used to display objects onto
 * the client or server UIs.
 *
 */
public interface ConIF {
	/**
	 * Method that when overriden is used to display objects onto a UI.
	 */
	public abstract void display(Object msg);
}
