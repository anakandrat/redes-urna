package server;

import server.db.JdbcSQLiteConnection;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static server.db.JdbcSQLiteConnection.*;


public class Candidato implements Serializable {

    private static final long serialVersionUID = -1600846827341664608L;

    private String codigoVotacao;
    private String nomeCandidato;
    private String partido;
    private int numVotos;


    public Candidato() {

    }


    /**
     * @param codigoVotacao
     * @param nomeCandidato
     * @param partido
     * @param numVotos
     */
    public Candidato(String codigoVotacao, String nomeCandidato, String partido, int numVotos) {

        this.codigoVotacao = codigoVotacao;
        this.nomeCandidato = nomeCandidato;
        this.partido = partido;
        this.numVotos = numVotos;

    }


    /**
     * @return codigoVotacao
     */
    public String getCodigoVotacao() {

        return codigoVotacao;

    }


    /**
     * @param codigoVotacao
     */
    public void setCodigoVotacao(String codigoVotacao) {

        this.codigoVotacao = codigoVotacao;

    }


    /**
     * @return nomeCandidato
     */
    public String getNomeCandidato() {

        return nomeCandidato;

    }


    /**
     * @param nomeCandidato
     */
    public void setNomeCandidato(String nomeCandidato) {

        this.nomeCandidato = nomeCandidato;

    }


    /**
     * @return partido
     */
    public String getPartido() {

        return partido;

    }


    /**
     * @param partido
     */
    public void setPartido(String partido) {

        this.partido = partido;

    }


    /**
     * @return numVotos
     */
    public int getNumVotos() {

        return numVotos;

    }


    /**
     * @param numVotos
     */
    public void setNumVotos(int numVotos) {

        this.numVotos = numVotos;

    }


    /**
     * Obtém uma lista de todos os candidatos
     *
     * @return ArrayList
     * @throws SQLException
     */
    public static String getCandidatos() throws SQLException {


        JdbcSQLiteConnection sqLiteConnection = new JdbcSQLiteConnection();

        ResultSet resultSet = queryCandidatos();
        StringBuilder candidatos = new StringBuilder();
        while (resultSet.next()) {
            candidatos.append(resultSet.getString(CODIGO_VOTACAO))
                    .append(" ")
                    .append(resultSet.getString(NOME_CANDIDATO))
                    .append("\n");
        }

        // Retornando candidatos se houver ao menos uma tupla, senão, null
        return candidatos.length() > 0 ? candidatos.toString() : null;
    }


    /**
     * Checa se o código de votação é válido
     * @param codigoVotacao
     * @return
     * @throws SQLException
     */
    private static Boolean votoValido(int codigoVotacao) throws SQLException {
        return findCandidato(codigoVotacao);
    }


    /**
     * Computa múltiplos votos dada uma lista de votos
     *
     * @param listCodigoVotacao
     * @return Boolean
     * @throws SQLException
     */
    public Boolean computarMultiplosVotos(List<Integer> listCodigoVotacao) throws SQLException {

        // Removendo votos que possuam códigos de votação inválidos
        Iterator<Integer> i = listCodigoVotacao.iterator();
        while (i.hasNext()) {
            Integer integer = i.next();
            if (!votoValido(integer)) {
                i.remove();
            }
        }

        return updateCandidatos(listCodigoVotacao);

    }


    /**
     * Computa um voto para um candidato específico
     *
     * @param codigoVotacao
     * @return Boolean
     * @throws SQLException
     */
    public Boolean computarVoto(int codigoVotacao) throws SQLException {

        return updateCandidato(codigoVotacao);

    }


    /**
     * Retorna as parciais dos votos
     * @return
     * @throws SQLException
     */
    public String getParciais() throws SQLException {
        JdbcSQLiteConnection sqLiteConnection = new JdbcSQLiteConnection();

        ResultSet resultSet = queryCandidatos();
        StringBuilder candidatos = new StringBuilder();
        while (resultSet.next()) {
            candidatos.append(resultSet.getString(CODIGO_VOTACAO))
                    .append(" ")
                    .append(resultSet.getString(NOME_CANDIDATO))
                    .append(" | ")
                    .append(String.valueOf(resultSet.getInt(NUM_VOTOS)))
                    .append(" votos")
                    .append("\n");
        }
        return candidatos.toString();
    }


    @Override
    public String toString() {

        return "Número do candidato: " + codigoVotacao + "\n" +
                "Nome: " + nomeCandidato + "\n" +
                "Partido: " + partido + "\n" +
                "Votos: " + numVotos + "\n";

    }

}