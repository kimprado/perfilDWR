/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.dao;

import br.perfil.dwr.modelo.Aluno;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author userp
 */
public class AlunoDAOTest {
    
    public AlunoDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of find method, of class AlunoDAO.
     */
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        Long id = 1L;
        AlunoDAO instance = new AlunoDAO();
        Aluno aluno = instance.find(id);
        assertNotNull("Não pode ser nula", aluno);
    }
    
    /**
     * Test of find method, of class AlunoDAO.
     */
    @Test
    public void testFindMultiplasChamadas() throws Exception {
        System.out.println("find");
        Long id = 1L;
        for (int i=0; i < 30; i++) {
            AlunoDAO instance = new AlunoDAO();
            Aluno aluno = instance.find(id);
            assertNotNull("Não pode ser nula", aluno);
        }
    }
}
