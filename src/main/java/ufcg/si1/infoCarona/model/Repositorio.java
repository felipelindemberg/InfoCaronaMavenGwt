package ufcg.si1.infoCarona.model;

import java.util.LinkedList;
import java.util.List;

import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.carona.CaronaMunicipal;
import ufcg.si1.infoCarona.model.carona.TiposCarona;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.usuario.Usuario;


/**
 * REPOSITÓRIO DO PROJETO
 * Nessa classe existe todos os metodos relacionados a arquivos e persistência de dados.
 * @author Felipe Lindemberg
 * @author Italo Tavares
 * @author Gilles Medeiros
 * @author Wallison Fernando
 */
public class Repositorio {
	private List<Usuario> listaDeUsuarios;
	private ManipuladorArquivoXML arquivo;
	
	public static Repositorio instance;
	
	protected Repositorio() {
		criaRepositorio();
	}

	/**
	 * Método que define o diretorio do arquivo XML utilizado como banco de dados.
	 */
	private void criaRepositorio() {
		arquivo = new ManipuladorArquivoXML("src/main/java/ufcg/si1/infoCarona/arquivos/usuarios");
		listaDeUsuarios = arquivo.ler();
	}
	
	/**
	 * Construtor Singleton do Repositório
	 * @return - Retorna uma instância do repositório.
	 */
	public static Repositorio getInstance(){
		if (instance == null){
			instance = new Repositorio();
		}
		return instance;
	}


	/**
	 * Metodo que adiciona um novo usuário ao sistema
	 * @param usuario
	 */
	public void addUsuario(Usuario usuario) {
		listaDeUsuarios.add(usuario);
	}

	/**
	 * Metodo que busca um usuário através do seu login
	 * @param login - recebe um login como paramêtro
	 * @return - Retorna um Usuario caso exista algum com esse login ou retorna null caso não exista.
	 */
	public Usuario buscaUsuarioLogin(String login) {
		Usuario retorno = null;
		for (Usuario usuario : listaDeUsuarios) {
			if (usuario.getLogin().equals(login)) {
				retorno = usuario;
			}
		}

		return retorno;
	}

	/**
	 * Metodo que remove um usuario do sistema
	 * @param usuario - Recebe um Usuario como parametro e o remove caso ele exista.
	 */
	public void removeUsuarioLogin(Usuario usuario) {
		listaDeUsuarios.remove(usuario);
	}

	/**
	 * Metodo que retorna todos os usuarios cadastrados
	 * @return - retorna uma List<Usuario> com todos os usuarios cadastrados
	 */
	public List<Usuario> getTodosOsUsuarios() {
		return listaDeUsuarios;
	}

	/**
	 * Metodo que busca um Usuario através do email
	 * @param email - Recebe uma String email e procura algum Usuario com esse Email
	 * @return - Retorna o Usuario caso exista ou null se nao existir.
	 */
	public Usuario buscaUsuarioEmail(String email) {
		Usuario retorno = null;
		for (Usuario usuario : listaDeUsuarios) {
			if (usuario.getEmail().equals(email)) {
				retorno = usuario;
			}
		}

		return retorno;
	}

	/**
	 * Metodo que localiza um Usuario pelo seu nome
	 * @param nome - Recebe um String nome como parametro
	 * @return - Retorna uma List<Usuario> com os Usuario que possuem esse nome.
	 */
	public List<Usuario> buscaUsuarioNome(String nome) {
		List<Usuario> retorno = new LinkedList<Usuario>();

		for (Usuario usuario : listaDeUsuarios) {
			if (usuario.getNome().equals(nome)) {
				retorno.add(usuario);
			}
		}

		return retorno;
	}

	/**
	 * Metodo que retorna todas as Caronas cadastradas no sistema.
	 * @return - Retorna uma List<Carona> com todas as caronas cadastradas no sistema.
	 */
	public List<Carona> getTodasAsCaronas() {
		List<Carona> retorno = new LinkedList<Carona>();

		for (Usuario usuario : listaDeUsuarios) {
			for (Carona caronaTemp : usuario.getCaronas()) {
				retorno.add(caronaTemp);
			}
		}
		return retorno;
	}

	
	/**
	 * Metodo que procura uma carona pelo seu ID
	 * @param idCarona - Recebe uma String ID
	 * @return - Retorna uma Carona caso exista uma carona com o ID passado
	 * @throws CaronaException - Lança exceção caso nao exista esse ID associado a uma carona
	 */
	public Carona getCaronaId(String idCarona)
			throws CaronaException {
		for (Usuario usuario : listaDeUsuarios) {
			for(Carona caronaTemp : usuario.getCaronas()){
				if(caronaTemp.getIdCarona().equals(idCarona)){
					return caronaTemp;
				}
			}
		}
		return null;
	}
	
