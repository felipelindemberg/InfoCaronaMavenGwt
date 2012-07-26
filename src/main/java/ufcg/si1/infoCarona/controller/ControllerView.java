package ufcg.si1.infoCarona.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ufcg.si1.infoCarona.model.ArgumentoInexistenteException;
import ufcg.si1.infoCarona.model.LoggerException;
import ufcg.si1.infoCarona.model.NumeroMaximoException;
import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.negociacao.EstadoSolicitacao;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.sistema.SistemaCarona;
import ufcg.si1.infoCarona.model.sistema.SistemaNegociacao;
import ufcg.si1.infoCarona.model.sistema.SistemaRaiz;
import ufcg.si1.infoCarona.model.sistema.SistemaUsuario;
import ufcg.si1.infoCarona.model.sistema.SistemaUsuarioNaoLogado;
import ufcg.si1.infoCarona.model.usuario.Usuario;
import ufcg.si1.infoCarona.util.UtilInfo;
	

public class ControllerView  {
	
	private SistemaUsuarioNaoLogado sistemaNaoLogado;
	private SistemaRaiz sistemaRaiz;
	private SistemaCarona sistemaCarona;
	private SistemaUsuario sistemaUsuario;
	private SistemaNegociacao sistemaNegociacao;
	
	public ControllerView() {
		sistemaNegociacao = SistemaNegociacao.getInstance();
		sistemaNaoLogado = SistemaUsuarioNaoLogado.getInstace();
		sistemaRaiz = SistemaRaiz.getInstance();
		sistemaCarona = SistemaCarona.getInstance();
		sistemaUsuario = SistemaUsuario.getInstance();
	}
	
	public String abrirSessao(String login, String senha) throws LoggerException, NumeroMaximoException, ArgumentoInexistenteException {
		return sistemaNaoLogado.abrirSessao(login, senha);
		
	}
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws LoggerException {
		sistemaNaoLogado.criarUsuario(login, senha, nome, endereco, email);
	}
	
	public String cadastrarCarona(String idUsuario, String origem, String destino, String data, String hora, String vagas) throws NumberFormatException, CaronaException, NumeroMaximoException, ArgumentoInexistenteException, ParseException{
		Calendar calendario = UtilInfo.converteStringEmCalendar(data, hora);
		return sistemaCarona.cadastrarCarona(idUsuario, origem, destino, calendario, Integer.parseInt(vagas));
	}

	public String cadastrarCaronaMunicipal(String idUsuario, String origem,
			String destino, String cidade, String data, String hora,
			String vagas) throws NumberFormatException, CaronaException, NumeroMaximoException, ArgumentoInexistenteException {
		Calendar calendario = UtilInfo.converteStringEmCalendar(data, hora);
		return sistemaCarona.cadastrarCaronaMunicipal(idUsuario, origem, destino, cidade, calendario, Integer.parseInt(vagas));
	}
	
	public List<String> getTodasCaronas() {
		List<String> todasCaronas = new ArrayList<String>();
		for (Carona carona : sistemaCarona.getTodasCaronas()) {
			todasCaronas.add(carona.toStringView());
		}
		return todasCaronas;
	}
	
	public Usuario getUsuarioLogado(String idSessao) throws ArgumentoInexistenteException {
		return sistemaRaiz.procuraUsuarioLogado(idSessao);
	}

	public String[] getInformacoesUsuario(String login) throws LoggerException, ArgumentoInexistenteException {
		String[]  informacoes = { sistemaUsuario.getAtributoPerfil(login, "nome"), sistemaUsuario.getAtributoPerfil(login, "endereco"),	sistemaUsuario.getAtributoPerfil(login, "email")};
		return informacoes;
	}
	
	public List<List<String>> getListaPontoEncontroCarona(String idCarona) throws CaronaException {
		return sistemaCarona.getSugestoes(idCarona);
	}

	public String cadastrarNovosPontosEncontro(String idSessao, String idCarona, String novosPontos) throws CaronaException, NumeroMaximoException, ArgumentoInexistenteException {
		return sistemaNegociacao.sugerirPontoEncontro(idSessao, idCarona, novosPontos);
	}

	public void encerrarSistema() {
		sistemaRaiz.encerrarSistema();
	}

	public List<String> getListaMotoristaPontoEncontroCarona(String idCarona) throws CaronaException {
		return sistemaCarona.getSugestoesMotorista(idCarona);
	}

	public String solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws CaronaException, NumeroMaximoException, ArgumentoInexistenteException {
		return sistemaNegociacao.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
	}

	public List<List<String>> getSolicitacoesAceitasUsuario(String idSessao) throws ArgumentoInexistenteException {
		List<SolicitacaoDeVaga> lista = getUsuarioLogado(idSessao).getListaDeSolicitacaoDeVagas();
		List<List<String>> retorno = new ArrayList<List<String>>();
		for (SolicitacaoDeVaga solicitacaoDeVaga : lista) {
			List<String> aux = new ArrayList<String>();
			if (solicitacaoDeVaga.getEstado().equals(EstadoSolicitacao.ACEITA)) {
				aux.add(solicitacaoDeVaga.getIdSolicitacao());
				aux.add(solicitacaoDeVaga.getCarona().getIdCarona());
				aux.add(solicitacaoDeVaga.getPonto());
			}
			retorno.add(aux);
		}
		return retorno;
	}
	
