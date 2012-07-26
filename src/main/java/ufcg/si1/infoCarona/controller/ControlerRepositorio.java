package ufcg.si1.infoCarona.controller;

import java.util.LinkedList;
import java.util.List;

import ufcg.si1.infoCarona.model.ArgumentoInexistenteException;
import ufcg.si1.infoCarona.model.LoggerException;
import ufcg.si1.infoCarona.model.Repositorio;
import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.carona.Escapada;
import ufcg.si1.infoCarona.model.carona.TiposCarona;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.usuario.Usuario;
import ufcg.si1.infoCarona.util.UtilInfo;


/**
 * Classe que faz o controle de dados entre o repositório e o sistema
 * @author Felipe Lindemberg
 * @author Gilles Medeiros
 * @author Italo Tavares
 * @author Wallison Tavares
 *
 */
public class ControlerRepositorio {
	Repositorio repositorio;
	
	/**
	 * Construtor do controller do repositório
	 */
	public ControlerRepositorio(){
		repositorio = Repositorio.getInstance();
	}
	

	/**
	 * Metodo que adiciona um novo Usuario ao sistema
	 * @param novoUsuario - Recebe um novo Usuario e adiciona ao sistema
	 */
	public void addUsuario(Usuario novoUsuario) {
		repositorio.addUsuario(novoUsuario);
	}
	
	/**
	 * Metodo que busca um Usuario pelo seu login
	 * @param login - Recebe uma String login
	 * @return - Retorna um Usuario, caso exista com esse login
	 * @throws LoggerException - Lança exceção caso seja passado algum login inválido
	 * @throws ArgumentoInexistenteException - Lança exceção caso nao exista um Usuario com esse login
	 */
	public Usuario buscarUsuarioPorLogin(String login) throws LoggerException, ArgumentoInexistenteException {
		Usuario retorno = null;
		retorno = repositorio.buscaUsuarioLogin(login);
		if(retorno == null){
			throw new ArgumentoInexistenteException("Usuário inexistente");
		}
		
		return retorno;
	}


	/**
	 * Metodo que retorna os atributos de um Usuario de acordo com o paramêtro passado.
	 * @param login - Recebe uma String login do Usuario que possui os atributos.
	 * @param atributo - Recebe uma String atributo com o atributo desejado.
	 * @return - Retorna uma String com o atributo desejado, caso exista o atributo desejado
	 * @throws LoggerException - Lança exceção caso o atributo passado seja inválido  
	 * @throws ArgumentoInexistenteException - Lança exceção caso nao exista um Usuario com esse login
	 */
	public String getAtributoUsuario(String login, String atributo) throws LoggerException, ArgumentoInexistenteException {
		Usuario usuarioTemp = buscarUsuarioPorLogin(login);
		List<String> listaTemp = new LinkedList<String>();
		String retorno = "";

		if (atributo.equals("nome")) {
			retorno = usuarioTemp.getNome();
		}
		else if (atributo.equals("endereco")) {
			retorno = usuarioTemp.getEndereco();
		}
		else if (atributo.equals("email")) {
			retorno = usuarioTemp.getEmail();
		}
		else if (atributo.equals("historico de caronas")) {
			for (Carona caronaTemp : usuarioTemp.getCaronas()) {
				listaTemp.add(caronaTemp.getIdCarona());
			}
			if(listaTemp.isEmpty()){
				retorno = "";
			}else{
			retorno = listaTemp.toString().replace("{", "[").replace("}", "]");
			}
		}
		
		else if (atributo.equals("caronas seguras e tranquilas")) {
			retorno = usuarioTemp.getCaronasSeguras() + "";
		}
		else if (atributo.equals("caronas que não funcionaram")) {
			retorno = usuarioTemp.getCaronasNaoFuncionaram() + "";
		} 
		else if (atributo.equals("faltas em vagas de caronas")) {
			retorno = usuarioTemp.getFaltasEmVagas() + "";
		} 
		else if (atributo.equals("presenças em vagas de caronas")) {
			retorno = usuarioTemp.getPresencaEmVagas()+ "";
		}

		return retorno;
	}
	
