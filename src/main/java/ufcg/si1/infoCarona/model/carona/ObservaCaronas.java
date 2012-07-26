package ufcg.si1.infoCarona.model.carona;

import java.util.LinkedList;
import java.util.List;

import ufcg.si1.infoCarona.model.negociacao.EstadoSolicitacao;
import ufcg.si1.infoCarona.model.negociacao.SolicitacaoDeVaga;
import ufcg.si1.infoCarona.model.usuario.Usuario;
import ufcg.si1.infoCarona.util.EnviarEmail;

public class ObservaCaronas {
	
	private List<Expirada> expiradas;
	
	public ObservaCaronas(){
		expiradas = new LinkedList<Expirada>();
	}
	
	public void addExpirada(Expirada e){
		expiradas.add(e);
		enviarEmail(e);
	}

	private void enviarEmail(Expirada e) {
		for (Usuario usuario : emailTo(e)) {
			EnviarEmail.sendMail(usuario.getNome(), usuario.getEmail(), "Carona Expirada", "A carona" + e.getCarona().toString() + "foi expirada!");
		}
	}

	public List<Expirada> getExpiradas() {
		return this.expiradas;
	}
	
	public void reviewCarona(String review, Usuario donoDaReview, Carona carona){
		if (review.equals("segura e tranquila")){
			carona.getDonoDaCarona().addUsuarioPreferencial(donoDaReview);
		}	
	}
	
	public List<Usuario> emailTo(Expirada expirada){
		List<Usuario> retorno = new LinkedList<Usuario>();
		
		for (SolicitacaoDeVaga solicitacao : expirada.getCarona().getListaDeSolicitacao()) {
			if(solicitacao.getEstado().equals(EstadoSolicitacao.ACEITA)){
				retorno.add(solicitacao.getDonoSolicitacao());
			}
		}
		
		return retorno;
	}
	
	public Expirada procuraExpirada(String idExpirada){
		Expirada retorno = null;
		
		for (Expirada expirada : expiradas) {
			if (idExpirada.equals(expirada.getId())){
				retorno = expirada;
			}
		}
		
		return retorno;
	}
}
