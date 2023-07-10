package tech.stystatic.commandbridge.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommandBridgeConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> PORT;
    public static final ForgeConfigSpec.ConfigValue<String> IPADDRESS;

    static {
        BUILDER.push("Configs for Chat Bridge");

        PORT = BUILDER.comment("Port for outgoing connections")
                        .define("Port", 2832);

        IPADDRESS = BUILDER.comment("Server IP address (Set to public ip of server, Local IP or Loopback for Intranetwork)")
                        .define("Ip", "127.0.0.1");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
