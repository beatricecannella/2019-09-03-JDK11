/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Portion;
import it.polito.tdp.food.model.PortionConnesse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<Portion> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	String num = this.txtPassi.getText();
    	
    	if(num == null) {
    		txtResult.appendText("Inserire numero passi N");
    	}
    	int n = 0;
    	try {
    		n = Integer.parseInt(num);
    	}
    	catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.appendText("Inserire numero intero di passi N");
    	}
    	if(model.getGrafo() == null) {
    		this.txtResult.appendText("Crea prima un grafo");
    		return;
    	}
    	Portion pp = this.boxPorzioni.getValue();
    	List<Portion> percorso = model.percorsoMigliore(pp, n);
    	this.txtResult.appendText("Peso totale: " + model.pesoFinale());
    	for(Portion portion : percorso) {
    		txtResult.appendText(portion.toString() + "\n");
    	}
    	
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate..." + "\n");
    	if(model.getGrafo() == null) {
    		this.txtResult.appendText("Crea prima un grafo");
    		return;
    	}
    	Portion pp = this.boxPorzioni.getValue();
    	
    	for(PortionConnesse p: model.portionConnesse(pp)) {
    		this.txtResult.appendText(p.toString() + "\n");
    	}
    	
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String cc = this.txtCalorie.getText();
    	if(cc == null) {
    		txtResult.appendText("Inserire calorie");
    	}
    	int c = 0;
    	try {
    		c = Integer.parseInt(cc);
    	}
    	catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.appendText("Inserire calorie");
    	}
    	
    	txtResult.appendText("Creazione grafo...");
    	this.model.creaGrafo(c);
    	this.boxPorzioni.getItems().addAll(model.getPortion());
    	txtResult.appendText("GRAFO CREATO!\n");
    	txtResult.appendText("#VERTICI: "+this.model.vertici() + "\n");
    	txtResult.appendText("#ARCHI: "+this.model.archi() + "\n");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    
    
    }
}
