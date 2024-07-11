package com.weezard12.shop;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Villager spawnVillager(Location location, List<MerchantRecipe> recipes, String name) {
        // Create a new villager entity
        Villager villager = location.getWorld().spawn(location, Villager.class);

        villager.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,-1,10,false,false));
        villager.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,-1,10,false,false));
        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,-1,10,false,false));

        // Set the villager's profession and type
        villager.setProfession(Villager.Profession.TOOLSMITH);
        villager.setVillagerType(Villager.Type.SWAMP);
        villager.setVillagerLevel(5);

        villager.setRecipes(recipes);

        villager.setCustomName(ChatColor.RED + name);
        villager.setCustomNameVisible(true);

        return villager;
    }
    public static MerchantRecipe createRecipe(ItemStack input, ItemStack output) {
        MerchantRecipe recipe = new MerchantRecipe(output, Integer.MAX_VALUE);
        recipe.addIngredient(input);
        return recipe;
    }

    public static List<String> getShopNames(){
        List<String> completions = new ArrayList<>();
            for (Shop shop: ShopPlugin.Shops.values()) {
                completions.add(shop.name);
            }
        return completions;
    }

    public static Villager getClosestVillager(Player player) {
        Villager closestVillager = null;
        double closestDistance = Double.MAX_VALUE;
        Location playerLocation = player.getLocation();

        for (Shop shop : ShopPlugin.Shops.values()) {
            for (Villager villager : shop.murchents) {
                double distance = villager.getLocation().distance(playerLocation);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestVillager = villager;
                }
            }
        }

        return closestVillager;
    }
/*    public static Villager getClosestVillagerInWorld(Player player) {
        Villager closestVillager = null;
        double closestDistance = Double.MAX_VALUE;
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Villager) {
                Villager villager = (Villager) entity;
                if(!villager.isCustomNameVisible()){
                    double distance = villager.getLocation().distance(playerLocation);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestVillager = villager;
                    }
                }

            }
        }
        for (MerchantRecipe recipe : closestVillager.getRecipes())
            recipe.setPriceMultiplier(5);

        return closestVillager;
    }*/

    public static Shop getShopOfVillager(Villager villager){
        for (Shop shop : ShopPlugin.Shops.values()) {
            if(shop.murchents.contains(villager))
                return shop;
        }
        return null;
    }

    public Shop tryGetShopByName(String name){
        if(ShopPlugin.Shops.containsKey(name))
            return ShopPlugin.Shops.get(name);

        else return null;
    }

    public static String getShopNotExistsText(String shopName){
        return "Could not fined the Shop: " + shopName;
    }
    public static String tradeToString(MerchantRecipe trade){
        StringBuilder s = new StringBuilder();

        List<ItemStack> ingredients = trade.getIngredients();
        ItemStack result = trade.getResult();

        // Add first ingredient
        if (!ingredients.isEmpty()) {
            ItemStack firstIngredient = ingredients.get(0);
            s.append(ChatColor.GOLD)
                    .append(firstIngredient.getType().name().toLowerCase())
                    .append(ChatColor.RESET)
                    .append(ChatColor.WHITE)
                    .append("x")
                    .append(firstIngredient.getAmount())
                    .append(ChatColor.RESET);
        }

        // Add second ingredient if it exists
        if (ingredients.size() > 1) {
            ItemStack secondIngredient = ingredients.get(1);
            s.append(" + ")
                    .append(ChatColor.GOLD)
                    .append(secondIngredient.getType().name().toLowerCase())
                    .append(ChatColor.RESET)
                    .append(ChatColor.WHITE)
                    .append("x")
                    .append(secondIngredient.getAmount())
                    .append(ChatColor.RESET);
        }

        // Add the result
        s.append(ChatColor.DARK_GREEN)
                .append(" -> ")
                .append(ChatColor.RESET)
                .append(ChatColor.GOLD)
                .append(result.getType().name().toLowerCase())
                .append(ChatColor.RESET)
                .append(ChatColor.WHITE)
                .append("x")
                .append(result.getAmount())
                .append(ChatColor.RESET);

        return s.toString();
    }

    public static final String useShopList =  "use '/shop list' to view all shops";
}
