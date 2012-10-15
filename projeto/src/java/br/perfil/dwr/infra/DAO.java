/*
 * DAO.java
 *
 * Created on 28 de Fevereiro de 2007, 10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.perfil.dwr.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author collares
 */
public class DAO {

    private final static Logger LOG = Logger.getLogger(DAO.class);
    protected Connection con = null;
    transient protected boolean conexaoExterna = false;
    private static int contador;

    /**
     * Constroi a superclasse DAO.
     * @author	    Rogério Colares
     * @version	    1.0
     **/
    public DAO() {
        this.conexaoExterna = false;
        this.conectar();
    }

    /**
     * Constroi o objeto DAO fornecendo uma conexão externa.
     * @param	    con objeto Connection externo
     * @author	    Rogério Colares
     * @version	    1.0
     **/
    public DAO(final Connection con) {
        this.conexaoExterna = true;
        if (con != null) {
            this.con = con;
        } else {
            this.conectar();
            this.conexaoExterna = false;
        }
    }

    /*
    public DAO(final Connection con, String usuario) {
        this.conexaoExterna = true;
        if (con != null) {
            this.con = con;
            String sql = "dbms_application_info.set_client_info('" + usuario + "')";
            Statement stat = this.getCon().createStatement();
            ResultSet result = stat.executeQuery(sql);

        } else {
            this.conectar();
            this.conexaoExterna = false;
            String sql = "dbms_application_info.set_client_info('" + usuario + "')";
            Statement stat1 = this.getCon().createStatement();
            ResultSet result = stat1.executeQuery(sql);

        }
    }
     * /


     
     * /**
     * Conecta usando o DBCP (Database Connection Pool).
     * Se não conseguir, utliza a conexão (Connection) convencional.
     * Se a superclasse DAO foi criada usando uma conexão externa, simplesmente ignora a chamada e retorna sem executar.
     * @author Rogério
     * @since 2007-Novembro
     **/
    public final Connection conectar() {
        try {
            if (this.conexaoExterna) {
                return this.con;
            }
            if (this.con != null && !this.con.isClosed()) {
                return this.con;
            }

            this.setCon(this.conectarLocal());

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return this.getCon();
    }

    /**
     * Conecta localmente, sem usar o DBCP.
     * @author	    Rogério Colares
     * @return	    Connection
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    private Connection conectarLocal() {

        //Saluti09102009 Conexão obtida via DataSource, gerenciada pelo framework SpringMVC
        final DataSource dataSource = DataSourceFactory.getDataSource();
        try {
            if (null == this.con) {
                this.con = dataSource.getConnection();
                //LOG.info("Conexão a obtida: " + con);
                contador++;
                //LOG.debug("abrindo con ->>> " + this.getClass().getName() + " -> " + this.con.hashCode() + " abertas = " + contador);
            }
        } catch (Exception e) {
            LOG.error("##### DAO.conectarLocal: Erro no DAO.conectarLocal() #####");
            LOG.error(e.getMessage(), e);
        }
        return getCon();
    }

    /**
     * Desconecta do banco.
     * Se a superclasse DAO foi criada usando uma conexão externa, simplesmente ignora a chamada e retorna sem desconectar.
     * @author	    Rogério Colares
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    public void desconectar() {
        try {
            if (this.conexaoExterna) {
                return;
            }
            if (null == this.con) {
                LOG.info("tentando fechar conexao nula " + this.getClass().getName());
            } else {
                //LOG.debug("fechando con <<<- " + this.getClass().getName() + " -> " + this.con.hashCode() + " abertas = " + --contador);
                if (!this.getCon().isClosed()) {
                    //LOG.info("Conexão a Liberar: " + con);
                    this.getCon().close();
                }
                this.con = null;
            }
        } catch (Exception e) {
            LOG.error("##### Erro no DAO.desconectar() #####");
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Grava o erro na tabela "Erro".
     * @author	    Fábio Messias
     * @param	    usuario String
     * @param	    sessao String
     * @param	    msg String
     * @since	    2007-Janeiro
     * @version	    1.0
     **/
    public void gravaErro(final String usuario, final String sessao, final String msg) {

        if (null != this.con) {
            final Date hoje = new Date();
            final SimpleDateFormat data = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            final SimpleDateFormat hora = new SimpleDateFormat("HHmmss", Locale.getDefault());
            final String data1 = data.format(hoje);
            final String hora1 = "1" + hora.format(hoje);

            final String sqlErro = "INSERT INTO \"Erro\" (\"Erro_data\", \"Erro_hora\", \"Erro_usuario\", \"Erro_sessao\", \"Erro_erro\") "
                    + "VALUES (?,?,?,?,?)";
            final PreparedStatement prs;
            try {
                prs = this.getCon().prepareStatement(sqlErro);
                prs.setString(1, data1);
                prs.setString(2, hora1);
                prs.setString(3, usuario);
                prs.setString(4, sessao);
                prs.setString(5, msg);
                prs.executeUpdate();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage(), ex);
            } finally {
                this.desconectar();
            }
        }
    }