	/**
	 * Metodo que localiza uma carona que possua destino e origem específicos
	 * @param origem - Recebe uma String origem
	 * @param destino- Recebe uma String destino
	 * @return - Retorna uma List<Carona> que possua origem e destino especificado
	 */
	public List<Carona> localizarCarona(String origem, String destino) {
		
		List<Carona> retorno;
		if ((!origem.equals("")) && ((!destino.equals("")))) {
			retorno = repositorio.localizaCaronaPorOrigemDestino(origem, destino);
		} else if ((origem.equals("")) && ((!destino.equals("")))) {
			retorno = repositorio.localizaCaronaPorDestino(destino);

		} else if ((!origem.equals("")) && ((destino.equals("")))) {
			retorno = repositorio.localizaCaronaPorOrigem(origem);
		} else {
			retorno = repositorio.getCaronas();
		}
		return retorno;
		
	}
	
	/**
	 * Metodo que retorna os atributos de uma Carona
	 * @param idCarona - Recebe umas String idCarona
	 * @param atributo - Recebe uma String atributo
	 * @return - Retorna uma String com o atributo desejado caso exista a Carona ou o atributo passado seja válido
	 * @throws ArgumentoInexistenteException - Lança exceção caso nao exista uma Carona com esse idCarona
	 */
	public String getAtributoCarona(String idCarona, String atributo) throws ArgumentoInexistenteException, CaronaException {
		Carona caronaTemp = repositorio.localizaCaronaPorId(idCarona);
		if(caronaTemp == null){
			throw new ArgumentoInexistenteException("Item inexistente");
		}
		String retorno = "";
        
        if(atributo.equals("origem")){
                retorno = caronaTemp.getOrigem();
        }else if(atributo.equals("vagas")){
                retorno = (caronaTemp.getVagas()+"");
        }else if(atributo.equals("destino")){
                retorno = caronaTemp.getDestino();
        }else if(atributo.equals("data")){
                retorno = UtilInfo.converteCalendarEmStringData(caronaTemp.getCalendario());
        }else if(atributo.equals("ehMunicipal")){
            if(caronaTemp.getTipoCarona().equals(TiposCarona.MUNICIPAL)){
            	retorno = "true" ;
            }else{
            	retorno = "false"; //sem logica essa bagaça tem q refatorar
            }
        }else if(atributo.equals("tipoDeCarona")){
        	retorno = caronaTemp.getTipoCarona().getNomeTipo();
        }else if(atributo.equals("gasolina")){
        	retorno = caronaTemp.getGasolina()+"";
        }
        
        return retorno;
	}
	
	/**
	 * Metodo que localiza uma Carona pelo seu ID
	 * @param idCarona - Recebe uma String idCarona
	 * @return - Retorna uma Carona caso ela exista
	 * @throws CaronaException - Lança uma exceção caso nao exista uma Carona com esse idCarona.
	 */
	public Carona localizaCaronaPorId(String idCarona) throws CaronaException {
		return repositorio.getCaronaId(idCarona);
	}

	/**
	 * Metodo que retorna o trajeto de uma Carona
	 * @param idCarona - Recebe uma String idCarona
	 * @return - Retorna uma String com o trajeto da Carona caso a carona exista.
	 * @throws CaronaException
	 * @throws ArgumentoInexistenteException - Lança exceção caso nao exista uma Carona com esse id
	 */
	public String getTrajeto(String idCarona) throws CaronaException, ArgumentoInexistenteException {
		
		Carona caronaTemp = repositorio.getCaronaId(idCarona);
		if(caronaTemp == null){
			throw new ArgumentoInexistenteException("Trajeto Inexistente");
		}
		return caronaTemp.getOrigem() + " - " + caronaTemp.getDestino();
	}
	
	/**
	 * Metodo que localiza uma SugestaoDePontoDeEncontro atraves do id da sugestão
	 * @param idSugestao - Recebe uma String idSugestão
	 * @param idCarona - Recebe uma String idCarona
	 * @return - Retorna uma SugestãoDePontoDeEncontro localizada na carona que possui o IdCarona
	 * @throws CaronaException - Retorna uma exceção caso nao exista carona com o Id passado.
	 */
	public SugestaoDePontoDeEncontro getSugestaoId(String idSugestao, String idCarona) throws CaronaException{
		return repositorio.getSugestaoId(idSugestao, idCarona);
	}
	
	/**
	 * Metodo que verifica se ja existe algum usuario cadastrado com esse login
	 * @param login - recebe uma String login
	 * @return - Retorna True caso exista algum Usuario com esse login ou False caso nao exista.
	 */
	public boolean checaExisteLogin(String login){
		return repositorio.checaExisteLogin(login);
	}
	
	/**
	 * Metodo que verifica se ja existe algum usuario cadastrado com esse email
	 * @param email - Recebe uma String email
	 * @return - Retorna True caso exista algum Usuario com esse email ou False caso nao exista.
	 */
	public boolean checaExisteEmail(String email){
		return repositorio.checaExisteEmail(email);
	}
	
