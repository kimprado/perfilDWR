/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.perfil.dwr.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.extend.ScriptSessionManager;

/**
 * Servlet criado com o único propósito de adicionar
 * um listener de SessãoDWR, para que quando uma sessãoDWR
 * for criada, atributos possam ser adicionados a ela. Estes
 * atributos, serão, geralmente, utilizados no AjaxReverso.
 * @author Saluti
 */
public class ConfiguraRAjax extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ConfiguraRAjax.class);
    @Override
    public void init() throws ServletException {
        Container container = ServerContextFactory.get().getContainer();//conteiner de aplicações
            ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
            OuvinteSessaoDWR ouv = new OuvinteSessaoDWR();
            manager.addScriptSessionListener(ouv);

            LOG.debug("Listener DWR iniciado no startup e configurado corretamente");
    }
}
