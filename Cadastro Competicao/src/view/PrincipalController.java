package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Competicao;

public class PrincipalController {

	@FXML
	TextField txtNome;
	@FXML
	TextField txtDistancia;
	@FXML
	DatePicker txtData;
	@FXML
	TextField txtColocacao;
	@FXML
	TextField txtMelhor;

	@FXML
	TableView<Competicao> tbl;
	@FXML
	TableColumn<Competicao, String> colNome;
	@FXML
	TableColumn<Competicao, String> colData;
	@FXML
	TableColumn<Competicao, Number> colDistancia;
	@FXML
	TableColumn<Competicao, Number> colColocacao;

	private ArrayList<Competicao> competicoes = new ArrayList<>();

	@FXML
	public void initialize() {
		inicializaTbl();
	}

	private void inicializaTbl() {
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
		colDistancia.setCellValueFactory(cellData -> cellData.getValue().distanciaProperty());
		colColocacao.setCellValueFactory(cellData -> cellData.getValue().coloca��oProperty());

	}

	@FXML
	public void incluir() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Competicao c = new Competicao();
		c.setNome(txtNome.getText());
		c.setData(dtf.format(txtData.getValue()));
		c.setColoca��o(Integer.parseInt(txtColocacao.getText()));
		c.setDistancia(Integer.parseInt(txtDistancia.getText()));
		competicoes.add(c);
		tbl.setItems(FXCollections.observableArrayList(competicoes));
		melhor();
		// limpaTela();

	}

	@FXML
	public void selecionar() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		Competicao c = tbl.getSelectionModel().getSelectedItem();

		txtNome.setText(c.getNome());
		txtColocacao.setText(String.format("%d", c.getColoca��o()));
		txtDistancia.setText(String.format("%s", c.getDistancia()));
		txtData.setValue(LocalDate.parse(c.getData(), dtf));

	}

	public void remover() {

		tbl.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1) {
				selecionar();

				competicoes.remove(tbl.getSelectionModel().getSelectedItem());
				tbl.setItems(FXCollections.observableArrayList(competicoes));
				melhor();
			}
		});

		// Competicao c = tbl.getSelectionModel().getSelectedItem();
		// for (Competicao x : competicoes)
		// if (c.toString().equals(x.toString())) {
		// tbl.getItems().removeAll(tbl.getSelectionModel().getSelectedItems());
		// competicoes.remove(x);
		// tbl.getSelectionModel().clearSelection();
		// }
		// System.out.println("chamou o metodo melhor");
		// tbl.setItems(FXCollections.observableArrayList(competicoes));
		// melhor();
	}

	@FXML
	public void melhor() {
		int melhor = competicoes.get(0).getColoca��o();
		System.out.println("Entrou no melhor " + melhor);
		for (Competicao c : competicoes) {
			System.out.println(c.getColoca��o() + " NO FOR  " + melhor);
			if (c.getColoca��o() < melhor) {
				System.out.println(c.getColoca��o() + " NO IF  " + melhor);
				melhor = c.getColoca��o();
			}
		}
		System.out.println("Setou o melhor " + melhor);

		txtMelhor.setText(Integer.toString(melhor));

	}

	@FXML
	public void limpaTela() {
		txtNome.setText("");
		txtColocacao.setText("");
		txtDistancia.setText("");
		txtData.setValue(null);

	}

}
