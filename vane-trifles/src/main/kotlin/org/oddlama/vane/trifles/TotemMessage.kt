package org.oddlama.vane.trifles

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityResurrectEvent
import org.oddlama.vane.annotation.lang.LangMessage
import org.oddlama.vane.core.Listener
import org.oddlama.vane.core.lang.TranslatedMessage
import org.oddlama.vane.core.module.Context

/**
 * Broadcasts a server-wide message when a player is saved by a totem of undying.
 */
class TotemMessage(context: Context<Trifles?>) : Listener<Trifles?>(
    context.group(
        "TotemMessage",
        "Broadcasts a message to the whole server when a player is saved by a totem of undying."
    )
) {
    /** Broadcast when a player's totem of undying is used up. */
    @LangMessage
    var langTotemUsed: TranslatedMessage? = null

    /**
     * The event is cancelled when no totem is held, so ignoring cancelled events
     * leaves only actual resurrections.
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onEntityResurrect(event: EntityResurrectEvent) {
        val player = event.entity as? Player ?: return
        langTotemUsed?.broadcastServer(player.displayName())
    }
}
