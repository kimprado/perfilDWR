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
     * executa uma função no cliente chamando ScriptSession.addFunctionCall, que,
     * por sua vez, adiciona uma chamada de função no cliente, esta função pode
     * ser construída dinâmicamente.
     *
     * o método ScriptSessions.addFunctionCall recebe uma função javascript
     * como parâmetro, esta função será executada via ajax reverso no clientea
     */
    public void run() {
        ScriptSessions.addFunctionCall("(function(){try{parent.exibeMensagemRAjax('" + mensagem.replaceAll("\n", "<BR>") + "')}catch(e){}})");//chamando uma função javascript, caso ela não exista, o erro é suprimido
        //ScriptSessions.addFunctionCall("(function(){try{alert('teste');}catch(e){}})");//chamando uma função javascript, caso ela não exista, o erro é suprimido
        LOG.debug("Mensagem enviada via RAjax");
    }
}
