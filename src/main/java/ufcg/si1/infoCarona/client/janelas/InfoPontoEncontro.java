package ufcg.si1.infoCarona.client.janelas;

public class InfoPontoEncontro {
	
	private final String idPontoEncontro;
	private final String pontoEncontro;
	
	public InfoPontoEncontro(String idPontoEncontro, String pontoEncontro) {
		this.idPontoEncontro = idPontoEncontro;
		this.pontoEncontro = pontoEncontro;
	}
	
	public String getIdPontoEncontro() {
		return idPontoEncontro;
	}
	
	public String getPontoEncontro() {
		return pontoEncontro;
	}

}
