package net.zepalesque.redux.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.TooltipUtils;
import net.zepalesque.redux.item.VeridiumItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VeridiumHoeItem extends HoeItem implements VeridiumItem {
    private final Supplier<? extends Item> uninfused;

    public VeridiumHoeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, Supplier<? extends Item> uninfused) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.uninfused = uninfused;
    }

    @Override
    public Item getUninfusedItem(ItemStack stack) {
        return this.uninfused.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
        MutableComponent infusion = Component.translatable("tooltip.aether_redux.infusion_charge", stack.getTag() == null ? 0 : stack.getTag().getByte(VeridiumItem.NBT_KEY)).withStyle(ChatFormatting.GRAY);

        tooltips.add(infusion);
        Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("gui.aether_redux.infusion_info"));
        tooltips.add(info);
        super.appendHoverText(stack, level, tooltips, advanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int amount = target.getType().is(ReduxTags.Entities.VALID_PICKAXE_TARGETS) ? 1 : 2;
        boolean bool = super.hurtEnemy(stack, target, attacker);
        ItemStack transform = this.deplete(stack, attacker, amount);
        if (!attacker.level().isClientSide() && transform != null && transform != stack) {
            attacker.setItemSlot(EquipmentSlot.MAINHAND, transform);
            if (attacker instanceof ServerPlayer sp) {
                this.sendSound(sp);
            }
        }
        return bool;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity user) {
        // Call the vanilla method do do things like tool damaging
        boolean bool = super.mineBlock(stack, level, state, pos, user);
        if (!user.level().isClientSide()) {
            boolean instaBreak = state.getDestroySpeed(level, pos) <= 0.0F;
            // Avoid decreasing infusion on insta-break blocks
            if (!instaBreak) {
                int amount = stack.isCorrectToolForDrops(state) ? 1 : 2;
                ItemStack transform = this.deplete(stack, user, amount);
                if (!user.level().isClientSide() && transform != null && transform != stack) {
                    user.setItemSlot(EquipmentSlot.MAINHAND, transform);
                    if (user instanceof ServerPlayer sp) {
                        this.sendSound(sp);
                    }
                }
            }
        }
        return bool;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken) * VeridiumItem.DURABILITY_DMG_MULTIPLIER;
    }

    public static class Uninfused extends HoeItem {

        public Uninfused(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
            super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
            Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("gui.aether_redux.infusion_info"));
            tooltips.add(info);
            super.appendHoverText(stack, level, tooltips, advanced);
        }
    }
}
