package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.aluno.Aluno;
import model.aluno.AlunoServiceImpl;
import util.Conexao;

public class CadastroController {
	
	@FXML TextField txtNome;
	@FXML TextField txtFiltro;
	
	@FXML ComboBox<String> chUf;
	
	@FXML TableView<Object> tableView;
	@FXML TableColumn<Aluno, String> colNome;
	
	private AlunoServiceImpl alunoService = new AlunoServiceImpl();
	private List<Aluno> alunos = new ArrayList<>();
	

	@FXML
	public void initialize() {
		alunoService.initialize(colNome,chUf);
		preencheComboCidade();
		preencheComboCurso();
	}
	
	@FXML
	public void cadastrarAluno() {
		Aluno aluno = alunoService.objectMap(txtNome,chUf);
		Aluno salvo = alunoService.cadastrarAluno(aluno);
		alunos.add(salvo);
		tableView.setItems(FXCollections.observableArrayList(alunos));
	}
	
	@FXML
	public void filtrar() {
		tableView.setItems(FXCollections.observableArrayList(
				alunos.stream().filter(a -> a.getNome().equals(txtFiltro.getText())).collect(Collectors.toList())));
		
	}
	
	public void cadastra() {
		
		try { //cria objeto aluno
			Aluno = a = new Aluno();
			a.setNome(txtNome.getText())
			a.setIDade(Integer.parseInt(txtIdade.getText()))
			a.setCidade(cbCidade.getSelectionModel().getSelectedItem())
			a.setCurso(cbCurso.getSelectionModel().getSelectedItem())
			Connection conn = Conexao.getConexao();
			String sql = "Insert into alno (nome,idade,cidade,curso) + values (?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getNome());
			ps.setInt(2, a.getIdade());
			ps.setInt(3, a.getCidade().getCodigo());
			ps.setInt(4, a.getCurso().getCodigo());
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	private void preencheComboCidade() {
		try { 
			Connection conn = Conexao.getConexao();
			String sql = "Select * from cidade order by nome";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet = ps.executeQuery();
			while(rs.next()) {
				Cidade c = new Cidade();
			c.setCodigo(rs.getInt("codigo");
			c.setnome(rs.getString("nome");
			c.setUf(rs.getString("uf");
			cbCidade.getItens().add(c);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
