package org.alqj.coder.lobbysystem.commands;

import org.alqj.coder.lobbysystem.LobbySystem;
import org.alqj.coder.lobbysystem.color.Msg;
import org.alqj.coder.lobbysystem.xseries.XSound;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class LobbyCommand implements CommandExecutor {

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

    public LobbyCommand(LobbySystem ls) {
        Optional<XSound> xs = XSound.matchXSound(ls.getConfigFile().get("options.sounds.perm"));
        Optional<XSound> xs1 = XSound.matchXSound(ls.getConfigFile().get("options.sounds.cooldown"));
        if (xs.isPresent() || xs1.isPresent()) {
            this.sound = xs.get().parseSound();
            this.sound1 = xs.get().parseSound();
        } else {
            this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();
            this.sound1 = XSound.BLOCK_NOTE_BLOCK_PLING.parseSound();
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
        // Check if the sender is a player.
        if(sender instanceof Player){
            Player player = (Player) sender;
            // Check if the player have the permission.
            if(player.hasPermission("lobbysystem.cmd.lobby")){
                // Check if the command contains any argument.
                if(args.length == 0){
                    String list = ls.getConfigFile().get("options.messages.lobby.usage");
                    String[] cmds = list.split("\\n");
                    for(int i = 0 ; i < cmds.length ; i++){
                        String list_cmds = cmds[i];
                        list_cmds = Msg.color(list_cmds);
                        player.sendMessage(list_cmds);
                    }
                    return true;
                }

                // Check if the argument '0' of command is "set".
                if(args[0].equalsIgnoreCase("set")){
                    // Check if the player have a cooldown enabled.
                    if(ls.getCooldowns().hasCooldown(player.getUniqueId().toString())){
                        String message = ls.getConfigFile().get("options.messages.cooldown_time");
                        message = message.replace("<time>", ls.getCooldowns().getCooldown(player.getUniqueId().toString()) + "");
                        if(ls.getConfigFile().getBoolean("options.sounds.reproduce")) execute1(player);
                        player.sendMessage(Msg.color(message));
                        return true;
                    }
                    // Create a cooldown for the player.
                    ls.getCooldowns().setCooldown(player.getUniqueId().toString());
                    // Get the player location.
                    Location location = player.getLocation();

                    /**
                     * This get the coordinates XYZ of player, also the yaw, pitch and the world.
                     */
                    double x = location.getX();
                    double y = location.getY();
                    double z = location.getZ();

                    float yaw = location.getYaw();
                    float pitch = location.getPitch();

                    String world = location.getWorld().getName();

                    /**
                     * This set the dates to the config file, for save
                     * the lobby location.
                     */
                    ls.getConfigFile().set("options.location.lobby.world", world);
                    ls.getConfigFile().set("options.location.lobby.x", x);
                    ls.getConfigFile().set("options.location.lobby.y", y);
                    ls.getConfigFile().set("options.location.lobby.z", z);
                    ls.getConfigFile().set("options.location.lobby.yaw", yaw);
                    ls.getConfigFile().set("options.location.lobby.pitch", pitch);

                    // Save the config file.
                    ls.getConfigFile().save();

                    player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.setup")));
                    return true;
                }

                // Check if the argument '0' of command is "remove".
                if(args[0].equalsIgnoreCase("remove")){
                    // Check if the player have a cooldown enabled.
                    if(ls.getCooldowns().hasCooldown(player.getUniqueId().toString())){
                        String message = ls.getConfigFile().get("options.messages.cooldown_time");
                        message = message.replace("<time>", ls.getCooldowns().getCooldown(player.getUniqueId().toString()) + "");
                        if(ls.getConfigFile().getBoolean("options.sounds.reproduce")) execute1(player);
                        player.sendMessage(Msg.color(message));
                        return true;
                    }
                    // Create a cooldown for the player.
                    ls.getCooldowns().setCooldown(player.getUniqueId().toString());

                    // Reset the content of config file.
                    ls.getConfigFile().set("options.location.lobby.world", "");
                    ls.getConfigFile().set("options.location.lobby.x", "");
                    ls.getConfigFile().set("options.location.lobby.y", "");
                    ls.getConfigFile().set("options.location.lobby.z", "");
                    ls.getConfigFile().set("options.location.lobby.yaw", "");
                    ls.getConfigFile().set("options.location.lobby.pitch", "");

                    // Save the config file.
                    ls.getConfigFile().save();

                    player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.removed")));
                    return true;
                }

                player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.not_command")));
                return false;
            } else {
                String message = ls.getConfigFile().get("options.messages.not_perm");
                message = message.replace("<permission>", "lobbysystem.cmd.lobby");
                if(ls.getConfigFile().getBoolean("options.sounds.reproduce")) execute(player);
                player.sendMessage(Msg.color(message));
                return false;
            }
        } else {
            sender.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.not_console")));
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
