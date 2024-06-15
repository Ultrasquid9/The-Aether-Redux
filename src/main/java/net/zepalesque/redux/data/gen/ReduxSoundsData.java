package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.prov.ReduxSoundsProvider;
import net.zepalesque.zenith.util.DatagenUtil;

public class ReduxSoundsData extends ReduxSoundsProvider {
    public ReduxSoundsData(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(ReduxSounds.INFUSE_ITEM, sound ->
                definition().with(sound("aether_redux:item/generic/infuse_item"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));
        this.add(ReduxSounds.INFUSION_EXPIRE, sound ->
                definition().with(sound("aether_redux:item/generic/infusion_expire"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));
    }
}