/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.view;

import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

/**
 * Filtro para checar se a sess�o DWR pertence a determinada
 * unidade de atendimento, este filtro dever� ser utilizado por
 * um m�todo enviando um script para o servidor via Ajax Reverso
 * @author Saluti05102009
 */
public class FiltroUnidade implements ScriptSessionFilter {
    private static final Logger LOG = Logger.getLogger(FiltroUnidade.class);
    private String unidade;

    public FiltroUnidade(String unidade) {
        if (unidade == null) {
            this.unidade = "";
        } else {
            this.unidade = unidade;
        }
    }
    /**
     * verifica se a unidade de atendimento est� contida na sess�o do dwr
     * @param session a sess�o do dwr(ScriptSession)
     * @return false ou true, dependendo da unidade de atendimento da sess�o
     */
    public boolean match(ScriptSession session) {
        boolean retorno = false;
        //System.out.println(session.getAttribute("uni"));
        if (session.getAttribute("uni") != null) {
            if (session.getAttribute("uni").equals(unidade)) {//verificando a unidade
                retorno = true;
                LOG.debug("Listener DWR, unidade de atendimento confere: " + unidade);
            } else {
                retorno = false;
            }

        }
        return retorno;
    }
}