	public List<List<String>> getSolicitacoesPendentesUsuario(String idSessao) throws ArgumentoInexistenteException {
		List<SolicitacaoDeVaga> lista = getUsuarioLogado(idSessao).getListaDeSolicitacaoDeVagas();
		List<List<String>> retorno = new ArrayList<List<String>>();
		for (SolicitacaoDeVaga solicitacaoDeVaga : lista) {
			List<String> aux = new ArrayList<String>();
			if (solicitacaoDeVaga.getEstado().equals(EstadoSolicitacao.PENDENTE)) {
				aux.add(solicitacaoDeVaga.getIdSolicitacao());
				aux.add(solicitacaoDeVaga.getCarona().getIdCarona());
				aux.add(solicitacaoDeVaga.getPonto());
			}
			retorno.add(aux);
		}
		return retorno;
	}
	
	public List<List<String>> getSolicitacoesRejeitadasUsuario(String idSessao) throws ArgumentoInexistenteException {
		List<SolicitacaoDeVaga> lista = getUsuarioLogado(idSessao).getListaDeSolicitacaoDeVagas();
		List<List<String>> retorno = new ArrayList<List<String>>();
		for (SolicitacaoDeVaga solicitacaoDeVaga : lista) {
			List<String> aux = new ArrayList<String>();
			if (solicitacaoDeVaga.getEstado().equals(EstadoSolicitacao.REJEITADA)) {
				aux.add(solicitacaoDeVaga.getIdSolicitacao());
				aux.add(solicitacaoDeVaga.getCarona().getIdCarona());
				aux.add(solicitacaoDeVaga.getPonto());
			}
			retorno.add(aux);
		}
		return retorno;
	}

	public List<String> getInformacoesCarona(String idCarona) throws CaronaException {
		List<String> retorno = new ArrayList<String>();
		Carona carona = sistemaCarona.localizaCaronaPorId(idCarona);
		retorno.add(carona.getIdCarona());
		retorno.add(carona.getDonoDaCarona().getNome());
		retorno.add(carona.getOrigem());
		retorno.add(carona.getDestino());
		retorno.add(UtilInfo.converteCalendarEmStringData(carona.getCalendario()));
		retorno.add(UtilInfo.converteCalendarEmStringHora(carona.getCalendario()));
		retorno.add(""+carona.getVagas());
		return retorno;
	}

	public List<List<String>> getListaCaronasUsuario(String idSessao) throws CaronaException, ArgumentoInexistenteException {
		List<List<String>> retorno = new ArrayList<List<String>>();
		for (Carona carona : getUsuarioLogado(idSessao).getCaronas()) {
			retorno.add(getInformacoesCarona(carona.getIdCarona()));
		}
		return retorno;
	}

	public List<List<String>> getListSolicitacoesPendentesCarona(String idCarona) throws CaronaException {
		List<List<String>> retorno = new ArrayList<List<String>>();
		for (SolicitacaoDeVaga solicitacao : sistemaCarona.localizaCaronaPorId(idCarona).getListaDeSolicitacao()) {
			if (solicitacao.getEstado().equals(EstadoSolicitacao.PENDENTE)) {
				List<String> aux = new ArrayList<String>();
				aux.add(solicitacao.getIdSolicitacao());
				aux.add(solicitacao.getDonoDaCarona().getLogin());
				retorno.add(aux);
			}
		}
		return retorno;
	}

	public void aceitarSolicitacaoUsuario(String idSessao, String idSolicitacao) throws ArgumentoInexistenteException {
		sistemaNegociacao.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}

	public void recusarSolicitacaoUsuario(String idSessao, String idSolicitacao) throws ArgumentoInexistenteException {
		sistemaNegociacao.rejeitarSolicitacao(idSessao, idSolicitacao);
	}
	
	public List<List<String>> getListaSugestaoPontoEncontro(String idCarona) throws CaronaException {
		List<List<String>> retorno = new ArrayList<List<String>>();
		List<SugestaoDePontoDeEncontro> listaSugestao = sistemaCarona.localizaCaronaPorId(idCarona).getListaDeSugestoes();
		for (SugestaoDePontoDeEncontro sugestaoDePontoDeEncontro : listaSugestao) {
			List<String> aux = new ArrayList<String>();
			aux.add(sugestaoDePontoDeEncontro.getIdSugestao());
			aux.add(sugestaoDePontoDeEncontro.getUsuarioQueSugeriu().getLogin());
			for (String sugestao : sugestaoDePontoDeEncontro.getListaDeSugestaoDePontosDeEncontro()) {
				aux.add(sugestao);
			}
			retorno.add(aux);
		}
		return retorno;
	}

	public String responderSugestaoPontoEncontro(String idSessao, String idCarona,
			String idSugestao, String pontos) throws CaronaException, ArgumentoInexistenteException {
		sistemaNegociacao.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, pontos);
		return null;
		
	}
}
