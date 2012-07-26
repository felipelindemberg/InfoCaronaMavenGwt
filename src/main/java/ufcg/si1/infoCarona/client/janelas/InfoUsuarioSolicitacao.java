package ufcg.si1.infoCarona.client.janelas;

public class InfoUsuarioSolicitacao {
	
	private final String idSolicitacao;
	private final String loginUsuarioSolicitacao;
	
	public InfoUsuarioSolicitacao(String idSolicitacao, String loginUsuarioSolicitacao) {
		this.idSolicitacao = idSolicitacao;
		this.loginUsuarioSolicitacao = loginUsuarioSolicitacao;
	}
	
	public String getIdSolicitacao() {
		return idSolicitacao;
	}
	public String getLoginUsuario() {
		return loginUsuarioSolicitacao;
	}
}
