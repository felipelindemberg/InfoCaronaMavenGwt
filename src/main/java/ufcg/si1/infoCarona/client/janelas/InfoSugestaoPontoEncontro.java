package ufcg.si1.infoCarona.client.janelas;

public class InfoSugestaoPontoEncontro {

	private String idSugestaoPontoEncontro;
	private String loginUsuario;
	private String sugestoesFeitas;
	private String sugestoesConfirmadas;
	
	public InfoSugestaoPontoEncontro(String idSugestaoPontoEncontro, String loginUsuario, String sugestoesFeitas) {
		this.idSugestaoPontoEncontro = idSugestaoPontoEncontro;
		this.loginUsuario = loginUsuario;
		this.sugestoesFeitas = sugestoesFeitas;
		this.sugestoesConfirmadas = "";
	}
	
	public String getIdSugestaoPontoEncontro() {
		return idSugestaoPontoEncontro;
	}
	
	public String getLoginUsuario() {
		return loginUsuario;
	}
	
	public String getSugestoesFeitas() {
		return sugestoesFeitas;
	}

	public void setSugestoesConfirmadas(String value) {
		sugestoesConfirmadas = value;
	}

	public String getSugestoesConfirmadas() {
		return sugestoesConfirmadas;
	}
}
