package org.oddlama.vane.trifles;

import org.oddlama.vane.core.Listener;
import org.oddlama.vane.core.module.Context;

import net.minecraft.world.entity.LivingEntity;

public class TotemBreakListener extends Listener<Trifles> {

    public TotemBreakListener(Context<Trifles> context) {
        super(
            context.group("parisTODO","test")
        );
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void on_totem_break(EntityRessurectEvent event) {
        LivingEntity entity = event.getEntity();
        String message = entity.getScoreboardName() + " nearly died. Point and laugh";
        return;
    }


    
    
}
