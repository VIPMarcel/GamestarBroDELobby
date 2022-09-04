package vip.marcel.gamestarbro.lobby.utils.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final Lobby plugin;

    private final ItemStack currentItem;

    public ItemBuilder(Lobby plugin, Material type, int amount, short damage, Byte data) {
        this.plugin = plugin;
        this.currentItem = new ItemStack(type, amount, damage, data);
    }

    public ItemBuilder(Lobby plugin, Material type, int amount, short damage) {
        this.plugin = plugin;
        this.currentItem = new ItemStack(type, amount, damage);
    }

    public ItemBuilder(Lobby plugin, Material type, int amount) {
        this.plugin = plugin;
        this.currentItem = new ItemStack(type, amount, (short) 0);
    }

    public ItemBuilder(Lobby plugin, Material type) {
        this.plugin = plugin;
        this.currentItem = new ItemStack(type, 1, (short) 0);
    }

    public ItemBuilder setDisplayname(String name) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.setDisplayName(name);
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setNoName() {
        setDisplayname("§8");
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.removeItemFlags(itemFlags);
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int value) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.addEnchant(enchantment, value, true);
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.removeEnchant(enchantment);
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean flag) {
        ItemMeta itemMeta = this.currentItem.getItemMeta();
        itemMeta.setUnbreakable(flag);
        this.currentItem.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta)this.currentItem.getItemMeta();
        itemMeta.setColor(color);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setLeatherArmorColorRGB(int r, int g, int b) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta)this.currentItem.getItemMeta();
        itemMeta.setColor(Color.fromRGB(r, g, b));
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBannerBaseDyeColor(DyeColor dyeColor) {
        BannerMeta itemMeta = (BannerMeta)this.currentItem.getItemMeta();
        itemMeta.setBaseColor(dyeColor);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBannerPattern(int variable, Pattern pattern) {
        BannerMeta itemMeta = (BannerMeta)this.currentItem.getItemMeta();
        itemMeta.setPattern(variable, pattern);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBannerPatterns(List<Pattern> patterns) {
        BannerMeta itemMeta = (BannerMeta)this.currentItem.getItemMeta();
        itemMeta.setPatterns(patterns);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBlockState(BlockState blockState) {
        BlockStateMeta itemMeta = (BlockStateMeta)this.currentItem.getItemMeta();
        itemMeta.setBlockState(blockState);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBookTitle(String title) {
        BookMeta itemMeta = (BookMeta)this.currentItem.getItemMeta();
        itemMeta.setTitle(title);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBookAuthor(String author) {
        BookMeta itemMeta = (BookMeta)this.currentItem.getItemMeta();
        itemMeta.setAuthor(author);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBookPage(int page, String text) {
        BookMeta itemMeta = (BookMeta)this.currentItem.getItemMeta();
        itemMeta.setPage(page, text);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBookPages(List<String> pages) {
        BookMeta itemMeta = (BookMeta)this.currentItem.getItemMeta();
        itemMeta.setPages(pages);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBookPages(String... pages) {
        BookMeta itemMeta = (BookMeta)this.currentItem.getItemMeta();
        itemMeta.setPages(pages);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setFireworkEffect(FireworkEffect fireworkEffect) {
        FireworkEffectMeta itemMeta = (FireworkEffectMeta)this.currentItem.getItemMeta();
        itemMeta.setEffect(fireworkEffect);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setFireworkPower(int power) {
        FireworkMeta itemMeta = (FireworkMeta)this.currentItem.getItemMeta();
        itemMeta.setPower(power);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder addFireworkEffect(FireworkEffect fireworkEffect) {
        FireworkMeta itemMeta = (FireworkMeta)this.currentItem.getItemMeta();
        itemMeta.addEffect(fireworkEffect);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder removeFireworkEffect(int variable) {
        FireworkMeta itemMeta = (FireworkMeta)this.currentItem.getItemMeta();
        itemMeta.removeEffect(variable);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder addFireworkEffects(FireworkEffect... fireworkEffects) {
        FireworkMeta itemMeta = (FireworkMeta)this.currentItem.getItemMeta();
        itemMeta.addEffects(fireworkEffects);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder addFireworkEffects(Iterable<FireworkEffect> fireworkEffects) {
        FireworkMeta itemMeta = (FireworkMeta)this.currentItem.getItemMeta();
        itemMeta.addEffects(fireworkEffects);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setMapScaling(boolean flag) {
        MapMeta itemMeta = (MapMeta)this.currentItem.getItemMeta();
        itemMeta.setScaling(flag);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setBasePotionData(PotionType potionType) {
        PotionMeta itemMeta = (PotionMeta)this.currentItem.getItemMeta();
        itemMeta.setBasePotionData(new PotionData(potionType));
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setPotionMainEffect(PotionEffectType potionEffectType) {
        PotionMeta itemMeta = (PotionMeta)this.currentItem.getItemMeta();
        itemMeta.setMainEffect(potionEffectType);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder addPotionCustomEffect(PotionEffect potionEffect, boolean overwrite) {
        PotionMeta itemMeta = (PotionMeta)this.currentItem.getItemMeta();
        itemMeta.addCustomEffect(potionEffect, overwrite);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder removePotionCustomEffect(PotionEffectType potionEffectType) {
        PotionMeta itemMeta = (PotionMeta)this.currentItem.getItemMeta();
        itemMeta.removeCustomEffect(potionEffectType);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setSkullOwner(String skullOwner) {
        SkullMeta itemMeta = (SkullMeta)this.currentItem.getItemMeta();
        itemMeta.setOwner(skullOwner);
        this.currentItem.setItemMeta((ItemMeta)itemMeta);
        return this;
    }

    public ItemBuilder setSkullTexture(String texture) {
        try {
            SkullMeta itemMeta = (SkullMeta)this.currentItem.getItemMeta();
            GameProfile profile = (GameProfile)this.plugin.getReflectionUtil().get(itemMeta, "profile");
            if (profile == null)
                profile = new GameProfile(UUID.randomUUID(), "customSkull");
            profile.getProperties().put("textures", new Property("textures", texture));
            this.plugin.getReflectionUtil().set(itemMeta, "profile", profile);
            this.currentItem.setItemMeta((ItemMeta)itemMeta);
            return this;
        } catch (Exception exception) {
            return this;
        }
    }

    public ItemStack build() {
        return this.currentItem;
    }

}