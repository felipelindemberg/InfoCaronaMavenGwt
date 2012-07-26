package ufcg.si1.infoCarona.model.carona;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ufcg.si1.infoCarona.model.negociacao.EstadoSolicitacao;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.usuario.Usuario;
import ufcg.si1.infoCarona.util.UtilInfo;

public abstract class CaronaAbstract implements Carona{
	private String origem, destino, idCarona;
	private Calendar calendar;
	private int vagas;
	private List<SugestaoDePontoDeEncontro> listaDeSugestoes;
	private List<SolicitacaoDeVaga> listaDeSolicitacao;
	private List<String> listaPontosDeEncontroPermitidos;
	private Map<Usuario, String> reviewsCarona;
	private Usuario donoDaCarona;
	private boolean preferencial;
	/**
	 * Construtor da classe Carona
	 * @param origem - recebe uma String informando a origem da corana
	 * @param destino - recebe uma String informando o destino da carona
	 * @param data - recebe uma String no mormato xx/xx/xxxx e verifica se essa data é válida e nao aconteceu ainda.
	 * @param hora - recebe uma String no formato xx:xx informando a hora que a carona vai acontecer, e verifica se é um hora válida
	 * @param vagas - recebe um int informando o numero de vagas disponivel na carona
	 * @param idCarona - recebe uma String identifcador da carona
	 * @param donoDaCarona - recebe uma String com o login do dono da carona.
	 * @throws SessaoInvalidaException
	 * @throws OrigemInvalidaException - retorna uma exceção caso a origem passada seja null ou vazia
	 * @throws DestinoInvalidoException - retorna uma exceção caso o destino passado seja null ou vazio
	 * @throws DataInvalidaException - retorna uma exceção caso a data passada seja null, vazia ou data "passada"
	 * @throws HoraInvalidaException- retorna uma exceção caso a hora passada seja null, vazia ou data em formato inválida
	 * @throws VagaInvalidaException - retorna uma exceção caso o numero de vagsa seja negativo ou nao seja um numéro válido.
	 */
	public CaronaAbstract(String origem, String destino, Calendar calendar, int vagas, String idCarona, Usuario donoDaCarona) throws CaronaException {
        setOrigem(origem);
        setDestino(destino);
        setCalendario(calendar);
        setVagas(vagas);
        
        this.donoDaCarona = donoDaCarona;
        this.idCarona = idCarona;
        this.listaDeSugestoes = new LinkedList<SugestaoDePontoDeEncontro>();
        this.listaDeSolicitacao = new LinkedList<SolicitacaoDeVaga>();
        this.listaPontosDeEncontroPermitidos = new LinkedList<String>();
        this.reviewsCarona = new HashMap<Usuario, String>();
        this.preferencial = false;
	}
	
	/**
	 * Metodo que retorna os pontos de encontro permitidos pelo dono da carona para um usuário solicitar vaga e sugerir
	 * @return - retorna uma List<String> com todos os pontos válidos
	 */
	public List<String> getListaPontosDeEncontroPermitidos() {
		return listaPontosDeEncontroPermitidos;
	}
	/**
	 * Metodo que retorna todas as solicitações de vaga que foram feitas para a carona
	 * @return - retorna uma List<SolicitacaoDeVaga> com todas as solicitações realizadas
	 */
	public List<SolicitacaoDeVaga> getListaDeSolicitacao() {
		return listaDeSolicitacao;
	}
	/**
	 * Metodo que retorna todos os objetos de sugestão de pontos de encontro feitos para a carona
	 * @return - retorna uma lista List<SugestaoDePontoDeEncontro> com todos os objetos de sugestao de pontos de encontro
	 */
	public List<SugestaoDePontoDeEncontro> getListaDeSugestoes(){
		return this.listaDeSugestoes;
	}

	/**
	 * @return Origem
	 */
	public String getOrigem() {
		return this.origem;
	}
	
	/**
	 * @return Usuario, dono da carona
	 */
	public Usuario getDonoDaCarona(){
		return this.donoDaCarona;
	}
	
	public abstract TiposCarona getTipoCarona();
	
	/**
	 * modifica origem da carona
	 */
	public void setOrigem(String origem) throws CaronaException {
        if((origem == null) || (origem.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%¨&*0-9].*")) ||(origem.trim().equals(""))){
        	throw new CaronaException("Origem inválida");
        }
        this.origem = origem.trim();
	}

	/**
	 * @return destino
	 */
	public String getDestino() {
		return this.destino;
	}

	/**
	 * @param destino
	 */
	public void setDestino(String destino) throws CaronaException {
        if ((destino == null) || (destino.matches("[\\-/.\\[_\\]()!\"+,:;<=>{|}#@$%¨&*0-9].*")) ||(destino.trim().equals(""))) {
                throw new CaronaException("Destino inválido");
        }
        this.destino = destino.trim();
	}

	/**
	 * @param calendar
	 */
	public void setCalendario(Calendar calendar){
		this.calendar = calendar;
	}
	
