/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.perfil.dwr.view;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;

/**
 * Ouvinte desenvolvido especificamente para registrar a
 * unidade de atendimento que est� na HttpSession na sess�o
 * do dwr(ScriptSession)
 * @author Saluti05102009
 */
public class OuvinteSessaoDWR implements ScriptSessionListener{
    private static final Logger LOG = Logger.getLogger(OuvinteSessaoDWR.class);
    /**
     * Executado sempre que a sess�o do DWR for criada
     * @param arg0
     */
    public void sessionCreated(ScriptSessionEvent arg0) {
        arg0.getSession().setAttribute("uni", WebContextFactory.get().getSession().getAttribute("uni"));//copiando a unidade da HttpSession para a ScriptSession
        arg0.getSession().setAttribute("usu", WebContextFactory.get().getSession().getAttribute("usu"));//copiando o usu�rio da HttpSession para a ScriptSession
        LOG.debug("Sess�o criada, listener DWR funcionando");
    }

    public void sessionDestroyed(ScriptSessionEvent arg0) {
        
    }

}
