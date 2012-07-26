package ufcg.si1.infoCarona.client.janelas;

import java.util.ArrayList;
import java.util.List;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class PanelMinhasCaronas extends Composite {
	
	private static List<InfoCarona> listaCaronas;
	private ListDataProvider<InfoCarona> dataProvider; 
	private CellTable<InfoCarona> tabelaCaronas;
	private InfoCaronaServerAsync controller;
	private String idSessao;

	public PanelMinhasCaronas(final InfoCaronaServerAsync controller, final String idSessao) {
		this.controller = controller;
		this.idSessao = idSessao;
		
		VerticalPanel panelMinhasCaronas = new VerticalPanel();
		panelMinhasCaronas.setWidth("100%");		
		//tabela para colocar as caronas
		tabelaCaronas = new CellTable<InfoCarona>();
	    //pager para passar as paginas da tabela
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(tabelaCaronas);
	    pager.setPageSize(10);

	   

		//coluna id
		TextColumn<InfoCarona> colunaIdCarona = new TextColumn<InfoCarona>() {
			@Override
			public String getValue(InfoCarona carona) {
				return carona.getIdCarona();
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
		
		ActionCell editCell = new ActionCell<InfoCarona>(
				"Responder Solicitações",
				new ActionCell.Delegate<InfoCarona>() {
					public void execute(InfoCarona carona) {
						DialogResponderSolicitacaoCarona dialogResponderSolicitacaoCarona = new DialogResponderSolicitacaoCarona(controller, idSessao, carona.getIdCarona());
						dialogResponderSolicitacaoCarona.show();
					}
				});

		Column<InfoCarona, ActionCell> colunaResponderSolicitacoes = (new IdentityColumn(
				editCell));
		
		ActionCell editCell2 = new ActionCell<InfoCarona>(
				"Responder Sugestões",
				new ActionCell.Delegate<InfoCarona>() {
					public void execute(InfoCarona carona) {
						DialogResponderSugestaoPontoEncontro dialogResponderSugestaoCarona = new DialogResponderSugestaoPontoEncontro(controller, idSessao, carona.getIdCarona());
						dialogResponderSugestaoCarona.show();
					}
				});

		Column<InfoCarona, ActionCell> colunaResponderSugestoes = (new IdentityColumn(
				editCell2));

		tabelaCaronas.addColumn(colunaIdCarona,"ID");
	    tabelaCaronas.addColumn(colunaOrigem,"Origem");
	    tabelaCaronas.addColumn(colunaDestino,"Destino");
	    tabelaCaronas.addColumn(colunaData,"Data");
	    tabelaCaronas.addColumn(colunaHora,"Hora");
	    tabelaCaronas.addColumn(colunaVagas,"Vagas");
	    tabelaCaronas.addColumn(colunaResponderSolicitacoes,"Solicitaçoes Pendentes");
	    tabelaCaronas.addColumn(colunaResponderSugestoes,"Sugestões Pendentes");
	    
	    colunaData.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    colunaHora.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    colunaVagas.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    colunaResponderSolicitacoes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    colunaResponderSugestoes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
	    popularTabela();
	    
		panelMinhasCaronas.add(tabelaCaronas);
		panelMinhasCaronas.add(pager);		
		
		panelMinhasCaronas.setCellHorizontalAlignment(tabelaCaronas, HasHorizontalAlignment.ALIGN_CENTER);
		panelMinhasCaronas.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		panelMinhasCaronas.setSpacing(8);
		
		
		
		initWidget(panelMinhasCaronas);
		
	}

	public void popularTabela() {
		controller.getListaCaronasUsuario(idSessao, new AsyncCallback<List<List<String>>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(List<List<String>> result) {
				dataProvider = new ListDataProvider<InfoCarona>();
				listaCaronas = new ArrayList<InfoCarona>();
				for (List<String> infoCarona : result) {
					String idCarona = infoCarona.get(0);
					String motorista = infoCarona.get(1);
					String origem = infoCarona.get(2);
					String destino = infoCarona.get(3);
					String data = infoCarona.get(4);
					String hora = infoCarona.get(5);
					String vagas = infoCarona.get(6);
					listaCaronas.add(new InfoCarona(idCarona, motorista, origem, destino, data, hora, vagas));
				}
				tabelaCaronas.setRowCount(listaCaronas.size(), true);
				tabelaCaronas.setRowData(0, listaCaronas);
				dataProvider.setList(listaCaronas);  
				dataProvider.addDataDisplay(tabelaCaronas);
			}
		});
		
	}
}
