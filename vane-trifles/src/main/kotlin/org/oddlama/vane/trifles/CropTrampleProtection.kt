package org.oddlama.vane.trifles

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityInteractEvent
import org.oddlama.vane.core.Listener
import org.oddlama.vane.core.module.Context

/**
 * Prevents mobs and animals from trampling farmland.
 */
class CropTrampleProtection(context: Context<Trifles?>) : Listener<Trifles?>(
    context.group(
        "CropTrampleProtection",
        "Prevents mobs and animals from trampling farmland. Players can still trample crops, and villagers can still harvest and plant them."
    )
) {
    /**
     * Non-player entities stepping on farmland fire an [EntityInteractEvent], cancelling it prevents the trampling.
     * Villager farming changes crops through other events, so it is unaffected.
     */
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityInteract(event: EntityInteractEvent) {
        if (event.entity is Player) return

        if (event.block.type == Material.FARMLAND) {
            event.isCancelled = true
        }
    }
}
