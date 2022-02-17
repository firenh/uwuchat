package fireopal.uwuchat;

import java.util.Random;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class UwuChat implements ModInitializer {
    public static final FOModVersion VERSION = FOModVersion.fromString("0.1.0");
    private static final String MODID = "uwuchat"; 
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static Random random = new Random();

    public static Random getRandom() {
        return random;
    }

    public static boolean isUwuMessage() {
        return random.nextFloat(0.0f, 1.0f) < CONFIG.chance;
    }

    public static Config CONFIG;

    public static void loadConfigFromFile() {
        CONFIG = Config.init();
        if (CONFIG.log_when_loaded) LOGGER.info("Loaded config for " + MODID);
    }

    @Override
    public void onInitialize() {
        loadConfigFromFile();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                (LiteralArgumentBuilder<ServerCommandSource>) CommandManager.literal("uwu")
                    .then(CommandManager.argument("message", MessageArgumentType.message())
                        .executes(UwuChat::executeCommand)
                    )
            );
        });
    }

    private static int executeCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var source = context.getSource();

        // TranslatableText text = new TranslatableText("chat.type.text", player == null ? "@" : player.getDisplayName(), context.getArgument("message", MessageArgumentType.class));
        Text text = MessageArgumentType.getMessage(context, "message");
        TranslatableText text2 = new TranslatableText("chat.type.text", source.getDisplayName(), text);
        source.getServer().getPlayerManager().broadcast(text2, MessageType.CHAT, source.getEntity().getUuid());

        return 1;
    }
}
