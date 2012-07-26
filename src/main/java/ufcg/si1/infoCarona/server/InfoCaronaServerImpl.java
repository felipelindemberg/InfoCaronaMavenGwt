package ufcg.si1.infoCarona.server;

import java.util.List;

import ufcg.si1.infoCarona.client.ExceptionSerialized;
import ufcg.si1.infoCarona.client.InfoCaronaServer;
import ufcg.si1.infoCarona.controller.ControllerView;
import ufcg.si1.infoCarona.model.ArgumentoInexistenteException;
import ufcg.si1.infoCarona.model.LoggerException;
import ufcg.si1.infoCarona.model.NumeroMaximoException;
import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.sistema.SistemaRaiz;
import ufcg.si1.infoCarona.model.usuario.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial") 
public class InfoCaronaServerImpl extends RemoteServiceServlet implements InfoCaronaServer { 
	
	private ControllerView  controller;
	
	public InfoCaronaServerImpl() {
		controller = new ControllerView();
	}
	
	public String abrirSessao(String login, String senha) throws ExceptionSerialized {
			try {
				return controller.abrirSessao(login, senha);
			} catch (Exception e) {
				throw new ExceptionSerialized(e.getMessage());
			}
	}
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws ExceptionSerialized {
		try {
			controller.criarUsuario(login, senha, nome, endereco, email);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage()); 
		}
	}

	@Override
	public String cadastrarCarona(String idUsuario, String origem,
			String destino, String data, String hora, String vagas) throws ExceptionSerialized {
		
		try{
			return controller.cadastrarCarona(idUsuario, origem, destino, data, hora, vagas);
		}catch (Exception e){
			throw new ExceptionSerialized(e.getMessage()); 
		}
	}
	
	@Override
	public String cadastrarCaronaMunicipal(String idUsuario, String origem,
			String destino, String cidade, String data, String hora, String vagas) throws ExceptionSerialized {
		
		try {
			return controller.cadastrarCaronaMunicipal(idUsuario, origem, destino, cidade, data, hora, vagas);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
	
	@Override
	public List<String> getTodasCaronas() throws ExceptionSerialized {
		return controller.getTodasCaronas();
	}
	
	@Override
	public String getLoginUsuarioLogado(String idSessao) throws ExceptionSerialized {
		try {
			return controller.getUsuarioLogado(idSessao).getLogin();
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
	
	@Override
	public String[] getInformacoesUsuario(String login) throws ExceptionSerialized {
		try {
			return controller.getInformacoesUsuario(login);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
	
	public String setInformacoesUsuario(String idSessao, String nome, String endereco, String email, String novaSenha) throws ExceptionSerialized {
		try {
			Usuario usuario = controller.getUsuarioLogado(idSessao);
			usuario.setNome(nome);
			usuario.setEndereco(endereco);
			usuario.setEmail(email);
			usuario.setSenha(novaSenha);
			return null;
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<List<String>> getListaPontoEncontroCarona(String idCarona) throws ExceptionSerialized {
		try {
			return controller.getListaPontoEncontroCarona(idCarona);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
	
	@Override
	public String cadastrarNovosPontosEncontro(String idSessao, String idCarona, String novosPontos) throws ExceptionSerialized {
		try {
			return controller.cadastrarNovosPontosEncontro(idSessao, idCarona, novosPontos);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
	
	@Override
	public String encerrarSistema() {
		controller.encerrarSistema();
		return null;
	}
	
	@Override
	public List<String> getListaMotoristaPontoEncontroCarona(String idCarona) throws ExceptionSerialized {
		try {
			return controller.getListaMotoristaPontoEncontroCarona(idCarona);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public String solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws ExceptionSerialized {
		try {
			return controller.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<List<String>> getSolicitacoesAceitasUsuario(String idSessao) throws ExceptionSerialized {
		try {
			return controller.getSolicitacoesAceitasUsuario(idSessao);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
	
	@Override
	public List<List<String>> getSolicitacoesPendentesUsuario(String idSessao) throws ExceptionSerialized {
		try {
			return controller.getSolicitacoesPendentesUsuario(idSessao);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<List<String>> getSolicitacoesRejeitadasUsuario(String idSessao)
			throws ExceptionSerialized {
		try {
			return controller.getSolicitacoesRejeitadasUsuario(idSessao);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<String> getInformacoesCarona(String idCarona)
			throws ExceptionSerialized {
		try {
			return controller.getInformacoesCarona(idCarona);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<List<String>> getListaCaronasUsuario(String idSessao)
			throws ExceptionSerialized {
		try {
			return controller.getListaCaronasUsuario(idSessao);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<List<String>> getListaSolicitacoesPendentesCarona(String idCarona)
			throws ExceptionSerialized {

		try {
			return controller.getListSolicitacoesPendentesCarona(idCarona);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public String confirmarSolicitacaoUsuario(String idSessao,
			String idSolicitacao) throws ExceptionSerialized {
		try {
			controller.aceitarSolicitacaoUsuario(idSessao, idSolicitacao);
			return null;
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public String recusarSolicitacaoUsuario(String idSessao,
			String idSolicitacao) throws ExceptionSerialized {
		try {
			controller.recusarSolicitacaoUsuario(idSessao, idSolicitacao);
			return null;
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public List<List<String>> getListaSugestaoPontoEncontro(String idCarona)
			throws ExceptionSerialized {
		try {
			return controller.getListaSugestaoPontoEncontro(idCarona);
			
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}

	@Override
	public String aceitarSolicitacaoPontoEncontro(String idSessao,
			String idCarona, String idSugestao, String pontos) throws ExceptionSerialized {
		try {
			return controller.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, pontos);
		} catch (Exception e) {
			throw new ExceptionSerialized(e.getMessage());
		}
	}
}
