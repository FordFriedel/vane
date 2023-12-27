package org.oddlama.vane.trifles;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.oddlama.vane.annotation.config.ConfigBoolean;
import org.oddlama.vane.core.Listener;

import io.papermc.paper.event.entity.EntityMoveEvent;
import net.minecraft.world.entity.monster.Monster;

public class FastWalkingListener extends Listener<Trifles> {

	FastWalkingGroup fast_walking;

	public FastWalkingListener(FastWalkingGroup context) {
		super(context);
		this.fast_walking = context;
	}
	@ConfigBoolean(def = false, desc = "Enable to allow hostile mobs to speed walk on paths.")
	public boolean hostile_speedwalk;

	@ConfigBoolean(def = true, desc = "Disable to PREVENT villagers from speed walking on paths.")
	public boolean villiager_speedwalk;
	
	@ConfigBoolean(def = false, desc = "Enable to allow ONLY players to speed walk on paths. (will override other path walk settings)")
	public boolean players_only_speedwalk;

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on_player_move(final PlayerMoveEvent event) {
		// Players mustn't be flying
		final var player = event.getPlayer();
		if (player.isGliding()) {
			return;
		}

		LivingEntity effect_entity = player;
		if (player.isInsideVehicle() && player.getVehicle() instanceof LivingEntity vehicle) {
			effect_entity = vehicle;
		}

		// Inspect block type just a little below
		var block = effect_entity.getLocation().clone().subtract(0.0, 0.1, 0.0).getBlock();
		if (!fast_walking.config_materials.contains(block.getType())) {
			return;
		}

		// Apply potion effect
		effect_entity.addPotionEffect(fast_walking.walk_speed_effect);
	}

	// This is fired for entities except players.
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on_entity_move(final EntityMoveEvent event) {
		final var entity = event.getEntity();

		// Cancel event if speedwalking is only enabled for players
		if(players_only_speedwalk) return;

		// Cancel event if speedwalking is disabled for Hostile mobs
		if(entity instanceof Monster && !hostile_speedwalk) return;
		
		// Cancel event if speedwalking is disabled for Villiagers
		if(entity.getType() == EntityType.VILLAGER && !villiager_speedwalk) return;

		// Inspect block type just a little below
		var block = event.getTo().clone().subtract(0.0, 0.1, 0.0).getBlock();
		if (!fast_walking.config_materials.contains(block.getType())) {
			return;
		}

		// Apply potion effect
		entity.addPotionEffect(fast_walking.walk_speed_effect);
	}
}
