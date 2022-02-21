package fireopal.uwuchat.mixin;

import java.util.Collection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.uwuchat.UwuChat;
import fireopal.uwuchat.Uwuifier;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {
    @Inject(method = "tryReloadDataPacks", at = @At("RETURN"))
    private static void tryReloadDataPacks(Collection<String> dataPacks, ServerCommandSource source, CallbackInfo info) {
        try {
            UwuChat.loadConfigFromFile();
        } catch (Exception e) {}
    }
}
