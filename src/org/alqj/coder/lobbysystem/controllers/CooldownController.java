package org.alqj.coder.lobbysystem.controllers;

import org.alqj.coder.lobbysystem.LobbySystem;

import java.util.HashMap;

/**
 * @author Alqj
 */
public class CooldownController {

    // Create an instance for Main.
    private final LobbySystem ls;
    // Create an HashMap with the parameters 'String', 'Integer', called cooldowns.
    private final HashMap<String, Integer> cooldowns;

    public CooldownController(LobbySystem ls){
        this.ls = ls;
        this.cooldowns = new HashMap<>();
    }

    /**
     * This method create a cooldown for the player.
     *
     * @param uuid = player UUID
     */
    public void setCooldown(final String uuid){
        cooldowns.put(uuid, (int) System.currentTimeMillis() / 1000);
    }

    /**
     * This method check if the player have a cooldown.
     *
     * @param uuid = player UUID
     * @return true
     */
    public boolean hasCooldown(final String uuid){
        if(!(cooldowns.containsKey(uuid))) return false;

        if(cooldowns.containsKey(uuid)){
            if(((int) System.currentTimeMillis() / 1000) - cooldowns.get(uuid) < ls.getConfigFile().getInt("options.cooldown")){
                return true;
            }
        }
        return false;
    }

    /**
     * This method just remove the cooldown of player.
     *
     * @param uuid = player UUID
     */
    public void removeCooldown(final String uuid){
        cooldowns.remove(uuid);
    }

    /**
     * This method return the cooldown time for the player.
     *
     * @param uuid = player UUID
     * @return time
     */
    public Integer getCooldown(final String uuid){
        if(!(cooldowns.containsKey(uuid))) return null;
        return ls.getConfigFile().getInt("options.cooldown") - (((int) System.currentTimeMillis() / 1000) - cooldowns.get(uuid));
    }
}
