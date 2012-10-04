/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.view;

import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

/**
 * Filtro para checar se a sessão DWR pertence a determinada
 * unidade de atendimento, este filtro deverá ser utilizado por
 * um método enviando um script para o servidor via Ajax Reverso
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
     * verifica se a unidade de atendimento está contida na sessão do dwr
     * @param session a sessão do dwr(ScriptSession)
     * @return false ou true, dependendo da unidade de atendimento da sessão
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
