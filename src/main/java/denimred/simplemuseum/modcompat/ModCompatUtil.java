package denimred.simplemuseum.modcompat;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import cryptcraft.cryptcomp.entity.EntityComponent;
import cryptcraft.cryptgui.CryptGuiScreen;
import cryptcraft.cryptmaster.CryptMasterMod;
import cryptcraft.cryptmaster.PossessableComponent;
import cryptcraft.cryptmaster.PossessionUtil;
import cryptcraft.cryptmaster.forge.CryptMasterForgeMod;
import denimred.simplemuseum.client.util.ClientUtil;
import denimred.simplemuseum.common.entity.MuseumPuppetEntity;
import denimred.simplemuseum.modcompat.cryptmaster.MuseumPlugin;
import denimred.simplemuseum.modcompat.cryptmaster.PuppetPossessableBehavior;

public class ModCompatUtil {
    public static void enqueueIMC(@SuppressWarnings("unused") final InterModEnqueueEvent event) {
        // Constants are inlined at compile time
        InterModComms.sendTo(
                CryptMasterMod.MOD_ID,
                CryptMasterForgeMod.REGISTER_PLUGIN_IMC,
                new MuseumPlugin.Thing());
    }

    public static boolean isCryptMasterActive() {
        return ModList.get().isLoaded(CryptMasterMod.MOD_ID)
                && ClientUtil.MC.currentScreen instanceof CryptGuiScreen;
    }

    public static boolean isCryptMasterPossessing(MuseumPuppetEntity puppet) {
        return ModList.get().isLoaded(CryptMasterMod.MOD_ID)
                && PossessionUtil.INSTANCE.isPossessed(puppet);
    }

    public static void registerCryptMasterPossession() {
        if (ModList.get().isLoaded(CryptMasterMod.MOD_ID)) {
            EntityComponent.INSTANCE.registerInitializer(
                    MuseumPuppetEntity.class,
                    PossessableComponent.class,
                    entity ->
                            PuppetPossessableBehavior.createComponent((MuseumPuppetEntity) entity));
        }
    }
}
