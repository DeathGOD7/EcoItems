package com.willfp.ecoitems.display

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.display.DisplayModule
import com.willfp.eco.core.display.DisplayPriority
import com.willfp.ecoitems.fuels.FuelUtils
import com.willfp.ecoitems.items.ItemUtils
import org.bukkit.inventory.ItemStack

class ItemsDisplay(plugin: EcoPlugin) : DisplayModule(plugin, DisplayPriority.LOWEST) {
    override fun display(
        itemStack: ItemStack,
        vararg args: Any
    ) {
        val meta = itemStack.itemMeta ?: return
        val ecoItem = ItemUtils.getEcoItem(meta)
        val fuel = FuelUtils.getFuelFromItem(meta)

        if (fuel != null) {
            val fuelMeta = fuel.itemStack.itemMeta ?: return
            val lore: MutableList<String> = fuelMeta.lore?.toMutableList() ?: return

            if (meta.hasLore()) {
                lore.addAll(meta.lore ?: return)
            }

            meta.lore = lore
            meta.setDisplayName(fuelMeta.displayName)
            itemStack.itemMeta = meta
        }

        if (ecoItem != null) {
            val ecoItemMeta = ecoItem.itemStack.itemMeta ?: return
            val lore: MutableList<String> = ecoItemMeta.lore?.toMutableList() ?: return

            if (meta.hasLore()) {
                lore.addAll(meta.lore ?: return)
            }

            meta.lore = lore
            meta.setDisplayName(ecoItemMeta.displayName)
            meta.addItemFlags(*ecoItemMeta.itemFlags.toTypedArray())
            itemStack.itemMeta = meta
        }
    }
}