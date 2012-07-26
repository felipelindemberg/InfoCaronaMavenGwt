package ufcg.si1.infoCarona.model.usuario;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import ufcg.si1.infoCarona.model.ArgumentoInexistenteException;
import ufcg.si1.infoCarona.model.Interesse;
import ufcg.si1.infoCarona.model.LoggerException;
import ufcg.si1.infoCarona.model.NumeroMaximoException;
import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaComum;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.carona.CaronaMunicipal;
import ufcg.si1.infoCarona.model.carona.Escapada;
import ufcg.si1.infoCarona.model.carona.Rachada;
import ufcg.si1.infoCarona.model.negociacao.EstadoSolicitacao;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;

/**
 * Classe Usuario que possui todos os dados de um usuarui do sistema
 * @author Italo Tavares
 * @author Felipe Lindemberg 
 * @author Gilles Medeiros
 * @author Wallison Tavares
 *
 */
public class Usuario implements Interessado{

	private List<Carona> listaDeCaronas;
	private List<SolicitacaoDeVaga> listaDeSolicitacaoDeVagas;
	private List<String> Reviews;
	private List<Interesse> listaDeInteresses;
	private List<String> listaDeMensagens;
	private List<Usuario> usuariosPreferenciais;

	private int caronasSeguras, caronaNaoFuncionaram, faltasEmVagas, presencaEmVagas;
	private String nome, email, endereco, senha, login;

	/**
	 * Construtor de Usuario
	 * @param nome - Recebe uma String nome
	 * @param email - Recebe uma String email
	 * @param endereco - Recebe uma String endereço
	 * @param senha - Recebe uma String senha
	 * @param login - Recebe uma String login
	 * @throws LoggerException - Caso algum dos parametros passados seja inválido, é lançado a exceção
	 */
	public Usuario(String nome, String email, String endereco, String senha,String login) throws LoggerException{
		setNome(nome);
		setEmail(email);
		setEndereco(endereco);
		setSenha(senha);
		setLogin(login);
		this.listaDeCaronas = new LinkedList<Carona>();
		this.listaDeSolicitacaoDeVagas = new LinkedList<SolicitacaoDeVaga>();
		this.listaDeInteresses = new LinkedList<Interesse>();
		this.listaDeMensagens = new LinkedList<String>();
		this.Reviews = new LinkedList<String>();
		this.usuariosPreferenciais = new LinkedList<Usuario>();
		this.caronasSeguras = 0;
		this.caronaNaoFuncionaram = 0;
		this.faltasEmVagas = 0;
		this.presencaEmVagas = 0;
	}

	/**
	 * Metodo que retorna uma lista com os interesse do Usuario
	 */
	public List<Interesse> getListaDeInteresses() {
		return listaDeInteresses;
	}

	/**
	 * Metodo para cadastrar uma carona comum
	 * @param origem - Recebe uma String origem
	 * @param destino - Recebe uma String destino
	 * @param calendar- Recebe um objeto Calendar que contem data e hora da carona
	 * @param vagas - Recebe um int que é o numero de vagas da carona
	 * @param idCarona - Recebe uma String que é o id da Carona
	 * @return - Retorna o Id da carona caso ela seja cadastrada
	 * @throws CaronaException - Se for passado algum dado errado para a carona, a exceção é lançada
	 * @throws NumeroMaximoException - Se for atingido o numero maximo de id's é lançada a exceção
	 */
	public String cadastrarCarona(String origem, String destino, Calendar calendar, int vagas, String idCarona)
			throws CaronaException, NumeroMaximoException {
		
		Carona carona = new CaronaComum(origem, destino, calendar, vagas,
				idCarona, this);
		listaDeCaronas.add(carona);
		return idCarona;
	}

	public String toString() {
		return "Nome: " + nome + " Login: " + login;
	}

