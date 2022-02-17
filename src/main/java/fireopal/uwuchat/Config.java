package fireopal.uwuchat;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

public class Config {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Config Default Values

    public String CONFIG_VERSION_DO_NOT_TOUCH_PLS = UwuChat.VERSION.toString();

    public boolean enabled = true;
    public boolean enableCommand = true;
    public double chance = 0.01;
    public boolean log_when_loaded = false;
    
    //~~~~~~~~

    public static Config init() {
        Config config = null;

        try {
            Path configPath = Paths.get("", "config", "uwuchat.json");

            if (Files.exists(configPath)) {
                config = gson.fromJson(
                    new FileReader(configPath.toFile()),
                    Config.class
                );

                if (!config.CONFIG_VERSION_DO_NOT_TOUCH_PLS.equals(UwuChat.VERSION.toString())) {
                    config.CONFIG_VERSION_DO_NOT_TOUCH_PLS = UwuChat.VERSION.toString();

                    BufferedWriter writer = new BufferedWriter(
                        new FileWriter(configPath.toFile())
                    );

                    writer.write(gson.toJson(config));
                    writer.close();
                }

            } else {
                config = new Config();
                Paths.get("", "config").toFile().mkdirs();

                BufferedWriter writer = new BufferedWriter(
                    new FileWriter(configPath.toFile())
                );

                writer.write(gson.toJson(config));
                writer.close();
            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return config;
    }
}
