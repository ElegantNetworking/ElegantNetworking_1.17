package hohserg.elegant.networking.impl;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public interface Network<PacketRepresentation> {

    Network defaultImpl =
            Main.config.getBackgroundPacketSystem() == Config.BackgroundPacketSystem.CCLImpl ?
                    throwUnsupportedCCL()
                    :
                    new ForgeNetworkImpl();

    static Network throwUnsupportedCCL() {
        throw new RuntimeException("CCLImpl is unsupported now. Turn it in elegant_networking.cfg");
    }

    static Network getNetwork() {
        return defaultImpl;
    }

    void sendToPlayer(ServerToClientPacket packet, ServerPlayer player);

    void sendToClients(ServerToClientPacket packet);

    void sendPacketToAllAround(ServerToClientPacket packet, Level world, double x, double y, double z, double range);

    void sendToDimension(ServerToClientPacket packet, Level world);

    void sendToChunk(ServerToClientPacket packet, Level world, int chunkX, int chunkZ);

    void sendToServer(ClientToServerPacket packet);

    void onReceiveClient(PacketRepresentation packetRepresent, String channel);

    void onReceiveServer(PacketRepresentation packetRepresent, ServerPlayer player, String channel);

    void registerChannel(String channel);

    default void checkSendingSide(ServerToClientPacket packet) {
        if (EffectiveSide.get() == LogicalSide.CLIENT)
            throw new RuntimeException("Attempt to send ServerToClientPacket from client side: " + packet.getClass().getCanonicalName());
    }

    default void checkSendingSide(ClientToServerPacket packet) {
        if (EffectiveSide.get() == LogicalSide.SERVER)
            throw new RuntimeException("Attempt to send ClientToServerPacket from server side: " + packet.getClass().getCanonicalName());
    }


}