	/**
	 * Metodo para sugerir um ponto de encontro para uma Carona
	 * @param pontos - Recebe uma String com os pontos separados por ";"
	 * @param carona - Recebe uma Carona na qual vai ser sugerido os pontos
	 * @param idSugestao - Recebe uma String id que vai ser a id da Sugestao
	 * @param usuarioQueSugeriu - Recebe um Usuario, que é o Usuario que sugeriu os pontos.
	 * @return - Retorna o Id da sugestao, caso a sugestao seja válida
	 * @throws CaronaException - Se for passado algum dado errado para a carona, a exceção é lançada
	 * @throws NumeroMaximoException - Se for atingido o numero maximo de id's é lançada a exceção
	 */
	public String sugerirPontoEncontro(String pontos, Carona carona,
			String idSugestao, Usuario usuarioQueSugeriu)
			throws CaronaException, NumeroMaximoException {
		SugestaoDePontoDeEncontro sugestao = new SugestaoDePontoDeEncontro(
				idSugestao, usuarioQueSugeriu);

		String[] locais = pontos.split(";");// sugestao de locais(ponto) de
											// encontro
		for (String local : locais) {
			if (!carona.getListaPontosDeEncontroPermitidos().contains(local)) {
				sugestao.getListaDeSugestaoDePontosDeEncontro().add(local);
			} else {
				throw new IllegalArgumentException("Ponto inválido");
			}

		}

		carona.getListaDeSugestoes().add(sugestao);
		return idSugestao;
	}

	/**
	 * Metodo que retorna uma lista das solicitações de vagas realizadas pelo Usuario
	 * @return - retorna uma List<SolicitacaoDeVaga> das solicitações de vagas
	 */
	public List<SolicitacaoDeVaga> getListaDeSolicitacaoDeVagas() {
		return listaDeSolicitacaoDeVagas;
	}

	/**
	 * Metodo para responder uma Sugestao de Ponto de Encontro feita para a carona do Usuario
	 * @param sugestao - Recebe um objeto SugestaoPontoDeEncontro
	 * @param pontos - Recebe os pontos que são válidos
	 * @param carona - Recebe uma Carona no qual vai ser realizada a sugestão
	 * @throws CaronaException - Se for passado algum dado errado para a carona, a exceção é lançada
	 */
	public void responderSugestaoPontoEncontro(
			SugestaoDePontoDeEncontro sugestao, String pontos, Carona carona)
			throws CaronaException {
		String[] locais = pontos.split(";");
		for (String local : locais) {
			if (!carona.getListaPontosDeEncontroPermitidos().contains(local)) {
				sugestao.getlistaDeRespostasDePontosDeEncontro().add(local);
				carona.addPontoEncontroPermitido(local);
			} else {
				throw new IllegalArgumentException("Ponto inválido");
			}
		}
	}

	/**
	 * Metodo que o Usuario usa para Solicitar vaga em uma carona
	 * @param ponto - Recebe uma String com o ponto de encontro
	 * @param carona - Recebe uma Carona na qual o Usuario solicita a vaga
	 * @param IdSolicitacao - Recebe uma String que é o id da solicitação
	 * @param donoSolcitacao - Recebe um Usuario que é o dono da Solicitação
	 * @return - Retorna a id da Soliicitação
	 * @throws CaronaException - Se for passado algum dado errado para a carona, a exceção é lançada
	 * @throws NumeroMaximoException - Se for atingido o numero maximo de id's é lançada a exceção
	 */
	public String solicitarVagaPontoEncontro(String ponto, Carona carona,
			String IdSolicitacao, Usuario donoSolcitacao)
			throws CaronaException,
			NumeroMaximoException {
		
		if(carona.isPreferencial()){
			boolean preferencial = false;
			List<Usuario> preferenciais = carona.getDonoDaCarona().getUsuariosPreferenciais();
			for(Usuario usuario : preferenciais){
				if(usuario.equals(donoSolcitacao)){
					preferencial = true;
				}
			}
			if(!preferencial){
				throw new CaronaException("Usuário não está na lista preferencial da carona");
			}
		}
		
		if (carona.getVagas() > 0) {
			SolicitacaoDeVaga novaSolicitacao = new SolicitacaoDeVaga(carona,
					ponto, IdSolicitacao, donoSolcitacao);
			listaDeSolicitacaoDeVagas.add(novaSolicitacao);
			carona.addNovaSolicitacao(novaSolicitacao);
		}else{
			throw new CaronaException("Carona ja está completa.");
		}
		return IdSolicitacao;
	}

