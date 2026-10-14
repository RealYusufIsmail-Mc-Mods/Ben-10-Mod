# Roadmap after step 7 (proposal, 2026-10-14)

Ideas from the user's first play session, grouped and ordered. Nothing here is built yet unless marked. Steps 1-7 are
in [progression.md](progression.md).

## Release plan

Each version is one PR (or a short stack), released to CurseForge for Forge and Fabric. Version format:
`<minecraft>-<mod>`, e.g. `1.20.1-2.0.0`. A version ships when `runData`, `build`, both GameTest suites and an
in-game check on both loaders pass. Ideas that don't make a version move to the next one; dates aren't promised past
2.0.0.

| Version | Name | Scope |
|---|---|---|
| **2.0.0** (Fri 16 Oct 2026) | The Omnitrix | Everything in PR #93 (1.20.1 port, Forge + Fabric, Omnitrix and 10 aliens, DNA, Crafter chain, Kraab, Vilgax, the Null Void, Zs'Skayr, Master Control) + QA round 1 (powers on `V`, `H` revert, creative shows all aliens, visible timeout and recharge, Field Guide, new icon and wrist model) + death-while-transformed fix + the mob render crash fix. |
| 2.1.0 | Living Null Void | Smarter prisoners, Null Guardians, Lepidopterrans, prison blocks with the warden, wrecked Plumber outposts, Taydenite caves, useful drops. Heatblast/Stinkfly/Ghostfreak flight animations. |
| 2.2.0 | Plumbers | Plumber NPC and JSON missions, Plumber gear, original-series aliens batch (Cannonbolt, Wildvine, Ditto, Eye Guy). |
| 2.3.0 | Diagon | Rift dimension, Diagon fight with healing pylons, Celestialsapien DNA, transformation cut-in. Rest of the original-series aliens (Way Big, Blitzwolfer, Snare-oh, Frankenstrike). |
| 3.0.0 | Alien Force | Azmuth's workshop, recalibrated Omnitrix, DNA-helix animation, Alien X with the Serena/Bellicus Council, Alien Force aliens batch 1 (Swampfire, Humungousaur, Big Chill, Jetray, Echo Echo, Spidermonkey). |
| 3.1.0 | Alien Force II | Chromastone, Brainstorm, Goop, Rath, Lodestar, Nanomech. |
| 4.0.0 | Ultimate Alien | Albedo boss, Ultimatrix, Ultimate key and the seven ultimate forms. |
| 4.1.0 | Ultimate Alien II | Water Hazard, AmpFibian, Armodrillo, Terraspin, NRG, Fasttrack, ChamAlien, Eatle, Clockwork, Juryrigg. |
| 5.0.0 | Omniverse | Malware boss, Omniverse Omnitrix, playlists, first Omniverse aliens batch. |
| 5.1.0 | Omniverse II | Remaining Omniverse aliens. |

## Already being built (QA round 1)

- Alien power key `V` (one power per alien, e.g. Heatblast fireballs), revert key `H`, all aliens in creative.
- Visible timeout (HUD countdown, beeps, red flash) and a 60 s recharge; Master Control removes both.
- Omnitrix Field Guide book, new creative-tab icon, wrist-mounted Omnitrix model.

## Bugs found while playing

- Dying while transformed keeps you as the alien after respawn. Fix: revert on death and on respawn. (Next round.)
- New mobs crashed the client (doubled `entity/` model path). Fixed in PR #93.

## Phase A: a living Null Void

