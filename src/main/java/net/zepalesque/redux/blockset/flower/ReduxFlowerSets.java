package net.zepalesque.redux.blockset.flower;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.natural.bush.CustomBoundsFlowerBlock;
import net.zepalesque.redux.blockset.flower.type.BaseFlowerSet;
import net.zepalesque.redux.blockset.flower.type.EnchantedFlowerSet;
import net.zepalesque.redux.blockset.flower.type.UntintedFlowerSet;
import net.zepalesque.redux.world.tree.ReduxTreeGrowers;
import net.zepalesque.zenith.api.blockset.AbstractFlowerSet;
import net.zepalesque.zenith.block.util.CommonPlantBounds;

public class ReduxFlowerSets {

    public static final BaseFlowerSet<CustomBoundsFlowerBlock.Enchanted> AURUM = register(new EnchantedFlowerSet<>("aurum", "natural/",
            () -> new CustomBoundsFlowerBlock.Enchanted(CommonPlantBounds.FLOWER,
                    () -> MobEffects.LUCK, 60, Properties.ofFullCopy(Blocks.DANDELION).hasPostProcess((s, l, p) -> true)), 1, 0xFFFFFF)
            .creativeTab(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.WHITE_FLOWER)
            .craftsIntoShapeless(1, () -> Items.YELLOW_DYE, 1, RecipeCategory.MISC)
            .withFlowerTag(BlockTags.FLOWERS)
            .withPotTag(BlockTags.FLOWER_POTS)
            .withLore("A golden flower found in the Gilded Groves. Some say it brings good luck!"));

    public static final BaseFlowerSet<SaplingBlock> CLOUDROOT_SAPLING = register(new UntintedFlowerSet<>("cloudroot_sapling", "natural/",
            () -> new SaplingBlock(ReduxTreeGrowers.CLOUDROOT, Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.QUARTZ)))
            .creativeTab(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.SKYROOT_SAPLING)
            .withFlowerTag(BlockTags.SAPLINGS)
            .withPotTag(BlockTags.FLOWER_POTS)
            .compost(0.3F)
            .withLore("The sapling of the Cloudroot tree. It can be grown by waiting or using Bone Meal."));

    public static <T extends AbstractFlowerSet> T register(T set) {
        Redux.BLOCK_SETS.add(set);
        return set;
    }

    public static void init() {}
}