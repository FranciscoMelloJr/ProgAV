package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class PrincipalController {

	@FXML
	TextField txtTamanho;
	@FXML
	TextField txtNumero;
	@FXML
	TextField txtPosicao;

	private int[] vetor;

	@FXML
	public void instanciar() {
		try {
			int tamanho = Integer.parseInt(txtTamanho.getText());
			if (tamanho > 10)
				throw new NumberFormatException("Limite 10");
			vetor = new int[tamanho];
		} catch (NumberFormatException e) {
			mostraMensagem("Erro do tamanho do vetor\n" + e.getMessage(), AlertType.ERROR);
			txtTamanho.requestFocus();
			txtTamanho.selectAll();
		} catch (NegativeArraySizeException e) {
			mostraMensagem("N�o pode ser negativo", AlertType.ERROR);
			txtTamanho.requestFocus();
			txtTamanho.selectAll();
		} catch (Exception e) {
			mostraMensagem("Erro n�o identificado", AlertType.WARNING);
		}

	}

	@FXML
	public void inserir() {
		try {

			int numero = Integer.parseInt(txtNumero.getText());
			int posicao = Integer.parseInt(txtPosicao.getText());
			insereNoVetor(posicao, numero);
			// vetor[posicao] = numero;
			mostraMensagem("Numero inserido com sucesso", AlertType.INFORMATION);

		} catch (NumberFormatException e) {
			mostraMensagem("Erro de convers�ao numerica", AlertType.ERROR);
		} catch (NegativeArraySizeException e) {
			mostraMensagem("Campo POSI��O n�o pode ser negativo", AlertType.ERROR);
		} catch (NullPointerException e) {
			mostraMensagem("Vetor n�o instanciado", AlertType.ERROR);
		} catch (ArrayIndexOutOfBoundsException e) {
			mostraMensagem("Posi��o n�o existe no vetor - No inserir", AlertType.ERROR);
		} catch (Exception e) {
			mostraMensagem("Erro n�o identificado", AlertType.WARNING);
		}// finally {
			//mostraMensagem("Fim do programa", AlertType.INFORMATION);
	//	}
	}

	public void insereNoVetor(int pos, int nr) throws ArrayIndexOutOfBoundsException {
		try {
			vetor[pos] = nr;
		} catch (ArrayIndexOutOfBoundsException e) {
			mostraMensagem("Posi��o n�o existe no vetor - No m�todo", AlertType.ERROR);
			throw e;
		}
	}

	public void mostraMensagem(String msg, AlertType tipo) {
		Alert a = new Alert(tipo);
		a.setHeaderText(null);
		a.setContentText(msg);
		a.show();

	}

}
