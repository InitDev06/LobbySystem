package org.alqj.coder.lobbysystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String la, String[] args) {
        if(args.length >= 1){
            if(cmd.getName().equalsIgnoreCase("lobby") && sender.hasPermission("lobbysystem.cmd.lobby")) {
                return Arrays.asList("set", "remove");
            } else if(cmd.getName().equalsIgnoreCase("lobbysystem") && sender.hasPermission("lobbysystem.cmd.list") && sender.hasPermission("lobbysystem.cmd.reload")){
                return Arrays.asList("list", "reload");
            }
        }
        return null;
    }
}
