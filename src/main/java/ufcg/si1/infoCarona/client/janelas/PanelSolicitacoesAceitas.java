package ufcg.si1.infoCarona.client.janelas;

import java.util.ArrayList;
import java.util.List;

import ufcg.si1.infoCarona.client.InfoCaronaServerAsync;
import ufcg.si1.infoCarona.util.UtilInfo;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.view.client.ListDataProvider;

public class PanelSolicitacoesAceitas extends Composite {

	private InfoCaronaServerAsync controller;
	private CellTable<InfoSolicitacao> tabelaSolicitacoes;
	private ListDataProvider<InfoSolicitacao> dataProvider;
	private final List<InfoSolicitacao> listaSolicitacoes;
	private String idSessao;

	public PanelSolicitacoesAceitas(final InfoCaronaServerAsync controller,
			final String idSessao) {
		this.controller = controller;
		this.idSessao = idSessao;
		
		listaSolicitacoes = new ArrayList<InfoSolicitacao>();
		
		VerticalPanel panelSolicitacoesAceitas = new VerticalPanel();
		panelSolicitacoesAceitas.setWidth("100%");
		// tabela para colocar as caronas
		tabelaSolicitacoes = new CellTable<InfoSolicitacao>();
		// pager para passar as paginas da tabela
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pesquisador = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pesquisador.setDisplay(tabelaSolicitacoes);
		pesquisador.setPageSize(10);

		// coluna id
		TextColumn<InfoSolicitacao> colunaIdSolicitacao = new TextColumn<InfoSolicitacao>() {
			@Override
			public String getValue(InfoSolicitacao solicitacao) {
				return solicitacao.getIdSolicitacao();
			}
		};

		ActionCell editCell = new ActionCell<InfoSolicitacao>(
				"Informações da Carona",
				new ActionCell.Delegate<InfoSolicitacao>() {
					public void execute(InfoSolicitacao solicitacao) {
						String idCarona = solicitacao.getIdCarona();
						controller.getInformacoesCarona(idCarona, new AsyncCallback<List<String>>() {
							@Override
							public void onFailure(Throwable caught) {
								DialogMensagemUsuario dialogErro = new DialogMensagemUsuario(
										"Erro",caught.getMessage()
										);
								dialogErro.show();
							}
							@Override
							public void onSuccess(List<String> result) {
								String idCarona = result.get(0);
								String motorista = result.get(1);
								String origem = result.get(2);
								String destino = result.get(3);
								String data = result.get(4);
								String hora = result.get(5);
								String vagas= result.get(6);
								String mensagem = "ID: " + idCarona + "| Motorista: " + motorista + "| Origem: " + origem + "| Destino: " + destino + "| Data: " + data + "| Hora: " + hora + "| Vagas: " + vagas;
								DialogMensagemUsuario dialogInfoCarona = new DialogMensagemUsuario("Informação da Carona - "+idCarona, mensagem);
								dialogInfoCarona.show();
							}
						});
					}
				});

		Column<InfoSolicitacao, ActionCell> colunaIdCaronaSolicitacao = (new IdentityColumn(
				editCell));

		// coluna ponto de encontro
		TextColumn<InfoSolicitacao> colunaPontoEncontro = new TextColumn<InfoSolicitacao>() {
			@Override
			public String getValue(InfoSolicitacao solicitacao) {
				return solicitacao.getPontoEncontro();
			}
		};

		tabelaSolicitacoes.addColumn(colunaIdSolicitacao, "ID");
		tabelaSolicitacoes.addColumn(colunaPontoEncontro, "Ponto de Encontro");
		tabelaSolicitacoes.addColumn(colunaIdCaronaSolicitacao, "Carona");

		colunaIdCaronaSolicitacao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colunaIdSolicitacao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colunaPontoEncontro.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);		
		
		popularTabela();

		panelSolicitacoesAceitas.add(tabelaSolicitacoes);
		panelSolicitacoesAceitas.add(pesquisador);

		panelSolicitacoesAceitas.setCellHorizontalAlignment(tabelaSolicitacoes,
				HasHorizontalAlignment.ALIGN_CENTER);
		panelSolicitacoesAceitas.setCellHorizontalAlignment(pesquisador,
				HasHorizontalAlignment.ALIGN_CENTER);
		panelSolicitacoesAceitas.setSpacing(8);

		initWidget(panelSolicitacoesAceitas);

	}

	public void popularTabela() {
		
		for (int i = 0; i < listaSolicitacoes.size(); i++) {
			listaSolicitacoes.remove(0);
		}
		
		controller.getSolicitacoesAceitasUsuario(idSessao,
				new AsyncCallback<List<List<String>>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(List<List<String>> result) {
						for (List<String> list : result) {
							String idSolicitacao = list.get(0);
							String idCarona = list.get(1);
							String pontoEncontro = list.get(2);
							InfoSolicitacao solicitacaoInfo = new InfoSolicitacao(
									idSolicitacao, idCarona, pontoEncontro);
							listaSolicitacoes.add(solicitacaoInfo);

						}

						tabelaSolicitacoes.setRowCount(
								listaSolicitacoes.size(), true);
						tabelaSolicitacoes.setRowData(0, listaSolicitacoes);
						dataProvider = new ListDataProvider<InfoSolicitacao>();
						dataProvider.setList(listaSolicitacoes);
						dataProvider.addDataDisplay(tabelaSolicitacoes);
					}
				});

	}
}
