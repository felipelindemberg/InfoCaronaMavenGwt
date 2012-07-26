package ufcg.si1.infoCarona.client.janelas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

public class PanelInteresses extends Composite {

	public PanelInteresses(InfoCaronaServerAsync controller, String idSessao) {
		
		VerticalPanel panelInteresse = new VerticalPanel();
		
		HorizontalPanel hPanel01 = new HorizontalPanel();
		TextBox boxInteresse = new TextBox();
		boxInteresse.setStyleName("boxModificada");
		boxInteresse.setWidth("150px");
		Button buttonAdicionarInteresse = new Button("");
		buttonAdicionarInteresse.setTitle("Adicionar Interesse");
		buttonAdicionarInteresse.setStyleName("botaoModificado");
		Image imageInteresse = new Image("imagens/adicionar.png");
		imageInteresse.setSize("15px", "15px");
		buttonAdicionarInteresse.getElement().appendChild(imageInteresse.getElement());
		buttonAdicionarInteresse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		hPanel01.add(boxInteresse);
		hPanel01.add(buttonAdicionarInteresse);
		hPanel01.setSpacing(8);
		
	/*	CellTable<InfoInteresse> tabelaInteresse = new CellTable<InfoInteresse>();
		// pager para passar as paginas da tabela
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(tabelaInteresse);
		pager.setPageSize(10);

		// coluna id
		TextColumn<InfoSolicitacao> colunaIdSolicitacao = new TextColumn<InfoSolicitacao>() {
			@Override
			public String getValue(InfoSolicitacao solicitacao) {
				return solicitacao.getIdSolicitacao();
			}
		};*/
		
		
		HorizontalPanel hPanel02 = new HorizontalPanel();
		
		
		
		panelInteresse.add(hPanel01);
		
		initWidget(panelInteresse);
		
		
		
	}
}
