package org.oddlama.vane.trifles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityInteractEvent;
import org.oddlama.vane.core.Listener;
import org.oddlama.vane.core.module.Context;

public class CropTrampleProtection extends Listener<Trifles> {

    public CropTrampleProtection(Context<Trifles> context) {
        super(
            context.group(
                "crop_trample_protection",
                "Prevents mobs and animals from trampling farmland. Players can still trample crops, and villagers can still harvest and plant them."
            )
        );
    }

    // Non-player entities stepping on farmland fire an EntityInteractEvent, cancelling it prevents the trampling.
    // Villager farming changes crops through other events, so it is unaffected.
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void on_entity_interact(final EntityInteractEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }

        if (event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
