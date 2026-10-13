package org.oddlama.vane.trifles;

import static org.oddlama.vane.util.ItemUtil.skull_for_player;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.oddlama.vane.annotation.config.ConfigDouble;
import org.oddlama.vane.core.Listener;
import org.oddlama.vane.core.module.Context;

public class PlayerHeadDrops extends Listener<Trifles> {

    @ConfigDouble(
        def = 1.0,
        min = 0.0,
        max = 1.0,
        desc = "Chance that a player drops their head when killed by another player."
    )
    public double config_drop_chance;

    public PlayerHeadDrops(Context<Trifles> context) {
        super(
            context.group(
                "player_head_drops",
                "Players drop their own head when killed by another player."
            )
        );
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on_player_death(final PlayerDeathEvent event) {
        final var victim = event.getEntity();
        final var killer = victim.getKiller();
        if (killer == null || killer.equals(victim)) {
            return;
        }

        if (ThreadLocalRandom.current().nextDouble() >= config_drop_chance) {
            return;
        }

        final var head = skull_for_player(victim, false);
        if (event.getKeepInventory()) {
            // Drops are discarded when inventories are kept, so drop the head manually
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);
        } else {
            event.getDrops().add(head);
        }
    }
}