	/**
	 * Metodo que localiza uma SugestaoDePontoDeEncontro atraves do ID
	 * @param idSugestao - Recebe uma String Id da sugestão
	 * @param idCarona - Recebe uma String Id da sugestão
	 * @return - Retorna uma sugestao localizada na carona que possui o IdCarona
	 * @throws CaronaException - Retorna uma exceção caso nao exista carona com o Id passado.
	 */
	public SugestaoDePontoDeEncontro getSugestaoId(String idSugestao, String idCarona) throws CaronaException{
		Carona caronaTemp = this.getCaronaId(idCarona);
		for (SugestaoDePontoDeEncontro sugestaoTemp : caronaTemp.getListaDeSugestoes()) {
			if(sugestaoTemp.getIdSugestao().equals(idSugestao)){
				return sugestaoTemp;
			}
		}
		
		return null;
	}

	
	/**
	 * Metodo que localiza uma carona que possua destino e origem específicos
	 * @param origem - Recebe uma String origem
	 * @param destino- Recebe uma String destino
	 * @return - Retorna uma List<Carona> que possua origem e destino especificado
	 */
	public List<Carona> localizaCaronaPorOrigemDestino(String origem,
			String destino) {

		List<Carona> retorno = new LinkedList<Carona>();

		for (Usuario UsuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : UsuarioTemp.getCaronas()) {
				if ((caronaTemp.getOrigem().equals(origem))
						&& (caronaTemp.getDestino().equals(destino))) {
					retorno.add(caronaTemp);
				}
			}
		}
		return retorno;
	}

	/**
	 * Metodo que localiza uma carona que possua destino específico
	 * @param destino- Recebe uma String destino
	 * @return - Retorna uma Carona que possua o destino especificado
	 */
	public List<Carona> localizaCaronaPorDestino(String destino) {
		List<Carona> retorno = new LinkedList<Carona>();

		for (Usuario UsuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : UsuarioTemp.getCaronas()) {
				if (caronaTemp.getDestino().equals(destino)) {
					retorno.add(caronaTemp);
				}
			}
		}
		return retorno;
	}

	/**
	 * Metodo que localiza uma carona que possua uma origem específica
	 * @param origem- Recebe uma String origem
	 * @return - Retorna uma Carona que possua a origem especificada
	 */
	public List<Carona> localizaCaronaPorOrigem(String origem) {
		List<Carona> retorno = new LinkedList<Carona>();

		for (Usuario UsuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : UsuarioTemp.getCaronas()) {
				if (caronaTemp.getOrigem().equals(origem)) {
					retorno.add(caronaTemp);
				}
			}
		}
		return retorno;
	}

	/**
	 * Metodo que retorna das as caronas do sistema
	 * @return - retorna uma List<Carona> com todas as caronas
	 */
	public List<Carona> getCaronas() {
		List<Carona> retorno = new LinkedList<Carona>();

		for (Usuario UsuarioTemp : listaDeUsuarios) {
			retorno.addAll(UsuarioTemp.getCaronas());
		}
		return retorno;
	}

	/**
	 * Metodo que localiza uma carona pelo seu ID
	 * @param idCarona - Recebe uma String idCarona
	 * @return - retrorna uma Carona caso exista alguma com o ID passado
	 */
	public Carona localizaCaronaPorId(String idCarona) {
		for (Usuario usuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : usuarioTemp.getCaronas()) {
				if (caronaTemp.getIdCarona().equals(idCarona)) {
					return caronaTemp;
				}
			}
		}
		return null;
	}
	
	/**
	 * Metodo que verifica se ja existe algum usuario cadastrado com esse login
	 * @param login - recebe uma String login
	 * @return - Retorna True caso exista algum Usuario com esse login ou False caso nao exista.
	 */
	public boolean checaExisteLogin(String login){
		for (Usuario UsuarioTemp : listaDeUsuarios) {
			if(UsuarioTemp.getLogin().equals(login)){
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * Metodo que verifica se ja existe algum usuario cadastrado com esse email
	 * @param email - Recebe uma String email
	 * @return - Retorna True caso exista algum Usuario com esse email ou False caso nao exista.
	 */
	public boolean checaExisteEmail(String email){
		for (Usuario UsuarioTemp : listaDeUsuarios) {
			if(UsuarioTemp.getEmail().equals(email)){
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * Metodo que localiza uma solicitacao de vaga por um ID
	 * @param idSolicitacao - Recebe uma String idSolicitacao
	 * @return - Retorna uma SolicitacaoDeVaga caso exista alguma solicitacao com o Id passado.
	 */
	public SolicitacaoDeVaga localizaSolicitacaoPorId(String idSolicitacao) {
		for (Usuario usuarioTemp : listaDeUsuarios) {
			for (SolicitacaoDeVaga solicitacaoTemp : usuarioTemp.getListaDeSolicitacaoDeVagas()) {
				if (solicitacaoTemp.getIdSolicitacao().equals(idSolicitacao)) {
					return solicitacaoTemp;
				}
			}
		}
		return null;
	}

	/**
	 * Metodo que inicia um novo sistema 
	 */
	public void zerarSistema() {
		arquivo.clear();
		criaRepositorio();
	}
	
	/**
	 * Metodo que salva todos os usuarios cadastrados num arquivo XML
	 */
	private void salvarXML(){
		arquivo.setLista(listaDeUsuarios);
		arquivo.finalizarXML();
	}

	/**
	 * Metodo que encerra o sistema e salva os dados
	 */
	public void encerrarSistema() {
		this.salvarXML();
	}
	
	/**
	 * Metodo que localiza uma CaronaMunicipal através da CIDADE
	 * @param cidade - Recebe uma String cidade
	 * @return - retorna uma List<Carona> com as carona municipais que possuem a Cidade passada
	 * @throws CaronaException - Lança exceção caso nao exista essa cidade.
	 */
	public List<Carona> localizarCaronaMunicipal(String cidade) throws CaronaException {
		List<Carona> retorno = new LinkedList<Carona>();
		boolean existeCidade = false;
		
		for (Usuario UsuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : UsuarioTemp.getCaronas()) {
				if (caronaTemp.getTipoCarona().equals(TiposCarona.MUNICIPAL)) {
					if(((CaronaMunicipal) caronaTemp).getCidade().equals(cidade)){
						existeCidade = true;
						retorno.add(caronaTemp);
					}
					
				}
			}
		}
		if(!existeCidade){
			throw new CaronaException("Cidade inexistente");
		}
		return retorno;
	}
	
	/**
	 * Metodo que lozaliza uma CaronaMunicipal atravas dos dados de origem, destino e cidade
	 * @param cidade - Recebe uma String cidade
	 * @param origem - Recebe uma String origem
	 * @param destino - Recebe uma String destino
	 * @return - Retorna uma List<Carona> que possua origem, destino e cidade iguais aos passados como parâmetro
	 * @throws CaronaException
	 */
	public List<Carona> localizarCaronaMunicipal(String cidade, String origem, String destino) throws CaronaException {
		List<Carona> retorno = new LinkedList<Carona>();
		boolean existeCidade = false;
		
		for (Usuario usuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : usuarioTemp.getCaronas()) {
				if (caronaTemp.getTipoCarona().equals(TiposCarona.MUNICIPAL)) {
					if(((CaronaMunicipal) caronaTemp).getCidade().equals(cidade)){
						existeCidade = true;
						if(caronaTemp.getOrigem().equals(origem) && caronaTemp.getDestino().equals(destino)){
							retorno.add(caronaTemp);
						}
						
					}
					
				}
			}
		}
		if(!existeCidade){
			throw new CaronaException("Cidade inexistente");
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
		List<Carona> retorno = new LinkedList<Carona>();
		
		for (Usuario usuarioTemp : listaDeUsuarios) {
			for (Carona caronaTemp : usuarioTemp.getCaronas()) {
				if (caronaTemp.getTipoCarona().equals(TiposCarona.RACHADACOMUM) || caronaTemp.getTipoCarona().equals(TiposCarona.RACHADAESCAPADA) || caronaTemp.getTipoCarona().equals(TiposCarona.RACHADAMUNICIPAL)) {
					if(caronaTemp.getOrigem().equals(origem) && caronaTemp.getDestino().equals(destino)){
							retorno.add(caronaTemp);
					}
				}
			}
		}
		return retorno;
	}
}
