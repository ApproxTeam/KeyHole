package io.github.approxteam.keyholeplugin.test;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.approxteam.dto.PlayerDTO;
import io.github.approxteam.exception.KeyException;
import io.github.approxteam.keybean.ejb.KeyBeanRemote;
import io.github.approxteam.keyholeplugin.context.ContextUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author razikus
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testContext() {
        InitialContext ctx = null;
        try {
            ctx = ContextUtils.initCtx(
                    "org.jboss.naming.remote.client.InitialContextFactory",
                    "http-remoting://localhost:8080",
                    true);
            KeyBeanRemote kbr = ContextUtils.getKeyBeanRemote(ctx);
            PlayerDTO p = new PlayerDTO();
            p.setName("Adam");
            p.setEncodedPassword("adam454");
            p.setEmail("eloelo@elo.pl");
            System.out.println(kbr.addUser(p));
        } catch (NamingException ex) {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyException ex) {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(ctx);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
