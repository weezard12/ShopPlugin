package com.weezard12.shop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopVillagerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args[0].equals("move")){
            Villager villager = Utils.getClosestVillager((Player) commandSender);
            villager.teleport(((Player) commandSender).getLocation());
        }
        if (args[0].equals("remove")){
            Villager villager = Utils.getClosestVillager((Player) commandSender);
            Utils.getShopOfVillager(villager).murchents.remove(villager);
            villager.setHealth(0);
        }
        if (args[0].equals("name")){
            Villager villager = Utils.getClosestVillager((Player) commandSender);
            villager.setCustomName(ChatColor.RED + String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
            completions.add("move");
            completions.add("remove");
            completions.add("name");
        return completions;
    }
}
