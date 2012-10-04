/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.perfil.dwr.view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


import org.apache.log4j.Logger;
import org.directwebremoting.ConversionException;
import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.extend.ObjectOutboundVariable;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.extend.Property;
import org.directwebremoting.extend.PropertyDescriptorProperty;
import org.directwebremoting.util.LocalUtil;

/**
 *
 * @author Saluti
 */
public class LsfBeanConverter extends BeanConverter {

    private static final Logger LOG = Logger.getLogger(LsfBeanConverter.class);

    @Override
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws ConversionException {
        // Where we collect out converted children
        Map<String, OutboundVariable> ovs = new TreeMap<String, OutboundVariable>();

        // We need to do this before collecting the children to save recursion
        ObjectOutboundVariable ov = new ObjectOutboundVariable(outctx, data.getClass(), getJavascript());
        outctx.put(data, ov);

        try {
            Map<String, Property> properties = getPropertyMapFromObject(data, true, false);
            HashSet<String> aRemover = new HashSet();
            for (Entry<String, Property> entry : properties.entrySet()) {
                String name = entry.getKey();
                Property property = entry.getValue();

                Object value = property.getValue(data);
                if (value != null) {
                    OutboundVariable nested = getConverterManager().convertOutbound(value, outctx);
                    ovs.put(name, nested);
                } else {
                    LOG.debug("Propriedade é nula, mapa a contém? " + properties.containsKey(name));
                    aRemover.add(name);
                }
            }
            
            for (String nomePropriedade : aRemover) {
                properties.remove(nomePropriedade);
            }
        } catch (ConversionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ConversionException(data.getClass(), ex);
        }

        ov.setChildren(ovs);

        return ov;
    }
}
