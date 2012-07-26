package ufcg.si1.infoCarona.model.sistema;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ufcg.si1.infoCarona.controller.ControlerRepositorio;
import ufcg.si1.infoCarona.model.ArgumentoInexistenteException;
import ufcg.si1.infoCarona.model.Id;
import ufcg.si1.infoCarona.model.NumeroMaximoException;
import ufcg.si1.infoCarona.model.carona.Carona;
import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.carona.Escapada;
import ufcg.si1.infoCarona.model.carona.Expirada;
import ufcg.si1.infoCarona.model.carona.ObservaCaronas;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.usuario.Usuario;

public class SistemaCarona {
	
	private Id id;
	private ControlerRepositorio controler;
	private ObservaCaronas observer;
	
	public static SistemaCarona instance;
	
	protected SistemaCarona() {
		id = Id.getInstance(5);
		controler = new ControlerRepositorio();
		observer = new ObservaCaronas();
	}
	
	public static SistemaCarona getInstance(){
		if(instance == null){
			instance = new SistemaCarona();
		}
		return instance;
	}

	public List<Carona> localizarCarona(String origem, String destino)
			throws CaronaException {

		if ((origem == null)
				|| (origem.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Origem inválida");
		}
		if ((destino == null)
				|| (destino
						.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Destino inválido");
		}

		return controler.localizarCarona(origem, destino);
	}

	public String getAtributoCarona(String idCarona, String atributo)
			throws CaronaException, ArgumentoInexistenteException {

		return controler.getAtributoCarona(idCarona, atributo);
	}
	
	public String cadastrarCarona(String idSessao, String origem,
			String destino, Calendar calendario, int vagas)
			throws CaronaException, NumeroMaximoException, CaronaException, ArgumentoInexistenteException, ParseException {

		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		String idCarona = usuarioTemp.cadastrarCarona(origem, destino, calendario, vagas, id.gerarId());
		
		Carona caronaTemp = controler.localizaCaronaPorId(idCarona);
		
		SistemaRaiz.observer.cadastrouCarona(caronaTemp);
		
		return idCarona;
	}
	
	public String getTrajeto(String idCarona)
			throws
			CaronaException, ArgumentoInexistenteException {

		return controler.getTrajeto(idCarona);
	}
	
	public String getCaronaUsuario(String idSessao, int indexCarona) throws ArgumentoInexistenteException {
		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		return usuarioTemp.getCaronas().get(indexCarona - 1).getIdCarona();
	}
	
	public List<Carona> getTodasCaronasUsuario(String idSessao) throws ArgumentoInexistenteException {
		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		return usuarioTemp.getCaronas();
	}
	
	public String cadastrarCaronaMunicipal(String idSessao, String origem, String destino, String cidade, Calendar calendario, int vagas)
			throws CaronaException, NumeroMaximoException, ArgumentoInexistenteException {

			Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		String idCarona = usuarioTemp.cadastrarCaronaMunicipal(origem, destino, cidade, calendario, vagas, id.gerarId());
		
		return idCarona;
	}

	public List<Carona> localizarCaronaMunicipal(String cidade, String origem, String destino) throws CaronaException {
		if ((cidade == null)
				|| (cidade.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Cidade inexistente");
		}
		if ((origem == null)
				|| (origem.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Origem inválida");
		}
		if ((destino == null)
				|| (destino
						.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Destino inválido");
		}

		return controler.localizarCaronaMunicipal(cidade, origem, destino);
	}
	
	public void reviewCarona(String idSessao, String idCarona, String review) throws CaronaException, ArgumentoInexistenteException {
		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		Carona caronaTemp = controler.localizaCaronaPorId(idCarona);
		if( !((review.equals("segura e tranquila")) || (review.equals("não funcionou")))){
			throw new IllegalArgumentException("Opção inválida.");
		}
		if(SistemaRaiz.usuarioJahEstahNaCarona(usuarioTemp, caronaTemp)){
			caronaTemp.addReviewCarona(usuarioTemp, review);
			observer.reviewCarona(review, usuarioTemp, caronaTemp);
		}else{
			throw new CaronaException("Usuário não possui vaga na carona.");
		}	
	}
	
	public String cadastrarEscapada(String idSessao, String origem,
			String destino, Calendar dataIda, Calendar dataVolta,
			int minimoCaroneiros, int vagas) throws ArgumentoInexistenteException, CaronaException, NumeroMaximoException {
		
		Usuario usuarioTemp = SistemaRaiz.procuraUsuarioLogado(idSessao);
		String idCarona = usuarioTemp.cadastrarEscapada(origem, destino, dataIda, dataVolta, vagas, id.gerarId(), minimoCaroneiros);
		
		Carona caronaTemp = controler.localizaCaronaPorId(idCarona);
		
		SistemaRaiz.observer.cadastrouCarona(caronaTemp);
		
		return idCarona;
	}

	public String getAtributoCaronaRelampago(String idCarona, String atributo) throws CaronaException, ArgumentoInexistenteException {
		return controler.getAtributoCaronaRelampago(idCarona, atributo);
	}

	public String setCaronaRelampagoExpired(String idCarona) throws CaronaException, NumeroMaximoException {
		Carona carona = controler.localizaCaronaPorId(idCarona);
		((Escapada) carona).setExpirada();
		String idExpirada = id.gerarId();
		Expirada expirada = new Expirada(carona, idExpirada);
		observer.addExpirada(expirada);
		return idExpirada;
	}

	public List<Usuario> getAtributoExpired(String idExpired, String atributo) {
		List<Usuario> retorno = null;
		
		if(atributo.equals("emailTo")){
			Expirada expirada = observer.procuraExpirada(idExpired);
			retorno = observer.emailTo(expirada);
		}
		return retorno;
	}

	public void definirCaronaPreferencial(String idCarona) throws CaronaException {
		Carona carona = controler.localizaCaronaPorId(idCarona);
		carona.setCaronaPreferencial();
	}

	public boolean isCaronaPreferencial(String idCarona) throws CaronaException {
		Carona carona = controler.localizaCaronaPorId(idCarona);
		return carona.isPreferencial();
	}

	public List<Usuario> getUsuariosPreferenciaisCarona(String idCarona) throws CaronaException {
		Carona carona = controler.localizaCaronaPorId(idCarona);
		return carona.getDonoDaCarona().getUsuariosPreferenciais();
	}

	public String cadastrarCaronaRachadaComum(String idSessao, String origem,
			String destino, Calendar calendario, int vagas, double gasolina) throws NumeroMaximoException, ArgumentoInexistenteException, CaronaException {
		Usuario usuario = SistemaRaiz.procuraUsuarioLogado(idSessao);
		return usuario.cadastrarCaronaRachadaComum(origem, destino, calendario, vagas, gasolina, id.gerarId());
	}

	public String cadastrarEscapadaRachada(String idSessao, String origem,
			String destino, Calendar calendarioIda, Calendar calendarioVolta,
			int minimoCaroneiros, double valorDaGasolina, int vagas) throws ArgumentoInexistenteException, CaronaException, NumeroMaximoException {
		Usuario usuario = SistemaRaiz.procuraUsuarioLogado(idSessao);
		return usuario.cadastrarEscapadaRachada(origem, destino, calendarioIda, calendarioVolta, minimoCaroneiros, valorDaGasolina, vagas, id.gerarId());
	}

	public String cadastrarCaronaMunicipalRachada(String idSessao,
			String origem, String destino, String cidade, Calendar calendario, int vagas, double gasolina) throws ArgumentoInexistenteException, NumeroMaximoException, CaronaException {
		Usuario usuario = SistemaRaiz.procuraUsuarioLogado(idSessao);
		return usuario.cadastrarCaronaMunicipalRachada(idSessao, origem, destino, cidade, calendario, vagas, gasolina, id.gerarId());
	}

	public List<Carona> localizarCaronaRachada(String origem, String destino) throws CaronaException {
		if ((origem == null)
				|| (origem.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Origem inválida");
		}
		if ((destino == null)
				|| (destino
						.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%�&*0-9].*"))) {
			throw new CaronaException("Destino inválido");
		}

		return controler.localizarCaronaRachada(origem, destino);
	}
	
	public List<Carona> getTodasCaronas() {
		return controler.getTodasCaronas();
	}
	
	public List<List<String>> getSugestoes(String idCarona) throws CaronaException{
		List<List<String>> retorno = new ArrayList<List<String>>();
		Carona carona = controler.localizaCaronaPorId(idCarona);
		for (SugestaoDePontoDeEncontro sugestao : carona.getListaDeSugestoes()) {
			List<String> aux = new ArrayList<String>();
			aux.add(sugestao.getIdSugestao());
			for (String sugest : sugestao.getListaDeSugestaoDePontosDeEncontro()) {
				aux.add(sugest);
			}
			retorno.add(aux);
		}
		return retorno;
	}

	public List<String> getSugestoesMotorista(String idCarona) throws CaronaException {
		return controler.localizaCaronaPorId(idCarona).getListaPontosDeEncontroPermitidos();
		
	}
	
	public Carona localizaCaronaPorId(String idCarona) throws CaronaException {
		return controler.localizaCaronaPorId(idCarona);
	}
}
