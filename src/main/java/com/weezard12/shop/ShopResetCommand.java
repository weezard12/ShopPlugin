package com.weezard12.shop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShopResetCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args[0].equals("!all_shops!")){
            commandSender.sendMessage("Resetting All Shops");
            for (Shop shop : ShopPlugin.Shops.values()) {
                shop.destroy();
            }
            ShopPlugin.Shops.clear();
        }
        else if(ShopPlugin.Shops.containsKey(args[0])){
            commandSender.sendMessage("Resetting Shop: " + args[0]);
            ShopPlugin.Shops.get(args[0]).trades.clear();
            ShopPlugin.Shops.get(args[0]).updateVillagers();
            return true;
        }
        commandSender.sendMessage(Utils.getShopNotExistsText(args[0]));
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = Utils.getShopNames();
        completions.add("!all_shops!");
        return completions;
    }
}
