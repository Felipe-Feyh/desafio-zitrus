package org.healthcare.dao;

import liquibase.exception.LiquibaseException;
import org.healthcare.config.ConfigLoader;
import org.healthcare.config.LiquibaseManager;

import java.sql.*;

public class DAO {
    private static final ConfigLoader config = new ConfigLoader();
    private static final String JDBC_URL = config.getProperty("jdbc.url");
    private static final String JDBC_USER = config.getProperty("jdbc.user");
    private static final String JDBC_PASS = config.getProperty("jdbc.password");
    private static final LiquibaseManager liquibaseManager = new LiquibaseManager(config);

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            liquibaseManager.executarLiquibase(con);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver H2 não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (LiquibaseException e) {
            System.err.println("Erro ao executar Liquibase: " + e.getMessage());
        }
        return con;
    }

    public String autorizarProcedimento(JavaBeans parametros) {
        String query = "SELECT permitido FROM procedimento "
                + "WHERE cdprocedimento = ? AND nridade = ? AND sexo = ?";
        String resultado = "Procedimento não autorizado";

        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, parametros.getCdProcedimento());
            pst.setInt(2, parametros.getNrIdade());
            pst.setString(3, parametros.getSexo().getValue());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int permitido = rs.getInt("permitido");
                resultado = permitido == 1 ? "Procedimento autorizado" : "Procedimento não autorizado";
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultado = "Erro ao verificar procedimento";
        }

        return resultado;
    }

    public boolean verificarProcedimentoExistente(int cdProcedimento) {
        String query = "SELECT COUNT(*) FROM procedimento WHERE cdprocedimento = ?";
        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, cdProcedimento);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
