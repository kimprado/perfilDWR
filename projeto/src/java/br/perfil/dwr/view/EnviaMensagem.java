/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.view;

import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSessions;

/**
 * exibe uma mensagem no cliente
 * via ajax reverso
 * @author Saluti 05102009
 */
public class EnviaMensagem implements Runnable {
    private static final Logger LOG = Logger.getLogger(EnviaMensagem.class);
    private String mensagem;

    public EnviaMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    /*
     * executa uma fun��o no cliente chamando ScriptSession.addFunctionCall, que,
     * por sua vez, adiciona uma chamada de fun��o no cliente, esta fun��o pode
     * ser constru�da din�micamente.
     *
     * o m�todo ScriptSessions.addFunctionCall recebe uma fun��o javascript
     * como par�metro, esta fun��o ser� executada via ajax reverso no clientea
     */
    public void run() {
        ScriptSessions.addFunctionCall("(function(){try{parent.exibeMensagemRAjax('" + mensagem.replaceAll("\n", "<BR>") + "')}catch(e){}})");//chamando uma fun��o javascript, caso ela n�o exista, o erro � suprimido
        //ScriptSessions.addFunctionCall("(function(){try{alert('teste');}catch(e){}})");//chamando uma fun��o javascript, caso ela n�o exista, o erro � suprimido
        LOG.debug("Mensagem enviada via RAjax");
    }
}
