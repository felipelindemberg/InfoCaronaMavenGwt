package ufcg.si1.infoCarona.model.negociacao;

import java.util.LinkedList;
import java.util.List;

import ufcg.si1.infoCarona.model.usuario.Usuario;

/**
 * 
 * @author Italo
 *
 */
public class SugestaoDePontoDeEncontro {
	
	private String idSugestao;
	private List<String> listaDeSugestaoDePontosDeEncontro;
	private List<String> listaDeRespostasDePontosDeEncontro;
	private Usuario usuarioQueSugeriu;
	
	/**
	 * 
	 * @param idSugestao
	 * @param usuarioQueSugeriu
	 */
	public SugestaoDePontoDeEncontro(String idSugestao, Usuario usuarioQueSugeriu) {
		this.usuarioQueSugeriu = usuarioQueSugeriu;
		this.idSugestao = idSugestao;
		this.listaDeSugestaoDePontosDeEncontro = new LinkedList<String>();
		this.listaDeRespostasDePontosDeEncontro = new LinkedList<String>();
	}
	

	/**
	 * 
	 * @return idSugestão
	 */
	public String getIdSugestao(){
		return this.idSugestao;
	}
	
	/**
	 * 
	 * @return usuario um usuário que sugeriu
	 */
	public Usuario getUsuarioQueSugeriu() {
		return usuarioQueSugeriu;
	}


	/**
	 * 
	 * @return lista de sugestão de ponto de encontro
	 */
	public List<String> getListaDeSugestaoDePontosDeEncontro(){
		return this.listaDeSugestaoDePontosDeEncontro;
	}
	
	/**
	 * 
	 * @return lista de respostas de ponto de encontro
	 */
	public List<String> getlistaDeRespostasDePontosDeEncontro(){
		return this.listaDeRespostasDePontosDeEncontro;
	}
	
	/**
	 * 
	 * @param pontoSugestao
	 * @return boolean
	 */
	public boolean existeSugestao(String pontoSugestao){
		boolean retorno = false;
		for (String sugestao : listaDeSugestaoDePontosDeEncontro) {
			if(sugestao.equals(pontoSugestao)){
				retorno = true;
				break;
		}
		}
		return retorno;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof SugestaoDePontoDeEncontro)) {
			return false;
		}
		
		if (!(((SugestaoDePontoDeEncontro) obj).getIdSugestao().equals(this.idSugestao))){
			return false;
		}
		
		return true;
	}
}