	/**
	 * Metodo onde o Usuario aceita as solicitações feitas a sua carona
	 * @param solicitacao - Recebe um objeto SolicitacaoDeVaga
	 * @throws ArgumentoInexistenteException - Se a solicitação jah tiver sido aeita é lançada a exceção.
	 */
	public void aceitarSolicitacaoPontoEncontro(SolicitacaoDeVaga solicitacao) throws ArgumentoInexistenteException {
		if (solicitacao.getEstado().equals(EstadoSolicitacao.ACEITA)) {
			throw new ArgumentoInexistenteException("Solicitação inexistente");
		}
		solicitacao.solicitacaoAceita();
	}

	/**
	 * Metodo para um Usuario desistir de uma Solicitação de Vaga
	 * @param solicitacao - Recebe um objeto SolicitacaoDeVaga 
	 * @param caronaTemp - Recebe um Objeto Carona na qual voce deseja desistir da solicitação
	 */
	public void desistirRequisicao(SolicitacaoDeVaga solicitacao,
			Carona caronaTemp) {
		listaDeSolicitacaoDeVagas.remove(solicitacao);
		caronaTemp.removeSolicitacao(solicitacao);
	}

	public int getCaronasSeguras() {
		return this.caronasSeguras;
	}

	public void setCaronasSeguras() {
		this.caronasSeguras++;
	}

	public int getCaronasNaoFuncionaram() {
		return this.caronaNaoFuncionaram;
	}

	public void setCaronasNaoFuncionaram() {
		this.caronaNaoFuncionaram++;
	}

	public int getFaltasEmVagas() {
		return this.faltasEmVagas;
	}

	public void setFaltasEmVagas() {
		this.faltasEmVagas++;
	}

	public int getPresencaEmVagas() {
		return this.presencaEmVagas;
	}

	public void setPresencaEmVagas() {
		this.presencaEmVagas++;
	}

	// aki come�a os metodos do usuario

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws LoggerException {
		if ((email == null || email.trim().equals("") || email.contains(" "))) {
			throw new LoggerException("Email inválido");
		}
		this.email = email.trim();
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco.trim();
	}

	public String getSenha() {
		return this.senha;
	}

	public List<String> getListaDeMensagens() {
		return this.listaDeMensagens;
	}

	public void setSenha(String senha) throws LoggerException {
		if ((senha == null || senha.trim().equals(""))) {
			throw new LoggerException("Senha inválido");
		}
		this.senha = senha.trim();
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	private void setLogin(String login) throws LoggerException {
		if ((login == null || login.trim().equals(""))) {
			throw new LoggerException("Login inválido");
		}
		this.login = login.trim();
	}

	public void setNome(String nome) throws LoggerException {
		if (nome == null || nome.trim().equals("")) {
			throw new LoggerException("Nome inválido");
		}
		this.nome = nome.trim();
	}

	/**
	 * Metodo para Rejeitar um solicitação feita a uma Carona do Usuario
	 * @param solicitacao - Recebe um Objeto SolicitacaoDeVaga
	 * @throws ArgumentoInexistenteException - Caso a carona já tenha sido rejeitada é lançada a exceção
	 */
	public void rejeitarSolicitacao(SolicitacaoDeVaga solicitacao)
			throws ArgumentoInexistenteException{
		if (solicitacao.getEstado().equals(EstadoSolicitacao.REJEITADA)) {
			throw new ArgumentoInexistenteException("Solicitação inexistente");
		}
		solicitacao.solicitacaoRejeitada();

	}

	public List<Carona> getCaronas() {
		return listaDeCaronas;
	}

	public String visualizarPerfil(Usuario usuarioProcurado) {
		return usuarioProcurado.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Usuario)) {
			return false;
		}

