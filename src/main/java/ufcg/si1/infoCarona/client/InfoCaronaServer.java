package ufcg.si1.infoCarona.client;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface InfoCaronaServer extends RemoteService{
	String abrirSessao(String login, String senha) throws ExceptionSerialized;
	
	void criarUsuario(String login, String senha, String nome, String endereco, String email) throws ExceptionSerialized;
	
	String cadastrarCarona(String idUsuario, String origem, String destino, String data, String hora, String vagas) throws ExceptionSerialized;
	
	String cadastrarCaronaMunicipal(String idUsuario, String origem,
			String destino, String cidade, String data, String hora,
			String vagas) throws ExceptionSerialized;

	List<String> getTodasCaronas() throws ExceptionSerialized;
	
	String getLoginUsuarioLogado(String idSessao) throws ExceptionSerialized;
	
	String[] getInformacoesUsuario(String login) throws ExceptionSerialized;
	
	String setInformacoesUsuario(String idSessao, String nome, String endereco, String email, String novaSenha) throws ExceptionSerialized;

	List<List<String>> getListaPontoEncontroCarona(String idCarona) throws ExceptionSerialized;

	String cadastrarNovosPontosEncontro(String idSessao, String idCarona,
			String novosPontos) throws ExceptionSerialized;
	
	String encerrarSistema();

	List<String> getListaMotoristaPontoEncontroCarona(String idCarona) throws ExceptionSerialized;

	String solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto) throws ExceptionSerialized;
	
	List<List<String>> getSolicitacoesAceitasUsuario(String idSessao)
			throws ExceptionSerialized;
	
	List<List<String>> getSolicitacoesPendentesUsuario(String idSessao)
			throws ExceptionSerialized;
	
	List<List<String>> getSolicitacoesRejeitadasUsuario(String idSessao)
			throws ExceptionSerialized;
	
	List<String> getInformacoesCarona(String idCarona) throws ExceptionSerialized;
	
	List<List<String>> getListaCaronasUsuario(String idSessao) throws ExceptionSerialized;
	
	List<List<String>> getListaSolicitacoesPendentesCarona(String idCarona) throws ExceptionSerialized;
	
	String confirmarSolicitacaoUsuario(String idSessao, String idSolicitacao) throws ExceptionSerialized;

	String recusarSolicitacaoUsuario(String idSessao, String idSolicitacao) throws ExceptionSerialized;
	
	List<List<String>> getListaSugestaoPontoEncontro(String idCarona) throws ExceptionSerialized;

	String aceitarSolicitacaoPontoEncontro(String idSessao, String idCarona,
			String idSugestao, String pontos) throws ExceptionSerialized;
}
