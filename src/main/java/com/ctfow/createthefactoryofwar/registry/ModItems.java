package com.ctfow.createthefactoryofwar.registry;

import com.ctfow.createthefactoryofwar.CreateOfWar;
import com.ctfow.createthefactoryofwar.item.CustomClue;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ModItems {
    public static final ItemEntry<CustomClue> CUSTOM_GLUE = CreateOfWar.REGISTRATE
            .item("custom_glue", CustomClue::new)
            .register();

    public static void register() {
        CreateOfWar.LOGGER.info("Registering Items!");
    }
}
