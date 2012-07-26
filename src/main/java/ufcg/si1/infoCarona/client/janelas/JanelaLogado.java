package ufcg.si1.infoCarona.client.janelas;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class JanelaLogado extends Composite {

	private PanelCadastrarCarona panelCadastrarCarona;
	private PanelBuscarCarona panelBuscarCarona;
	private PanelMinhasCaronas panelMinhasCaronas;
	
	private PanelSolicitacoesAceitas panelSolicitacoesAceitas;
	private PanelSolicitacoesPendentes panelSolicitacoesPendentes;
	private PanelSolicitacoesRejeitadas panelSolicitacoesRejeitadas;
	
	private PanelInteresses panelInteresses;
	private PanelAlterarCadastro panelAlterarCadastro;
	
	private InfoCaronaServerAsync controller;
	
	public JanelaLogado(InfoCaronaServerAsync controller, String idSessao) {
		this.controller = controller;

		DockPanel panelLogado = new DockPanel();
		panelLogado.setWidth("100%");
		
		PanelUsuarioLogado panelUsuarioLogado = new PanelUsuarioLogado(controller, idSessao);
		panelLogado.add(panelUsuarioLogado, DockPanel.NORTH);
		
		PanelRodape panelRodape = new PanelRodape();
		panelLogado.add(panelRodape, DockPanel.SOUTH);
		
		//panels para carregarno centro
		HorizontalPanel panelCentral = new HorizontalPanel();
		panelLogado.add(panelCentral, DockPanel.CENTER);

		
		panelCadastrarCarona = new PanelCadastrarCarona(controller, idSessao);		
		panelCadastrarCarona.setVisible(false);
		panelCentral.add(panelCadastrarCarona);
		
		panelBuscarCarona = new PanelBuscarCarona(controller, idSessao);
		panelBuscarCarona.setVisible(false);
		panelCentral.add(panelBuscarCarona);
		
		panelMinhasCaronas = new PanelMinhasCaronas(controller, idSessao);
		panelMinhasCaronas.setVisible(false);
		panelCentral.add(panelMinhasCaronas);
		
		panelSolicitacoesAceitas = new PanelSolicitacoesAceitas(controller, idSessao);
		panelSolicitacoesAceitas.setVisible(false);
		panelCentral.add(panelSolicitacoesAceitas);
		
		panelSolicitacoesPendentes = new PanelSolicitacoesPendentes(controller, idSessao);
		panelSolicitacoesPendentes.setVisible(false);
		panelCentral.add(panelSolicitacoesPendentes);
		
		panelSolicitacoesRejeitadas = new PanelSolicitacoesRejeitadas(controller, idSessao);
		panelSolicitacoesRejeitadas.setVisible(false);
		panelCentral.add(panelSolicitacoesRejeitadas);
		
		panelInteresses = new PanelInteresses(controller, idSessao);
		panelInteresses.setVisible(false);
		panelCentral.add(panelInteresses);
		
		panelAlterarCadastro = new PanelAlterarCadastro(controller, idSessao);
		panelAlterarCadastro.setVisible(false);
		panelCentral.add(panelAlterarCadastro);
		///////////////////////////////
		
		PanelMenuUsuarioLogado panelMenuUsuarioLogado = new PanelMenuUsuarioLogado(this);
		panelLogado.add(panelMenuUsuarioLogado, DockPanel.WEST);	
		
		panelLogado.setCellWidth(panelLogado.getWidget(2), "80%");
		panelLogado.setCellHorizontalAlignment(panelLogado.getWidget(2), HasHorizontalAlignment.ALIGN_CENTER);
		panelLogado.setCellVerticalAlignment(panelLogado.getWidget(2), HasVerticalAlignment.ALIGN_MIDDLE);

		initWidget(panelLogado);
		
	}
	
	public void ocultarPanels() {
		panelCadastrarCarona.setVisible(false);
		panelBuscarCarona.setVisible(false);
		panelMinhasCaronas.setVisible(false);
		panelSolicitacoesAceitas.setVisible(false);
		panelSolicitacoesPendentes.setVisible(false);
		panelSolicitacoesRejeitadas.setVisible(false);
		panelInteresses.setVisible(false);
		panelAlterarCadastro.setVisible(false);
	}
	
	public void selecionarMenu(int menu) {
		ocultarPanels();
		switch (menu) {
			case 1:
				panelCadastrarCarona.setVisible(true);
				break;
			case 2:
				panelBuscarCarona.setVisible(true);
				popularTabela();
				break;
			case 3:
				panelMinhasCaronas.setVisible(true);
				popularMinhasCaronas();
				break;
			case 5:
				panelSolicitacoesAceitas.setVisible(true);
				popularSolicitacoesAceitas();
				break;
			case 6:
				panelSolicitacoesPendentes.setVisible(true);
				popularSolicitacoesPendentes();
				break;
			case 7:
				panelSolicitacoesRejeitadas.setVisible(true);
				popularSolicitacoesRejeitadas();
				break;
			case 9:
				panelInteresses.setVisible(true);
				break;
			case 10:
				panelAlterarCadastro.setVisible(true);
				setInformacoes();
				break;
			default:
				break;
		}
	}

	public void popularMinhasCaronas() {
		panelMinhasCaronas.popularTabela();
	}
	public void popularTabela() {
		panelBuscarCarona.popularTabela();		
	}

	public void setInformacoes() {
		panelAlterarCadastro.setInformacoes();
	}

	public void popularSolicitacoesAceitas() {
		panelSolicitacoesAceitas.popularTabela();
	}

	public void popularSolicitacoesPendentes() {
		panelSolicitacoesPendentes.popularTabela();
	}

	public void popularSolicitacoesRejeitadas() {
		panelSolicitacoesRejeitadas.popularTabela();
	}
}