	public Calendar getCalendario(){
		return this.calendar;
	}
	
	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		if(vagas < 0){
			throw new IllegalArgumentException("Vaga inválida");
		}
		this.vagas = vagas;
	}
	
	
	public String getIdCarona(){
		return this.idCarona;
	}

	/**
	 * Metodo que retorna o to String das caronas
	 * @return - retorna uma String no formato "ORIGEM para DESTINO no dia XX/XX/XXXX as XX:XX
	 */
	@Override
	public String toString(){
		return (this.origem + " para " + this.destino + ", no dia " + UtilInfo.converteCalendarEmStringData(calendar) + ", as " + UtilInfo.converteCalendarEmStringHora(calendar));
	}
	
	public String getDadosCarona(){
		return ("origem=" + origem + " destino=" + destino + " data=" + UtilInfo.converteCalendarEmStringData(calendar) + " hora=" + UtilInfo.converteCalendarEmStringHora(calendar) + " vagas=" + vagas);
	}
	public void addNovaSolicitacao(SolicitacaoDeVaga novaSolicitacao){
		listaDeSolicitacao.add(novaSolicitacao);
	}
	
	/**
	 * Checa se a data esta correta
	 * @param stringData
	 * @return boolean
	 */
    private boolean checaData(String stringData) {
            boolean retorno = true;
            Calendar data = Calendar.getInstance();
            try {
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    formato.setLenient(false);
                    data.setTime(formato.parse(stringData));
            } catch (ParseException e) {
                    retorno = false;
            }
            Calendar dataAtual = Calendar.getInstance();
            if (dataAtual.getTime().compareTo(data.getTime()) == 1) { // -1 data valida, 1 data invalida
                    retorno = false;
            }
            return retorno;
    }
    
    
    /**
     * Chega se a hora é válida
     * @param hora
     * @return boolean
     */
    private boolean checaHoraInvalida(String hora) {
        boolean retorno = true;
        String[] listaHoraMinuto = hora.split(":");
        try {
                int horas = Integer.parseInt(listaHoraMinuto[0]);
                int minutos = Integer.parseInt(listaHoraMinuto[1]);
                if ((horas >= 24) || (minutos >= 60)) {
                        retorno = false;
                }
        } catch (Exception e) {
                retorno = false;
        }
        return retorno;
    }
    
    /**
     * Remove uma solicitação de vaga da lista
     */
    public void removeSolicitacao(SolicitacaoDeVaga solicitacao){
    	if(solicitacao.getEstado().equals(EstadoSolicitacao.ACEITA)){
    		vagas++;
    	}
    	listaDeSolicitacao.remove(solicitacao);
    }
	
    @Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Carona)) {
			return false;
		}
		
		if (!(((Carona) obj).getIdCarona().equals(this.idCarona))){
			return false;
		}
		
		return true;
	}
    
    public List<SolicitacaoDeVaga> getSolicitacoesConfirmadas() {
		List<SolicitacaoDeVaga> retorno = new LinkedList<SolicitacaoDeVaga>();
		for (SolicitacaoDeVaga solicitacao : listaDeSolicitacao) {
			if(solicitacao.getEstado().equals(EstadoSolicitacao.ACEITA)){
				retorno.add(solicitacao);
			}
		}
		return retorno;
	}
    
    public List<SolicitacaoDeVaga> getSolicitacoesPendentes(){
    	List<SolicitacaoDeVaga> retorno = new LinkedList<SolicitacaoDeVaga>();
		for (SolicitacaoDeVaga solicitacao : listaDeSolicitacao) {
			if(solicitacao.getEstado().equals(EstadoSolicitacao.PENDENTE)){
				retorno.add(solicitacao);
			}
		}
		return retorno;
    }
    
    public void addPontoEncontroPermitido(String ponto){
    	listaPontosDeEncontroPermitidos.add(ponto);
    }
    
    public void addReviewCarona(Usuario usuario, String review){
 	   if(review.equals("segura e tranquila")){
 		   this.donoDaCarona.setCaronasSeguras();
 	   }else if(review.equals("não funcionou")){
 		   this.donoDaCarona.setCaronasNaoFuncionaram();
 	   }
 	   
 	   reviewsCarona.put(usuario, review);
    }
    
    public void setCaronaPreferencial(){
    	this.preferencial = true;
    }
    
    public boolean isPreferencial(){
    	return this.preferencial;
    }
    
    public abstract Calendar getVolta() throws CaronaException;
	
	public abstract double getGasolina() throws CaronaException;
	
	public abstract int getMinimoCaroneiros() throws CaronaException;
	
	public String toStringView() {
    	return (this.idCarona +  "!" + this.donoDaCarona.getNome() + "!" +this.origem + "!" + this.destino + "!" + UtilInfo.converteCalendarEmStringData(calendar) + "!" + UtilInfo.converteCalendarEmStringHora(calendar) + "!" + this.vagas);
    }
}

