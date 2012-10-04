/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.infra;

import java.io.File;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Marco A Soares - O8W
 * Esta classe serve para encapsular a implementacao do datasource que vai ser
 * retornado para o DAO.
 * Serve para pegar o DataSource via contexto web ou local para testes via metodo
 * main.
 */
public class DataSourceFactory {

    private static final Logger LOG = Logger.getLogger(DataSourceFactory.class);
    private static DataSource dataSource = null;
    static File aux = new File("temp");
    //ksp21032012 - Permitir testes no componente sem precisar iniciar o tomcat.
    // Habilitar procura de arquivo de configuração do Spring onde estão definidas propriedades da conexão.
    // Procura realizada apenas quando a execução inicia de um método main no projeto de componentes.
    private static final String fileName = aux.getAbsolutePath().
            substring(0, aux.getAbsolutePath().
            indexOf(aux.getPath())) + "test" +
            File.separator + "applicationContextDesenv.xml";

    public static DataSource getDataSource() {
        if (null == dataSource) {
            String dsName = "dataSourceLSF";
            if (null == BeanRetriever.getBean(dsName)) {
//                throw new DAOConfigException();
                //ksp21032012 - Permitir testes no componente sem precisar iniciar o tomcat.
                // Habilitar procura de arquivo de configuração do Spring onde estão definidas propriedades da conexão.
                // Procura realizada apenas quando a execução inicia de um método main no projeto de componentes.
                LOG.debug("Pegando o DS pelo arquivo ");
                String[] files = {fileName};
                File myFile = new File(fileName);
                if (!myFile.isFile()) {
                    throw new DAOConfigException(fileName);
                }
                ApplicationContext ac = new FileSystemXmlApplicationContext(files);
                dataSource = (DataSource) ac.getBean(dsName);
            } else {
                LOG.debug("Pegando o DS do contexto WEB...");
                dataSource = (DataSource) BeanRetriever.getBean(dsName);
            }
        }
        return dataSource;
    }
}

/**
 * Exception criada somente para uso dessa classe, caso alguém se esqueça de
 * colocar o arquivo de configuração no local certo!
 * @author Marco
 */
class DAOConfigException extends RuntimeException {

    public DAOConfigException(String fileName) {
        super("Voce deve colocar o arquivo applicatioContext.xml no local certo: " + fileName);
    }
    public DAOConfigException() {
        super("Nao foi possível encontrar o arquivo de configuracao : applicatioContext.xml");
    }
}
