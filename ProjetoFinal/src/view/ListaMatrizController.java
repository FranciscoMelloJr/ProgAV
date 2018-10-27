package view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Aresta;

public class ListaMatrizController {
	
	@FXML
	TextArea txtMatrizIncidencia;
	@FXML
	TextArea txtMatrizAdjacencia;
	@FXML
	TextArea txtMatrizDistancia;
	@FXML
	TextArea txtListaAdjacencia;
	@FXML
	TextArea txtListaAresta;

	ArrayList<String> verticeLista = new ArrayList<String>();
	ArrayList<Aresta> arestaLista = new ArrayList<Aresta>();

	public void calculaMatrizIncidencia() {

		String matrizIncidencia[][] = new String[verticeLista.size() + 1][arestaLista.size() + 1];

		for (int i = 0; i < verticeLista.size(); i++) {
			matrizIncidencia[i + 1][0] = verticeLista.get(i);
		}

		for (int j = 0; j < arestaLista.size(); j++) {
			matrizIncidencia[0][j + 1] = arestaLista.get(j).nValorado();
		}

		for (int j = 0; j < verticeLista.size(); j++) {
			for (int i = 0; i < arestaLista.size(); i++) {
				if (arestaLista.get(i).getOrigem().equals(verticeLista.get(j))) {
					if (ckValorado.isSelected()) {
						matrizIncidencia[j + 1][i + 1] = String.valueOf(" " + arestaLista.get(i).getValor());
					} else {
						matrizIncidencia[j + 1][i + 1] = "1   ";
					}
				}
				if (arestaLista.get(i).getDestino().equals(verticeLista.get(j))) {
					if (!ckOrientado.isSelected()) {
						matrizIncidencia[j + 1][i + 1] = "1   ";
						if (arestaLista.get(i).getDestino().equals(arestaLista.get(i).getOrigem())) {
							matrizIncidencia[j + 1][i + 1] = "2   ";
						}
						if (ckValorado.isSelected()) {
							matrizIncidencia[j + 1][i + 1] = String.valueOf(" " + arestaLista.get(i).getValor());
						}
					} else {
						if (ckValorado.isSelected()) {
							matrizIncidencia[j + 1][i + 1] = String.valueOf("-" + arestaLista.get(i).getValor());
						} else {
							matrizIncidencia[j + 1][i + 1] = "-1   ";
						}
					}
				}
			}
		}

		matrizIncidencia[0][0] = " ";
		String stringMatrizIncidencia = "";
		for (int i = 0; i < verticeLista.size() + 1; i++) {
			for (int j = 0; j < arestaLista.size() + 1; j++) {
				if (matrizIncidencia[i][j] == null) {
					if (ckValorado.isSelected()) {
						matrizIncidencia[i][j] = "X  ";
					} else {
						matrizIncidencia[i][j] = "0  ";
					}
				}
				stringMatrizIncidencia += (matrizIncidencia[i][j] + "  ");
			}
			stringMatrizIncidencia += "\n";
		}
		txtMatrizIncidencia.setText(stringMatrizIncidencia);
	}

