package org.alqj.coder.lobbysystem.listeners;

import org.alqj.coder.lobbysystem.LobbySystem;
import org.alqj.coder.lobbysystem.color.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    // Create an instance for Main.
    private final LobbySystem ls;

    public WorldListener(LobbySystem ls){
        this.ls = ls;
    }

    /**
     * This method will check if the player has placed a block, if the player have a permission, can do this.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onPlace(BlockPlaceEvent ev){
        Player player = ev.getPlayer();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.build") == false){
            if(player.hasPermission("lobbysystem.bypass.build")) return;

            ev.setCancelled(true);
            player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.not_build")));
        }
    }

    /**
     * This method will check if the player has broken a block, if the player have a permission, can do this.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onBreak(BlockBreakEvent ev){
        Player player = ev.getPlayer();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.build") == false){
            if(player.hasPermission("lobbysystem.bypass.build")) return;

            ev.setCancelled(true);
            player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.not_break")));
        }
    }

    /**
     * This method will check if the player has suffered any damage, if the player have a permission, can receive damage.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onDamage(EntityDamageEvent ev){
        Player player = (Player) ev.getEntity();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.damage") == false){
            if(player.hasPermission("lobbysystem.bypass.damage")) return;

            ev.setCancelled(true);
        }
    }

    /**
     * This method will check if the player has suffered hunger, if the player have a permission, can suffer hunger.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onHunger(FoodLevelChangeEvent ev){
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.hunger") == false){
            ev.setCancelled(true);
        }
    }

    /**
     * This method will check if the player has try attack to any player, if the player have a permission, can do this.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onPvP(PlayerInteractEntityEvent ev){
        Player player = ev.getPlayer();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.pvp") == false && ev.getRightClicked() instanceof Player){
            if(player.hasPermission("lobbysystem.bypass.pvp")) return;

            ev.setCancelled(true);
            player.sendMessage(Msg.color(ls.getConfigFile().getConfig().getString("options.messages.lobby.not_pvp")));
        }
    }

    /**
     * This method will check if the player has falling to vacuum, if the player have a permission, can do this.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onVoidFall(EntityDamageEvent ev){
        Player player = (Player) ev.getEntity();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(ls.getConfigFile().getBoolean("options.location.void") == false){
            if(player.hasPermission("lobbysystem.bypass.void")) return;

            player.setFallDistance(0.0F);

            double x = Double.valueOf(ls.getConfigFile().getInt("options.location.lobby.x"));
            double y = Double.valueOf(ls.getConfigFile().getInt("options.location.lobby.y"));
            double z = Double.valueOf(ls.getConfigFile().getInt("options.location.lobby.z"));

            float yaw = Float.valueOf(ls.getConfigFile().getInt("options.location.lobby.yaw"));
            float pitch = Float.valueOf(ls.getConfigFile().getInt("options.location.lobby.pitch"));

            if(world != null){
                Location location = new Location(world, x, y, z, yaw, pitch);
                location.add(x > 0 ? 0.5 : -0.5, 0.0, z > 0 ? 0.5 : -0.5);
                player.teleport(location);
            } else { player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.not_exists"))); }
        }
    }

    /**
     * This method will check if when there are a thunder in the world, if the player have a permission, can bypass this event.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onWeather(WeatherChangeEvent ev){
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.weather") == false){
            ev.setCancelled(true);

            world.setThundering(false);
            world.setWeatherDuration(0);
        }
    }

    /**
     * This method will check if the player has pick up an item, if the player have a permission, can do this.
     *
     * @param ev (event)
     */
    @SuppressWarnings("deprecated")
    @EventHandler
    public void onPickup(PlayerPickupItemEvent ev){
        Player player = ev.getPlayer();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.pickup_items")){
            if(player.hasPermission("lobbysystem.bypass.pickup")) return;

            ev.setCancelled(true);
            player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.not_pickup")));
        }
    }

    /**
     * This method will check if the player has dropped an item, if the player have a permission, can do this.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent ev){
        Player player = ev.getPlayer();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));
        if(world != null && ls.getConfigFile().getBoolean("options.location.drop_items") == false){
            if(player.hasPermission("lobbysystem.bypass.drop")) return;

            ev.setCancelled(true);
            player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.not_drop")));
        }
    }

    /**
     * This method will teleport to player to lobby location.
     *
     * @param ev (event)
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent ev){
        Player player = ev.getPlayer();
        World world = ls.getServer().getWorld(ls.getConfigFile().get("options.location.lobby.world"));

        double x = Double.valueOf(ls.getConfigFile().getInt("options.location.lobby.x"));
        double y = Double.valueOf(ls.getConfigFile().getInt("options.location.lobby.y"));
        double z = Double.valueOf(ls.getConfigFile().getInt("options.location.lobby.z"));

        float yaw = Float.valueOf(ls.getConfigFile().getInt("options.location.lobby.yaw"));
        float pitch = Float.valueOf(ls.getConfigFile().getInt("options.location.lobby.pitch"));

        if(world != null) {
            Location location = new Location(world, x, y, z, yaw, pitch);
            location.add(x > 0 ? 0.5 : -0.5, 0.0, z > 0 ? 0.5 : -0.5);
            player.teleport(location);
        } else { player.sendMessage(Msg.color(ls.getConfigFile().get("options.messages.lobby.not_exists"))); }
    }
}
