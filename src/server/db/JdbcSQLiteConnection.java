package server.db;

import java.sql.*;
import java.util.List;


/**
 * Created by anakandrat on 6/27/16.
 */
public class JdbcSQLiteConnection {
    public static int CODIGO_VOTACAO = 1;
    public static int NOME_CANDIDATO = 2;
    public static int PARTIDO = 3;
    public static int NUM_VOTOS = 4;


    /**
     * Conecta à base de dados
     *
     * @return
     * @throws SQLException
     */
    private static Connection connect() throws SQLException {
        // parâmetros do banco de dados
        String url = "jdbc:sqlite:eleicao.db";

        return DriverManager.getConnection(url);
    }


    /**
     * Cria a tabela candidatos caso não exista
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {

        Connection conn = connect();

        String sql;
        Statement statement = conn.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS candidatos (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	codigo_votacao integer NOT NULL,\n"
                + "	nome_candidato text NOT NULL,\n"
                + " partido text NOT NULL,\n"
                + " num_votos int DEFAULT 0);\n";
        statement.execute(sql);
        statement.close();
        insertCandidatos();
    }


    /**
     * Insere candidatos na tabela
     * @throws SQLException
     */
    private static void insertCandidatos() throws SQLException {
        Connection conn = connect();
        Statement statement = conn.createStatement();

        String sql = "INSERT OR IGNORE INTO candidatos\n"
                + " (id, codigo_votacao, nome_candidato, partido, num_votos)\n"
                + " VALUES (1, 213, 'Carroll Morgan', 'BRACKETS', 0);\n";
        statement.execute(sql);
        sql = "INSERT OR IGNORE INTO candidatos\n"
                + " (id, codigo_votacao, nome_candidato, partido, num_votos)\n"
                + " VALUES (2, 445, 'Ada Lovelace', 'TURIN', 0);\n";
        statement.execute(sql);
        sql = "INSERT OR IGNORE INTO candidatos\n"
                + " (id, codigo_votacao, nome_candidato, partido, num_votos)\n"
                + " VALUES (3, 654, 'Lorem Ipsum', 'LRP', 0);\n";
        statement.execute(sql);

        statement.close();
    }


    /**
     * Busca todos os candidatos no banco de dados
     *
     * @return ResultSet
     * @throws SQLException
     */
    public static ResultSet queryCandidatos() throws SQLException {

        Connection conn = connect();

        // SQL para obter todos os candidatos cadastrados
        String sql = "SELECT codigo_votacao, nome_candidato, partido, num_votos FROM candidatos ORDER BY num_votos DESC;";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        return preparedStatement.executeQuery();
    }


    /**
     * Localiza candidato no banco de dados dado um codigoVotacao
     *
     * @param codigoVotacao Código de votação do candidato
     * @return ResultSet
     * @throws SQLException
     */
    public static boolean findCandidato(int codigoVotacao) throws SQLException {
        Connection conn = connect();

        // SQL para obter o candidato com determinado codigoVotacao
        String sql = "SELECT codigo_votacao, nome_candidato, partido, num_votos " +
                "FROM candidatos WHERE codigo_votacao = " + codigoVotacao + ";";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet result = preparedStatement.executeQuery();
        boolean bool = result.next();

        result.close();
        return bool;

    }


    /**
     * Incrementa quantidade de votos dos candidatos no banco de dados
     *
     * @param listCodigoVotacao Lista de códigos de votação
     * @return Boolean
     * @throws SQLException
     */
    public static Boolean updateCandidatos(List<Integer> listCodigoVotacao) throws SQLException {
        Connection conn = connect();

        StringBuilder sql = new StringBuilder();

        for (Integer aListCodigoVotacao : listCodigoVotacao) {
            sql.append("UPDATE candidatos\n" + "SET num_votos = num_votos + 1\n" + "WHERE codigo_votacao = ")
                    .append(aListCodigoVotacao)
                    .append(";\n");
        }

        PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
        int count = preparedStatement.executeUpdate();

        return count > 0;
    }


    /**
     * Incrementa quantidade de votos de um candidato específico
     *
     * @param codigoVotacao Código de votação do candidato
     * @return Boolean
     * @throws SQLException
     */
    public static Boolean updateCandidato(int codigoVotacao) throws SQLException {
        Connection conn = connect();

        String sql = "UPDATE candidatos " +
                "SET num_votos = num_votos + 1 " +
                "WHERE codigo_votacao = " + codigoVotacao + ";";

        Statement statement = conn.createStatement();
        return statement.execute(sql);
    }

}