package fireopal.uwuchat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import fireopal.uwuchat.UwuChat;
import fireopal.uwuchat.Uwuifier;
import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    // @ModifyVariable(method = "handleMessage(Lnet/minecraft/server/filter/TextStream$Message;)V", at = @At("STORE"), ordinal = 0)
    @ModifyVariable(method = "onChatMessage(Lnet/minecraft/network/packet/c2s/play/ChatMessageC2SPacket;)V", at = @At("STORE"), ordinal = 0)
    private String string(String string) {
        System.out.println(string);

        if (string.charAt(0) == '/') {
            return string;
        }

        if (UwuChat.isUwuMessage()) {
            return Uwuifier.uwuify(string, UwuChat.getRandom());
        }

        // if (UwuChat.isUwuMessage()) {
        //     if (string.startsWith("/")) return string;
        //     return Uwuifier.uwuify(string, UwuChat.getRandom());
        // }

        return string;
    }
}