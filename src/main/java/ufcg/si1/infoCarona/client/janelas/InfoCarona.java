package ufcg.si1.infoCarona.client.janelas;

public class InfoCarona {
	private final String idCarona;
    private final String motorista;
    private final String origem;
    private final String destino;
    private final String data;
    private final String hora;
    private final String vagas;

    public InfoCarona(String idCarona, String motorista, String origem, String destino, String data, String hora, String vagas) {
      this.idCarona = idCarona;
      this.motorista = motorista;
      this.origem = origem;
      this.destino = destino;
      this.data = data;
      this.hora = hora;
      this.vagas = vagas;
    }
    public String toString() {
    	return motorista +" "+ origem +" "+ destino +" "+ data +" "+ hora +" "+ vagas;  
    }
	public String getMotorista() {
		return motorista;
	}
	public String getOrigem() {
		return origem;
	}
	public String getDestino() {
		return destino;
	}
	public String getData() {
		return data;
	}
	public String getHora() {
		return hora;
	}
	public String getVagas() {
		return vagas;
	}
	public String getIdCarona() {		
		return idCarona;
	}
}