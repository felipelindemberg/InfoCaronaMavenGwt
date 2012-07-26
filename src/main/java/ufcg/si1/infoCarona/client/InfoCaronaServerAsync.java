package ufcg.si1.infoCarona.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InfoCaronaServerAsync {
	void abrirSessao(String login, String senha, AsyncCallback<String> asyncCallback);
	
	void criarUsuario(String login, String senha, String nome, String endereco, String email, AsyncCallback<Void> retorno);
	
	void cadastrarCarona(String idUsuario, String origem, String destino,
			String data, String hora, String vagas,
			AsyncCallback<String> callback);
	
	void cadastrarCaronaMunicipal(String idUsuario, String origem,
			String destino, String cidade, String data, String hora,
			String vagas, AsyncCallback<String> retorno );

	void getTodasCaronas(AsyncCallback<List<String>> retorno);
	
	void getLoginUsuarioLogado(String idSessao, AsyncCallback<String> login);
	
	void getInformacoesUsuario(String login, AsyncCallback<String[]> retorno);

	void setInformacoesUsuario(String idSessao, String nome, String endereco, String email, String novaSenha, AsyncCallback<String> retorno);
	
	void getListaPontoEncontroCarona(String idCarona, AsyncCallback<List<List<String>>> retorno);
	
	void cadastrarNovosPontosEncontro(String idSessao, String idCarona,
			String novosPontos, AsyncCallback<String> retorno);

	void encerrarSistema(AsyncCallback<String> retorno);
	
	void getListaMotoristaPontoEncontroCarona(String idCarona, AsyncCallback<List<String>> retorno);

	void solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto, AsyncCallback<String> retorno);

	void getSolicitacoesAceitasUsuario(String idSessao,
			AsyncCallback<List<List<String>>> retorno);
	
	void getSolicitacoesPendentesUsuario(String idSessao,
			AsyncCallback<List<List<String>>> retorno);
	
	void getSolicitacoesRejeitadasUsuario(String idSessao,
			AsyncCallback<List<List<String>>> retorno);

	void getInformacoesCarona(String idCarona, AsyncCallback<List<String>> retorno);

	void getListaCaronasUsuario(String idSessao, AsyncCallback<List<List<String>>> retorno);

	void getListaSolicitacoesPendentesCarona(String idCarona, AsyncCallback<List<List<String>>> retorno);

	void confirmarSolicitacaoUsuario(String idSessao, String idSolicitacao,
			AsyncCallback<String> retorno);

	void recusarSolicitacaoUsuario(String idSessao, String idSolicitacao,
			AsyncCallback<String> retorno);

	void getListaSugestaoPontoEncontro(String idCarona, AsyncCallback<List<List<String>>> retorno);

	void aceitarSolicitacaoPontoEncontro(String idSessao, String idCarona,
			String idSugestao, String pontos, AsyncCallback<String> retorno);
}
