package hohserg.elegant.networking.impl;


import io.netty.buffer.ByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/*
pattern:

ABC_([A-z]+)

replacement:

default void serialize_$1_Generic($1 value, ByteBuf acc) {
    RegistryHandler.instance.serializeSingleton($1.class, value, acc);
}

default $1 unserialize_$1_Generic(ByteBuf buf) {
    return RegistryHandler.instance.unserializeSingleton($1.class, buf);
}
*/
public interface RegistrableSingletonSerializer {

    default void serialize_Item_Generic(Item value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(Item.class, value, acc);
    }

    default Item unserialize_Item_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(Item.class, buf);
    }

    default void serialize_Block_Generic(Block value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(Block.class, value, acc);
    }

    default Block unserialize_Block_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(Block.class, buf);
    }

    default void serialize_Fluid_Generic(Fluid value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(Fluid.class, value, acc);
    }

    default Fluid unserialize_Fluid_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(Fluid.class, buf);
    }

    default void serialize_Potion_Generic(Potion value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(Potion.class, value, acc);
    }

    default Potion unserialize_Potion_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(Potion.class, buf);
    }

    default void serialize_Biome_Generic(Biome value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(Biome.class, value, acc);
    }

    default Biome unserialize_Biome_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(Biome.class, buf);
    }

    default void serialize_SoundEvent_Generic(SoundEvent value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(SoundEvent.class, value, acc);
    }

    default SoundEvent unserialize_SoundEvent_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(SoundEvent.class, buf);
    }

    default void serialize_MobEffect_Generic(MobEffect value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(MobEffect.class, value, acc);
    }

    default MobEffect unserialize_MobEffect_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(MobEffect.class, buf);
    }

    default void serialize_Enchantment_Generic(Enchantment value, ByteBuf acc) {
        RegistryHandler.instance.serializeSingleton(Enchantment.class, value, acc);
    }

    default Enchantment unserialize_Enchantment_Generic(ByteBuf buf) {
        return RegistryHandler.instance.unserializeSingleton(Enchantment.class, buf);
    }


    enum RegistryHandler implements IRegistryHandlerBase {
        instance;

        RegistryHandler() {
            register(Item.class, ForgeRegistries.ITEMS);
            register(Block.class, ForgeRegistries.BLOCKS);
            register(Fluid.class, ForgeRegistries.FLUIDS);
            register(Potion.class, ForgeRegistries.POTIONS);
            register(Biome.class, ForgeRegistries.BIOMES);
            register(SoundEvent.class, ForgeRegistries.SOUND_EVENTS);
            register(MobEffect.class, ForgeRegistries.MOB_EFFECTS);
            register(Enchantment.class, ForgeRegistries.ENCHANTMENTS);
        }

        private <A extends IForgeRegistryEntry<A>> void register(Class<A> type, IForgeRegistry<A> forgeRegistry) {
            register(type, ((ForgeRegistry<A>) forgeRegistry)::getID, ((ForgeRegistry<A>) forgeRegistry)::getValue);
        }
    }
}


