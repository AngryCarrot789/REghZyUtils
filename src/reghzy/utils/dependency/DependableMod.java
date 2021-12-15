package reghzy.utils.dependency;

/**
 * A dependency upon a mod (lmao this shouldn't even be possible with plugins but i made it work >:))
 */
public enum DependableMod {
    None(null),
    MCP("mcp"),
    FML("FML"),
    Forge("Forge"),
    CodeChickenCode("CodeChickenCore"),
    Microblocks("ImmibisMicroblocks"),
    MicdoodleCore("Micdoodlecore"),
    NotEnoughItems("NotEnoughItems"),
    OpenModsCore("OpenModsCore"),
    PowerCrystalsCore("PowerCrystalsCore"),
    AppliedEnergistics("AppliedEnergistics"),
    AppliedEnergisticsCore("AppliedEnergistics-Core"),
    AtomicScience("AtomicScience"),
    Autoutils("Autoutils"),
    Backpack("Backpack"),
    BigReactors("BigReactors"),
    bspkrsCore("bspkrsCore"),
    BuildCraft("BuildCraft|Builders"),
    BuildCraftCore("BuildCraft|Core"),
    BuildCraftEnergy("BuildCraft|Energy"),
    BuildCraftFactory("BuildCraft|Factory"),
    BuildCraftSilicon("BuildCraft|Silicon"),
    BuildCraftTransport("BuildCraft|Transport"),
    Additional("Additional-Buildcraft-Objects"),
    CalclaviaCore("CalclaviaCore"),
    CarpentersBlocks("CarpentersBlocks"),
    ChickenChunks("ChickenChunks"),
    CoFHCore("CoFHCore"),
    CoFHLoot("CoFHLoot"),
    CoFHMasquerade("CoFHMasquerade"),
    CoFHSocial("CoFHSocial"),
    CoFHWorld("CoFHWorld"),
    CCTurtle("CCTurtle"),
    ComputerCraft("ComputerCraft"),
    DimensionalAnchors("DimensionalAnchors"),
    dimdoors("dimdoors"),
    EnderStorage("EnderStorage"),
    EE3("EE3"),
    extracells("extracells"),
    ExtraUtilities("ExtraUtilities"),
    GalacticraftCore("GalacticraftCore"),
    GalacticraftMars("GalacticraftMars"),
    GregsLighting("GregsLighting"),
    ImmibisCore("ImmibisCore"),
    inventorytweaks("inventorytweaks"),
    IronChest("IronChest"),
    LogisticsPipes("LogisticsPipes|Main"),
    MFRForestry("MFR Compat Forestry Trees"),
    MFRExtraTrees("MFR Compat Extra Trees"),
    MineFactoryReloaded("MineFactoryReloaded"),
    MineFactoryReloadedCompatAppliedEnergistics("MineFactoryReloaded|CompatAppliedEnergistics"),
    MineFactoryReloadedCompatAtum("MineFactoryReloaded|CompatAtum"),
    MineFactoryReloadedCompatBackTools("MineFactoryReloaded|CompatBackTools"),
    MineFactoryReloadedCompatBuildCraft("MineFactoryReloaded|CompatBuildCraft"),
    MineFactoryReloadedCompatChococraft("MineFactoryReloaded|CompatChococraft"),
    MineFactoryReloadedCompatExtraBiomes("MineFactoryReloaded|CompatExtraBiomes"),
    MineFactoryReloadedCompatForestry("MineFactoryReloaded|CompatForestry"),
    MineFactoryReloadedCompatForestryPre("MineFactoryReloaded|CompatForestryPre"),
    MineFactoryReloadedCompatForgeMicroblock("MineFactoryReloaded|CompatForgeMicroblock"),
    MineFactoryReloadedCompatIC2("MineFactoryReloaded|CompatIC2"),
    MineFactoryReloadedCompatMystcraft("MineFactoryReloaded|CompatMystcraft"),
    MineFactoryReloadedCompatPams("MineFactoryReloaded|CompatPams"),
    MineFactoryReloadedCompatProjRed("MineFactoryReloaded|CompatProjRed"),
    MineFactoryReloadedCompatRailcraft("MineFactoryReloaded|CompatRailcraft"),
    MineFactoryReloadedCompatRP2("MineFactoryReloaded|CompatRP2"),
    MineFactoryReloadedCompatSufficientBiomes("MineFactoryReloaded|CompatSufficientBiomes"),
    MineFactoryReloadedCompatThaumcraft("MineFactoryReloaded|CompatThaumcraft"),
    MineFactoryReloadedCompatThermalExpansion("MineFactoryReloaded|CompatThermalExpansion"),
    MineFactoryReloadedCompatTwilightForest("MineFactoryReloaded|CompatTwilightForest"),
    MineFactoryReloadedCompatVanilla("MineFactoryReloaded|CompatVanilla"),
    MineFactoryReloadedCompatXyCraft("MineFactoryReloaded|CompatXyCraft"),
    MFFS("MFFS"),
    powersuits("powersuits"),
    Mystcraft("Mystcraft"),
    NEIPlugins("NEIPlugins"),
    NetherOres("NetherOres"),
    numina("numina"),
    OCS("OCS"),
    OpenMods("OpenMods"),
    OpenPeripheral("OpenPeripheral"),
    OpenPeripheralCore("OpenPeripheralCore"),
    powersuitaddons("powersuitaddons"),
    bau5_ProjectBench("bau5_ProjectBench"),
    ProjRedCore("ProjRed|Core"),
    ProjRedCompatibility("ProjRed|Compatibility"),
    ProjRedIntegration("ProjRed|Integration"),
    ProjRedTransmission("ProjRed|Transmission"),
    ProjRedIllumination("ProjRed|Illumination"),
    ProjRedExpansion("ProjRed|Expansion"),
    ProjRedTransportation("ProjRed|Transportation"),
    ProjRedExploration("ProjRed|Exploration"),
    QuantumCraft("QuantumCraft"),
    Redstone("Redstone Arsenal"),
    JAKJ_RedstoneInMotion("JAKJ_RedstoneInMotion"),
    simplyjetpacks("simplyjetpacks"),
    StevesCarts("StevesCarts"),
    ThermalExpansion("ThermalExpansion"),
    tradeboothmod("tradeboothmod"),
    TreeCapitator("TreeCapitator"),
    UniversalElectricity("UniversalElectricity"),
    Vending("Vending"),
    Waila("Waila"),
    weaponmod("weaponmod"),
    WRCBEAddons("WR-CBE|Addons"),
    WRCBECore("WR-CBE|Core"),
    WRCBELogic("WR-CBE|Logic"),
    ForgeMicroblock("ForgeMicroblock"),
    ForgeMultipart("ForgeMultipart"),
    McMultipart("McMultipart");

    public static final boolean isModded;

    static {
        boolean modded = false;
        try {
            cpw.mods.fml.common.Loader.isModLoaded("ok");
            modded = true;
        }
        catch (NoClassDefFoundError ignored) {
        }

        isModded = modded;
    }

    private final String modName;

    DependableMod(String pluginName) {
        this.modName = pluginName;
    }

    public String getModName() {
        return modName;
    }

    public boolean isAvailable() {
        if (this.modName == null) {
            return true;
        }

        if (!isModded) {
            return false;
        }

        return cpw.mods.fml.common.Loader.isModLoaded(this.modName);
    }
}
