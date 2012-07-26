package ufcg.si1.infoCarona.model.carona;

import java.util.Calendar;

import ufcg.si1.infoCarona.model.usuario.Usuario;

/**
 * 
 * @author Italo
 *
 */
public class Rachada extends CaronaAbstract{
	
	private Carona carona;
	private double valorDaGasolina;
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @param calendar
	 * @param vagas
	 * @param idCarona
	 * @param donoDaCarona
	 * @param valorDaGasolina
	 * @param carona
	 * @throws CaronaException
	 */
	public Rachada(String origem, String destino, Calendar calendar, int vagas,
			String idCarona, Usuario donoDaCarona, double valorDaGasolina,Carona carona) throws CaronaException {
		super(origem, destino, calendar, vagas, idCarona, donoDaCarona);
		this.carona = carona;
		this.valorDaGasolina = valorDaGasolina;
	}
	
	public double getGasolina(){
		return this.valorDaGasolina;
	}
	
	@Override
	public TiposCarona getTipoCarona() {
		TiposCarona retorno = null;
		
		switch (carona.getTipoCarona()) {
		case COMUM:
			retorno = TiposCarona.RACHADACOMUM;
			break;
		case MUNICIPAL:
			retorno = TiposCarona.RACHADAMUNICIPAL;
			break;
		case ESCAPADA:
			retorno = TiposCarona.RACHADAESCAPADA;
			break;
		}
		
		return retorno;
	}

	@Override
	public Calendar getVolta() throws CaronaException {
		return carona.getVolta();
	}

	@Override
	public int getMinimoCaroneiros() throws CaronaException {
		return carona.getMinimoCaroneiros();
	}

}
