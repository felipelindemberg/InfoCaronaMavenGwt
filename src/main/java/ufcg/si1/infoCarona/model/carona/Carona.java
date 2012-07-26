package ufcg.si1.infoCarona.model.carona;

import java.util.Calendar;
import java.util.List;

import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.usuario.Usuario;

/**Interface cCarona
 * @author Italo Tavares 
 * @author Felipe Lindemberg
 * @author Wallison Fernando
 * @author Gilles Medeiros
 */
public interface Carona {
	
	/**
	 * Metodo que diz os pontos de encontros permitids em uma Carona
	 * @return - Retorna uma List<String> com os pontos de encontros permitidos
	 */
	public List<String> getListaPontosDeEncontroPermitidos();
	
	/**
	 * Retorna as solicitações de uma Carona
	 * @return - Retorna uma List<SolicitacaoDeVaga> com todas as solicitações de uma Carona
	 */
	public List<SolicitacaoDeVaga> getListaDeSolicitacao();
	
	/**
	 * Retorna uma lista de sugestão de ponto de encontro de uma Carona
	 * @return - Retorna uma List<SugestaoPontoDeEncontro> com todas as sugestoes de uma Carona
	 */
	public List<SugestaoDePontoDeEncontro> getListaDeSugestoes();
	
	/**
	 * Retorna a origem de uma Carona
	 * @return - Retorna uma string com a origem da Carona
	 */
	public String getOrigem();
	
	/**
	 * Retorna o dono da carona
	 * @return - Retorna um Usuario que é o dono da carona
	 */
	public Usuario getDonoDaCarona();
	
	public void setOrigem(String origem) throws CaronaException;

	public String getDestino();

	public void setDestino(String destino) throws CaronaException;

	public Calendar getCalendario();
	
	public void setCalendario(Calendar calendario);

	public int getVagas();

	public void setVagas(int vagas);
	
	
	public String getIdCarona();
	
	public TiposCarona getTipoCarona();
	/**
	 * Metodo que retorna o to String das caronas
	 * @return - retorna uma String no formato "ORIGEM para DESTINO no dia XX/XX/XXXX as XX:XX
	 */
	@Override
	public String toString();
	
	public String getDadosCarona();
	
	/**
	 * Adiciona uma nova solicitação a uma carona
	 * @param novaSolicitacao - Recebe um objeto SolicitacaoDeVaga
	 */
	public void addNovaSolicitacao(SolicitacaoDeVaga novaSolicitacao);
	
	
    /**
     * Metodo que remove um solicitação feita a uma carona
     * @param solicitacao
     */
    public void removeSolicitacao(SolicitacaoDeVaga solicitacao);
    @Override
	public boolean equals(Object obj);
    
    public List<SolicitacaoDeVaga> getSolicitacoesConfirmadas();
    
    public List<SolicitacaoDeVaga> getSolicitacoesPendentes();
    
    public void addPontoEncontroPermitido(String ponto);
    
    public void addReviewCarona(Usuario usuario, String review);
    
	public void setCaronaPreferencial();
	
	public boolean isPreferencial();
	
	public Calendar getVolta() throws CaronaException;
	
	public double getGasolina() throws CaronaException;
	
	public int getMinimoCaroneiros() throws CaronaException;
	public String toStringView();
}