		if (!(((Usuario) obj).getLogin().equals(this.login))) {
			return false;
		}

		return true;
	}

	/**
	 * Metodo onde o Usuario pode adicionar um Review a uma Carona que ele pegou
	 * @param caronaTemp - Recebe um objetoCarona
	 * @param review - Recebe uma String com o Review
	 */
	public void adicionaReview(Carona caronaTemp, String review) {
		if (review.equals("não faltou")) {
			this.setPresencaEmVagas();
		} else {
			this.setFaltasEmVagas();
		}
		Reviews.add(review + " na Carona de " + caronaTemp.toString());
	}

	/**
	 * Metodo que retorna todas as Caronas onde as solicitaçoes aceitas por um Usuario
	 * @return - Retorna uma List<Carona> com todas as caronas que foram aceitas
	 */
	public List<Carona> getSolicitacaoAceitas() {
		LinkedList<Carona> listaCaronaAceitas = new LinkedList<Carona>();
		for (SolicitacaoDeVaga solicitacao : listaDeSolicitacaoDeVagas) {
			if (solicitacao.getEstado().equals(EstadoSolicitacao.ACEITA)) {
				listaCaronaAceitas.add(solicitacao.getCarona());
			}
		}
		return listaCaronaAceitas;
	}

	/**
	 * 
	 * @param origem, local de saída da carona
	 * @param destino, local de chegada da carona
	 * @param cidade, de origem da carona
	 * @param calendario, Objeto tipo Calendar com a data da carona
	 * @param vagas, numero de vagas que ainda existe na carona
	 * @param idCarona
	 * @return idCarona
	 * @throws CaronaException, exceção de carona invalida
	 */
	public String cadastrarCaronaMunicipal(String origem, String destino,
			String cidade, Calendar calendario, int vagas, String idCarona)
			throws 
			CaronaException{
		Carona carona = new CaronaMunicipal(origem, destino, cidade, calendario, vagas, idCarona, this);
		listaDeCaronas.add(carona);
		
		return idCarona;
	}

	/**
	 * 
	 * @param origem, local de saída da carona
	 * @param destino, local de chegada da carona
	 * @param calendarioInicial, Objeto tipo Calendar com a data da ida da carona
	 * @param calendarioFinal, Objeto tipo Calendar com a data da volta da carona
	 * @param id
	 * @param caronaEhNoDia
	 * @return id da carona
	 * @throws CaronaException, exceção de carona invalida
	 */
	public String cadastrarInteresse(String origem, String destino,
			Calendar calendarioInicial, Calendar calendarioFinal, String id, boolean caronaEhNoDia)
			throws CaronaException {
		Interesse interesseTemp = new Interesse(this, origem, destino,
				calendarioInicial, calendarioFinal, id, caronaEhNoDia);
		listaDeInteresses.add(interesseTemp);
	
		return id;
	}

	/**
	 * Adiciona mensagem à lista de mensagens
	 * @param novaMensagem
	 */
	public void addMensagen(String novaMensagem) {
		listaDeMensagens.add(novaMensagem);
	}
	
	/**
	 * 
	 * @param origem, local de saída da carona
	 * @param destino, local de chegada da carona
	 * @param dataIda, Objeto tipo Calendar com a data da ida da carona
	 * @param dataVolta, Objeto tipo Calendar com a data da volta da carona
	 * @param vagas, numero de vagas que ainda existe na carona
	 * @param idCarona
	 * @param minimoCaroneiro
	 * @return idCarona
	 * @throws CaronaException, exceção de carona invalida
	 */
	public String cadastrarEscapada(String origem, String destino,
			Calendar dataIda, Calendar dataVolta, int vagas, String idCarona, int minimoCaroneiro) throws CaronaException {
		
		Carona carona = new Escapada(origem, destino, dataIda, vagas,
				idCarona, this, dataVolta, minimoCaroneiro);
		listaDeCaronas.add(carona);
		return idCarona;
	}
	
	/**
	 * Adiciona um usuário de preferencia à lista
	 * @param usuario
	 */
	public void addUsuarioPreferencial(Usuario usuario){
		usuariosPreferenciais.add(usuario);
	}
	
	/**
	 * Retorna uma lista de usuarios preferenciais
	 * @return usuariosPreferenciais
	 */
	public List<Usuario> getUsuariosPreferenciais(){
		return this.usuariosPreferenciais;
	}

	/**
	 * 
	 * @param origem, local de saída da carona
	 * @param destino, local de chegada da carona
	 * @param calendario, Objeto tipo Calendar com a data da ida da carona
	 * @param vagas, numero de vagas que ainda existe na carona
	 * @param gasolina, preço da valor da gasolina
	 * @param idCarona
	 * @return idCarona
	 * @throws CaronaException, exceção de carona invalida
	 */
	public String cadastrarCaronaRachadaComum(String origem, String destino,
			Calendar calendario, int vagas, double gasolina, String idCarona) throws CaronaException {
		
		Carona carona = new CaronaComum(origem, destino, calendario, vagas, idCarona, this);
		Carona rachada = new Rachada(origem, destino, calendario, vagas, idCarona, this, gasolina, carona);
		listaDeCaronas.add(rachada);
		
		return idCarona;
	}

	/**
	 * 
	 * @param origem, local de saída da carona
	 * @param destino, local de chegada da carona
	 * @param calendarioIda, Objeto tipo Calendar com a data da ida da carona
	 * @param calendarioVolta, Objeto tipo Calendar com a data da volta da carona
	 * @param minimoCaroneiros
	 * @param valorDaGasolina, preço da valor da gasolina
	 * @param vagas
	 * @param idCarona
	 * @return idCarona
	 * @throws CaronaException, exceção de carona invalida
	 */
	public String cadastrarEscapadaRachada(String origem, String destino,
			Calendar calendarioIda, Calendar calendarioVolta,
			int minimoCaroneiros, double valorDaGasolina, int vagas, String idCarona) throws CaronaException {
		
		Carona escapada = new Escapada(origem, destino, calendarioIda, vagas, idCarona, this, calendarioVolta, minimoCaroneiros);
		Carona rachada = new Rachada(origem, destino, calendarioIda, vagas, idCarona, this, valorDaGasolina, escapada);
		listaDeCaronas.add(rachada);
		
		return idCarona;
	}

	/**
	 * 
	 * @param idSessao, id da sessão logada
	 * @param origem, local de saída da carona
	 * @param destino, local de chegada da carona
	 * @param cidade, cidade de origem da carona
	 * @param calendario, Objeto tipo Calendar com a data da carona
	 * @param vagas, numero de vagas existente na carona
	 * @param gasolina, valor do preço da gasolina
	 * @param idCarona
	 * @return idCarona
	 * @throws CaronaException, exceção de carona invalida
	 */
	public String cadastrarCaronaMunicipalRachada(String idSessao,
			String origem, String destino, String cidade, Calendar calendario,
			int vagas, double gasolina, String idCarona) throws CaronaException {
		
		Carona caronaMunicipal = new CaronaMunicipal(origem, destino, cidade, calendario, vagas, idCarona, this);
		Carona rachada = new Rachada(origem, destino, calendario, vagas, idCarona, this, gasolina, caronaMunicipal);
		listaDeCaronas.add(rachada);
		
		return idCarona;
	}
}
