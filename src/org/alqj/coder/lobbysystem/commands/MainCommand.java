package org.alqj.coder.lobbysystem.commands;

import org.alqj.coder.lobbysystem.LobbySystem;
import org.alqj.coder.lobbysystem.color.Msg;
import org.alqj.coder.lobbysystem.xseries.XSound;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MainCommand implements CommandExecutor {

    // Create an instance for Main.
    private final LobbySystem ls;
    // Create a first instance for Sound.
    private Sound sound;
    // Create a second instance for Sound.
    private Sound sound1;
    // Create an instance of type int.
    private int volume;
    // Create another instance of type int.
    private int pitch;

    public MainCommand(LobbySystem ls){
        Optional<XSound> xs = XSound.matchXSound(ls.getConfigFile().get("options.sounds.perm"));
        Optional<XSound> xs1 = XSound.matchXSound(ls.getConfigFile().get("options.sounds.reload"));
        if(xs.isPresent() || xs1.isPresent()){
            this.sound = xs.get().parseSound();
            this.sound1 = xs1.get().parseSound();
        } else {
            this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();
            this.sound1 = XSound.UI_BUTTON_CLICK.parseSound();
        }
        this.volume = ls.getConfigFile().getInt("options.sounds.volume");
        this.pitch = ls.getConfigFile().getInt("options.sounds.pitch");
        this.ls = ls;
    }

    /**
     * This method return the first sound.
     *
     * @return sound
     */
    private Sound getSound(){
        return sound;
    }

    /**
     * This method return the second sound.
     *
     * @return sound
     */
    private Sound getSound1(){
        return sound1;
    }

    /**
     * This method return the volume of the sounds.
     *
     * @return sound
     */
    private int getVolume() {
        return volume;
    }

    /**
     * This method return the pitch of the sounds.
     *
     * @return sound
     */
    private int getPitch() {
        return pitch;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String la, String[] args) {
        String prefix = ls.getConfigFile().get("options.messages.prefix");
        // Check if the sender is a player.
        if(sender instanceof Player){
            Player player = (Player) sender;
            // Check if the command contains any argument.
            if(args.length == 0){
                player.sendMessage(Msg.color(prefix + "&7Running on &e" + ls.getVersion() + "&7 by &a" + ls.getAuthor()));
                return true;
            }

            // Check if the argument '0' of command is "list".
            if(args[0].equalsIgnoreCase("list")){
                // Check if the player have the permission.
                if(player.hasPermission("lobbysystem.cmd.list")){
                    String list = ls.getConfigFile().get("options.messages.cmds");
                    String[] cmds = list.split("\\n");
                    for(int i = 0 ; i < cmds.length ; i++){
                        String list_cmds = cmds[i];
                        list_cmds = Msg.color(list_cmds);
                        player.sendMessage(list_cmds);
                    }
                } else {
                    /**
                     * If the player don't have the permission...
                     *
                     * Create a String called 'message' with the message string not_perm.
                     */
                    String message = ls.getConfigFile().get("options.messages.not_perm");
                    // Replace the variable '<permission>' of message to permission name.
                    message = message.replace("<permission>", "lobbysystem.cmd.list");
                    // Check if the sounds boolean is true.
                    if(ls.getConfigFile().getBoolean("options.sounds.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            // Check if the argument '0' of command is "reload".
            if(args[0].equalsIgnoreCase("reload")){
                // Check if the player have the permission.
                if(player.hasPermission("lobbysystem.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    ls.reload();
                    String message = ls.getConfigFile().get("options.messages.reload");
                    // Replace the variable '<permission>' of message to permission name.
                    message = message.replace("<time>", (System.currentTimeMillis() - RELOAD) + "");
                    if(ls.getConfigFile().getBoolean("options.sounds.reproduce")) execute1(player);
                    sender.sendMessage(Msg.color(message));
                } else {
                    /**
                     * If the player don't have the permission...
                     *
                     * Create a String called 'message' with the message string not_perm.
                     */
                    String message = ls.getConfigFile().get("options.messages.not_perm");
                    // Replace the variable '<permission>' of message to permission name.
                    message = message.replace("<permission>", "lobbysystem.cmd.reload");
                    // Check if the sounds boolean is true.
                    if(ls.getConfigFile().getBoolean("options.sounds.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            // If the argument doesn't exists.
            player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.not_command")));
            return false;

        } else {
            // Check if the command contains any argument.
            if(args.length == 0){
                sender.sendMessage(Msg.color(prefix + "&7Running on &e" + ls.getVersion() + "&7 by &a" + ls.getAuthor()));
                return true;
            }

            // Check if the argument '0' of command is "list".
            if(args[0].equalsIgnoreCase("list")){
                // Check if the player have the permission.
                if(sender.hasPermission("lobbysystem.cmd.list")){
                    String list = ls.getConfigFile().get("options.messages.cmds");
                    String[] cmds = list.split("\\n");
                    for(int i = 0 ; i < cmds.length ; i++){
                        String list_cmds = cmds[i];
                        list_cmds = Msg.color(list_cmds);
                        sender.sendMessage(list_cmds);
                    }
                } else {
                    /**
                     * If the player don't have the permission...
                     *
                     * Create a String called 'message' with the message string not_perm.
                     */
                    String message = ls.getConfigFile().get("options.messages.not_perm");
                    // Replace the variable '<permission>' of message to permission name.
                    message = message.replace("<permission>", "lobbysystem.cmd.list");
                    // Check if the sounds boolean is true.
                    sender.sendMessage(Msg.color(message));
                }
                return true;
            }

            // Check if the argument '0' of command is "reload".
            if(args[0].equalsIgnoreCase("reload")){
                // Check if the player have the permission.
                if(sender.hasPermission("lobbysystem.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    ls.reload();
                    String message = ls.getConfigFile().get("options.messages.reload");
                    // Replace the variable '<permission>' of message to permission name.
                    message = message.replace("<time>", (System.currentTimeMillis() - RELOAD) + "");
                    sender.sendMessage(Msg.color(message));
                } else {
                    /**
                     * If the player don't have the permission...
                     *
                     * Create a String called 'message' with the message string not_perm.
                     */
                    String message = ls.getConfigFile().get("options.messages.not_perm");
                    // Replace the variable '<permission>' of message to permission name.
                    message = message.replace("<permission>", "lobbysystem.cmd.reload");
                    sender.sendMessage(Msg.color(message));
                }
                return true;
            }

            // If the argument doesn't exists.
            sender.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.not_command")));
            return false;
        }
    }

    private void execute(Player player){
        player.playSound(player.getLocation(), getSound(), getVolume(), getPitch());
    }

    private void execute1(Player player){
        player.playSound(player.getLocation(), getSound1(), getVolume(), getPitch());
    }
}
