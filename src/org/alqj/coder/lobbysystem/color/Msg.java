package org.alqj.coder.lobbysystem.color;

import org.bukkit.ChatColor;

/**
 * @author Alqj
 */
public class Msg {

    /**
     * This method convert the '&' in color codes valid.
     *
     * @param str
     * @return str
     */
    public static String color(String str){
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
