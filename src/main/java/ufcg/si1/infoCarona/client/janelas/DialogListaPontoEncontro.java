package ufcg.si1.infoCarona.client.janelas;

import java.util.ArrayList;
import java.util.List;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class DialogListaPontoEncontro extends DialogBox {
	
	public InfoCaronaServerAsync controller;
	public String idCarona;
	public static List<InfoPontoEncontro> listaPontoEncontros;
	public List<InfoPontoEncontro> listaNovosPontosEncontros;
	public CellTable<InfoPontoEncontro> tabelaPontoEncontro;
	public ListDataProvider<InfoPontoEncontro> dataProvider;
	public String novosPontos;
	
	public DialogListaPontoEncontro(final InfoCaronaServerAsync controller, final String idSessao, final String idCarona) {
		novosPontos = "";
		listaNovosPontosEncontros = new ArrayList<InfoPontoEncontro>();
		this.idCarona = idCarona;
		this.controller = controller;
		
		setText("Pontos de Encontro - ID: " + idCarona);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		center();
		
		VerticalPanel panelListaPontoEncontro = new VerticalPanel();
		tabelaPontoEncontro = new CellTable<InfoPontoEncontro>();
		tabelaPontoEncontro.setWidth("400px");
		dataProvider = new ListDataProvider<InfoPontoEncontro>();
		
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
	    
		
		tabelaPontoEncontro.addColumn(colunaPontosEncontro,"Pontos de Encontro");
		listaPontoEncontros = new ArrayList<InfoPontoEncontro>();
		populaTabela();
					
		HorizontalPanel hPanel01 = new HorizontalPanel();
		final TextBox boxNovoPontoEncontro = new TextBox();
		boxNovoPontoEncontro.setWidth("200px");
		boxNovoPontoEncontro.setStyleName("boxModificada");
		
		Button buttonAdicionarOutroPontoEncontro = new Button();
		buttonAdicionarOutroPontoEncontro.setStyleName("botaoModificado");
		buttonAdicionarOutroPontoEncontro.setTitle("Adicionar Sugestão");
		buttonAdicionarOutroPontoEncontro.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (boxNovoPontoEncontro.getText().trim().equals("")) {
					DialogMensagemUsuario dialogErro = new DialogMensagemUsuario(
							"Falhou", "Ponto de Encontro Inválido");
					dialogErro.show();
				} else {
					novosPontos += boxNovoPontoEncontro.getText().trim() + ";";
					InfoPontoEncontro novoPontoEncontro = new InfoPontoEncontro("00", boxNovoPontoEncontro.getText().trim());
					boxNovoPontoEncontro.setText("");
					
					listaNovosPontosEncontros.add(novoPontoEncontro);
					listaPontoEncontros = new ArrayList<InfoPontoEncontro>();
					
					for (InfoPontoEncontro novoPonto : listaNovosPontosEncontros) {
						listaPontoEncontros.add(novoPonto);
					}
					
					populaTabela();
				}
			}
		});
		
		Image imageFiltro = new Image("imagens/adicionar.png");
		imageFiltro.setSize("15px", "15px");
		buttonAdicionarOutroPontoEncontro.getElement().appendChild(imageFiltro.getElement());
		
		
		Button buttonSalvarNovosPontos = new Button("Salvar");
		buttonSalvarNovosPontos.setStyleName("botaoModificado");
		buttonSalvarNovosPontos.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				controller.cadastrarNovosPontosEncontro(idSessao, idCarona, novosPontos, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						DialogMensagemUsuario dialogSucess = new DialogMensagemUsuario(
								"Erro",
								caught.getMessage());
						dialogSucess.show();
						
					}

					@Override
					public void onSuccess(String result) {
						DialogMensagemUsuario dialogSucess = new DialogMensagemUsuario(
								"ID: " + result,
								"Pontos de Encontro Cadastrados com Sucesso!");
						dialogSucess.show();
						hide();
					}
				});
				
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
				
		hPanel01.setCellVerticalAlignment(boxNovoPontoEncontro, HasVerticalAlignment.ALIGN_BOTTOM);
		hPanel01.setCellVerticalAlignment(buttonSalvarNovosPontos, HasVerticalAlignment.ALIGN_TOP);
		hPanel01.setCellVerticalAlignment(buttonAdicionarOutroPontoEncontro, HasVerticalAlignment.ALIGN_TOP);
		hPanel01.setCellVerticalAlignment(buttonFechar, HasVerticalAlignment.ALIGN_TOP);
		hPanel01.setCellHorizontalAlignment(boxNovoPontoEncontro, HasHorizontalAlignment.ALIGN_CENTER);
		hPanel01.setCellHorizontalAlignment(buttonSalvarNovosPontos, HasHorizontalAlignment.ALIGN_CENTER);
		hPanel01.setCellHorizontalAlignment(buttonAdicionarOutroPontoEncontro, HasHorizontalAlignment.ALIGN_CENTER);
		hPanel01.setCellHorizontalAlignment(buttonFechar, HasHorizontalAlignment.ALIGN_CENTER);
		hPanel01.setWidth("100%");
		hPanel01.setSpacing(6);
		
		hPanel01.add(boxNovoPontoEncontro);
		hPanel01.add(buttonAdicionarOutroPontoEncontro);
		hPanel01.add(buttonSalvarNovosPontos);
		hPanel01.add(buttonFechar);
		
		panelListaPontoEncontro.add(tabelaPontoEncontro);
		panelListaPontoEncontro.add(pager);
		panelListaPontoEncontro.add(hPanel01);
		
		panelListaPontoEncontro.setCellVerticalAlignment(tabelaPontoEncontro, HasVerticalAlignment.ALIGN_MIDDLE);
		panelListaPontoEncontro.setCellVerticalAlignment(pager, HasVerticalAlignment.ALIGN_MIDDLE);
		
		panelListaPontoEncontro.setCellHorizontalAlignment(tabelaPontoEncontro, HasHorizontalAlignment.ALIGN_CENTER);
		panelListaPontoEncontro.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		panelListaPontoEncontro.setSpacing(8);
		
		setWidget(panelListaPontoEncontro);
	}

	private void populaTabela() {		
		controller.getListaPontoEncontroCarona(idCarona, new AsyncCallback<List<List<String>>>() {
			@Override
			public void onSuccess(List<List<String>> retorno) {
				if (retorno.size() != 0) {
					for (List<String> list : retorno) {
						String idPontoEncontro = list.get(0);
						for (int i = 1; i < list.size(); i++) {
							String pontoEncontro = list.get(i);
							InfoPontoEncontro pontoEncontroInfo = new InfoPontoEncontro(idPontoEncontro, pontoEncontro);
							listaPontoEncontros.add(pontoEncontroInfo);
						}
						
					}	
				}
				tabelaPontoEncontro.setRowCount(listaPontoEncontros.size(), true);
				tabelaPontoEncontro.setRowData(0, listaPontoEncontros);
				dataProvider.setList(listaPontoEncontros);  
				dataProvider.addDataDisplay(tabelaPontoEncontro);
				
			}
			@Override
			public void onFailure(Throwable caught) {
				//
			}
		});
		
	}
	
}
