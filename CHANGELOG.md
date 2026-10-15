# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.20.1-2.1.0] - Unreleased

The Null Void is now a more dangerous place to explore, with new creatures, prison structures and Plumber gear.

### Added
- Null Void prisoners hunt in packs, flank players, flee when badly hurt and sometimes fight each other.
- Null Guardians patrol the islands and pull targets in with a tractor beam. Lepidopterran scouts dive-bomb and spit
  slowing slime.
- Floating prison blocks hold prisoner spawners, a prison warden and a key-locked loot vault. Wrecked Plumber outposts
  contain supply chests.
- Taydenite ore and storage blocks, guardian membranes, prisoner cuffs, cell keys and Plumber loot.
- A Plumber blaster, Null Void glider and full Plumber armour set, with recipes and set bonuses.
- Flight animations: Heatblast flies like a rocket with a flame trail, Stinkfly beats his wings, Ghostfreak drifts.
- XLR8 has a sprint animation (leaned forward, legs a blur, arms swept back, tail streaming) with a blue speed streak
  and kicked-up dust behind him. Sprinting fast enough, he runs across water; slow down or sneak and he drops in.
- Transformed camera height now follows each alien's hitbox. Every transformation has a green particle burst and a
  third-person morph; reverts use green or red bursts.
- Holding the power key repeats powers when they are ready. Alien powers have tuned cooldowns and a cooldown bar.
- The Null Void Projector opens a walkable portal that pulls in nearby creatures and items, with arrival guidance for
  the Null Void's floating islands.
- Vilgax, Kraab, Zs'Skayr and the prison warden speak to nearby players during fights.
- The Plumber blaster fires a fixed-damage energy bolt instead of an arrow.
- Prison cells hold loot chests. The Prison Vault now gives diamonds, more taydenite, Null Crystals and Infinitum, two
  Plumber armour pieces, a blaster or glider, and golden apples (sometimes an enchanted one).
- Your first trip to the Null Void hands you a Null Void Glider (in a chest beside you if your inventory is full).
- Beating the prison warden tells nearby players to use its Cell Key on the vault. Every Null Void arrival shows how to
  get home (use the projector again), and the guide book explains it.

### Changed
- Stinkfly's flying wingbeat is slower and wider, so the flap is visible.
- The Null Void landing pad is lit by sea lanterns at its corners instead of a tall end-rod pillar.
- Armour and tool stats follow the ore ladder. Starter sets are around iron, Legendary and Black Diamond around
  diamond, Infinitum around netherite; no armour piece gives more than 8 points. Black Diamond tools are diamond tier,
  Infinitum and Imperium netherite tier.

