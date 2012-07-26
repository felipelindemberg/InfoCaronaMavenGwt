package ufcg.si1.infoCarona.model.carona;

import java.util.Calendar;

import ufcg.si1.infoCarona.model.usuario.Usuario;


public class CaronaMunicipal extends CaronaAbstract {
	
	private String cidade;
	public CaronaMunicipal(String origem, String destino, String cidade, Calendar calendario, int vagas, String idCarona, Usuario donoDaCarona)
			throws CaronaException {
		super(origem, destino, calendario, vagas, idCarona, donoDaCarona);
		this.cidade = cidade;
	}

	public String getCidade() {
		return cidade;
	}
	
	public TiposCarona getTipoCarona(){
		return TiposCarona.MUNICIPAL;
	}

	@Override
	public Calendar getVolta() throws CaronaException {
		throw new CaronaException("A carona municipal não existe volta");
	}

	@Override
	public double getGasolina() throws CaronaException {
		throw new CaronaException("A carona municipal não existe gasolina");
	}

	@Override
	public int getMinimoCaroneiros() throws CaronaException {
		throw new CaronaException("A carona municipal não existe minimo de caroneiros");
	}
}
