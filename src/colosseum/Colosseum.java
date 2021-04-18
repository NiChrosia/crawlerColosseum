package colosseum;

import arc.util.CommandHandler;
import arc.util.Log;
import colosseum.commands.CCommands;
import mindustry.mod.Plugin;

public class Colosseum extends Plugin {
    @Override
    public void init() {
        super.init();

        CEvents.init();

        Log.info("Loaded plugin Crawler Colosseum successfully.");
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        super.registerClientCommands(handler);

        CCommands.loadClient(handler);
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        super.registerServerCommands(handler);

        CCommands.loadServer(handler);
    }
}