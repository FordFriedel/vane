package org.oddlama.vane.enchantments.enchantments;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.oddlama.vane.annotation.config.ConfigBoolean;
import org.oddlama.vane.annotation.config.ConfigInt;
import org.oddlama.vane.annotation.enchantment.Rarity;
import org.oddlama.vane.annotation.enchantment.VaneEnchantment;
import org.oddlama.vane.core.config.recipes.RecipeList;
import org.oddlama.vane.core.config.recipes.ShapedRecipeDefinition;
import org.oddlama.vane.core.module.Context;
import org.oddlama.vane.core.enchantments.CustomEnchantment;
import org.oddlama.vane.enchantments.Enchantments;

import com.destroystokyo.paper.MaterialTags;

@VaneEnchantment(
    name = "lifesteal",
    max_level = 3,
    rarity = Rarity.RARE,
    treasure = true,
    target = EnchantmentTarget.WEAPON
)
public class Lifesteal extends CustomEnchantment<Enchantments> {
    
    public Lifesteal(Context<Enchantments> context) {
        super(context, true);

    }

    @Override
    public boolean can_enchant(@NotNull ItemStack item_stack) {
        return MaterialTags.HOES.isTagged(item_stack);
    }

    @Override
    public RecipeList default_recipes() {
        return RecipeList.of(new ShapedRecipeDefinition("generic")
        .shape("w","w","w")
        .set_ingredient('w', Material.WITHER_ROSE)
        .result(on("vane_enchantments:enchanted_ancient_tome_of_knowledge")));
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void on_attack(final EntityDamageByEntityEvent event) {

        // TODO
        if(!(event.getDamager() instanceof Player)) return;

        Player wielder = (Player) event.getDamager();
        final var victim = (LivingEntity) event.getEntity();
        final var item = wielder.getEquipment().getItemInMainHand();
        final var level = item.getEnchantmentLevel(this.bukkit());
        PotionEffect test = new PotionEffect(PotionEffectType.WITHER, 20, 2);

        if (level == 0) return;

        wielder.setHealth(wielder.getHealth() + 2.0);

        victim.addPotionEffect(test);
    }
}