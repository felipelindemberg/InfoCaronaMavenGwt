package ufcg.si1.infoCarona.client.janelas;

import java.util.ArrayList;
import java.util.List;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class DialogResponderSugestaoPontoEncontro extends DialogBox {
	
	public DialogResponderSugestaoPontoEncontro(final InfoCaronaServerAsync controller, final String idSessao, final String idCarona) {
		setAnimationEnabled(true);
		setGlassEnabled(true);
		center();
		
		final CellTable<InfoSugestaoPontoEncontro> tabelaPontosEncontro = new CellTable<InfoSugestaoPontoEncontro>();
		
		
		VerticalPanel panelResponderSugestaoPontoEncontro = new VerticalPanel();
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(tabelaPontosEncontro);
	    pager.setPageSize(5);
		
	    TextColumn<InfoSugestaoPontoEncontro> colunaIdSugestaoPontoEncontro = new TextColumn<InfoSugestaoPontoEncontro>() {
			@Override
			public String getValue(InfoSugestaoPontoEncontro sugestao) {
				return sugestao.getIdSugestaoPontoEncontro();
			}
		};
	    
	    TextColumn<InfoSugestaoPontoEncontro> colunaLoginUsuario = new TextColumn<InfoSugestaoPontoEncontro>() {
			@Override
			public String getValue(InfoSugestaoPontoEncontro sugestao) {
				return sugestao.getLoginUsuario();
			}
		};
		
		TextColumn<InfoSugestaoPontoEncontro> colunaSugestoesFeitas = new TextColumn<InfoSugestaoPontoEncontro>() {
			@Override
			public String getValue(InfoSugestaoPontoEncontro sugestao) {
				return sugestao.getSugestoesFeitas();
			}
		};
		
	/*	EditTextCell boxSugestoes = new EditTextCell();

		Column<SugestaoPontoEncontroInfo, String> colunaBoxSugestoes = (new IdentityColumn(
				boxSugestoes));
*/		
		

/*	    Column<SugestaoPontoEncontroInfo, String> editTextColumn =
	        addColumn(new EditTextCell(), "EditText", new GetValue<String>() {
	          @Override
	          public String getValue(SugestaoPontoEncontroInfo contact) {
	            return contact.getFirstName();
	          }
	        }, new FieldUpdater<SugestaoPontoEncontroInfo, String>() {
	          @Override
	          public void update(int index, SugestaoPontoEncontroInfo object, String value) {
	            pendingChanges.add(new FirstNameChange(object, value));
	          }
	        });*/
		
		Column<InfoSugestaoPontoEncontro, String> colunaBoxSugestoes = new Column<InfoSugestaoPontoEncontro, String>(
				new EditTextCell()) {
			@Override
			public String getValue(InfoSugestaoPontoEncontro sugestao) {
				return sugestao.getSugestoesConfirmadas();
			}
		};
		colunaBoxSugestoes.setFieldUpdater(new FieldUpdater<InfoSugestaoPontoEncontro, String>() {
					@Override
					public void update(int index, InfoSugestaoPontoEncontro sugestao, String value) {
						sugestao.setSugestoesConfirmadas(value);
					}
				});

		controller.getListaSugestaoPontoEncontro(idCarona, new AsyncCallback<List<List<String>>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(List<List<String>> result) {
				final List<InfoSugestaoPontoEncontro> listaPontosEncontro = new ArrayList<InfoSugestaoPontoEncontro>();
				for (List<String> listaSugestao : result) {
					String idSugestaoPontoEncontro = listaSugestao.get(0);
					String loginUsuario = listaSugestao.get(1);
					String sugestoesFeitas = "";
					for (int i = 2; i < listaSugestao.size(); i++) {
						sugestoesFeitas += listaSugestao.get(i) +", ";
					}
					InfoSugestaoPontoEncontro sugestao = new InfoSugestaoPontoEncontro(idSugestaoPontoEncontro, loginUsuario, sugestoesFeitas);
					listaPontosEncontro.add(sugestao);
				}
				tabelaPontosEncontro.setRowCount(listaPontosEncontro.size(), true);
				tabelaPontosEncontro.setRowData(0, listaPontosEncontro);
				final ListDataProvider<InfoSugestaoPontoEncontro> dataProvider = new ListDataProvider<InfoSugestaoPontoEncontro>();
				dataProvider.setList(listaPontosEncontro);  
				dataProvider.addDataDisplay(tabelaPontosEncontro);
				
			}
		});

		ActionCell editCell = new ActionCell<InfoSugestaoPontoEncontro>(
				"Confirmar",
				new ActionCell.Delegate<InfoSugestaoPontoEncontro>() {
					public void execute(InfoSugestaoPontoEncontro sugestaoInfo) {
						controller.aceitarSolicitacaoPontoEncontro(idSessao, idCarona, sugestaoInfo.getIdSugestaoPontoEncontro(), sugestaoInfo.getSugestoesConfirmadas(), new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(String result) {
								DialogMensagemUsuario dialogSucess = new DialogMensagemUsuario(
										"Feito",
										"Solicitação de Ponto de Encontro Confirmada com Sucesso!");
								dialogSucess.show();								
							}
						});
					}
				});
		
		Column<InfoSugestaoPontoEncontro, ActionCell> colunaConfirmar = (new IdentityColumn(
				editCell));
		
		tabelaPontosEncontro.addColumn(colunaIdSugestaoPontoEncontro,"ID da Sugestão");
		tabelaPontosEncontro.addColumn(colunaLoginUsuario,"Login");
		tabelaPontosEncontro.addColumn(colunaSugestoesFeitas,"Sugestões Oferecidas");
		
		tabelaPontosEncontro.addColumn(colunaBoxSugestoes,"Resposta");
		tabelaPontosEncontro.addColumn(colunaConfirmar,"");
		
		;

		Button buttonFechar = new Button("Fechar");
		buttonFechar.setStyleName("botaoModificado");
		buttonFechar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();				
			}
		});
		
		panelResponderSugestaoPontoEncontro.add(tabelaPontosEncontro);
		panelResponderSugestaoPontoEncontro.add(pager);		
		panelResponderSugestaoPontoEncontro.add(buttonFechar);
		
		panelResponderSugestaoPontoEncontro.setCellHorizontalAlignment(tabelaPontosEncontro, HasHorizontalAlignment.ALIGN_CENTER);
		panelResponderSugestaoPontoEncontro.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		panelResponderSugestaoPontoEncontro.setCellHorizontalAlignment(buttonFechar, HasHorizontalAlignment.ALIGN_RIGHT);
		panelResponderSugestaoPontoEncontro.setSpacing(8);
		
		
		setWidget(panelResponderSugestaoPontoEncontro);
		
	}
}
