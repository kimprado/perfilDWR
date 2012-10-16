package br.perfil.dwr.dao;

import br.perfil.dwr.infra.DAO;
import br.perfil.dwr.modelo.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author Kim Prado
 */
public class AlunoDAO extends DAO {
    
    private static final Logger LOG = Logger.getLogger(AlunoDAO.class);
    private static final Random generator = new Random();
    
    public AlunoDAO() {
        super();
        try {
            //LOG.info("Construtor padrão AlunoDAO. Conection: " + con);
            //LOG.info("Construtor: " + con);
            int segundos = generator.nextInt(500);
            Thread.sleep(segundos);
        } catch (Exception ex) {
        }
    }

    public AlunoDAO(Connection con) {
        super(con);
    }
    
    public Aluno find(Long id) throws Exception {
        Aluno aluno = null;
        try {
            conectar();
            StringBuilder sql = new StringBuilder();
                sql.append("SELECT id, nome, telefone  ")
                .append("FROM aluno a ")
                .append("WHERE id = ? ");
            
            PreparedStatement stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getLong("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setTelefone(rs.getString("telefone"));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            desconectar();
        }
        return aluno;
    }
}
