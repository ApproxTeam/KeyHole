/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.approxteam.keyholeplugin;

import com.google.common.reflect.TypeToken;
import io.github.approxteam.keyholeplugin.application.Application;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.NamingException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;



/**
 *
 * @author razikus
 */
@Plugin(id = "keyholeplugin", 
        name = "KeyHole", 
        version = "1.0", 
        description = "Authentication plugin")
public class KeyHoleMain {
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Logger logger;
    
    @Listener
    public void onSpongePreInitialization(GamePreInitializationEvent event) {
        try {
            ConfigurationNode node = configManager.load();
            if(node.getNode("namingContext", "version").getValue() == null) {
                ConfigurationNode n = node.getNode("namingContext");
                n.getNode("version").setValue(1);
                n.getNode("jboss.naming.client.ejb.context").setValue(true);
                n.getNode("java.naming.factory.initial").setValue("org.jboss.naming.remote.client.InitialContextFactory");
                n.getNode("java.naming.provider.url").setValue("http-remoting://localhost:8080");
                configManager.save(node);
            }
            
            Application.preInitialization(configManager.load().getNode("namingContext"));
        } catch (NamingException ex) {
            Logger.getLogger(KeyHoleMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.ALL, "Can't get config");

            System.exit(1);
        }
    }
    
    @Listener
    public void onSpongeInitialization(GameInitializationEvent event) {
        Application.registerCommands(this);
    }
}