	public void calculaMatrizAdjacencia() {

		String matrizAdjacencia[][] = new String[verticeLista.size() + 1][verticeLista.size() + 1];
		int indiceX = 1;
		int indiceY = 1;
		for (Aresta a : arestaLista) {
			for (int x = 0; x < verticeLista.size(); x++) {
				if (a.getOrigem().equals(verticeLista.get(x))) {
					indiceX = x + 1;
				}
			}
			for (int y = 0; y < verticeLista.size(); y++) {
				if (a.getDestino().equals(verticeLista.get(y))) {
					indiceY = y + 1;
				}
			}
			if ((matrizAdjacencia[indiceX][indiceY] != null)) {
				int arestas = Integer.parseInt(matrizAdjacencia[indiceX][indiceY]);
				arestas++;
				matrizAdjacencia[indiceX][indiceY] = String.valueOf(arestas);
			} else {
				matrizAdjacencia[indiceX][indiceY] = "1";
			}
			if (ckValorado.isSelected()) {
				matrizAdjacencia[indiceX][indiceY] = String.valueOf(a.getValor());
			}
			if (!ckOrientado.isSelected()) {
				if (matrizAdjacencia[indiceY][indiceX] != null) {
					int aux = Integer.parseInt(matrizAdjacencia[indiceY][indiceX]);
					if (indiceX != indiceY) {
						aux++;
					}
					matrizAdjacencia[indiceY][indiceX] = String.valueOf(aux);
				} else {
					matrizAdjacencia[indiceY][indiceX] = "1";
				}
				if (ckValorado.isSelected()) {
					matrizAdjacencia[indiceY][indiceX] = String.valueOf(a.getValor());
				}
			}
		}

		for (int k = 0; k < verticeLista.size(); k++) {
			matrizAdjacencia[0][k + 1] = verticeLista.get(k);
			matrizAdjacencia[k + 1][0] = verticeLista.get(k);
		}

		matrizAdjacencia[0][0] = " ";
		String stringMatrizAdjacencia = "";
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia.length; j++) {
				if (matrizAdjacencia[i][j] == null) {
					if (ckValorado.isSelected()) {
						matrizAdjacencia[i][j] = "X";
					} else {
						matrizAdjacencia[i][j] = "0";
					}
				}
				stringMatrizAdjacencia += matrizAdjacencia[i][j] + "  ";
			}
			stringMatrizAdjacencia += "\n";
		}
		txtMatrizAdjacencia.setText(stringMatrizAdjacencia);
	}

	public void calculaListaAdjacencia() {

		ArrayList<Aresta> adjacenciaLista = new ArrayList<Aresta>();

		for (int i = 0; i < verticeLista.size(); i++) {
			Aresta a = new Aresta();
			a.setOrigem(verticeLista.get(i) + "->");
			adjacenciaLista.add(a);
			for (Aresta aresta : arestaLista) {
				if (verticeLista.get(i).equals(aresta.getOrigem())) {
					a.setDestino(a.getDestino() + " " + aresta.getDestino());
				}
			}
		}

		if (!ckOrientado.isSelected()) {
			for (int j = 0; j < verticeLista.size(); j++) {
				for (Aresta aresta : arestaLista) {
					if (verticeLista.get(j).equals(aresta.getDestino())) {
						if (!aresta.getOrigem().equals(aresta.getDestino())) {
							adjacenciaLista.get(j)
									.setOrigem(adjacenciaLista.get(j).getOrigem() + " " + aresta.getOrigem());
						}
					}
				}
			}
		}

		String stringListaAdjacencia = "";
		for (int k = 0; k < verticeLista.size(); k++) {
			stringListaAdjacencia += adjacenciaLista.get(k).nValorado() + "\n";
		}
		txtListaAdjacencia.setText(stringListaAdjacencia);
	}

	public void calculaListaAresta() {

		ArrayList<Aresta> lista = arestaLista;

		if (!ckValorado.isSelected()) {
			for (int i = 0; i < arestaLista.size(); i++) {
				for (Aresta aresta : lista) {
					if (arestaLista.get(i).nValorado().equals(aresta.nValorado())) {
						aresta.setValor(aresta.getValor() + 1);
					}
				}
			}
			for (int j = 0; j < lista.size(); j++) {
				for (int l = 0; l < lista.size(); l++) {
					if (lista.get(j).nValorado().equals(lista.get(l).nValorado())) {
						if (lista.get(l).getValor() > 1) {
							lista.remove(l);
						}
					}
				}
			}
		}
		String stringListaAresta = "";
		for (int k = 0; k < lista.size(); k++) {
			stringListaAresta += lista.get(k).toString() + "\n";
		}
		txtListaAresta.setText(stringListaAresta);
	}

	@FXML
	public void finalizar() {

		calculaMatrizIncidencia();
		calculaMatrizAdjacencia();
		calculaListaAdjacencia();
		calculaListaAresta();

	}

	@FXML
	public void adicionaAresta() {

		Aresta aresta = new Aresta();
		aresta.setOrigem(txtOrigem.getText());
		aresta.setDestino(txtDestino.getText());
		if (ckValorado.isSelected()) {
			aresta.setValor(Integer.parseInt(txtValor.getText()));
		} else {
			aresta.setValor(0);
		}
		arestaLista.add(aresta);
		limpaTelaE();
		txtOrigem.requestFocus();

	}

	@FXML
	public void adicionaVertice() {

		verticeLista.add(txtVertice.getText());
		txtVertice.setText("");

	}

	@FXML
	public void valoradoSN() {
		if (ckValorado.isSelected()) {
			txtValor.setDisable(false);
		} else {
			txtValor.setDisable(true);
		}
	}

	@FXML
	public void focusDestino() {
		txtDestino.requestFocus();
	}

	@FXML
	public void focus() {
		if (ckValorado.isSelected()) {
			txtValor.requestFocus();
		} else {
			adicionaAresta();
		}
	}

	@FXML
	public void limpaTelaE() {

		txtOrigem.setText("");
		txtValor.setText("");
		txtDestino.setText("");

	}

}
