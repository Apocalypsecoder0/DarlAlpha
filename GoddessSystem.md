
# Goddess System Documentation

## Overview
The Goddess System allows players to interact with divine beings that provide various blessings and bonuses to enhance gameplay mechanics.

### Available Goddesses
1. **Athena (WISDOM)**
   - Research Boost (+40%)
   - Experience Boost (+25%)
   - Domain: Technology and Learning
   - Special Ability: Can grant temporary perfect research efficiency

2. **Bellona (WAR)**
   - Attack Boost (+50%)
   - Shield Enhancement (+30%)
   - Domain: Combat and Defense
   - Special Ability: Can grant temporary invulnerability

3. **Fortuna (FORTUNE)**
   - Resource Boost (+35%)
   - Trading Luck (+45%)
   - Domain: Wealth and Prosperity
   - Special Ability: Can grant rare resource discovery chance

## Favor System
- Each goddess has a favor level (0-100)
- Favor can be increased through:
  - Offerings (resources/credits)
  - Completing goddess-specific missions
  - Building temples
- Blessing power increases with favor level (1% per favor level)
- Formula: Blessing Power = Base Power * (1 + favor_level/100)

## Temple System
- Players can build temples to each goddess
- Temple levels: 1-10
- Each level provides:
  - +5% to blessing effectiveness
  - +10% to favor gain rate
  - Unique temple abilities unlock at levels 5 and 10

## Daily Devotions
- Players can perform daily devotions to gain favor
- Different types of offerings provide different favor amounts
- Maximum 3 devotions per goddess per day

## Usage Example
```java
// Example of interacting with goddesses
GoddessSystem goddessSystem = new GoddessSystem();
Goddess athena = goddessSystem.getGoddesses().get("Athena");
athena.increaseFavor(10);
int researchBoost = athena.getBlessingPower("Research Boost");

// Building temples
Temple athenaTemple = goddessSystem.buildTemple("Athena");
athenaTemple.upgrade();  // Increases temple level

// Daily devotion
athena.performDevotion("rare_resources");  // Offers rare resources for favor
```

## Integration with Other Systems
- Research System: Goddess blessings affect research speed
- Combat System: War goddess affects battle outcomes
- Economy: Fortune goddess influences market prices
- Resource Generation: All goddesses can boost resource production
