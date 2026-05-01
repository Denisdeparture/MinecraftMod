package com.ctfow.createthefactoryofwar.registry;

import com.ctfow.createthefactoryofwar.CreateOfWar;
import com.ctfow.createthefactoryofwar.item.CustomClue;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ModItems {
    public static final ItemEntry<CustomClue> RADAR_GOGGLES = CreateOfWar.REGISTRATE
            .item("ctfow_clue", CustomClue::new)
            .register();
}
