package org.alqj.coder.lobbysystem.controllers;

import org.alqj.coder.lobbysystem.LobbySystem;
import org.alqj.coder.lobbysystem.color.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 * @author Alqj
 */
public class VersionController {

    // Create an instance for Main.
    private final LobbySystem ls;
    // Create an instance for ConsoleCommandSender called 'log' and is initialized with the sender from console.
    private final ConsoleCommandSender log;
    // Create an String called 'version'.
    private String version;

    // Constructor of the class.
    public VersionController(final LobbySystem ls){
        this.ls = ls;
        this.log = Bukkit.getConsoleSender();
        setupVersion();
    }

    /**
     * This method will be execute for check the version of Spigot of server.
     * And for load the NMS class.
     */
    private void setupVersion(){
        // Try to find the version id of Server.
        try{
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            // Check the version ID.
            switch(version){
                case "v1_8_R1":
                case "v1_8_R2":
                    log.sendMessage(Msg.color("&c You're using a older 1.8 version, please update to the 1.8.8 to next."));
                    Bukkit.getPluginManager().disablePlugin(ls);
                    return;
                case "v1_8_R3":
                    log.sendMessage(Msg.color("&6 Detected &e1.8.8 &6version."));
                    return;
                case "v1_9_R1":
                    log.sendMessage(Msg.color("&c You're using a older 1.9 version, please update to the 1.9.4 to next."));
                    Bukkit.getPluginManager().disablePlugin(ls);
                    return;
                case "v1_9_R2":
                    log.sendMessage(Msg.color("&6 Detected &e1.9.4 &6version."));
                    return;
                case "v1_10_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.10.2 &6version."));
                    return;
                case "v1_11_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.11.2 &6version."));
                    return;
                case "v1_12_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.12.2 &6version."));
                    return;
                case "v1_13_R1":
                    log.sendMessage(Msg.color("&c You're using a older 1.13 version, please update to the 1.13.2 to next."));
                    Bukkit.getPluginManager().disablePlugin(ls);
                    return;
                case "v1_13_R2":
                    log.sendMessage(Msg.color("&6 Detected &e1.13.2 &6version."));
                    return;
                case "v1_14_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.14.4 &6version."));
                    return;
                case "v1_15_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.15.2 &6version."));
                    return;
                case "v1_16_R1":
                case "v1_16_R2":
                    log.sendMessage(Msg.color("&c You're using a older 1.16 version, please update to the 1.16.5 to next."));
                    Bukkit.getPluginManager().disablePlugin(ls);
                    return;
                case "v1_16_R3":
                    log.sendMessage(Msg.color("&6Detected &e1.16.5 &6version."));
                    return;
                case "v1_17_R1":
                    log.sendMessage(Msg.color("&6Detected &e1.17.1 &6version."));
                    return;
            }
            // The version not is supported.
            log.sendMessage(Msg.color("&c You're using a unsupported version, check the compatible versions on the plugin page."));
            Bukkit.getPluginManager().disablePlugin(ls);
        } catch(ArrayIndexOutOfBoundsException ex){
            // If there are an error to check the version.
            log.sendMessage(Msg.color("&c An occurred a error while be checking the version."));
            Bukkit.getPluginManager().disablePlugin(ls);
        }
    }
}
