/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.approxteam.keyholeplugin.application;

import io.github.approxteam.exception.KeyException;
import io.github.approxteam.keybean.ejb.KeyBeanRemote;
import io.github.approxteam.keyholeplugin.KeyHoleMain;
import io.github.approxteam.keyholeplugin.application.command.RegisterCommand;
import io.github.approxteam.keyholeplugin.context.ContextUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

/**
 *
 * @author razikus
 */
public class Application {
    private static KeyBeanRemote kbr;
    private static InitialContext context;
    
    public static void preInitialization(ConfigurationNode config) throws NamingException {
        System.out.println(config);
        Boolean naming = config.getNode("jboss.naming.client.ejb.context").getBoolean();
        String factory = config.getNode("java.naming.factory.initial").getString();
        String provider = config.getNode("java.naming.provider.url").getString();
        
        context = ContextUtils.initCtx(factory, provider, naming);
        kbr = ContextUtils.getKeyBeanRemote(context);
        try {
            System.out.println(kbr.getUserByID(1));
        } catch (KeyException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static KeyBeanRemote getKbr() {
        return kbr;
    }

    public static InitialContext getContext() {
        return context;
    }

    public static void registerCommands(KeyHoleMain main) {
        CommandSpec registerPlayer = CommandSpec.builder()
            .description(Text.of("Register command"))
            .permission("keyhole.register")
            .arguments(
                    GenericArguments.string(Text.of("password")),
                    GenericArguments.string(Text.of("repeatPassword")),
                    GenericArguments.remainingRawJoinedStrings(Text.of("email"))
            )
            .executor(new RegisterCommand())
            .build();
        
        CommandSpec registerConsole = CommandSpec.builder()
            .description(Text.of("Register command"))
            .permission("keyhole.admin.register")
            .arguments(
                    GenericArguments.string(Text.of("username")),
                    GenericArguments.string(Text.of("password")),
                    GenericArguments.string(Text.of("repeatPassword")),
                    GenericArguments.remainingRawJoinedStrings(Text.of("email"))
            )
            .executor(new RegisterCommand())
            .build();
        Sponge.getCommandManager().register(main, registerPlayer, "register");
        Sponge.getCommandManager().register(main, registerConsole, "adminregister");
    }
    
    
    
}
