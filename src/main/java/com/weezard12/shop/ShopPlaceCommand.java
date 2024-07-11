package com.weezard12.shop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopPlaceCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length==0){
            commandSender.sendMessage("Please enter the name of the shop that you want to place. use '/shop list' to view all shops");
            return false;
        }


        if(ShopPlugin.Shops.get(args[0]).trades.size()==0){
            commandSender.sendMessage("This Shop Has No Trades. Use '/shop " + args[0] + " add' to add trades for this shop or '/shop help' for more info");
            return false;
        }
        ShopPlugin.Shops.get(args[0]).murchents.add(Utils.spawnVillager(((Player) commandSender).getPlayer().getLocation(),ShopPlugin.Shops.get(args[0]).trades,ShopPlugin.Shops.get(args[0]).name));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return Utils.getShopNames();
    }


}
