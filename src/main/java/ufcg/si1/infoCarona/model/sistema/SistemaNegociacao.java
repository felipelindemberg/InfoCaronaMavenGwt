package ufcg.si1.infoCarona.model.sistema;

import java.util.LinkedList;
import java.util.List;

import ufcg.si1.infoCarona.controller.ControlerRepositorio;
import ufcg.si1.infoCarona.model.ArgumentoInexistenteException;
import ufcg.si1.infoCarona.model.Id;
import ufcg.si1.infoCarona.model.NumeroMaximoException;
import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.usuario.Usuario;
import ufcg.si1.infoCarona.util.UtilInfo;

/**
 * 
 * @author Italo
 *
 */
public class SistemaNegociacao {
	
	private Id id;
	private ControlerRepositorio controler;
	
	public static SistemaNegociacao instance;
	
	protected SistemaNegociacao(){
		controler = new ControlerRepositorio();
		id = Id.getInstance(5);
	}
	
	/**
	 * 
	 * @return SistemaNegociacao
	 */
	public static SistemaNegociacao getInstance(){
		if(instance == null){
			instance = new SistemaNegociacao();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param idSessao
	 * @param idCarona
	 * @param pontos
	 * @return sugerirPontoEncontro
	 * @throws CaronaException
	 * @throws NumeroMaximoException
	 * @throws ArgumentoInexistenteException
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona,
			String pontos) throws CaronaException,
			NumeroMaximoException, ArgumentoInexistenteException {
		if (pontos == null || pontos.equals("")) {
			throw new IllegalArgumentException("Ponto Inválido");
		}
		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		Carona caronaTemp = this.getCarona(idCarona);
		if(SistemaRaiz.usuarioJahEstahNaCarona(usuarioTemp, caronaTemp)){
			throw new IllegalArgumentException("Ponto Inválido");
		}
		
		return usuarioTemp.sugerirPontoEncontro(pontos, caronaTemp,
				id.gerarId(), usuarioTemp);

	}
	
	/**
	 * 
	 * @param idSessao
	 * @param idCarona
	 * @param ponto
	 * @return solicitarVagaPontoEncontro
	 * @throws CaronaException
	 * @throws NumeroMaximoException
	 * @throws ArgumentoInexistenteException
	 */
	public String solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto) throws CaronaException,NumeroMaximoException, ArgumentoInexistenteException {

		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		Carona carona = controler.localizaCaronaPorId(idCarona);
		if (ponto.equals("")) {
			ponto = null; // subtende-se que o usuario aceita os pontos que o
							// dono da carona indicou
		}

		return usuarioTemp.solicitarVagaPontoEncontro(ponto, carona,
				id.gerarId(), usuarioTemp);
	}
	
	/**
	 * 
	 * @param idSessao
	 * @param idCarona
	 * @param idSugestao
	 * @param pontos
	 * @throws CaronaException
	 * @throws ArgumentoInexistenteException
	 */
	public void responderSugestaoPontoEncontro(String idSessao,
			String idCarona, String idSugestao, String pontos)
			throws CaronaException, ArgumentoInexistenteException{

		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		SugestaoDePontoDeEncontro sugestaoTemp = controler.getSugestaoId(idSugestao, idCarona);
		Carona caronaTemp = controler.localizaCaronaPorId(idCarona);

		usuarioTemp.responderSugestaoPontoEncontro(sugestaoTemp, pontos, caronaTemp);
	}
	
	/**
	 * 
	 * @param idSessao
	 * @param idSolicitacao
	 * @throws ArgumentoInexistenteException
	 */
	public void aceitarSolicitacaoPontoEncontro(String idSessao,
			String idSolicitacao) throws ArgumentoInexistenteException  {

		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		SolicitacaoDeVaga solicitacao = controler
				.localizaSolicitacaoPorId(idSolicitacao);

		usuarioTemp.aceitarSolicitacaoPontoEncontro(solicitacao);
	}
	
