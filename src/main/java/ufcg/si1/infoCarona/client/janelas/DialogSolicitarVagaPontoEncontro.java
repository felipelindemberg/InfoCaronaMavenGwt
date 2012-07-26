package ufcg.si1.infoCarona.client.janelas;

import java.util.ArrayList;
import java.util.List;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class DialogSolicitarVagaPontoEncontro extends DialogBox {
	
	public InfoCaronaServerAsync controller;
	public String idCarona;
	public static List<InfoPontoEncontro> listaPontoEncontros;
	public List<InfoPontoEncontro> listaPontosEncontros;
	public CellTable<InfoPontoEncontro> tabelaPontoEncontro;
	public ListDataProvider<InfoPontoEncontro> dataProvider;
	public String nomePontoEncontro;
	
	public DialogSolicitarVagaPontoEncontro(final InfoCaronaServerAsync controller, final String idSessao, final String idCarona) {

		listaPontosEncontros = new ArrayList<InfoPontoEncontro>();
		this.idCarona = idCarona;
		this.controller = controller;
		nomePontoEncontro = "";
		
		setText("Pontos de Encontro - ID: " + idCarona);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		center();
		
		VerticalPanel panelListaPontoEncontro = new VerticalPanel();
		tabelaPontoEncontro = new CellTable<InfoPontoEncontro>();
		tabelaPontoEncontro.setWidth("400px");
		dataProvider = new ListDataProvider<InfoPontoEncontro>();
		
		final SingleSelectionModel<InfoPontoEncontro> selectionModel = new SingleSelectionModel<InfoPontoEncontro>();

		tabelaPontoEncontro.setSelectionModel(selectionModel,
				DefaultSelectionEventManager
						.<InfoPontoEncontro> createCheckboxManager());

		// coluna de marcacao
		Column<InfoPontoEncontro, Boolean> colunaCheck = new Column<InfoPontoEncontro, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(InfoPontoEncontro object) {
				return selectionModel.isSelected(object);
			}
		};

		selectionModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				nomePontoEncontro = selectionModel.getSelectedObject().getPontoEncontro();
			}
		});

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(tabelaPontoEncontro);
		pager.setPageSize(5);
		
	    TextColumn<InfoPontoEncontro> colunaIdPontoEncontro = new TextColumn<InfoPontoEncontro>() {
			@Override
			public String getValue(InfoPontoEncontro pontoEncontro) {
				return pontoEncontro.getIdPontoEncontro();
			}
		};
	    
	    TextColumn<InfoPontoEncontro> colunaPontosEncontro = new TextColumn<InfoPontoEncontro>() {
			@Override
			public String getValue(InfoPontoEncontro pontoEncontro) {
				return pontoEncontro.getPontoEncontro();
			}
		};
	    
		tabelaPontoEncontro.addColumn(colunaCheck);
		tabelaPontoEncontro.addColumn(colunaPontosEncontro,"Pontos de Encontro");
		listaPontoEncontros = new ArrayList<InfoPontoEncontro>();
		
		populaTabela();
					
		HorizontalPanel hPanel01 = new HorizontalPanel();
		
		Button buttonSolicitarVaga = new Button("Solicitar Vaga");
		buttonSolicitarVaga.setStyleName("botaoModificado");
		buttonSolicitarVaga.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!selectionModel.getSelectedObject().equals(null)) {
					controller.solicitarVagaPontoEncontro(idSessao, idCarona, nomePontoEncontro, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable caught) {
							DialogMensagemUsuario dialogErro = new DialogMensagemUsuario(
									"Erro",
									caught.getMessage());
							dialogErro.show();
							
						}

						@Override
						public void onSuccess(String result) {
							DialogMensagemUsuario dialogSucess = new DialogMensagemUsuario(
									"ID: " + result,
									"Solicitação Realizada com Sucesso!");
							dialogSucess.show();
							hide();							
						}
					});
				} else {
					DialogMensagemUsuario dialogInfo = new DialogMensagemUsuario("Alerta","Selecione um Ponto de Encontro Primeiro!");
					dialogInfo.show();
				}
				
			}
		});
		
		Button buttonFechar = new Button("Fechar");
		buttonFechar.setStyleName("botaoModificado");
		
		buttonFechar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		colunaPontosEncontro.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		hPanel01.add(buttonSolicitarVaga);
		hPanel01.add(buttonFechar);
		
		hPanel01.setCellVerticalAlignment(buttonSolicitarVaga, HasVerticalAlignment.ALIGN_TOP);
		hPanel01.setCellVerticalAlignment(buttonFechar, HasVerticalAlignment.ALIGN_TOP);

		hPanel01.setCellHorizontalAlignment(buttonSolicitarVaga, HasHorizontalAlignment.ALIGN_RIGHT);
		hPanel01.setCellHorizontalAlignment(buttonFechar, HasHorizontalAlignment.ALIGN_RIGHT);
		hPanel01.setSpacing(6);
		
		panelListaPontoEncontro.add(tabelaPontoEncontro);
		panelListaPontoEncontro.add(pager);
		panelListaPontoEncontro.add(hPanel01);
		
		panelListaPontoEncontro.setCellVerticalAlignment(tabelaPontoEncontro, HasVerticalAlignment.ALIGN_MIDDLE);
		panelListaPontoEncontro.setCellVerticalAlignment(pager, HasVerticalAlignment.ALIGN_MIDDLE);
		
		panelListaPontoEncontro.setCellHorizontalAlignment(hPanel01, HasHorizontalAlignment.ALIGN_RIGHT);
		panelListaPontoEncontro.setCellHorizontalAlignment(tabelaPontoEncontro, HasHorizontalAlignment.ALIGN_CENTER);
		panelListaPontoEncontro.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		panelListaPontoEncontro.setSpacing(8);
		
		setWidget(panelListaPontoEncontro);
	}

	private void populaTabela() {	
		controller.getListaMotoristaPontoEncontroCarona(idCarona, new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> retorno) {
				if (retorno.size() != 0) {
					for (String pontoEncontro : retorno) {
						InfoPontoEncontro pontoEncontroInfo = new InfoPontoEncontro("000", pontoEncontro);
						listaPontoEncontros.add(pontoEncontroInfo);
					}	
				}
				tabelaPontoEncontro.setRowCount(listaPontoEncontros.size(), true);
				tabelaPontoEncontro.setRowData(0, listaPontoEncontros);
				dataProvider.setList(listaPontoEncontros);  
				dataProvider.addDataDisplay(tabelaPontoEncontro);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
