package com.weezard12.shop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ShopPlugin extends JavaPlugin {

    public static HashMap<String,Shop> Shops = new HashMap<String,Shop>();

    @Override
    public void onEnable() {
        getLogger().info("Shop Plugin Enabled");
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("shopcreate").setExecutor(new ShopCreateCommand());
        getCommand("shopplace").setExecutor(new ShopPlaceCommand());
        getCommand("shopremove").setExecutor(new ShopRemoveCommand());
        getCommand("shopreset").setExecutor(new ShopResetCommand());
        getCommand("shopvillager").setExecutor(new ShopVillagerCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shop Plugin Disabled");
    }




}
