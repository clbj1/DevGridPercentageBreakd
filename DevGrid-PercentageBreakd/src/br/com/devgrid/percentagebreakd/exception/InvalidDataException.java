package br.com.devgrid.percentagebreakd.exception;

/**
 * Exce��o para erros de dados inv�lidos informados
 * 
 * @author Cesar Buzin
 */
public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 8344581865531407763L;

	public InvalidDataException(String message) {
		super(message);
	}
}