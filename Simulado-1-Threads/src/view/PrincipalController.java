package view;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import model.Atleta;

public class PrincipalController {
	
	@FXML TextField distancia1;
	@FXML TextField distancia2;
	@FXML TextField tempo1;
	@FXML TextField tempo2;
	@FXML ProgressBar bar1;
	@FXML ProgressBar bar2;
	
	@FXML 
	public void iniciaComThread() {
		double d1 = Integer.parseInt(distancia1.getText());
		double d2 = Integer.parseInt(distancia2.getText());
		double t1 = Integer.parseInt(tempo1.getText())*1000;
		double t2 = Integer.parseInt(tempo2.getText())*1000;
		Atleta a1 = new Atleta (d1, t1, bar1);
		Atleta a2 = new Atleta (d2, t2, bar2);
		new Thread(a1).start();
		new Thread(a2).start();
	}
	
	@FXML
	public void sair() {
		System.exit(0);
	}
	
}
