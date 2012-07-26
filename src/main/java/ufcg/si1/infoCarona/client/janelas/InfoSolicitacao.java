package ufcg.si1.infoCarona.client.janelas;

public class InfoSolicitacao {
	
	private final String idSolicitacao;
	private final String idCarona;
	private final String pontoEncontro;
	
	public InfoSolicitacao(String idSolicitacao, String idCarona, String pontoEncontro) {
		this.idSolicitacao = idSolicitacao;
		this.idCarona = idCarona;
		this.pontoEncontro = pontoEncontro;
	}
	
	public String getIdSolicitacao() {
		return idSolicitacao;
	}
	
	public String getIdCarona() {
		return idCarona;
	}
	
	public String getPontoEncontro() {
		return pontoEncontro;
	}
}
