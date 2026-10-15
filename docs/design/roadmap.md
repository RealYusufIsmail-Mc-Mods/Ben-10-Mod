# Roadmap after step 7 (proposal, 2026-10-14)

Ideas from the user's first play session, grouped and ordered. Completed items are marked. Steps 1-7 are in
[progression.md](progression.md).

## Release plan

Each version is one PR (or a short stack), released to CurseForge for Forge and Fabric. Version format:
`<minecraft>-<mod>`, e.g. `1.20.1-2.0.0`. A version ships when `runData`, `build`, both GameTest suites and an
in-game check on both loaders pass. Ideas that don't make a version move to the next one; dates aren't promised past
2.0.0.

| Version | Status | Name | Scope |
|---|---|---|---|
| 2.0.0 | Released 14 Oct 2026 | The Omnitrix | Minecraft 1.20.1 on Forge and Fabric. The Omnitrix with 10 aliens in 3D, DNA scanning, alien powers on `V`, `H` to change back, a visible time limit and recharge, the Omnitrix Field Guide, the Crafter recipe chain, Kraab, Vilgax, the Null Void, Zs'Skayr and Master Control. |
| 2.0.1 | Released 15 Oct 2026 | Hotfix | Fixes a crash when Zs'Skayr or a Vilgax drone took off from the ground. |
| 2.1.0 | In progress | Living Null Void | Smarter prisoners, Null Guardians, Lepidopterran scouts, floating prison blocks with the Prison Warden and a key-locked vault, wrecked Plumber outposts, Taydenite, Plumber blaster, armour and glider, flight animations for Heatblast, Stinkfly and Ghostfreak, an XLR8 sprint animation and water running, balanced armour and tools. From playtesting: camera height that matches the alien's size, a transformation burst and morph for every alien, rapid-fire powers, powers that damage bosses, a walk-through Null Void portal that can banish mobs, bosses that talk in chat, Plumber energy bolts, safer Null Void arrival with a starter glider, prison loot chests, a richer warden vault and in-game hints for the vault and the way home. |
| 2.2.0 | Planned | Plumbers | Plumber NPC and missions, the Rustbucket (Grandpa Max's RV) you can drive with laser cannons and missiles, original-series aliens batch (Cannonbolt, Wildvine, Ditto, Eye Guy), a Four Arms moveset (four-hit combo, guard stance, thunder clap) and the Omnitrix fail-safe. |
| 2.3.0 | Planned | Diagon | Rift dimension, Diagon fight with healing pylons, Celestialsapien DNA, transformation cut-in. Rest of the original-series aliens (Way Big, Blitzwolfer, Snare-oh, Frankenstrike). Null Void structures and creatures from the show (research list below; pick which to build). |
| 3.0.0 | Planned | Alien Force | Azmuth's workshop, recalibrated Omnitrix, DNA-helix animation, Alien X with the Serena/Bellicus Council, Alien Force aliens batch 1 (Swampfire, Humungousaur, Big Chill, Jetray, Echo Echo, Spidermonkey). |
| 3.1.0 | Planned | Alien Force II | Chromastone, Brainstorm, Goop, Rath, Lodestar, Nanomech. |
| 4.0.0 | Planned | Ultimate Alien | Albedo boss, Ultimatrix, Ultimate key and the seven ultimate forms. |
| 4.1.0 | Planned | Ultimate Alien II | Water Hazard, AmpFibian, Armodrillo, Terraspin, NRG, Fasttrack, ChamAlien, Eatle, Clockwork, Juryrigg. |
| 5.0.0 | Planned | Omniverse | Malware boss, Omniverse Omnitrix, playlists, first Omniverse aliens batch. |
| 5.1.0 | Planned | Omniverse II | Remaining Omniverse aliens. |

## Already being built (QA round 1)

- Alien power key `V` (one power per alien, e.g. Heatblast fireballs), revert key `H`, all aliens in creative.
- Visible timeout (HUD countdown, beeps, red flash) and a 60 s recharge; Master Control removes both.
- Omnitrix Field Guide book, new creative-tab icon, wrist-mounted Omnitrix model.

## Bugs found while playing

- Dying while transformed keeps you as the alien after respawn. Fix: revert on death and on respawn. (Next round.)
- New mobs crashed the client (doubled `entity/` model path). Fixed in PR #93.

## Phase A: a living Null Void (2.1.0 gameplay implemented; validation pending)

- [x] **Smarter creatures.** Null Void Prisoners hunt in packs, flank and flee at low health, and fight each other
  sometimes. New mobs from the show:
  - [x] **Null Guardians**: big jellyfish-like flyers that patrol the islands, ignore you unless provoked, and pull you in
    with a tractor beam. They drop *Null Guardian membrane* (used for a Null Void glider).
  - [x] **Lepidopterran scouts** (Stinkfly's species): fast flyers that dive-bomb and spit slime.
- [x] **Loot places and dungeons.** Procedural structures beyond the fortress:
  - [x] *Floating prison block*: cell corridors, cell keys, prisoner spawners, a warden mini-boss, loot vault.
  - [x] *Wrecked Plumber outpost*: Plumber tech loot (badge, blaster, armour pieces).
  - [x] *Taydenite ore*: generated in Null Void stone and available as a storage block.
- [x] **Drops and gear.** Membrane, prisoner cuffs, cell keys and Taydenite have loot, crafting or vault uses; the badge
  is collectible and Plumber equipment can be crafted or looted.
- **Missions (deferred).** A Plumber NPC at wrecked outposts gives missions: "capture 5 escaped prisoners", "clear a
  prison block", "retrieve a Plumber data core". Rewards: Taydenite, DNA samples, Plumber gear. Missions are JSON so
  more can be added without code. The badge currently says missions arrive in a future update.

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

## Null Void from the show: research for 2.3.0 (2026-10-15)

The user asked for show-accurate Null Void structures and creatures, to choose from later. The 2.1.0 terrain (floating
islands everywhere) stays. Sources are fan wikis and episode recaps, so check the episodes before building; anything
unconfirmed is marked.

### What the show says the Null Void is
- An alternate dimension the Galvan found and turned into a penal colony; other civilisations later sent their
  prisoners there too. Prisoners who reformed settled down, and their descendants still live there.
- The Galvan built the Null Guardians (grey, tentacled, bat-winged, non-sapient) to keep the settlers and the
  criminals apart.
- The landscape is endless floating rock.
- In *Alien Force* "Voided" (sources disagree on the season and episode number), Dr. Animo had gone in to study new species, got stuck, took
  control of the Null Guardians and ruled as **D'Void**. He forced the settlers to mine a mineral that powered his
  drill and made him nearly invulnerable, and planned to open a wormhole to Earth. A resistance led by a character
  called **the Wrench** fought him; Ben, Max, Helen and Manny ended his rule.
- Named places: **Max's Hovel**, **D'Void's Citadel**, and (listed by fan wikis, unconfirmed as Null Void
  locations) an Incarcecon and a Rooter headquarters.

### Candidate structures
| Idea | From the show | Game version | Size |
|---|---|---|---|
| D'Void's Citadel | Animo's fortress in "Voided" | Boss fortress on a large island: walls, a drill tower, Null Guardian roosts. Boss: D'Void with controlled Null Guardians and mutant animals | Large (structure + boss) |
| The mineral mine | Settlers forced to mine D'Void's mineral | Mine cut into an island with ore veins, mine carts and guarded workers; the mineral is a crafting material | Medium |
| Resistance camp | The Wrench's resistance | Settler camp: tents and lookouts, neutral settler NPCs who trade and give rewards for clearing D'Void's patrols | Medium (NPC + trades) |
| Max's Hovel | Max's hideout when he was in the Null Void | Small hidden shelter with a bed, a map, a Plumber supply chest and a journal page | Small |
| Galvan prison ruins | The Galvan penal colony | Old Galvan cell blocks and a broken Null Void projector array, the oldest buildings in the dimension | Medium |
| Floating prisons and Plumber outposts | Already in 2.1.0 | Keep | Done |

### Candidate creatures
| Idea | From the show | Game version | Size |
|---|---|---|---|
| Null Guardians | Galvan-made wardens | Already in 2.1.0; add an "Alpha" Null Guardian that leads a flock | Small |
| D'Void (Dr. Animo) | Ruler of the Null Void in "Voided" | Boss at the Citadel: controls nearby creatures, uses the mined mineral for a shield | Large |
| Mutant animals | Animo's signature | D'Void's minions: mutated frog, bat and ant variants | Medium |
| Null Void settlers | Descendants of reformed prisoners | Neutral villager-style NPCs in camps (trading, small quests) | Medium |
| Lepidopterrans | Stinkfly's species; Animo is fascinated by them (no specific Null Void episode found) | Already in 2.1.0 as scouts | Done |
| Exiled prisoners | Classic series villains were banished there | Named mini-bosses in prison cells; check which villains the show actually sent there | Open question |

### Open questions
- Which villains did the show actually send to the Null Void? This needs episode checks.
- Does the Null Void need a "resistance vs D'Void" storyline in 2.3.0, or should it be one-off structures?
- Should D'Void replace or join the Diagon arc in 2.3.0?

Sources: [Null Void (Classic), Ben 10 Wiki](https://ben10.fandom.com/wiki/Null_Void_(Classic)),
[Voided, Ben 10 Wiki](https://ben10.fandom.com/wiki/Voided),
[Voided recap, TV Tropes](https://tvtropes.org/pmwiki/pmwiki.php/Recap/Ben10AlienForceS2E08Voided),
[Dr. Animo (Classic), Ben 10 Wiki](https://ben10.fandom.com/wiki/Dr._Animo_(Classic)),
[Null Guardian, Ben 10 list wiki](https://ben-10-list.fandom.com/wiki/Null_Guardian).

## Alien movesets and the Omnitrix fail-safe (2.2.0, decided 2026-10-15)

XLR8 running on water was pulled forward into 2.1.0 (user, 2026-10-15).

### Four Arms moveset
- **Four-hit combo:** consecutive hits cycle the arms (upper right, lower right, upper left, lower left). The fourth
  hit in a row is a haymaker with extra knockback and a screen shake. A miss or a pause resets the combo.
- **Guard stance:** while sneaking, the lower arms cross over the chest and cut damage from the front; the upper arms
  can still punch.
- **Thunder clap:** both pairs of hands clap and send a forward shockwave that knocks back, briefly stuns, and breaks
  glass and leaves. A second power, so it needs a second power keybind and packet (or replaces the ground slam in
  mid-air).
- Later, as a showpiece: grab a mob with the lower arms, carry it and throw it.

### Omnitrix fail-safe
- A hit that would kill a transformed player is cancelled. The player drops back to human with a few hearts, a red
  flash and a warning sound.
- The Omnitrix then enters a long recharge (about 5 minutes) before the next transformation, so it triggers at most
  once per recharge. Master Control shortens the recharge.
- Doesn't apply in human form, to the void or to `/kill`.
- Needs a death-cancel hook on each loader (Forge `LivingDeathEvent`, Fabric `ServerLivingEntityEvents.ALLOW_DEATH`)
  wired in each loader entry point, plus a GameTest.

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
