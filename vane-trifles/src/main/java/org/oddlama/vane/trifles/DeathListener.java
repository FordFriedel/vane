package org.oddlama.vane.trifles;

import org.oddlama.vane.core.module.Context;

import org.oddlama.vane.core.Listener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DeathListener extends Listener<Trifles> {

    public DeathListener(Context<Trifles> context) {
        super(
            context.group("paris","test")
        );
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void on_player_death(PlayerDeathEvent event) {
        if(event.getEntity().getKiller() == null) {
            return;
        }
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwningPlayer(event.getPlayer());
        skull.setItemMeta(sm);
        event.getDrops().add(skull);
    }
}
