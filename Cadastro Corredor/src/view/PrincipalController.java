package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Corredor;

public class PrincipalController {

	@FXML
	TextField txtNome;
	@FXML
	TextField txtPeito;
	@FXML
	TextField txtDistancia;
	@FXML
	DatePicker txtDataNascimento;

	@FXML
	TableView<Corredor> tbl;
	@FXML
	TableColumn<Corredor, String> colNome;
	@FXML
	TableColumn<Corredor, Number> colPeito;
	@FXML
	TableColumn<Corredor, Number> colDistancia;
	@FXML
	TableColumn<Corredor, String> colFaixa;

	private ArrayList<Corredor> corredores = new ArrayList<>();

	@FXML
	public void initialize() {
		inicializaTbl();
	}

	private void inicializaTbl() {
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colPeito.setCellValueFactory(cellData -> cellData.getValue().peitoProperty());
		colDistancia.setCellValueFactory(cellData -> cellData.getValue().distanciaProperty());
		colFaixa.setCellValueFactory(cellData -> cellData.getValue().faixaProperty());

	}

	private int calculaIdadeJava8(LocalDate dtNasc) {
		LocalDate hoje = LocalDate.now();
		long idade = ChronoUnit.YEARS.between(dtNasc, hoje);
		return (int) idade;
	}

	@FXML
	public void incluir() {

		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Corredor c = new Corredor();
			c.setNome(txtNome.getText());
			c.setNasc(dtf.format(txtDataNascimento.getValue()));
			c.setIdade(calculaIdadeJava8(txtDataNascimento.getValue()));
			c.setPeito(Double.parseDouble(txtPeito.getText()));
			c.setDistancia(Integer.parseInt(txtDistancia.getText()));
			if( c.getIdade()< 20)
				throw new NumberFormatException("Idade min�mia de 20 anos!");
			if (c.getIdade() < 30)
				c.setFaixa("A");
			if (c.getIdade() < 40)
				c.setFaixa("B");
			if (c.getIdade() < 50)
				c.setFaixa("C");
			if (c.getIdade() < 60)
				c.setFaixa("D");
			else
				c.setFaixa("E");
			corredores.add(c);
			tbl.setItems(FXCollections.observableArrayList(corredores));
			limpaTela();
		} catch (NumberFormatException e) {
			mostraMensagem("Insira um valor v�lido" + e.getMessage(), AlertType.WARNING);
		}
		
		
		catch (Exception e) {
			mostraMensagem("Erro n�o identificado", AlertType.WARNING);
		}
	}

	public void mostraMensagem(String msg, AlertType tipo) {
		Alert a = new Alert(tipo);
		a.setHeaderText(null);
		a.setContentText(msg);
		a.show();

	}

	@FXML
	public void limpaTela() {
		txtNome.setText("");
		txtPeito.setText("");
		txtDistancia.setText("");
		txtDataNascimento.setValue(null);

	}

}
