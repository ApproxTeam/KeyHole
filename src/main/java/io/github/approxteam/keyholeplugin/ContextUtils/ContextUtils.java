/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.approxteam.keyholeplugin.ContextUtils;

import io.github.approxteam.keybean.ejb.KeyBeanRemote;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author razikus
 */
public class ContextUtils {
    private static InitialContext remoteCTX;
    
    public static void initCtx(String factoryInitial, String provider, String namingContext) {
        if(remoteCTX == null) {
            try {
                Properties prop = new Properties();
                prop.put("jboss.naming.client.ejb.context", namingContext);
                prop.put("java.naming.factory.initial", factoryInitial);
                prop.put("java.naming.provider.url", provider);
                remoteCTX = new InitialContext(prop);
            } catch (NamingException ex) {
                System.exit(1);
            }
        }
    }
    
    public static InitialContext getCtx() {
        
        return remoteCTX;
            
    }
    
    public static KeyBeanRemote getKeyBeanRemote() throws NamingException {
        
        return (KeyBeanRemote) getCtx().lookup("KeyBean-1.0-SNAPSHOT/KeyBean!io.github.approxteam.keybean.ejb.KeyBeanRemote");
    }
}