- **Smarter creatures.** Null Void Prisoners hunt in packs, flank and flee at low health, and fight each other
  sometimes. New mobs from the show:
  - **Null Guardians**: big jellyfish-like flyers that patrol the islands, ignore you unless provoked, and pull you in
    with a tractor beam. They drop *Null Guardian membrane* (used for a Null Void glider).
  - **Lepidopterran scouts** (Stinkfly's species): fast flyers that dive-bomb and spit slime.
- **Loot places and dungeons.** Procedural structures beyond the fortress:
  - *Floating prison block*: cell corridors, cell keys, prisoner spawners, a warden mini-boss, loot vault.
  - *Wrecked Plumber outpost*: Plumber tech loot (badge, blaster, armour pieces).
  - *Crystal caverns*: Taydenite crystals (the show's gem currency) in hollow islands.
- **Drops worth having.** Every Null Void mob drops something used in a recipe (membrane, prisoner cuffs → Plumber
  restraints, Taydenite).
- **Missions.** A Plumber NPC at wrecked outposts gives missions: "capture 5 escaped prisoners", "clear a
  prison block", "retrieve a Plumber data core". Rewards: Taydenite, DNA samples, Plumber gear. Missions are JSON so
  more can be added without code.

## Phase B: the final boss and Alien X (the Ender Dragon equivalent)

- **Final boss: Diagon** (Ultimate Alien's eldritch overlord), fought in its own rift dimension opened with an item
  made from Zs'Skayr's and Vilgax's drops. Multiple phases with crystal pylons that heal it (like End crystals), minion
  waves, a boss bar, arena-wide attacks and a cinematic death.
- **Reward: Alien X DNA.** Defeating it drops the *Celestialsapien DNA* item, which unlocks the 11th alien, Alien X.

### Alien X (Celestialsapien)

- Show basis: Alien X's body is shared by three personalities, **Serena** (the voice of love and compassion),
  **Bellicus** (the voice of rage and aggression) and Alien X himself, who must agree before acting. Without an
  agreement Ben stands frozen.
- **Without Master Control:** transforming leaves you frozen. Each transform opens the *Council* screen, a short
  dialogue where you argue for an action. Arguments appeal to Serena (protect, heal, save) or Bellicus (fight, punish,
  destroy). Winning a debate with one raises your standing with them. Standing unlocks powers in tiers, so the player
  literally learns to talk to the two of them. Losing a debate wastes the transform.
- **With Master Control:** full control straight away, no Council (user's decision).
- **Powers, unlocked by tier.** Each is a power on `V` or a radial picker:
  1. Flight at any speed and invulnerability to non-boss damage.
  2. **Time rewind**: the player goes back to where they were 10 s ago with that health and hunger (positions are
     recorded every tick).
  3. **Time stop**: freezes every mob within 64 blocks for 10 s.
  4. **Dimension jump**: teleport to the Overworld, Nether, End or Null Void at the same coordinates (safe-spot search).
  5. **Reality edit**: set day or night, clear or start the weather, and heal everyone nearby.
- Balance: very long recharge, and it only works once the final boss has been beaten.

## Phase C: polish

- Heatblast flight animation: a fire-trail flight pose, flame particles and a distinct `fly` animation. Do the same for
  the other flyers (Stinkfly, Ghostfreak).
- Alien transform cut-in: arm raise, dial pop-up and slam before the flash.

## Phase D: Azmuth and the Omnitrix upgrades

After Diagon, the story moves from the original series through Alien Force and Ultimate Alien to Omniverse. Each
upgrade is a new watch item that replaces the old one. Every upgrade keeps your unlocked aliens, adds a new set of
aliens with their own powers and animations, and changes how the watch works.

### Azmuth's workshop
- When Diagon dies, a portal opens in the rift arena, much like the End gateway. It leads to a small island with a
  Galvan workshop hut, the home of **Azmuth**, the Omnitrix's creator. He is a tiny Galvan NPC who talks through a
  dialogue screen.
- He is where every upgrade happens. The hut stays reachable afterwards through that portal, and later through a
  Galvan beacon crafted from his first quest reward.

### Tier 1: the original Omnitrix (now)
- The 10 classic aliens, plus original-series additions over time: Cannonbolt, Wildvine, Way Big, Ditto, Eye Guy,
  Blitzwolfer, Snare-oh and Frankenstrike.
- The watch: a 5-minute timeout, a recharge, the green flash, a pop-up dial and a slap.

### Tier 2: the recalibrated Omnitrix (Alien Force)
- **Unlock:** beat Diagon, reach Azmuth, and bring him Celestialsapien DNA (Diagon's drop). He recalibrates the watch.
  This is also where **Alien X** joins, which fits the show.
- **New aliens:** Swampfire, Humungousaur, Big Chill, Jetray, Echo Echo, Chromastone, Brainstorm, Spidermonkey, Goop,
  Rath, Lodestar, Nanomech and Alien X.
- **Watch changes:** a sleeker black-and-green watch model, a longer timeout (10 min), a shorter recharge, and a
  scrolling hologram selector that changes the dial.
- **Animation:** a green DNA helix wraps the player before the flash.

### Tier 3: the Ultimatrix (Ultimate Alien)
- **Unlock:** defeat **Albedo**, the evil Galvan copy of Ben. He wears the Ultimatrix and fights with "negative"
  versions of your aliens. You take his Ultimatrix. Azmuth then repairs it or tunes it to you.
- **The Ultimate key:** while you are an alien that has an ultimate form, press it to evolve. Spikes push out and the
  form glows, with stronger powers for a short time and a long recharge afterwards.
- **Ultimate forms:**
  - Ultimate Swampfire: blue flames and seed bombs.
  - Ultimate Humungousaur: missile fingers.
  - Ultimate Big Chill: green fire-ice.
  - Ultimate Echo Echo: sonic disc drones.
  - Ultimate Spidermonkey: gorilla size and web cannons.
  - Ultimate Cannonbolt: a spiked roll.
  - Ultimate Wildmutt: a quill volley.
- **New aliens:** Water Hazard, AmpFibian, Armodrillo, Terraspin, NRG, Fasttrack, ChamAlien, Eatle, Clockwork and
  Juryrigg.
- **Animation:** the Ultimatrix evolution sequence, with the DNA pulse going from blue to green and the body
  transforming between the two models.

### Tier 4: the Omniverse Omnitrix
- **Unlock:** the Ultimatrix is destroyed fighting **Malware**, the corrupted Galvanic Mechamorph and the
  Omniverse-era boss. Azmuth builds the final Omnitrix.
- **Watch changes:** no timeout by default and a fast recharge. **Playlists** let you pick aliens straight from a
  hotbar-style selector, so you can switch from one alien to another without changing back to human first.
- **New aliens:** Feedback, Bloxx, Gravattack, Crashhopper, Kickin Hawk, Atomix, Whampire, Astrodactyl, Bullfrag,
  Toepick, Gutrot and Pesky Dust.
- **Animation:** a white-green Omniverse flash with a faceplate spin, and a matching first-person arm.

### Build notes
- Each alien is one JSON file (size, abilities, `power`, DNA sources) plus generated GeckoLib assets. Adding the next
  tier's aliens is mostly data and art, which is why they arrive in batches.
- Add a `tier` field to the alien JSON so the dial only shows aliens your watch can use. Add an optional `ultimate`
  field that points to the ultimate form's alien id.
- Transformation animations are chosen by the watch tier, not by the alien.

## Decisions (user, 2026-10-14)

- Final boss: **Diagon**.
- Arena: its **own rift dimension**, opened with an item made from Vilgax's and Zs'Skayr's drops.
- Missions: given by a **Plumber NPC** at wrecked outposts.
- Master Control gives full control of Alien X without the Council.
- After Diagon: Azmuth's workshop, then upgrades from Omnitrix to Alien Force to Ultimatrix to Omniverse, each with
  more aliens (ultimate forms with the Ultimatrix) and its own transformation animation.
