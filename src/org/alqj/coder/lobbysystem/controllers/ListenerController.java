package org.alqj.coder.lobbysystem.controllers;

import org.alqj.coder.lobbysystem.LobbySystem;
import org.alqj.coder.lobbysystem.listeners.WorldListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * @author Alqj
 */
public class ListenerController {

    // Create an instance for Main.
    private final LobbySystem ls;

    public ListenerController(LobbySystem ls){
        this.ls = ls;
        setupListeners();
    }

    private void setupListeners(){
        // Create an instance for PluginManager and is initialized.
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new WorldListener(ls), ls);
    }
}
