package io.github.thebusybiscuit.slimefun4.implementation.listeners;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This {@link Listener} listens to the {@link EntityDeathEvent} to automatically
 * create a waypoint for a {@link Player} who carries an Emergency Transmitter.
 *
 * @author TheBusyBiscuit
 *
 */
public class DeathpointListener implements Listener {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("(MMM dd, yyyy @ hh:mm)", Locale.ROOT);

    public DeathpointListener(@Nonnull SlimefunPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (SlimefunUtils.containsSimilarItem(p.getInventory(), SlimefunItems.GPS_EMERGENCY_TRANSMITTER, true)) {
            SlimefunPlugin.getGPSNetwork().addWaypoint(p, "player:death " + SlimefunPlugin.getLocalization().getMessage(p, "teleporter.deathpoint").replace("%date%", format.format(LocalDateTime.now())), p.getLocation().getBlock().getLocation());
        }
    }
}