### Fixed
- The Null Void had no islands between about 150 and 1,000 blocks from its centre (it reused the End's island layout),
  and the projector ignored islands in chunks that hadn't loaded yet, so most trips ended on the emergency pad. The
  Null Void now has its own floating islands everywhere, and arrivals land on the nearest one. Chunks generated
  before this update stay empty, so explore new areas or use a new world.
- The "nearest island" hint pointed at the emergency pad itself ("about 0 blocks").
- Flying mobs (Zs'Skayr, Vilgax drones, Null Guardians, Lepidopterrans) crashed the game when they took off.
- Leggings gave more armour than chestplates (values were in the wrong slot order).
- Mod armour wore out about twice as fast as vanilla armour of the same tier.
- Heatblast fireballs now damage fire-immune bosses, and Plumber armour resolves its namespaced textures.
- The power key (V) stopped working after dying or changing dimension, and the transformation animation could stick
  after changing dimension as an alien.

## [1.20.1-2.0.1] - 15/10/2026

### Fixed
- The game crashed when Zs'Skayr or a Vilgax drone took off from the ground.

## [1.20.1-2.0.0] - 14/10/2026

A rebuild of the mod for Minecraft 1.20.1 on Forge and Fabric, with a working Omnitrix.

### Added
- **Fabric support.** One mod for Forge (and NeoForge 1.20.1) and Fabric. Requires GeckoLib.
- **The Omnitrix.** Find it in a rare crash-site crater or build it in the Omnitrix Crafter. Right-click to strap it
  on for good. Open the dial with `G` or by right-clicking the air with an empty hand.
- **10 aliens in 3D:** Heatblast, Four Arms, XLR8, Diamondhead, Grey Matter, Ripjaws, Stinkfly, Upgrade, Wildmutt and
  Ghostfreak. Each has its own size, abilities, first-person arms and attack animation.
- **Alien powers on `V`:** Heatblast's fireballs, Four Arms' ground slam, XLR8's dash and more.
- **`H` to change back** to human.
- **DNA scanning:** unlock aliens by sneaking and right-clicking creatures and blocks with an empty hand.
- **Transformation timer:** 5 minutes per transformation with an on-screen countdown, then a 60-second recharge.
- **Omnitrix Field Guide:** a book every new player gets that explains the mod.
- **Bosses:**
  - **Kraab** hunts you down after you first equip the watch.
  - **Vilgax** comes when you use a Vilgax Beacon. He has two phases and sends drones.
  - **Zs'Skayr** waits in the Null Void.
- **The Null Void:** a new dimension of floating islands with escaped prisoners, null crystals and rare fortresses.
  Get there with the Null Void Projector.
- **Master Control:** dropped by Zs'Skayr. Unlocks every alien and removes the time limit.
- In creative mode, every alien is on the dial.

### Changed
- Alien armour sets are weaker. Ores are rarer the more powerful they are.
- The Omnitrix Crafter has a new look, and Ben 10 gear is crafted in it.
- New Omnitrix icon and a watch model on the player's wrist.

### Fixed
- Ores now generate in the world.
- Deepslate ruby and black diamond ores drop correctly with Silk Touch.
- Dying while transformed no longer leaves you stuck as the alien.

## [1.19.4-1.0.0] - 20/08/2023

### Ported to 1.19.4
### Fixed issue with a recipe book
### Fixed issue with omnitrix crafter
### Smiting table recipies removed till I make my own smiting table
### Fixed issue with omnitrix crafter

## [1.19.3-1.0.4] - 25/02/2023

### Fixed an issue where blocks where unminable

## [1.19.3-1.0.3] - 22/01/2023

## updated updateJSONURL

## [1.19.3-1.0.2] - 22/01/2023

## Fixed some bugs.
## Added custom recipe book

## [1.19.3-1.0.1] - 25/12/2022

### Tweaked some things with recipes
### Update RealYusufIsmail core version

## [1.19.3-1.0.0] - 22/12/2022

### Ported to 1.19.3
### Re Coded Ore Generation
### Removed vilgax. Will be added back in the future

## [1.19.1-1.0.0]
### Ported to 1.19.1. Some changes include:
#### TranslationTextComponent changed to Component.translatable
#### The way ore generation works has changed massively

## [1.17.1-1.10] - 25/09/2021

### Ported to 1.17.1. Everything works apart from the custom table. Will be fixed in the next version. Does not require Patchouli for 1.17.1 but will in 1.18. Still requires my core mod RealYusufIsmail Core.

## [1.16.5-1.17] - 19/06/2021

### Fixed some models and added a custom smithing table. Removed experimental features and four arms. Added Jei support still in beta. New alien coming soon. 1.17 forge coming soon which means i can start making 1.17. New alien table coming soon. Final release for another week or 2 weeks. Changes can be seen on GitHub in my 1.17-beta brach. I am working really hard on this so please consider supporting me by donating to my goFundMe. The omnitrix idea has been delayed might come out in 2022 or in December while i learn how to make it plus i am learning Java and i do this in my spare time and consider joining my discord. The link is https://discord.gg/kbVDCfvVKe. Thats all for this update.
