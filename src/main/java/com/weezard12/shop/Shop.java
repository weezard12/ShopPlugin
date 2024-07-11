package com.weezard12.shop;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    String name;
    List<Villager> murchents;
    List<MerchantRecipe> trades;

    public Shop(String name){
        this.name = name;
        murchents = new ArrayList<Villager>();
        trades = new ArrayList<MerchantRecipe>();
    }
    public void AddTrade(Material item1, int amount1, Material item2, int amount2){
        trades.add(Utils.createRecipe(new ItemStack(item1,amount1),new ItemStack(item2, amount2)));
        trades.getLast().setIgnoreDiscounts(true);
    }
    public void updateVillagers(){
        for (Villager villager : murchents) {
            villager.setRecipes(trades);
        }
    }
    public void destroy(){
        for (Villager villager : murchents) {
            villager.setHealth(0);
        }
    }
}
