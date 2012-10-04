/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.infra;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Classe para recuperar os beans definidos nos arquivos xml do sistema.
 * Implementa a interface ApplicationContextAware que chamada via call back
 * pelo container para setar o ApllicationContext que dá acesso aos Beans.
 *
 * Após o container ser carregado ele instancia um objeto da classe e atribui
 * o ApplicationContext.
 *
 * @author Marco Aurélio 08W Consultoria
 * @version 1.1
 */
public final class BeanRetriever implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Creates a new BeanRetriever object.
     */
    public BeanRetriever() {
    }

    /**
     * Método da interface que atribui o ApplicationContext.
     *
     * @param appContext - ApplicationContext carregado pelo container.
     */
    @Override
    public void setApplicationContext(ApplicationContext appContext) {
        BeanRetriever.applicationContext = appContext;
    }

    /**
     * Método para recuperar o bean definido nos arquivos xml do sistema.
     *
     * @param beanName - nome do bean definido no atributo id do xml element.
     *
     * @return Object - instancia do bean definido no xml. Necessita cast.
     */
    public static Object getBean(String beanName) throws
            NoSuchBeanDefinitionException {
        Object obj = null;
        if (null != applicationContext) {
            obj = applicationContext.getBean(beanName);
        }
        return obj;
    }
}

