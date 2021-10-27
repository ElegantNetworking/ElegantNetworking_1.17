package hohserg.elegant.networking.impl;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.io.IOException;

public interface ISerializer<Packet> extends ISerializerBase<Packet>, RegistrableSingletonSerializer {

    default void serialize_BlockPos_Generic(BlockPos value, ByteBuf acc) {
        acc.writeInt(value.getX());
        acc.writeInt(value.getY());
        acc.writeInt(value.getZ());
    }

    default BlockPos unserialize_BlockPos_Generic(ByteBuf buf) {
        return new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    default void serialize_CompoundTag_Generic(CompoundTag value, ByteBuf acc) {
        Preconditions.checkNotNull(value);
        try {
            NbtIo.write(value, new ByteBufOutputStream(acc));
        } catch (IOException e) {
            throw new EncoderException("Failed to write CompoundTag to packet.", e);
        }
    }


    default CompoundTag unserialize_CompoundTag_Generic(ByteBuf buf) {
        try {
            return NbtIo.read(new ByteBufInputStream(buf), new NbtAccounter(2097152L));
        } catch (IOException e) {
            throw new EncoderException("Failed to read CompoundTag from packet.", e);
        }
    }

    default void serialize_ItemStack_Generic(ItemStack value, ByteBuf acc) {
        new FriendlyByteBuf(acc).writeItemStack(value, false);
    }

    default ItemStack unserialize_ItemStack_Generic(ByteBuf buf) {
        return new FriendlyByteBuf(buf).readItem();
    }

    default void serialize_FluidStack_Generic(FluidStack value, ByteBuf acc) {
        new FriendlyByteBuf(acc).writeFluidStack(value);
    }

    default FluidStack unserialize_FluidStack_Generic(ByteBuf buf) {
        return new FriendlyByteBuf(buf).readFluidStack();
    }

    default void serialize_ResourceLocation_Generic(ResourceLocation value, ByteBuf acc) {
        serialize_String_Generic(value.toString(), acc);
    }

    default ResourceLocation unserialize_ResourceLocation_Generic(ByteBuf buf) {
        return new ResourceLocation(unserialize_String_Generic(buf));
    }
}