	/**
	 * Metodo que retorna os atributos de uma solicitação
	 * @param idSolicitacao - Recebe uma string idSolicitacao
	 * @param atributo - Recebe uma String atributo
	 * @return - Retorna uma String com o atributo solicitado caso exista a solicitacao ou o atributo seja valido
	 */
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) {		
		SolicitacaoDeVaga solicitacaoTemp = localizaSolicitacaoPorId(idSolicitacao);
		
		String retorno = "";
		if(atributo.equals("origem")){
			retorno = solicitacaoTemp.getOrigem();
		}else if(atributo.equals("destino")){
			retorno = solicitacaoTemp.getDestino();
		}else if(atributo.equals("Dono da carona")){
			retorno = solicitacaoTemp.getDonoDaCarona().getNome();
		}else if(atributo.equals("Dono da solicitacao")){
         retorno = solicitacaoTemp.getDonoSolicitacao().getNome();
		}else if(atributo.equals("Ponto de Encontro")){
			retorno = solicitacaoTemp.getPonto();
		}
		
		return retorno;
	}
	
	/**
	 * Metodo que localiza uma solicitacao de vaga por um ID
	 * @param idSolicitacao - Recebe uma String idSolicitacao
	 * @return - Retorna uma SolicitacaoDeVaga caso exista alguma solicitacao com o Id passado.
	 */
	public SolicitacaoDeVaga localizaSolicitacaoPorId(String idSolicitacao) {
		return repositorio.localizaSolicitacaoPorId(idSolicitacao);
	}

	/**
	 * Metodo que inicia um novo sistema 
	 */
	public void zerarSistema() {
		repositorio.zerarSistema();
	}

	/**
	 * Metodo que salva todos os usuarios cadastrados num arquivo XML
	 */
	public void encerrarSistema() {
		repositorio.encerrarSistema();
	}

	/**
	 * Metodo que lozaliza uma CaronaMunicipal atravas dos dados de origem, destino e cidade, caso
	 *  origem e destino sejam vazias, o metodo filtra apenas pela String cidade.
	 * @param cidade - Recebe uma String cidade
	 * @param origem - Recebe uma String origem
	 * @param destino - Recebe uma String destino
	 * @return - Retorna uma List<Carona> que possua origem, destino e cidade iguais aos passados como parâmetro
	 * @throws CaronaException
	 */
	public List<Carona> localizarCaronaMunicipal(String cidade, String origem, String destino) throws CaronaException {
		if(destino.equals("") && origem.equals("")){
			return repositorio.localizarCaronaMunicipal(cidade);
		}else{
			return repositorio.localizarCaronaMunicipal(cidade, origem, destino);
		}
	}

	/**
	 * Metodo que retorna os atributos de uma Carona Relampago
	 * @param idCarona - Recebe uma String idCarona
	 * @param atributo - Recebe uma String atributo
	 * @return - Retorna uma String com o atributo solicitado caso a carona exista e o atributo seja valido.
	 * @throws CaronaException - Lança exceção caso a carona nao exista.
	 * @throws ArgumentoInexistenteException - Lança exceção caso o atributo seja inválido
	 */
	public String getAtributoCaronaRelampago(String idCarona, String atributo) throws CaronaException, ArgumentoInexistenteException {
		String retorno = "";
		
		if (atributo.equals("minimoCaroneiros")){
			retorno = ((Escapada) localizaCaronaPorId(idCarona)).getMinimoCaroneiros()+"";
		}else if(atributo.equals("dataIda")){
			retorno = getAtributoCarona(idCarona, "data");
		}else if(atributo.equals("expired")){
			retorno = ((Escapada) localizaCaronaPorId(idCarona)).isExpirada()+"";
		}else{
			retorno = getAtributoCarona(idCarona, atributo);
		}
		if(retorno.equals("")){
	      	throw new ArgumentoInexistenteException("Atributo inexistente");
	    }
		
		return retorno;
	}

	/**
	 * Metodo que retorna uma lista com as Caronas Rachadas que possuem origem e destino passado como parametro.
	 * @param origem - Recebe uma String origem
	 * @param destino - Recebe uma String destino
	 * @return - Retorna uma List<Carona> com as caronas Rachadas.
	 */
	public List<Carona> localizarCaronaRachada(String origem, String destino) {
		return repositorio.localizarCaronaRachada(origem, destino);
	}
	
	public List<Carona> getTodasCaronas(){
		return repositorio.getCaronas();
	}
}