    /**
     * Retorna liberacao do lock ou nao.
     * @return	    String vazia ou com o usuário que está com o lock
     * @author	    Fabio Messias
     * @since	    22/09/2009
     * @version	    1.0
     **/
    public String lock(final String tabela, final String regLock, final String sessao, final String usuario) {

        String result = "Registro sendo usado por : ";
        try {

            final Date hoje = new Date();
            final SimpleDateFormat data = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            final SimpleDateFormat hora = new SimpleDateFormat("HHmmss", Locale.getDefault());
            final String dataHora = data.format(hoje) + hora.format(hoje);

            //Procura se o lock já existe
            final PreparedStatement prs;
            final PreparedStatement prs1;
            //final PreparedStatement prs2;
            final StringBuffer stringBufferSql = new StringBuffer(79);
            stringBufferSql.append("SELECT  * FROM \"Lock\" WHERE \"Lock_tabela\" = ? AND \"Lock_registro\" LIKE ? ");

            final String sql = stringBufferSql.toString();

            prs = con.prepareStatement(sql);
            prs.setString(1, tabela);
            //ps.setString(2,regLock);
            prs.setString(2, regLock + "%");
            final ResultSet res = prs.executeQuery();

            //Cria o lock na tabela
            if (!res.next()) {
                final StringBuffer sBufferSqlLock = new StringBuffer(50);
                sBufferSqlLock.append("INSERT INTO \"Lock\" (\"Lock_tabela\", \"Lock_registro\", \"Lock_lock\", \"Lock_usuario\", \"Lock_dataHora\") ");
                sBufferSqlLock.append("VALUES(?,?,?,?,?)");
                final String sqlLock = sBufferSqlLock.toString();
                prs1 = con.prepareStatement(sqlLock);
                prs1.setString(1, tabela);
                prs1.setString(2, regLock);
                prs1.setString(3, sessao);
                prs1.setString(4, usuario);
                prs1.setString(5, dataHora);
                prs1.executeUpdate();
                result = "";
            } else {
                result += res.getString("Lock_usuario");
            }
            res.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        } finally {
            this.desconectar();
        }
        return result;
    }

    /**
     * Libera lock
     * @author	    Fabio Messias
     * @since	    22/09/2009
     * @version	    1.0
     **/
    public void liberaLock(final String tabela, final String regLock, String sessao) {

        //this.conectar();
        try {
            final String[] sessaoSplit = sessao.split("\\^");

            String sqlDelete = null;
            PreparedStatement ps1;
            if (regLock.equals("ALL")) {
                sessao = sessaoSplit[0];
                sqlDelete = "DELETE FROM \"Lock\" WHERE \"Lock_lock\" LIKE ? ";
                ps1 = con.prepareStatement(sqlDelete);
                /*23092009 - Libera todos os locks da sessao
                ps1.setString(1, tabela);*/

                ps1.setString(1, sessao + "%");
            } else {
                sqlDelete = "DELETE FROM \"Lock\" WHERE \"Lock_tabela\" =? AND \"Lock_registro\" LIKE ? AND \"Lock_lock\" =? ";
                ps1 = con.prepareStatement(sqlDelete);
                ps1.setString(1, tabela);
                ps1.setString(2, regLock + "%");
                ps1.setString(3, sessao);
            }
            ps1.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error(e.getMessage(), e);
            gravaErro("", "liberaLock_AgendaDAO", e.getMessage());
        } finally {
            this.desconectar();
        }
    }

    public static void main(final String[] args) {
        final DAO dao = new DAO();
        LOG.debug("OK: " + dao.conectar());
        dao.desconectar();
    }

    /**
     * Retorna a conexão corrente.
     * @return	    Connection
     * @author	    Rogério Colares
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    public Connection getCon() {

        return this.con;
    }

    /**
     * Atribui uma conexão à superclasse DAO.
     * @author	    Rogério Colares
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    public final void setCon(final Connection con) {
        this.con = con;
    }

    /**
     * Inicia uma transação.
     * @author	    Rogério Colares
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    public void beginTransaction() throws SQLException {
        if (this.con != null) {
            this.con.setAutoCommit(false);
        }
    }

    /**
     * Finaliza uma transação salvando todas as informações.
     * @author	    Rogério Colares
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    public void commitTransaction() throws SQLException {
        if (this.con != null && !this.con.getAutoCommit()) {
            this.con.commit();
        }
    }

    /**
     * Finaliza uma transação retornando todas as informações ao seu estado inicial.
     * @author	    Rogério Colares
     * @since	    2007-Novembro
     * @version	    1.0
     **/
    public void rollbackTransaction() throws SQLException {
        if (this.con != null && !this.con.getAutoCommit()) {
            this.con.rollback();
        }
    }

    /**
     * Corrigige os precicados de uma query
     * em casos que o programador não sabe quantos serão
     * os predicados de consulta pode-se construir uma query assim: <BR>
     * SELECT * FROM tabela WHERE condicao1 WHERE condicao 2 WHERE condicao3
     * que este método o corrigirá para
     * SELECT * FROM tabela WHERE condicao1 AND condicao 2 AND condicao3 <BR>
     * OBS: para que o método funcione os comandos SQL deverão estar em letra
     * Maiúscula
     * @param sql o sql que se deseja consertar
     * @return o novo sql corrigido
     * @since 22/02/2010
     */
    public static String consertaPredicado(String sql) {
        String novoSql = "";
        String[] array = sql.split("WHERE");
        if (array.length > 2) {
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    novoSql = array[i] + "WHERE";
                } else {
                    if ((array.length - i) != 1) {
                        novoSql += array[i] + "AND";
                    } else {
                        novoSql += array[i];
                    }
                }
            }
        } else {
            novoSql = sql;
        }
        return novoSql;
    }
}
