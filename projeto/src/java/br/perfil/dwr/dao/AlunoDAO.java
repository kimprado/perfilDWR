package br.perfil.dwr.dao;

import br.perfil.dwr.infra.DAO;
import br.perfil.dwr.modelo.Aluno;
import java.sql.Connection;
import org.apache.log4j.Logger;

/**
 * 
 * @author Kim Prado
 */
public class AlunoDAO extends DAO {
    
    private static final Logger LOG = Logger.getLogger(AlunoDAO.class);
    
    public AlunoDAO() {
        super();
        //LOG.info("Construtor padrão AlunoDAO. Conection: " + con);
        LOG.info("Construtor: " + con);
    }

    public AlunoDAO(Connection con) {
        super(con);
    }
    
    public Aluno find(String id) throws Exception {
        Aluno aluno = null;
        try {
            conectar();
            aluno = new Aluno();
            aluno.setNome("José");
            aluno.setTelefone("3135-4923");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            desconectar();
        }
        return aluno;
    }
}