	/**
	 * 
	 * @param idSessao
	 * @param idSolicitacao
	 * @throws ArgumentoInexistenteException
	 */
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws ArgumentoInexistenteException{
		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		SolicitacaoDeVaga solicitacao = controler
				.localizaSolicitacaoPorId(idSolicitacao);
		usuarioTemp.rejeitarSolicitacao(solicitacao);

	}

	/**
	 * 
	 * @param idSessao
	 * @param idCarona
	 * @param idSolicitacao
	 * @throws CaronaException
	 * @throws ArgumentoInexistenteException
	 */
	public void desistirRequisicao(String idSessao, String idCarona,
			String idSolicitacao) throws CaronaException, ArgumentoInexistenteException {

		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		Carona caronaTemp = controler.localizaCaronaPorId(idCarona);
		SolicitacaoDeVaga solicitacaoTemp = controler.localizaSolicitacaoPorId(idSolicitacao);
		usuarioTemp.desistirRequisicao(solicitacaoTemp, caronaTemp);

	}
	
	/**
	 * 
	 * @param idCarona
	 * @return LinkedList<String> getPontosSugeridos
	 * @throws CaronaException
	 */
	public LinkedList<String> getPontosSugeridos(String idCarona) throws CaronaException {
		LinkedList<String> retorno = new LinkedList<String>();
		Carona caronaTemp = controler.localizaCaronaPorId(idCarona);
		List<SugestaoDePontoDeEncontro> listaSugestoes = caronaTemp.getListaDeSugestoes();
		for(SugestaoDePontoDeEncontro sugestao : listaSugestoes){
			for (String ponto : sugestao.getListaDeSugestaoDePontosDeEncontro()) {
				retorno.add(ponto);
			}
		}
		return retorno;
	}
	
	/**
	 * 
	 * @param idCarona
	 * @return List<String> getPontosEncontro
	 * @throws CaronaException
	 */
	public List<String> getPontosEncontro(String idCarona)
			throws CaronaException {
		List<String> retorno = new LinkedList<String>();
        List<SugestaoDePontoDeEncontro> sugestoes = controler.localizaCaronaPorId(idCarona)
                .getListaDeSugestoes();
        
        for (int i = 0; i < sugestoes.size(); i++) {
            List<String> pontos = sugestoes.get(i).getListaDeSugestaoDePontosDeEncontro();
            for (int j = 0; j < pontos.size(); j++) {
                String ponto = pontos.get(j);
                if (!retorno.contains(ponto)) {
                    retorno.add(ponto);
                }
            }
        }
        
        return retorno;
	}
	
	/**
	 * 
	 * @param idCarona
	 * @return List<SolicitacaoDeVaga>
	 * @throws CaronaException
	 */
	public List<SolicitacaoDeVaga> getSolicitacoesConfirmadas(String idCarona) throws CaronaException {
		return controler.localizaCaronaPorId(idCarona).getSolicitacoesConfirmadas();
	}

	/**
	 * 
	 * @param idCarona
	 * @return List<SolicitacaoDeVaga>
	 * @throws CaronaException
	 */
	public List<SolicitacaoDeVaga> getSolicitacoesPendentes(String idCarona)
			throws CaronaException {
		return controler.localizaCaronaPorId(idCarona)
				.getSolicitacoesPendentes();
	}
	
	/**
	 * 
	 * @param idSolicitacao
	 * @param atributo
	 * @return atributo
	 */
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) {
		return controler.getAtributoSolicitacao(idSolicitacao,
				atributo);
	}
	
	/**
	 * Localiza carona pelo id
	 * @param idCarona
	 * @return carona
	 * @throws CaronaException
	 */
	public Carona getCarona(String idCarona) throws CaronaException {
		if (UtilInfo.ehVazioOuNull(idCarona)) {
			throw new CaronaException("Carona Inválida");
		}
		return controler.localizaCaronaPorId(idCarona);
	}
}
