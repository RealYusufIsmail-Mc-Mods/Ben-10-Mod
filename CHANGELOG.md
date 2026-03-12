# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.16.5-2.0.0.beta.2] - 12/03/2026

### Added
- **Omnitrix system** — fully functional Omnitrix item that equips to the wrist slot via Curios API
- **10 playable aliens** — Heatblast, XLR8, Four Arms, Diamondhead, Grey Matter, Stinkfly, Ripjaws, Ghostfreak, Wildmutt, Upgrade
- **Alien abilities** — each alien has unique passive effects, combat bonuses, and special abilities:
  - Heatblast: fire immunity, flight, strength, fireball attack
  - XLR8: extreme speed boost, jump boost
  - Four Arms: high damage, knockback, strength
  - Diamondhead: high armor, toughness, crystal shard projectile
  - Grey Matter: night vision, intelligence buffs
  - Stinkfly: flight, poison cloud on attack
  - Ripjaws: water breathing, increased swim speed
  - Ghostfreak: invisibility, phasing (no collision)
  - Wildmutt: speed, jump boost, enhanced tracking
  - Upgrade: technology enhancement, armor buff
- **Alien DNA items** — 10 DNA samples (one per alien) used to unlock aliens in the Omnitrix
- **Omnitrix GUI** — radial dial screen (press O) to select alien transformation
- **Omnitrix HUD** — on-screen overlay showing current alien, transformation timer, and cooldown bar
- **Curios wrist slot** — Omnitrix equips permanently to wrist; cannot be removed once worn
- **Capability system** — persistent player data tracking unlocked aliens, transformation state, and cooldowns
- **Keybinds** — O to open Omnitrix dial, R to revert to human form
- **Omnitrix crafting recipe** — crafted with Omnitrix ingots, Legendary ingots, and Imperium ingots
- **Multiplayer sync** — alien transformations visible to other players

### Fixed
- Malformed UUIDs in alien ability attribute modifiers causing crash on transformation
- Duplicate keybind registration showing two entries per key in Controls screen
- Alien DNA items showing aliens as locked immediately after use (now syncs instantly)
- Omnitrix item renamed from "Omnitrix Ingot" to "Omnitrix"
- Removed duplicate plain Omnitrix item registration

### Changed
- Omnitrix now uses Curios API wrist slot instead of a regular inventory item
- Right-click equips the Omnitrix; once equipped it cannot be removed

### Data / Lang
- Added translations for all 10 DNA items, 3 keybinds, and keybind category
- Fixed typos: Legendary Block, Infinitum Block, Fire Block, Infinitum Ore, Raw Legendary, Ultimate Imperium Pickaxe

## [1.16.5-2.0.0.beta.1] - 19/12/2023

### Restarting work on 1.16.5 version of this mod. A lot to change in the coming versions.

## [1.16.5-1.17] - 19/06/2021

### Fixed some models and added a custom smithing table. Removed experimental features and four arms. Added Jei support still in beta. New alien coming soon. 1.17 forge coming soon which means i can start making 1.17. New alien table coming soon. Final release for another week or 2 weeks. Changes can be seen on GitHub in my 1.17-beta brach. I am working really hard on this so please consider supporting me by donating to my goFundMe. The omnitrix idea has been delayed might come out in 2022 or in December while i learn how to make it plus i am learning Java and i do this in my spare time and consider joining my discord. The link is https://discord.gg/kbVDCfvVKe. Thats all for this update.
