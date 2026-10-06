# Village Buff

Village Buff is a Fabric mod that improves Minecraft villages by making blacksmiths and villagers significantly more rewarding. The goal is to make villages feel like valuable places to explore throughout your world instead of something you loot once and forget.

## Features

### 🛠️ Enhanced Weaponsmith Chests

Weaponsmith chests have been completely overhauled with better and more useful loot.

Possible loot includes:

* 💎 Diamonds
* 🪨 Obsidian
* ⛏️ Iron and Diamond tools
* ⚔️ Iron and Diamond swords
* 🪙 Large amounts of Iron Ingots

The loot is distributed across multiple loot pools to create more natural-looking chest contents.

---

### 📚 Valuable Enchanted Books

Village tannery chests can now contain powerful enchanted books, including:

* Unbreaking III
* Mending
* Efficiency V
* Silk Touch
* Protection IV

These books provide players with a reason to continue exploring villages even into the late game.

---

### 🤝 Improved Villager Trades

Weaponsmith villagers now offer several powerful custom trades.

Current custom trades include:

**Level 1**

* Iron Sword → Diamond Sword
* Diamond → Diamond Block

**Level 2**

* 2 Diamond Swords → Netherite Upgrade Smithing Template
* Netherite Upgrade Smithing Template → 2 Netherite Upgrade Smithing Templates

These trades are designed to provide unique progression options and reward players who invest in villagers.

---

## Compatibility

This repository currently builds the **Fabric** edition.

| Platform | Status | Notes |
| --- | --- | --- |
| Fabric | Supported target | Metadata allows Minecraft `1.21.11+`; each new Minecraft release still needs a test build because Mojang mappings and APIs can change. |
| Forge / NeoForge | Not ported yet | Requires a separate loader entrypoint, registry setup, screen registration, event hooks, and metadata. |
| Paper | Not ported yet | Requires a Bukkit/Paper plugin rewrite. The custom Duper block/menu cannot be provided as a normal client-visible block without a modded client or a different implementation. |
| Vanilla | Partial datapack only | Vanilla can only support datapack-style features such as loot tables, recipes, tags, and functions. The Duper block, custom GUI, Java trade hooks, and mixins are not possible in pure vanilla. |

The Fabric metadata is intentionally broad for `1.21.11+`, but that is not a promise that one jar will run unchanged forever. Minecraft `1.26+` support will require testing against those released mappings/APIs when they exist.

## Installation

1. Install the Fabric Loader.
2. Install the Fabric API.
3. Place the Village Buff Fabric `.jar` into your `mods` folder.
4. Launch Minecraft.

## Porting Targets

To release this on every requested platform, split the project into separate deliverables:

* `fabric`: current implementation.
* `forge` or `neoforge`: loader-specific Java port.
* `paper`: server plugin rewrite for server-side-only features.
* `vanilla-datapack`: limited datapack version for loot/recipe/function content only.

## Future Plans

Planned additions include:

* More custom villager trades
* Better loot for additional village structures
* Profession-specific loot improvements
* More balanced progression
* Config support
* Additional village improvements

## Why Village Buff?

Vanilla villages are useful early on but quickly become obsolete. Village Buff gives players a reason to keep exploring villages by improving chest loot and adding meaningful villager trades while keeping the vanilla feel intact.

## License

This project is available under the license included in this repository.
