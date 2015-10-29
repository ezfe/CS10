/**
 * Custom exception used to send a message
 * @author Ezekiel Elin
 *
 */
public class CustomException extends Exception {

	/**
	 * Eclipse is being eclipse?
	 */
	private static final long serialVersionUID = 755243759652526715L;

	public CustomException(String string) {
		super(string);
	}

}
