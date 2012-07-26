package ufcg.si1.infoCarona.model;

import java.text.ParseException;

import ufcg.si1.infoCarona.model.carona.CaronaException;
import ufcg.si1.infoCarona.model.negociacao.SugestaoDePontoDeEncontro;
import ufcg.si1.infoCarona.model.sistema.SistemaCarona;
import ufcg.si1.infoCarona.model.sistema.SistemaNegociacao;
import ufcg.si1.infoCarona.model.sistema.SistemaRaiz;
import ufcg.si1.infoCarona.model.sistema.SistemaUsuarioNaoLogado;
import ufcg.si1.infoCarona.util.UtilInfo;

public class main {

	/**
	 * @param args
	 * @throws LoggerException 
	 * @throws ArgumentoInexistenteException 
	 * @throws NumeroMaximoException 
	 * @throws ParseException 
	 * @throws CaronaException 
	 */
	public static void main(String[] args) throws LoggerException, NumeroMaximoException, ArgumentoInexistenteException, CaronaException, ParseException {
		SistemaRaiz sis = SistemaRaiz.getInstance();
		SistemaUsuarioNaoLogado sisNao = SistemaUsuarioNaoLogado.getInstace();
		SistemaCarona siscar = SistemaCarona.getInstance();
		SistemaNegociacao sisNeg = SistemaNegociacao.getInstance();
		
		sis.zerarSistema();
		
		sisNao.criarUsuario("italo", "italo", "italo", "avenida c", "italo@gmail");  
		sisNao.criarUsuario("gilles", "gilles", "gilles", "rua a", "gilles@gmail");  
		sisNao.criarUsuario("felipe", "felipe", "felipe", "rua b", "felipe@gmail"); 
		
		sisNao.abrirSessao("italo", "italo"); //00000
		siscar.cadastrarCarona("00000", "Campina grande", "Joao Pessoa", UtilInfo.converteStringEmCalendar("23/12/2013", "12:30"), 4);	//00001
		siscar.cadastrarCarona("00000", "Taquaritinga", "Tangamandapio", UtilInfo.converteStringEmCalendar("12/06/2013", "13:40"), 3); //00002
		sisNao.abrirSessao("gilles", "gilles"); //00003
		siscar.cadastrarCarona("00003", "Caruaru", "Campina Grande", UtilInfo.converteStringEmCalendar("23/11/2013", "15:40"), 5); //00004
		siscar.cadastrarCarona("00003", "Tangamandapio", "Terra do Nunca", UtilInfo.converteStringEmCalendar("02/02/2013", "15:30"), 6); //00005
		sisNao.abrirSessao("felipe", "felipe"); //00006
		siscar.cadastrarCarona("00006", "El Dourado", "Atlantida", UtilInfo.converteStringEmCalendar("13/10/2013", "15:40"), 3); // 00007
		siscar.cadastrarCarona("00006", "Girimun", "Pedra preta", UtilInfo.converteStringEmCalendar("12/05/2013", "14:40"), 4); //00008
		
		sisNeg.sugerirPontoEncontro("00000", "00004", "Centro;Shopping"); //00009
		sisNeg.sugerirPontoEncontro("00003", "00001", "UFCG;Centro;Liberdade"); //00010
		sisNeg.sugerirPontoEncontro("00006", "00005", "abcd;efgh;ijlm"); //00011
		
		sisNeg.responderSugestaoPontoEncontro("00003", "00004", "00009", "Centro");
		sisNeg.responderSugestaoPontoEncontro("00000", "00001", "00010", "UFCG;Centro");
		sisNeg.responderSugestaoPontoEncontro("00003", "00005", "00011", "abcd;ijlm");
	
		sisNeg.solicitarVagaPontoEncontro("00006", "00001", "UFCG"); //00012
		sisNeg.solicitarVagaPontoEncontro("00000", "00004", "Centro"); //00013
		sisNeg.solicitarVagaPontoEncontro("00003", "00001", "Centro"); //00014
		sisNeg.solicitarVagaPontoEncontro("00000", "00005", "abcd"); //00015
		sisNeg.solicitarVagaPontoEncontro("00006", "00005", "ijlm"); //00016
		
		sisNeg.aceitarSolicitacaoPontoEncontro("00000", "00012");
		sisNeg.aceitarSolicitacaoPontoEncontro("00003", "00013");
		
		sis.encerrarSistema();

	}

}
