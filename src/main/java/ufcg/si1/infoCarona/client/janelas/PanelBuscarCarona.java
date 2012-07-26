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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class PanelBuscarCarona extends Composite {

	private static List<InfoCarona> listaCaronas;
	private InfoCaronaServerAsync controller;
	private CellTable<InfoCarona> tabelaCaronas;
	private ListDataProvider<InfoCarona> dataProvider; 
	private TextBox boxEfetuarBusca; 
	private String idCaronaSelecionada;
	
	public PanelBuscarCarona(final InfoCaronaServerAsync controller, final String idSessao) {
		this.controller = controller;
		
		boxEfetuarBusca = new TextBox();
		
		VerticalPanel panelBuscarCarona = new VerticalPanel();
		panelBuscarCarona.setWidth("100%");		
		//tabela para colocar as caronas
		tabelaCaronas = new CellTable<InfoCarona>();
	    //pager para passar as paginas da tabela
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(tabelaCaronas);
	    pager.setPageSize(10);

	    
	    //coluna selecao
	

	    
	    final SingleSelectionModel<InfoCarona> selectionModel = new SingleSelectionModel<InfoCarona>();

		tabelaCaronas.setSelectionModel(selectionModel,DefaultSelectionEventManager.<InfoCarona> createCheckboxManager());
		idCaronaSelecionada = "";
		//coluna de marcacao
		Column<InfoCarona, Boolean> colunaCheck = new Column<InfoCarona, Boolean>( new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(InfoCarona object) {
				return selectionModel.isSelected(object);
			}
		};
		
		selectionModel.addSelectionChangeHandler(new Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
			    idCaronaSelecionada = selectionModel.getSelectedObject().getIdCarona();
			}
		});
		
		//coluna id
		TextColumn<InfoCarona> colunaIdCarona = new TextColumn<InfoCarona>() {
			@Override
			public String getValue(InfoCarona carona) {
				return carona.getIdCarona();
			}
		};
		//coluna motorista
	    TextColumn<InfoCarona> colunaMotorista = new TextColumn<InfoCarona>() {
	      @Override
	      public String getValue(InfoCarona carona) {
	        return carona.getMotorista();
	      }
	    };
	    
	    //coluna origem
	    TextColumn<InfoCarona> colunaOrigem = new TextColumn<InfoCarona>() {
		      @Override
		      public String getValue(InfoCarona carona) {
		        return carona.getOrigem();
		      }
		};
		
		//coluna destino
		TextColumn<InfoCarona> colunaDestino = new TextColumn<InfoCarona>() {
		      @Override
		      public String getValue(InfoCarona carona) {
		        return carona.getDestino();
		      }
		};
		
		//coluna data
		TextColumn<InfoCarona> colunaData = new TextColumn<InfoCarona>() {
		      @Override
		      public String getValue(InfoCarona carona) {
		        return carona.getData();
		      }
		};
		
		//coluna hora
		TextColumn<InfoCarona> colunaHora = new TextColumn<InfoCarona>() {
		      @Override
		      public String getValue(InfoCarona carona) {
		        return carona.getHora();
		      }
		};

		//coluna vagas
		TextColumn<InfoCarona> colunaVagas = new TextColumn<InfoCarona>() {
		      @Override
		      public String getValue(InfoCarona carona) {
		        return carona.getVagas();
		      }
		};
		

		tabelaCaronas.addColumn(colunaCheck);
		tabelaCaronas.addColumn(colunaIdCarona,"ID");
	    tabelaCaronas.addColumn(colunaMotorista,"Motorista");
	    tabelaCaronas.addColumn(colunaOrigem,"Origem");
	    tabelaCaronas.addColumn(colunaDestino,"Destino");
	    tabelaCaronas.addColumn(colunaData,"Data");
	    tabelaCaronas.addColumn(colunaHora,"Hora");
	    tabelaCaronas.addColumn(colunaVagas,"Vagas");
	    
	    colunaData.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    colunaHora.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    colunaVagas.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    popularTabela();

		//panel com pesquisa de carona, filtro, sugerirPontoEncontro e solicitarVagaPontoEncontro
		HorizontalPanel hPanel01 = new HorizontalPanel();
		final DialogFiltroBusca dialogFiltro = new DialogFiltroBusca();
		
		boxEfetuarBusca.addKeyboardListener(new KeyboardListener() {
			@Override
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				List<InfoCarona> listaCaronasEncontradas = buscaCarona(listaCaronas, boxEfetuarBusca.getText());
				tabelaCaronas.setRowCount(listaCaronasEncontradas.size(), true);
				tabelaCaronas.setRowData(0, listaCaronasEncontradas);
				dataProvider.setList(listaCaronasEncontradas);  
				dataProvider.addDataDisplay(tabelaCaronas);
				
			}
			
			@Override
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
		});
		boxEfetuarBusca.setStyleName("boxModificada");
		boxEfetuarBusca.setWidth("200px");
				
		Image imagemBusca = new Image("imagens/busca01.png");
		imagemBusca.setSize("15px", "15px");
			
		Button botaoCriarFiltro = new Button("");
		botaoCriarFiltro.setTitle("Configurar Filtros");
		botaoCriarFiltro.setStyleName("botaoModificado");
		Image imageFiltro = new Image("imagens/filtro01.png");
		imageFiltro.setSize("15px", "15px");
		botaoCriarFiltro.getElement().appendChild(imageFiltro.getElement());
		
		botaoCriarFiltro.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				dialogFiltro.show();
			}
		});
		
		Button botaoSugerirPontoEncontro = new Button("Sugerir Ponto de Encontro");
		botaoSugerirPontoEncontro.setStyleName("botaoModificado");
		botaoSugerirPontoEncontro.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!selectionModel.getSelectedObject().equals(null)) {
					DialogBox dialogListaPontoEncontro = new DialogListaPontoEncontro(controller, idSessao, idCaronaSelecionada);
					dialogListaPontoEncontro.show();
				}
			}
		});
		
		Button botaoSolicitarVagaPontoEncontro = new Button("Solicitar Vaga Ponto de Encontro");
		botaoSolicitarVagaPontoEncontro.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (!selectionModel.getSelectedObject().equals(null)) {
					DialogBox dialogVagaPontoEncontro = new DialogSolicitarVagaPontoEncontro(controller, idSessao, idCaronaSelecionada);
					dialogVagaPontoEncontro.show();
				}
			}
		});
		botaoSolicitarVagaPontoEncontro.setStyleName("botaoModificado");
		
		hPanel01.add(imagemBusca);
		hPanel01.add(boxEfetuarBusca);
		hPanel01.add(botaoCriarFiltro);
		hPanel01.add(botaoSugerirPontoEncontro);
		hPanel01.add(botaoSolicitarVagaPontoEncontro);
		hPanel01.setCellVerticalAlignment(boxEfetuarBusca, HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel01.setCellVerticalAlignment(imagemBusca, HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel01.setCellVerticalAlignment(botaoCriarFiltro, HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel01.setCellVerticalAlignment(botaoSugerirPontoEncontro, HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel01.setCellHorizontalAlignment(botaoSugerirPontoEncontro, HasHorizontalAlignment.ALIGN_RIGHT);
		hPanel01.setSpacing(8);
				
		panelBuscarCarona.add(hPanel01);
		panelBuscarCarona.add(tabelaCaronas);
		panelBuscarCarona.add(pager);		
		
		panelBuscarCarona.setCellHorizontalAlignment(tabelaCaronas, HasHorizontalAlignment.ALIGN_CENTER);
		panelBuscarCarona.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		panelBuscarCarona.setSpacing(8);
		
		
		
		initWidget(panelBuscarCarona);
	    
		
	}

	
	public List<InfoCarona> buscaCarona(List<InfoCarona> listaCaronas, String palavraChave) {
		List<InfoCarona> caronasEncontradas = new ArrayList<InfoCarona>();
		for (InfoCarona carona : listaCaronas) {
					if(carona.toString().contains(palavraChave)) {
						caronasEncontradas.add(carona);
					}
		}
		return caronasEncontradas;
	}
	
	public void popularTabela() {
		boxEfetuarBusca.setText("");
		controller.getTodasCaronas(new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> todasCaronasOriginal) {
				listaCaronas = new ArrayList<InfoCarona>();
				for (String caronaInfo : todasCaronasOriginal) {
					String[] infoCarona = caronaInfo.split("!");
					String idCarona = infoCarona[0];
					String motorista = infoCarona[1];
					String origem = infoCarona[2];
					String destino = infoCarona[3]; 
					String data = infoCarona[4];
					String hora = infoCarona[5];
					String vagas = infoCarona[6];
					InfoCarona carona = new InfoCarona(idCarona, motorista, origem, destino, data, hora, vagas);
					listaCaronas.add(carona);
				}
				List<InfoCarona> listaCaronasEncontradas = buscaCarona(listaCaronas, "");
				tabelaCaronas.setRowCount(listaCaronasEncontradas.size(), true);
				tabelaCaronas.setRowData(0, listaCaronasEncontradas);
				dataProvider.setList(listaCaronasEncontradas);  
				dataProvider.addDataDisplay(tabelaCaronas);
				tabelaCaronas.redraw();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
