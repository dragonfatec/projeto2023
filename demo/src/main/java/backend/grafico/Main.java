package backend.grafico;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

public class Main {

    @FXML
    private BarChart<String, Double> graficoAreaChart;

    @FXML
    private ComboBox<String> opcoesEquipe;
    @FXML
    private ComboBox<String> opcoesAno;

    @FXML
    void initialize(){
        atualizar(null, null);
    }

    public void atualizar(String equipe, String ano){

        graficoAreaChart.getData().clear();

        Grafico consulta = new Grafico();

        Grafico.Opcoes opcoes = consulta.dadosOpcoes();

        opcoesEquipe.setItems(opcoes.getEquipes());
        opcoesAno.setItems(opcoes.getAnos());

        Grafico.DadosGrafico dados = consulta.consultaDados(equipe, ano);
        graficoAreaChart.getData().add(dados.getDadosExtra());
        graficoAreaChart.getData().add(dados.getDadosSobreaviso());

    }

    @FXML
    void opcoesConsultar(ActionEvent event) {
        if (opcoesEquipe.getValue() != null && opcoesAno.getValue() != null){
            atualizar(opcoesEquipe.getValue(), opcoesAno.getValue());
        } else if (opcoesEquipe.getValue() != null && opcoesAno.getValue() == null){
            atualizar(opcoesEquipe.getValue(), null);
        } else if (opcoesEquipe.getValue() == null && opcoesAno.getValue() != null){
            atualizar(null, opcoesAno.getValue());
        }

    }

/*
    public void main(Stage stage){
        App definir = new App();
        definir.janela("/resources/grafico.fxml", stage);
    }
*/

}
