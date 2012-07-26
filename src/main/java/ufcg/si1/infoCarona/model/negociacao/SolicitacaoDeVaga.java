package ufcg.si1.infoCarona.model.negociacao;

import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.usuario.Usuario;

/**
 * 
 * @author Italo
 *
 */
public class SolicitacaoDeVaga {
	
	private Carona carona;
	private Usuario donoSolicitacao;
	private String ponto, idSolicitacao;
	private EstadoSolicitacao estado;
	
	/**
	 * 
	 * @param carona
	 * @param ponto
	 * @param idSolicitacao
	 * @param donoSolicitacao
	 */
	public SolicitacaoDeVaga(Carona carona, String ponto, String idSolicitacao, Usuario donoSolicitacao){
		this.carona = carona;
		this.ponto = ponto;
		this.idSolicitacao = idSolicitacao;
		this.donoSolicitacao = donoSolicitacao;
		this.estado = EstadoSolicitacao.PENDENTE;
	}
	
	/**
	 * 
	 * @return usuario, dono da solicitação
	 */
	public Usuario getDonoSolicitacao() {
		return donoSolicitacao;
	}

	/**
	 * 
	 * @return origem da carona
	 */
	public String getOrigem(){
		return this.carona.getOrigem();
	}
	
	
	/**
	 * 
	 * @return destino da carona
	 */
	public String getDestino(){
		return this.carona.getDestino();
	}

	/**
	 * 
	 * @return usuario dono da carona
	 */
	public Usuario getDonoDaCarona(){
		return this.carona.getDonoDaCarona();
	}

	/**
	 * 
	 * @return ponto de carona
	 */
	public String getPonto(){
		return this.ponto;
	}
	
	/**
	 * 
	 * @return idSolicitação
	 */
	public String getIdSolicitacao(){
		return this.idSolicitacao;
	}
	
	/**
	 * setEstado
	 */
	public void solicitacaoAceita(){
		this.setEstado(EstadoSolicitacao.ACEITA);
		carona.setVagas(carona.getVagas()-1);	
	}
	
	/**
	 * setEstado
	 */
	public void solicitacaoRejeitada(){
		this.setEstado(EstadoSolicitacao.REJEITADA);
	}
	
	/**
	 * getEstado
	 */
	public EstadoSolicitacao getEstado(){
		return this.estado;
	}
	
	/**
	 * 
	 * @param estado
	 */
	public void setEstado(EstadoSolicitacao estado){
		this.estado = estado;
	}
	
	/**
	 * 
	 * @return carona
	 */
	public Carona getCarona(){
		return this.carona;
	}
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof SolicitacaoDeVaga)) {
			return false;
		}
		
		if (!(((SolicitacaoDeVaga) obj).getIdSolicitacao().equals(this.idSolicitacao))){
			return false;
		}
		
		return true;
	}

}
