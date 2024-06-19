package net.zepalesque.redux.config.enums;

import net.neoforged.fml.ModList;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

// Idk if this is a good idea lul
public enum AACompatType implements CharSequence, Supplier<Boolean> {
    TRUE("true", () -> true), FALSE("false", () -> false), WITHOUT_AA("without_aa", () -> !ModList.get().isLoaded("ancient_aether"));

    private final String serialized;
    private final Supplier<Boolean> value;

    AACompatType(String serialized, Supplier<Boolean> value) {
        this.serialized = serialized;
        this.value = value;
    }

    @Override
    public int length() {
        return serialized.length();
    }

    @Override
    public char charAt(int index) {
        return serialized.charAt(index);
    }

    @NotNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return serialized.subSequence(start, end);
    }

    @Override
    public String toString() {
        return serialized;
    }

    @Override
    public Boolean get() {
        return value.get();
    }
}