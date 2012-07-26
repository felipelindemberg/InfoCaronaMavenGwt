package ufcg.si1.infoCarona.model.carona;

/**
 * 
 * @author Italo
 *
 */
public enum TiposCarona {
	COMUM("Carona Comum"),
	MUNICIPAL("Carona Municipal"),
	ESCAPADA("Escapada"),
	RACHADACOMUM("Rachada e Comum"),
	RACHADAMUNICIPAL("Rachada e Municipal"),
	RACHADAESCAPADA("Rachada e Escapada");
	
	private String nomeTipo;
	TiposCarona(String nomeTipo){
		this.nomeTipo = nomeTipo;
	}
	public String getNomeTipo() {
		return nomeTipo;
	}
	
}
