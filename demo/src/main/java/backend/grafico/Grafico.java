package backend.grafico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class Grafico {

    String banco = "jdbc:postgresql://localhost:5432/postgres";
    String usuario = "postgres";
    String senha = "@1233";

    public class Opcoes{
        private ObservableList<String> equipes;
        private ObservableList<String> anos;

        public Opcoes(ObservableList<String> equipes, ObservableList<String> anos){
            this.equipes = equipes;
            this.anos = anos;
        }

        public ObservableList<String> getEquipes() { return equipes; }
        public ObservableList<String> getAnos(){ return anos; }

    }

    List<Opcoes> opcoes(){
        List<Opcoes> opcoes = new ArrayList<>();

        try{
            Connection conexao = DriverManager.getConnection(banco, usuario, senha);
            PreparedStatement stmt = conexao.prepareStatement("SELECT DISTINCT equipe.nome_equipe, EXTRACT(YEAR FROM data_hora_inicial) AS ano " +
            "FROM hora JOIN equipe ON hora.id_equipe = equipe.id_equipe ORDER BY ano");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String equipes = rs.getString("nome_equipe");
                String anos = rs.getString("ano");

                Opcoes dadosOpcoes = new Opcoes(FXCollections.observableArrayList(equipes), FXCollections.observableArrayList(anos));
                opcoes.add(dadosOpcoes);
            }

            conexao.close();
            stmt.close();
            rs.close();

        }catch (Exception e){
            System.out.println(e);
        }
        return opcoes;
    }

    Opcoes dadosOpcoes(){
        List<Opcoes> opcao = opcoes();
        ObservableList<String> equipes = FXCollections.observableArrayList();
        ObservableList<String> anos = FXCollections.observableArrayList();

        opcao.forEach(dados -> {
            String equipe = String.join(", ", dados.getEquipes());
            if (!equipes.contains(equipe)) {
                equipes.add(equipe);
            }
            anos.add(String.join(", ", dados.getAnos()));
        });

        return new Opcoes(equipes, anos);

    }

    public class Consulta{
        private String mes;
        private Double totalHoraExtra;
        private Double totalHoraSobreaviso;

        public Consulta(String mes, Double totalHoraExtra, Double totalHoraSobreaviso){
            this.mes = mes;
            this.totalHoraExtra = totalHoraExtra;
            this.totalHoraSobreaviso = totalHoraSobreaviso;
        }

        public String getMes(){ return mes; }

        public Double getExtra(){ return totalHoraExtra; }
        public Double getSobreaviso(){ return totalHoraSobreaviso; }

    }

    List<Consulta> consulta(String equipe, String ano){
        List<Consulta> consultas = new ArrayList<>();
        String where = "";

        try {
            Connection conexao = DriverManager.getConnection(banco, usuario, senha);
            PreparedStatement stmtEquipe = conexao.prepareStatement("SELECT id_equipe FROM equipe WHERE nome_equipe = ?");
            stmtEquipe.setString(1, equipe);
            ResultSet rsEquipe = stmtEquipe.executeQuery();

            if (rsEquipe.next()){
                equipe = rsEquipe.getString("id_equipe");
            }

            stmtEquipe.close();
            rsEquipe.close();

            if (equipe != null && ano != null){
                where = "WHERE id_equipe = '" + equipe + "' AND TO_CHAR(data_hora_inicial, 'YYYY') = '" + ano + "'";

            } else if (equipe != null && ano == null){
                where = "WHERE id_equipe = '" + equipe + "'";
            } else if (equipe == null && ano != null){
                where = "WHERE TO_CHAR(data_hora_inicial, 'YYYY') = '" + ano + "'";
            }

            if (where != ""){
                PreparedStatement stmt = conexao.prepareStatement("SELECT TO_CHAR(data_hora_inicial, 'MM') AS mes, " +

                "SUM(CASE " +
                "WHEN tipo_hora = 'Hora extra' AND status = 'Aprovado' AND EXTRACT(HOUR FROM data_hora_final) > EXTRACT(HOUR FROM data_hora_inicial) THEN EXTRACT(HOUR FROM data_hora_final) - EXTRACT(HOUR FROM data_hora_inicial) " +
                "WHEN tipo_hora = 'Hora extra' AND status = 'Aprovado' AND EXTRACT(HOUR FROM data_hora_final) < EXTRACT(HOUR FROM data_hora_inicial) AND EXTRACT(HOUR FROM data_hora_inicial) = 23 AND EXTRACT(MINUTE FROM data_hora_inicial) = 0 AND EXTRACT(HOUR FROM data_hora_final) = 0 AND EXTRACT(MINUTE FROM data_hora_final) = 0 THEN EXTRACT(HOUR FROM data_hora_final) + (24 - EXTRACT(HOUR FROM data_hora_inicial)) " +
                "WHEN tipo_hora = 'Hora extra' AND status = 'Aprovado' AND EXTRACT(HOUR FROM data_hora_final) < EXTRACT(HOUR FROM data_hora_inicial) AND EXTRACT(HOUR FROM data_hora_inicial) = 23 AND EXTRACT(HOUR FROM data_hora_final) = 0 AND EXTRACT(MINUTE FROM data_hora_final) >= EXTRACT(MINUTE FROM data_hora_inicial) THEN EXTRACT(HOUR FROM data_hora_final) + (24 - EXTRACT(HOUR FROM data_hora_inicial)) ELSE 0 END) AS total_horasextra, " +

                "SUM(CASE " +
                "WHEN tipo_hora = 'Hora extra' AND status = 'Aprovado' AND EXTRACT(MINUTE FROM data_hora_inicial) < EXTRACT(MINUTE FROM data_hora_final) THEN (EXTRACT(MINUTE FROM data_hora_final) - EXTRACT(MINUTE FROM data_hora_inicial)) " +
                "WHEN tipo_hora = 'Hora extra' AND status = 'Aprovado' AND EXTRACT(MINUTE FROM data_hora_inicial) > EXTRACT(MINUTE FROM data_hora_final) THEN (EXTRACT(MINUTE FROM data_hora_inicial) - EXTRACT(MINUTE FROM data_hora_final))ELSE 0 END) AS total_minutosextra, " +

                "SUM(CASE " +
                "WHEN tipo_hora = 'Sobreaviso' AND status = 'Aprovado' AND EXTRACT(HOUR FROM data_hora_final) > EXTRACT(HOUR FROM data_hora_inicial) THEN EXTRACT(HOUR FROM data_hora_final) - EXTRACT(HOUR FROM data_hora_inicial) " +
                "WHEN tipo_hora = 'Sobreaviso' AND status = 'Aprovado' AND EXTRACT(HOUR FROM data_hora_final) < EXTRACT(HOUR FROM data_hora_inicial) AND EXTRACT(HOUR FROM data_hora_inicial) = 23 AND EXTRACT(MINUTE FROM data_hora_inicial) = 0 AND EXTRACT(HOUR FROM data_hora_final) = 0 AND EXTRACT(MINUTE FROM data_hora_final) = 0 THEN EXTRACT(HOUR FROM data_hora_final) + (24 - EXTRACT(HOUR FROM data_hora_inicial)) " +
                "WHEN tipo_hora = 'Sobreaviso' AND status = 'Aprovado' AND EXTRACT(HOUR FROM data_hora_final) < EXTRACT(HOUR FROM data_hora_inicial) AND EXTRACT(HOUR FROM data_hora_inicial) = 23 AND EXTRACT(HOUR FROM data_hora_final) = 0 AND EXTRACT(MINUTE FROM data_hora_final) >= EXTRACT(MINUTE FROM data_hora_inicial) THEN EXTRACT(HOUR FROM data_hora_final) + (24 - EXTRACT(HOUR FROM data_hora_inicial)) ELSE 0 END) AS total_horassobreaviso, " +

                "SUM(CASE "  +
                "WHEN tipo_hora = 'Sobreaviso' AND status = 'Aprovado' AND EXTRACT(MINUTE FROM data_hora_inicial) < EXTRACT(MINUTE FROM data_hora_final) THEN (EXTRACT(MINUTE FROM data_hora_final) - EXTRACT(MINUTE FROM data_hora_inicial)) " +
                "WHEN tipo_hora = 'Sobreaviso' AND status = 'Aprovado' AND EXTRACT(MINUTE FROM data_hora_inicial) > EXTRACT(MINUTE FROM data_hora_final) THEN (EXTRACT(MINUTE FROM data_hora_inicial) - EXTRACT(MINUTE FROM data_hora_final)) ELSE 0 END) AS total_minutossobreaviso " +

                "FROM hora " + where + " GROUP BY mes ORDER BY mes");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Double extraHoras = rs.getDouble("total_horasextra");
                    Double extraMinutos = rs.getDouble("total_minutosextra");

                    while (extraMinutos >= 60){
                        extraMinutos -= 60;
                        extraHoras += 1;
                    }

                    Double sobreavisoHoras = rs.getDouble("total_horassobreaviso");
                    Double sobreavisoMinutos = rs.getDouble("total_minutossobreaviso");

                    while (sobreavisoMinutos >= 60){
                        sobreavisoMinutos -= 60;
                        sobreavisoHoras += 1;
                    }

                    Double totalHoraExtra = extraHoras + extraMinutos / 100;
                    Double totalSobreaviso = sobreavisoHoras + sobreavisoMinutos / 100;

                    String mes = null;

                    switch (rs.getInt("mes")){
                        case 1: mes = "Janeiro"; break;
                        case 2: mes = "Fevereiro"; break;
                        case 3: mes = "Março"; break;
                        case 4: mes = "Abril"; break;
                        case 5: mes = "Maio"; break;
                        case 6: mes = "Junho"; break;
                        case 7: mes = "Julho"; break;
                        case 8: mes = "Agosto"; break;
                        case 9: mes = "Setembro"; break;
                        case 10: mes = "Outubro"; break;
                        case 11: mes = "Novembro"; break;
                        case 12: mes = "Dezembro"; break;
                    }

                    Consulta consulta = new Consulta(mes, totalHoraExtra, totalSobreaviso);
                    consultas.add(consulta);
                }

                conexao.close();
                stmt.close();
                rs.close();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return consultas;
    }

    public class DadosGrafico{
        XYChart.Series<String, Double> dadosExtra = new XYChart.Series<>();
        XYChart.Series<String, Double> dadosSobreaviso = new XYChart.Series<>();

        public DadosGrafico(XYChart.Series<String, Double> dadosExtra, XYChart.Series<String, Double> dadosSobreaviso){
            this.dadosExtra = dadosExtra;
            this.dadosSobreaviso = dadosSobreaviso;
        }

        public XYChart.Series<String, Double> getDadosExtra(){ return dadosExtra; }
        public XYChart.Series<String, Double> getDadosSobreaviso(){ return dadosSobreaviso; }

    }

    DadosGrafico consultaDados(String equipe, String ano) {
        List<Consulta> horas = consulta(equipe, ano);
        XYChart.Series<String, Double> dadosExtra = new XYChart.Series<>();
        XYChart.Series<String, Double> dadosSobreaviso = new XYChart.Series<>();

        horas.forEach(dados -> {
            dadosExtra.getData().add(new XYChart.Data<>(dados.getMes(), dados.getExtra()));
            dadosSobreaviso.getData().add(new XYChart.Data<>(dados.getMes(), dados.getSobreaviso()));
        });

        return new DadosGrafico(dadosExtra, dadosSobreaviso);
    }
}
