package com.weezard12.shop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ShopCreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ShopPlugin.Shops.put(args[0],new Shop(args[0]));
        commandSender.sendMessage(ChatColor.GREEN+"Created Shop: " + ChatColor.WHITE + args[0]);
        return false;
    }
}
