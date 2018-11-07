package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Aresta;
import model.Vertice;

public class DistanciaController {

	private static final int INFINITO = 99;

	@FXML
	TextArea txtMatrizDistancia;
	@FXML
	TextArea txtMatrizTransposta;

	ArrayList<Vertice> verticeLista = new ArrayList<Vertice>();
	ArrayList<Aresta> arestaLista = new ArrayList<Aresta>();
	boolean valorado, orientado;

	@FXML
	public void initialize() {
		lerArquivoProperties();
		leVertice();
		leAresta();
		floydWarshall();
		calculaMatrizDistancia();
		calculaMatrizTransposta();

	}

	private void calculaMatrizDistancia() {

	}

	private void calculaMatrizTransposta() {

	}

	private void floydWarshall() {

		Integer[][] matrizDistancia = new Integer[verticeLista.size() + 1][verticeLista.size() + 1];
		int indiceX = 0;
		int indiceY = 0;

		// inicializa a matriz com 0 na principal e 99 nos outros
		for (int i = 0; i < verticeLista.size(); i++) {
			for (int j = 0; j < verticeLista.size(); j++) {
				if (i == j) {
					matrizDistancia[i][j] = 0;
				} else {
					matrizDistancia[i][j] = INFINITO;
				}
			}
		}

		// fun��o completa
		for (Aresta aresta : arestaLista) {
			for (int i = 0; i < verticeLista.size(); i++) {
				if (aresta.getOrigem().equals(verticeLista.get(i).getNome())) {
					indiceX = i++;
				}
			}
			// encontra o indice
			for (int y = 0; y < verticeLista.size(); y++) {
				if (aresta.getDestino().equals(verticeLista.get(y).getNome())) {
					indiceY = y++;
				}
			}

			if (valorado) { // coloca o valor da aresta
				matrizDistancia[indiceX][indiceY] = aresta.getValor();
			} else {
				matrizDistancia[indiceX][indiceY] = 1;
			}

			if (!orientado) { // coloca o valor nos dois caso nao for diagrafo
				if (valorado) {
					matrizDistancia[indiceY][indiceX] = aresta.getValor();
				} else {
					matrizDistancia[indiceY][indiceX] = 1;
				}
			}
			// altera os valores
			for (int k = 0; k < verticeLista.size(); k++) {
				for (int i = 0; i < verticeLista.size(); i++) {
					for (int j = 0; j < verticeLista.size(); j++) {
						if (matrizDistancia[i][k] + matrizDistancia[k][j] < matrizDistancia[i][j]) {
							matrizDistancia[i][j] = matrizDistancia[i][k] + matrizDistancia[k][j];
						}
					}
				}
			}
		}

		String[] linha = new String[verticeLista.size() + 1];
		String[] coluna = new String[verticeLista.size() + 1];

		String stringMatrizDistancia = "      ";

		// cria string da coluna e linha dos vertices
		for (int k = 0; k < verticeLista.size(); k++) {
			linha[k] = verticeLista.get(k).getNome();
			coluna[k] = verticeLista.get(k).getNome();
		}

		// Coloca Saida e Retorno
		if (orientado) {
			coluna[coluna.length - 1] = "S";
			linha[linha.length - 1] = "R";
		} else {
			linha[linha.length - 1] = "E"; // n�o orientado
		}
		// inicializa com a primeira linha de vertices
		for (int i = 0; i < matrizDistancia.length; i++) {
			stringMatrizDistancia += linha[i] + "  ";
		}
		stringMatrizDistancia += "\n";

		// Coloca os maiores valores das linhas no final E COLUNA
		for (int i = 0; i < matrizDistancia.length - 1; i++) {
			int aux = 0;
			for (int j = 0; j < matrizDistancia.length - 1; j++) {
				if (matrizDistancia[i][j] > aux)
					aux = matrizDistancia[i][j];
			}
			matrizDistancia[i][matrizDistancia.length - 1] = aux;
		}

		//coloca os maiores valores das colunas no final R Linha
		if (orientado) {
			for (int i = 0; i < matrizDistancia.length - 1; i++) {
				int aux = 0;
				for (int j = 0; j < matrizDistancia.length - 1; j++) {
					if (matrizDistancia[j][i] > aux)
						aux = matrizDistancia[j][i];
				}
				matrizDistancia[matrizDistancia.length - 1][i] = aux;
			}
		}
tratar os nulo
		// adiciona as colunas com os valores
		for (int i = 0; i < matrizDistancia.length; i++) {
			stringMatrizDistancia += coluna[i] + "->";
			for (int j = 0; j < matrizDistancia.length; j++) {
				stringMatrizDistancia += matrizDistancia[i][j] + "  ";
			}
			stringMatrizDistancia += "\n";
		}
		txtMatrizDistancia.setText(stringMatrizDistancia);
	}

	public Vertice pegaVerticeOrigem(Aresta aresta) {

		Vertice origem = null;

		for (Vertice vertice : verticeLista) {
			if (aresta.getOrigem().equals(vertice.getNome())) {
				origem = vertice;
			}
		}
		return origem;
	}

	public Vertice pegaVerticeDestino(Aresta aresta) {

		Vertice destino = null;

		for (Vertice vertice : verticeLista) {
			if (aresta.getDestino().equals(vertice.getNome())) {
				destino = vertice;
			}
		}
		return destino;
	}

	private void leAresta() {
		arestaLista.clear();
		try (BufferedReader br = new BufferedReader(new FileReader("aresta.txt"))) {
			String linha = "";
			while ((linha = br.readLine()) != null) {
				String origem = linha.substring(0, 5).trim();
				String destino = linha.substring(5, 10).trim();
				int valor = Integer.parseInt(linha.substring(10, 13).trim());
				Aresta a = new Aresta();
				a.setOrigem(origem);
				a.setDestino(destino);
				a.setValor(valor);
				arestaLista.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void leVertice() {
		verticeLista.clear();
		try (BufferedReader br = new BufferedReader(new FileReader("vertice.txt"))) {
			String linha = "";
			while ((linha = br.readLine()) != null) {
				Vertice v = new Vertice();
				String nome = linha.substring(0, 5).trim();
				v.setNome(nome);
				verticeLista.add(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void lerArquivoProperties() {
		Properties propertie = new Properties();
		try (FileReader fr = new FileReader("conf.properties")) {
			propertie.load(fr);
			orientado = Boolean.valueOf(propertie.getProperty("orientado"));
			valorado = Boolean.valueOf(propertie.getProperty("valorado"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
