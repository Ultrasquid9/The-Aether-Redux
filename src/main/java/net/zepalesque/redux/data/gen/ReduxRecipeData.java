package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.recipe.recipes.InfusionRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ReduxRecipeData extends ReduxRecipeProvider {

    public ReduxRecipeData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Redux.MODID);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        Redux.WOOD_SETS.forEach(set -> set.recipeData(this, output));
        Redux.STONE_SETS.forEach(set -> set.recipeData(this, output));

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.VERIDIUM_NUGGET.get(), RecipeCategory.MISC, ReduxItems.VERIDIUM_INGOT.get(), "veridium_nugget", "veridium_nugget_to_veridium_ingot");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.SENTRITE_CHUNK.get(), RecipeCategory.MISC, ReduxItems.REFINED_SENTRITE.get(), "sentrite_chunk", "sentrite_chunk_to_refined_sentrite");
        smeltingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxBlocks.VERIDIUM_ORE.get(), 0.8F).save(output, name("smelt_veridium"));
        blastingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxBlocks.VERIDIUM_ORE.get(), 0.8F).save(output, name("blast_veridium"));
        smeltingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxItems.RAW_VERIDIUM.get(), 0.8F).save(output, name("smelt_raw_veridium"));
        blastingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxItems.RAW_VERIDIUM.get(), 0.8F).save(output, name("blast_raw_veridium"));
        smeltingOreRecipe(ReduxItems.REFINED_SENTRITE.get(), ReduxStoneSets.SENTRITE.block().get(), 0.8F, 300).save(output, name("refine_sentrite_smelt"));
        blastingOreRecipe(ReduxItems.REFINED_SENTRITE.get(), ReduxStoneSets.SENTRITE.block().get(), 0.8F, 150).save(output, name("refine_sentrite_blast"));

        enchantingRecipe(RecipeCategory.DECORATIONS, ReduxBlocks.CLOUDROOT_SAPLING.get(), AetherBlocks.SKYROOT_SAPLING.get(), 0.1F, 1000).save(output);
        ambrosiumEnchanting(ReduxBlocks.CLOUDROOT_LEAVES.get(), AetherBlocks.SKYROOT_LEAVES.get()).save(output);

        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_BASE.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_PILLAR.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_BASE.get(), AetherBlocks.SENTRY_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_PILLAR.get(), AetherBlocks.SENTRY_STONE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_BASE.get(), 4)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.CARVED_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_PILLAR.get(), 6)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.CARVED_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_BASE.get(), 4)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.SENTRY_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_PILLAR.get(), 6)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.SENTRY_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);

        CompoundTag infusionInfo = new CompoundTag();
        infusionInfo.putByte(InfusionRecipe.ADDED_INFUSION, (byte) 4);
        Holder<SoundEvent> infusionSound = ReduxSounds.INFUSE_ITEM;

        infuse(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), ReduxItems.VERIDIUM_PICKAXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_pickaxe_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), ReduxItems.INFUSED_VERIDIUM_PICKAXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_pickaxe_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_AXE.get(), ReduxItems.VERIDIUM_AXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_axe_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_AXE.get(), ReduxItems.INFUSED_VERIDIUM_AXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_axe_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(), ReduxItems.VERIDIUM_SHOVEL.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_shovel_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(), ReduxItems.INFUSED_VERIDIUM_SHOVEL.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_shovel_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_HOE.get(), ReduxItems.VERIDIUM_HOE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_hoe_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_HOE.get(), ReduxItems.INFUSED_VERIDIUM_HOE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_hoe_increase_infusion"));

        makePickaxe(ReduxItems.VERIDIUM_PICKAXE, ReduxItems.VERIDIUM_INGOT).save(output);
        makeShovel(ReduxItems.VERIDIUM_SHOVEL, ReduxItems.VERIDIUM_INGOT).save(output);
        makeAxe(ReduxItems.VERIDIUM_AXE, ReduxItems.VERIDIUM_INGOT).save(output);
        makeHoe(ReduxItems.VERIDIUM_HOE, ReduxItems.VERIDIUM_INGOT).save(output);
//        makeSword(ReduxItems.VERIDIUM_SWORD, ReduxItems.VERIDIUM_INGOT).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_LANTERN.get(), 1)
                .define('C', ReduxItems.SENTRITE_CHUNK.get())
                .define('A', AetherBlocks.AMBROSIUM_TORCH.get())
                .pattern("CCC")
                .pattern("CAC")
                .pattern("CCC")
                .unlockedBy(getHasName(ReduxItems.SENTRITE_CHUNK.get()), has(ReduxItems.SENTRITE_CHUNK.get()))
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .unlockedBy(getHasName(AetherBlocks.AMBROSIUM_TORCH.get()), has(AetherBlocks.AMBROSIUM_TORCH.get()))
                .unlockedBy(getHasName(AetherItems.AMBROSIUM_SHARD.get()), has(AetherItems.AMBROSIUM_SHARD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_CHAIN.get(), 3)
                .define('I', ReduxItems.REFINED_SENTRITE.get())
                .define('N', ReduxItems.SENTRITE_CHUNK.get())
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .unlockedBy(getHasName(ReduxItems.SENTRITE_CHUNK.get()), has(ReduxItems.SENTRITE_CHUNK.get()))
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .save(output);

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.VERIDIUM_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.VERIDIUM_BLOCK.get(), "veridium_ingot_from_veridium_block", "veridium_ingot");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.RAW_VERIDIUM.get(), RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.RAW_VERIDIUM_BLOCK.get(), "raw_veridium_from_raw_veridium_block", "raw_veridium");

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.REFINED_SENTRITE.get(), RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.REFINED_SENTRITE_BLOCK.get(), "refined_sentrite_from_refined_sentrite_block", "refined_sentrite");

    }
}