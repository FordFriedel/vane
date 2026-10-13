package org.oddlama.vane.trifles

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.PlayerDeathEvent
import org.oddlama.vane.annotation.config.ConfigDouble
import org.oddlama.vane.core.Listener
import org.oddlama.vane.core.module.Context
import org.oddlama.vane.util.ItemUtil.skullForPlayer
import java.util.concurrent.ThreadLocalRandom

/**
 * Drops a player's head when they are killed by another player.
 */
class PlayerHeadDrops(context: Context<Trifles?>) : Listener<Trifles?>(
    context.group(
        "PlayerHeadDrops",
        "Players drop their own head when killed by another player."
    )
) {
    /** Chance that the victim's head drops on a player kill. */
    @ConfigDouble(
        def = 1.0,
        min = 0.0,
        max = 1.0,
        desc = "Chance that a player drops their head when killed by another player."
    )
    var configDropChance: Double = 1.0

    /** Adds the victim's head to the drops when the killer is another player. */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val victim = event.entity
        val killer = victim.killer ?: return
        if (killer == victim) return

        if (ThreadLocalRandom.current().nextDouble() >= configDropChance) return

        val head = skullForPlayer(victim, false)
        if (event.keepInventory) {
            // Drops are discarded when inventories are kept, so drop the head manually
            victim.world.dropItemNaturally(victim.location, head)
        } else {
            event.drops.add(head)
        }
    }
}
