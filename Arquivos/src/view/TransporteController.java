package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Aluno;
import model.Transporte;

public class TransporteController {

	@FXML
	RadioButton ckProprio;
	@FXML
	RadioButton ckPublico;

	@FXML
	TextField txtDistancia;
	@FXML
	TextField txtCustoMensal;

	@FXML
	TableView<Transporte> tbl;
	@FXML
	TableColumn<Transporte, String> colTipo;
	@FXML
	TableColumn<Transporte, Number> colDistancia;
	@FXML
	TableColumn<Transporte, Number> colCustoMensal;

	private ArrayList<Transporte> transportes = new ArrayList<>();

	@FXML
	public void initialize() {
		inicializaTabela();
		leArquivo();
	}

	private void inicializaTabela() {
		colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoTransporteProperty());
		colDistancia.setCellValueFactory(cellData -> cellData.getValue().distanciaProperty());
		colCustoMensal.setCellValueFactory(cellData -> cellData.getValue().custoMensalProperty());
	}

	@FXML
	public void cadastrar() {
		inserir();
		leArquivo();
	}

	private void leArquivo() {
		transportes.clear();
		try (BufferedReader br = new BufferedReader(new FileReader("transportes.txt"));) {
			String linha = "";
			while ((linha = br.readLine()) != null) {
				Transporte t = new Transporte();
				String[] dados = linha.split(",");
				t.setTipoTransporte(dados[0]);
				t.setDistancia(Double.parseDouble(dados[1]));
				t.setCustoMensal(Double.parseDouble(dados[2]));
				transportes.add(t);
			}
			tbl.setItems(FXCollections.observableArrayList(transportes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inserir() {
		try {
			Transporte t = new Transporte();
			t.setTipoTransporte(ckProprio.isSelected() ? "Pr�prio" : "P�blico");
			t.setDistancia(Double.parseDouble(txtDistancia.getText()));
			t.setCustoMensal(Double.parseDouble(txtCustoMensal.getText()));
			FileWriter fw = new FileWriter("transportes.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(t.getTipoTransporte() + "," + t.getDistancia() + "," + t.getCustoMensal() + "\n");
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unirFiles() {

		try (FileWriter fileWriter = new FileWriter("unidos.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				FileReader fileReaderTransportes = new FileReader("transportes.txt");
				BufferedReader bufferedReaderTransportes = new BufferedReader(fileReaderTransportes);
				FileReader fileReaderAlunos = new FileReader("alunos.txt");
				BufferedReader bufferedReaderAlunos = new BufferedReader(fileReaderAlunos);

		) {

			String linhaTransporte = "";
			String linhaAluno = "";

			while (Objects.nonNull(linhaTransporte = bufferedReaderTransportes.readLine())
					&& Objects.nonNull(linhaAluno = bufferedReaderAlunos.readLine())) {

				Transporte transporte = new Transporte();
				Aluno aluno = new Aluno();

				String[] linhasTranportes = linhaTransporte.split(",");
				String[] linhasAlunos = linhaAluno.split(",");

				aluno.setNome(linhasAlunos[0]);
				aluno.setSemestre(Integer.parseInt(linhasAlunos[1]));
				aluno.setCurso(linhasAlunos[2]);

				transporte.setTipoTransporte(linhasTranportes[0]);
				transporte.setDistancia(Double.parseDouble(linhasTranportes[1]));
				transporte.setCustoMensal(Double.parseDouble(linhasTranportes[2]));

				bufferedWriter.append(aluno.getNome() + "," + aluno.getSemestre() + "," + aluno.getCurso() + ","
						+ transporte.getTipoTransporte() + "," + transporte.getDistancia() + ","
						+ transporte.getCustoMensal() + "\n");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
}