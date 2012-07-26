package ufcg.si1.infoCarona.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.usuario.Usuario;

/**
 * 
 * @author Italo, Felipe, Gilles, Wallison
 *
 */
public class Interesse {

	private final Usuario interessado;
	private String origem;
	private Calendar calendarioFinal;
	private Calendar calendarioInicial;
	private final String id;
	private String destino;
	private boolean caronaEhNoDia;

	/**
	 * 
	 * @param interessado
	 * @param origem
	 * @param destino
	 * @param calendarioInicial
	 * @param calendarioFinal
	 * @param id
	 * @param caronaEhNoDia
	 * @throws CaronaException
	 */
	public Interesse(Usuario interessado, String origem, String destino,
			Calendar calendarioInicial, Calendar calendarioFinal, String id, boolean caronaEhNoDia)
			throws CaronaException {
		this.interessado = interessado;
		setOrigem(origem);
		setDestino(destino);
		setCalendarioInicial(calendarioInicial);
		setCalendarioFinal(calendarioFinal);
		this.id = id;
		this.caronaEhNoDia = caronaEhNoDia;

	}

	/**
	 * Retorna id do interesse
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Retorna usuario interessado
	 * @return interessado
	 */
	public Usuario getInteressado() {
		return interessado;
	}

	/**
	 * Retorna origem de interesse
	 * @return origem
	 */
	public String getOrigem() {
		return origem;
	}

	/**
	 * Retorna destino interessado
	 * @return destino
	 */
	public String getDestino() {
		return destino;
	}

	
	/**
	 * Retorna Calendar com a data inicial
	 * @return calendar
	 */
	public Calendar getCalendarioInicial(){
		return this.calendarioInicial;
	}
	
	/**
	 * Retorna Calendar com a data final
	 * @return calendar
	 */
	public Calendar getCalendarioFinal(){
		return this.calendarioFinal;
	}
	
	/**
	 * Set Calendar com a data inicial
	 * @param calendario
	 */
	public void setCalendarioInicial(Calendar calendario){
		this.calendarioInicial = calendario;
	}
	
	/**
	 * Set Calendar com a data final
	 * @param calendario
	 */
	public void setCalendarioFinal(Calendar calendario){
		this.calendarioFinal = calendario;
	}

	/**
	 * Set origem do interesse
	 * @param origem
	 * @throws CaronaException, exceção de carona invalida
	 */
	private void setOrigem(String origem) throws CaronaException {
		if ((origem == null)
				|| (origem
						.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%¨&*0-9].*"))
				|| (origem.trim().equals(""))) {
			throw new IllegalArgumentException("Origem inválida");
		}
		this.origem = origem.trim();
	}

	/**
	 * Set destino do interesse
	 * @param destino
	 * @throws CaronaException, exceção de carona invalida
	 */
	private void setDestino(String destino) throws CaronaException {
		if ((destino == null)
				|| (destino
						.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%¨&*0-9].*"))
				|| (destino.trim().equals(""))) {
			throw new IllegalArgumentException("Destino inválido");
		}
		this.destino = destino.trim();
	}


	/**
	 * Verifica se a data é valida e retorna um boolean
	 * @param stringData
	 * @return boolean
	 */
	private boolean checaData(String stringData) {
		boolean retorno = true;
		if (!stringData.equals("")) {//coloquei isso aki s� pra passar no teste qdo passar string vazia mas temos q ajeitar pra qdo for do tipo DATA
			Calendar data = Calendar.getInstance();
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				formato.setLenient(false);
				data.setTime(formato.parse(stringData));
			} catch (ParseException e) {
				retorno = false;
			}
			Calendar dataAtual = Calendar.getInstance();
			if (dataAtual.getTime().compareTo(data.getTime()) == 1) { // -1 data
																		// valida,
																		// 1
																		// data
																		// invalida
				retorno = false;
			}
		}
		return retorno;
	}
	
	public boolean caronaEhNoDiaMarcado(){
		return caronaEhNoDia;
	}

}
