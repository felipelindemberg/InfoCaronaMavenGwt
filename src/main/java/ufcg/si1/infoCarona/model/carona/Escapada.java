package ufcg.si1.infoCarona.model.carona;

import java.util.Calendar;

import ufcg.si1.infoCarona.model.usuario.Usuario;

public class Escapada extends CaronaAbstract{
	
	private Calendar calendarVolta;
	private int minimoCaroneiro;
	private boolean expirada;
	
	public Escapada(String origem, String destino, Calendar calendar,
			int vagas, String idCarona, Usuario donoDaCarona, Calendar calendarVolta, int minimoCaroneiros)
			throws CaronaException {
		super(origem, destino, calendar, vagas, idCarona, donoDaCarona);
		this.calendarVolta = calendarVolta;
		setMinimoCaroneiro(minimoCaroneiros);
		expirada = false;
	}
	
	public Calendar getVolta(){
		return calendarVolta;
	}
	
	public int getMinimoCaroneiros(){
		return minimoCaroneiro;
	}
	
	public void setMinimoCaroneiro(int minimoCaroneiro){
		if (minimoCaroneiro <= 0){
			throw new IllegalArgumentException("Minimo Caroneiros inválido");
		}
		this.minimoCaroneiro = minimoCaroneiro;
	}
	
	public boolean isExpirada(){
		if (expirada) return true;
		else return false;
	}
	
	public void setExpirada(){
		this.expirada = true;
	}

	@Override
	public TiposCarona getTipoCarona() {
		return TiposCarona.ESCAPADA;
	}

	@Override
	public double getGasolina() throws CaronaException {
		throw new CaronaException("A carona escapada não existe gasolina");
	}
}
