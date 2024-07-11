package com.weezard12.shop;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        //Help
        if (args.length == 0 || args[0].equalsIgnoreCase("help")){
            return handleHelpCommand(commandSender);
        }

        //List
        else if (args.length == 0 || args[0].equalsIgnoreCase("list")){
            return handleListCommand(commandSender);

        } else if (args.length == 0 || args[0].equalsIgnoreCase("about")) {
            commandSender.sendMessage("Shop Plugin by weezard12");
            return true;
        }
        if(!commandSender.hasPermission("op"))
            return false;
        //Edit
        if(args.length == 7 && args[1].equals("edit")){
            if(!ShopPlugin.Shops.containsKey(args[0])){
                commandSender.sendMessage(Utils.getShopNotExistsText(args[0]));
                return false;
            }
            handleEditCommand(args);
            commandSender.sendMessage(ChatColor.GREEN + "Edited trade for: " + ChatColor.WHITE + args[0]);
        }

        //Info
        else if(args.length == 2 && args[1].equals("info")){
            if(!ShopPlugin.Shops.containsKey(args[0])){
                commandSender.sendMessage(Utils.getShopNotExistsText(args[0]));
                return false;
            }
            return handleInfoCommand(commandSender,ShopPlugin.Shops.get(args[0]));
        }

        //Remove
        else if(args.length == 3 && args[1].equals("remove")){
            if(!ShopPlugin.Shops.containsKey(args[0])){
                commandSender.sendMessage(Utils.getShopNotExistsText(args[0]));
                return false;
            }
            return handleRemoveCommand(commandSender,args);
        }

        //Add
        else if(args.length == 6){
            handleAddCommand(args);
            commandSender.sendMessage(ChatColor.GREEN + "Added new trade for: " + ChatColor.WHITE + args[0]);
        }

        return false;
    }
    private boolean handleHelpCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Shop Plugin Help:");
        sender.sendMessage(ChatColor.YELLOW + "/shop help" + ChatColor.WHITE + " - Displays this help menu.");
        sender.sendMessage(ChatColor.YELLOW + "/shop list" + ChatColor.WHITE + " -  Displays all the current shops names.");
        sender.sendMessage(ChatColor.YELLOW + "/shop about" + ChatColor.WHITE + " -  'About' Text.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "Admin Only:");
        sender.sendMessage(ChatColor.YELLOW + "/shopcreate" + ChatColor.WHITE + " - Creates a new shop (Admin only).");
        sender.sendMessage(ChatColor.YELLOW + "/shopplace <shop name>" + ChatColor.WHITE + " - Place a specific shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shopremove <shop name>" + ChatColor.WHITE + " - Removes a specific shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shop <shop name> add <item> <amount> <item> <amount>" + ChatColor.WHITE + " - Adds new item to a shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shop <shop name> edit <item> <amount> <item> <amount> <index>" + ChatColor.WHITE + " - Edits item in a shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shop <shop name> edit <item> <amount> <item> <amount> <index>" + ChatColor.WHITE + " - Edits item in a shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shop <shop name> remove <index>" + ChatColor.WHITE + " - Removes the trade of that index from the shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shop <shop name> remove <index>" + ChatColor.WHITE + " - Removes the trade of that index from the shop.");
        sender.sendMessage(ChatColor.YELLOW + "/shopvillager move" + ChatColor.WHITE + " - Teleport the closest shop villager to your location");
        sender.sendMessage(ChatColor.YELLOW + "/shopvillager remove" + ChatColor.WHITE + " - Kills the closest shop villager to your location");
        sender.sendMessage(ChatColor.YELLOW + "/shopvillager name <name>" + ChatColor.WHITE + " -  Sets the name of the closest villager");
        sender.sendMessage(ChatColor.YELLOW + "/shopreset <shop name / !all_shops!>" + ChatColor.WHITE + " -  Reset all the shop trades but keeps the villagers");
        return true;
    }
    private boolean handleListCommand(CommandSender sender) {
        if(ShopPlugin.Shops.isEmpty()){
            sender.sendMessage("No Shops have been found. (use /shopcreate to add new shops)");
            return true;
        }

        sender.sendMessage(ShopPlugin.Shops.size() + " Shops been found");
        for (Shop shop : ShopPlugin.Shops.values()) {
            sender.sendMessage(shop.name);
        }


        return true;
    }
    private boolean handleAddCommand(String[] args){
        ShopPlugin.Shops.get(args[0]).AddTrade(
            Material.matchMaterial(args[2].toUpperCase()),
            Integer.parseInt(args[3]),
            Material.matchMaterial(args[4].toUpperCase()),
            Integer.parseInt(args[5]));

        ShopPlugin.Shops.get(args[0]).updateVillagers();
        return true;
    }

    private boolean handleEditCommand(String[] args){

        ShopPlugin.Shops.get(args[0]).trades.set(Integer.parseInt(args[6])-1, Utils.createRecipe(new ItemStack(Material.matchMaterial(args[2].toUpperCase()), Integer.parseInt(args[3])), new ItemStack(Material.matchMaterial(args[4].toUpperCase()), Integer.parseInt(args[5]))));

        ShopPlugin.Shops.get(args[0]).updateVillagers();

        return false;
    }

    private boolean handleInfoCommand(@NotNull CommandSender commandSender,Shop shop){
        commandSender.sendMessage("Info about shop: " + shop.name);
        if(shop.trades.isEmpty()){
            commandSender.sendMessage("No Trades Found.");
        }
        else{
            commandSender.sendMessage("Trades: "+shop.trades.size());
            int count = 1;
            for (MerchantRecipe trade : shop.trades) {
                commandSender.sendMessage(count + ". "+ Utils.tradeToString(trade));
                count++;
            }
        }
        if(shop.murchents.isEmpty()){
            commandSender.sendMessage("No Merchants Found.");
            return true;
        }
        commandSender.sendMessage("Villagers: " + shop.murchents.size());
        for (Villager villager : shop.murchents) {
            // Create a component with the location text
            Location location = villager.getLocation();
            Component component = Component.text("["+location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ()+"]")
                    .color(TextColor.color(0x00FF00)) // Green color
                    .hoverEvent(HoverEvent.showText(Component.text("Click to teleport!"))) // Hover text
                    .clickEvent(ClickEvent.runCommand("/tp " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ())); // Click event to teleport
            commandSender.sendMessage(component);
        }
        return false;
    }
    private boolean handleRemoveCommand(@NotNull CommandSender commandSender,String[] args){
        commandSender.sendMessage("Removed trade: " + Utils.tradeToString(ShopPlugin.Shops.get(args[0]).trades.get(Integer.parseInt(args[2])-1))+". from shop: "+args[0]);
        ShopPlugin.Shops.get(args[0]).trades.remove(Integer.parseInt(args[2])-1);
        ShopPlugin.Shops.get(args[0]).updateVillagers();
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1){
            completions = Utils.getShopNames();
            completions.add("list");
            completions.add("help");
        }
        else if(args.length == 2){
            if(!args[1].equals("help") && !args[1].equals("list")){
                completions.add("add");
                completions.add("edit");
                completions.add("remove");
                completions.add("info");
            }
        }
        else if(args.length == 3){
            if(args[1].equals("add") || args[1].equals("edit")){
                for (Material material : Material.values()) {
                    completions.add(material.name().toLowerCase());
                }
            }
            else if(args[1].equals("remove")){

                //if shop does not exist
                if(!ShopPlugin.Shops.containsKey(args[0]))
                    return completions;

                for (int i = 1; i < ShopPlugin.Shops.get(args[0]).trades.size()+1; i++) {
                    completions.add(""+i);
                }
            }
        }
        else if(args.length == 5){
            if(true){
                for (Material material : Material.values()) {
                    completions.add(material.name().toLowerCase());
                }
            }
        }

        return completions;
    }
}
