/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.approxteam.keyholeplugin.context;

import io.github.approxteam.keybean.ejb.KeyBeanRemote;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.naming.remote.client.InitialContextFactory;

/**
 *
 * @author razikus
 */
public class ContextUtils {
    InitialContextFactory dep;
    public static InitialContext initCtx(String factoryInitial, String provider, boolean namingContext) throws NamingException {
        InitialContext remoteCTX;
        Properties prop = new Properties();
        prop.put("jboss.naming.client.ejb.context", namingContext);
        prop.put("java.naming.factory.initial", factoryInitial);
        prop.put("java.naming.provider.url", provider);
        System.out.println(prop);
        
        remoteCTX = new InitialContext(prop);
        return remoteCTX;
    }
    
    
    public static KeyBeanRemote getKeyBeanRemote(InitialContext ctx) throws NamingException {
        
        return (KeyBeanRemote) ctx.lookup("KeyBean-1.0.1-SNAPSHOT/KeyBean!io.github.approxteam.keybean.ejb.KeyBeanRemote");
    }
}
