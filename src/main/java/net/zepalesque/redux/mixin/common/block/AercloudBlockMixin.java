package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.AercloudBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.util.compat.GenesisCompatUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AercloudBlock.class)
public class AercloudBlockMixin {
    @Inject(at = @At(value = "HEAD"), method = "entityInside")
    private void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        AercloudBlock block = (AercloudBlock) (Object) this;
        if (GenesisCompatUtil.goldCloudSFX(block)) {
            if (!entity.isShiftKeyDown()) {
                level.playSound((entity instanceof Player player ? player : null), pos, ReduxSoundEvents.GOLD_AERCLOUD_WHOOSH.get(), SoundSource.BLOCKS, 0.8f,
                        0.9f + (level.random.nextFloat() * 0.2f));
            }
        }
    }
}
