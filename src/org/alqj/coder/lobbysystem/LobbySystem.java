package org.alqj.coder.lobbysystem;

import org.alqj.coder.lobbysystem.color.Msg;
import org.alqj.coder.lobbysystem.commands.LobbyCommand;
import org.alqj.coder.lobbysystem.commands.MainCommand;
import org.alqj.coder.lobbysystem.commands.TabComplete;
import org.alqj.coder.lobbysystem.controllers.ConfigController;
import org.alqj.coder.lobbysystem.controllers.CooldownController;
import org.alqj.coder.lobbysystem.controllers.ListenerController;
import org.alqj.coder.lobbysystem.controllers.VersionController;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

/**
 * @author Alqj
 */
public class LobbySystem extends JavaPlugin {

    PluginDescriptionFile pdffile = getDescription();
    // Create an instance for PluginDescriptionFile (PDF).
    private final String AUTHOR = "iAlqjDV";
    // Create a String called 'AUTHOR' and is initialized with the name of creator.
    private final String VERSION = pdffile.getVersion();
    // Create a String called 'VERSION' and is initialized with the plugin version.
    private final ConsoleCommandSender log = Bukkit.getConsoleSender();
    // Create an instance for ConsoleCommandSender called 'log' and is initialized with the sender from console.
    // private FileConfiguration config;
    // Create an instance for FileConfiguration called 'config'.
    // private FileConfiguration data;
    // Create an instance for FileConfiguration called 'data'.
    private VersionController vc;
    // Create an instance for VersionController called 'vc'.
    private ListenerController lc;
    // Create an instance for ListenerController called 'lc'.
    private CooldownController cc;
    // Create an instance for CooldownController called 'cc'.
    private ConfigController config;
    // Create an instance for ConfigController called 'configController'.

    /**
     * This method return the AUTHOR placeholder be contains the creator name.
     *
     * @return AUTHOR
     */
     public String getAuthor(){
         return AUTHOR;
     }

    /**
     * This method return the VERSION placeholder be contains the version id.
     *
     * @return VERSION
     */
    public String getVersion(){
        return VERSION;
    }

    @Override
    public void onEnable() {
        // Get the time of system in milliseconds when the plugin is started.
        long START = System.currentTimeMillis();

        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&6   LobbySystem"));
        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&f Author: &biAlqjDV"));
        log.sendMessage(Msg.color("&f  Version: &b10.1"));
        log.sendMessage(Msg.color(""));

        // Try to find a Spigot class to verify if the server contains a .jar of Spigot.
        try{
            Class.forName("org.spigotmc.SpigotConfig");
        } catch(ClassNotFoundException ex){
            log.sendMessage(Msg.color("&c Could not found a Spigot jar, please install a Spigot jar and restart the server."));
            log.sendMessage(Msg.color("&c The plugin will be deactivated now..."));
            Bukkit.getPluginManager().disablePlugin(this);
        }

        /**
         * Here be load the components and class necessary to the plugin.
         * also, the commands and events required, and be created the configuration files.
         */
        config = new ConfigController(this, "config", true, false);
        lc = new ListenerController(this);
        setupCommands();
        cc = new CooldownController(this);

        /**
         * This check the server version, for verify if the version is supported for the plugin.
         * also, load the NMS class required per version.
         */
        vc = new VersionController(this);

        log.sendMessage(Msg.color("&a Has been enabled correctly in &e" + (System.currentTimeMillis() - START) + "ms&a."));
        log.sendMessage(Msg.color(""));
    }

    @Override
    public void onDisable() {}

    /**
     * This method return the instance of CooldownController.
     *
     * @return cc
     */
    public CooldownController getCooldowns(){
        return cc;
    }

    /**
     * This method return the instance of VersionController.
     *
     * @return vc
     */
    public VersionController getVc(){
        return vc;
    }

    public ConfigController getConfigFile() { return config; }

    /**
     * This method reload the specified file.
     */
    public void reload() { config.reload(); }

    /**
     * This method register the commands of other class.
     */
    private void setupCommands(){
        getCommand("lobbysystem").setExecutor(new MainCommand(this));
        getCommand("lobby").setExecutor(new LobbyCommand(this));
        getCommand("lobbysystem").setTabCompleter(new TabComplete());
        getCommand("lobby").setTabCompleter(new TabComplete());
    }
}
