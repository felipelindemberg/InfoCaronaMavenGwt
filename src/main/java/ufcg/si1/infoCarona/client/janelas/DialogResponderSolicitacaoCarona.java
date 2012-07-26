package ufcg.si1.infoCarona.client.janelas;

import java.util.ArrayList;
import java.util.List;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class DialogResponderSolicitacaoCarona extends DialogBox {
	
	private final CellTable<InfoUsuarioSolicitacao> tabelaUsuarios;
	private final List<InfoUsuarioSolicitacao> listaUsuarios;
	private final InfoCaronaServerAsync controller;
	private String idCarona;
	
	
	public DialogResponderSolicitacaoCarona(final InfoCaronaServerAsync controller,
			final String idSessao, final String idCarona) {
		
		this.controller = controller;
		this.idCarona = idCarona;
		
		setAnimationEnabled(true);
		setGlassEnabled(true);
		center();
		
		VerticalPanel panelResponderSolicitacaoCarona = new VerticalPanel();
		tabelaUsuarios = new CellTable<InfoUsuarioSolicitacao>();
		listaUsuarios = new ArrayList<InfoUsuarioSolicitacao>();
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(tabelaUsuarios);
	    pager.setPageSize(5);
		
	    TextColumn<InfoUsuarioSolicitacao> colunaIdSolicitacao = new TextColumn<InfoUsuarioSolicitacao>() {
			@Override
			public String getValue(InfoUsuarioSolicitacao usuario) {
				return usuario.getIdSolicitacao();
			}
		};
	    
	    TextColumn<InfoUsuarioSolicitacao> colunaLoginUsuario = new TextColumn<InfoUsuarioSolicitacao>() {
			@Override
			public String getValue(InfoUsuarioSolicitacao usuario) {
				return usuario.getLoginUsuario();
			}
		};
		
		ActionCell editCell = new ActionCell<InfoUsuarioSolicitacao>(
				"Confirmar",
				new ActionCell.Delegate<InfoUsuarioSolicitacao>() {
					public void execute(InfoUsuarioSolicitacao infoSolicitacao) {
						controller.confirmarSolicitacaoUsuario(idSessao, infoSolicitacao.getIdSolicitacao(), new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(String result) {
								populaTabela();
							}
						});
					}
				});

		Column<InfoUsuarioSolicitacao, ActionCell> colunaConfirmar = (new IdentityColumn(
				editCell));

		ActionCell editCell2 = new ActionCell<InfoUsuarioSolicitacao>(
				"Recusar", new ActionCell.Delegate<InfoUsuarioSolicitacao>() {
					public void execute(InfoUsuarioSolicitacao infoSolicitacao) {
						controller.recusarSolicitacaoUsuario(idSessao, infoSolicitacao.getIdSolicitacao(), new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
			
							}

							@Override
							public void onSuccess(String result) {
								populaTabela();
							}
						});
					}
				});

		Column<InfoUsuarioSolicitacao, ActionCell> colunaRecusar = (new IdentityColumn(
				editCell2));

		populaTabela();
		
		tabelaUsuarios.addColumn(colunaIdSolicitacao,"ID da Solicitação");
		tabelaUsuarios.addColumn(colunaLoginUsuario,"Login do Usuário");
		tabelaUsuarios.addColumn(colunaConfirmar,"");
		tabelaUsuarios.addColumn(colunaRecusar,"");
	    
		colunaIdSolicitacao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colunaLoginUsuario.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colunaConfirmar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colunaRecusar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		

		Button buttonFechar = new Button("Fechar");
		buttonFechar.setStyleName("botaoModificado");
		buttonFechar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();				
			}
		});
		
		panelResponderSolicitacaoCarona.add(tabelaUsuarios);
		panelResponderSolicitacaoCarona.add(pager);		
		panelResponderSolicitacaoCarona.add(buttonFechar);
		
		panelResponderSolicitacaoCarona.setCellHorizontalAlignment(tabelaUsuarios, HasHorizontalAlignment.ALIGN_CENTER);
		panelResponderSolicitacaoCarona.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		panelResponderSolicitacaoCarona.setCellHorizontalAlignment(buttonFechar, HasHorizontalAlignment.ALIGN_RIGHT);
		panelResponderSolicitacaoCarona.setSpacing(8);
		
		
		setWidget(panelResponderSolicitacaoCarona);
	}

	public void populaTabela() {
		listaUsuarios.clear();
		controller.getListaSolicitacoesPendentesCarona(idCarona, new AsyncCallback<List<List<String>>>() {
			@Override
			public void onSuccess(List<List<String>> result) {
				for (List<String> informacaoSolicitacao : result) {
					String idSolicitacao = informacaoSolicitacao.get(0);
					String loginUsuarioSolicitacao = informacaoSolicitacao.get(1);
					InfoUsuarioSolicitacao usuario = new InfoUsuarioSolicitacao(idSolicitacao, loginUsuarioSolicitacao);
					listaUsuarios.add(usuario);
				}
				tabelaUsuarios.setRowCount(listaUsuarios.size(), true);
				tabelaUsuarios.setRowData(0, listaUsuarios);
				final ListDataProvider<InfoUsuarioSolicitacao> dataProvider = new ListDataProvider<InfoUsuarioSolicitacao>();
				dataProvider.setList(listaUsuarios);  
				dataProvider.addDataDisplay(tabelaUsuarios);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}
}
