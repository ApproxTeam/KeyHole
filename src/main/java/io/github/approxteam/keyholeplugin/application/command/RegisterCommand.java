/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.approxteam.keyholeplugin.application.command;

import io.github.approxteam.dto.PlayerDTO;
import io.github.approxteam.exception.KeyException;
import io.github.approxteam.keyholeplugin.application.Application;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author razikus
 */
public class RegisterCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!args.<String>getOne("password").isPresent()) {
            throw new CommandException(Text.of("NO PASSWORD"));
        }
        if(!args.<String>getOne("repeatPassword").isPresent()) {
            throw new CommandException(Text.of("NO REPEATPASSWORD"));
        }
        if(!args.<String>getOne("email").isPresent()) {
            throw new CommandException(Text.of("NO EMAIL"));
        }
        String password = args.<String>getOne("password").get();
        String repeatPassword = args.<String>getOne("password").get();
        String email = args.<String>getOne("email").get();
        
        if(!password.equals(repeatPassword)) {
            throw new CommandException(Text.of("BAD REPEAT"));
        }
        
        if(src instanceof Player) {
            Player player = (Player) src;
            String nickname = player.getName();
            PlayerDTO newPlayer = new PlayerDTO();
            newPlayer.setName(nickname);
            newPlayer.setEncodedPassword(repeatPassword);
            newPlayer.setEmail(email);
            try {
                Application.getKbr().addUser(newPlayer);
            } catch (KeyException ex) {
                throw new CommandException(Text.of(ex.getName()));
            }
            return CommandResult.success();
        }
        else if(src instanceof ConsoleSource) {
            if(!args.<String>getOne("username").isPresent()) {
                throw new CommandException(Text.of("NO USERNAME"));
            }
            String nickname = args.<String>getOne("username").get();
            PlayerDTO newPlayer = new PlayerDTO();
            newPlayer.setName(nickname);
            newPlayer.setEncodedPassword(repeatPassword);
            newPlayer.setEmail(email);
            try {
                Application.getKbr().addUser(newPlayer);
            } catch (KeyException ex) {
                throw new CommandException(Text.of(ex.getName()));
            }
            return CommandResult.success();
            
        }
        
        throw new CommandException(Text.of("SRC NOT INSTANCEOF"));
    }
    
}
