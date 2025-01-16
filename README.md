
Dark Alpha:

A multiplayer space exploration and combat game built in Java.

## Core Systems
- Player & Empire Management
- Universe Generation 
- Combat & PvP Systems
- Economy & Trading
- Research & Technology Tree
- Alliance System
- Party & Dungeon Systems
- Equipment & Inventory
- Shipyard Systems
- Prestige & Ascension
- Empire Titles & Skills
- Message & Announcement Systems

## Source Code Structure
```
src/main/java/
├── Game.java              # Main game orchestrator
├── Player.java            # Player management
├── Empire.java            # Empire management
├── Universe.java          # Universe and galaxy generation
├── Combat/
│   ├── CombatSystem.java
│   ├── Battleground.java      # 3v3 PvP
│   ├── BattlefieldRaid.java   # 6-player raids
│   └── EnemyShip.java
├── Systems/
│   ├── PartySystem.java
│   ├── DungeonFinder.java
│   ├── ExpansionSystem.java
│   ├── PrestigeSystem.java
│   ├── AscensionSystem.java
│   ├── EmpireTitleSystem.java
│   ├── EmpireSkillSystem.java
│   ├── MessageSystem.java
│   ├── AnnouncementSystem.java
│   ├── AudioSystem.java
│   ├── SaveSystem.java
│   ├── ResearchSystem.java
│   └── EconomySystem.java
├── Equipment/
│   ├── Equipment.java
│   └── Inventory.java
├── Ships/
│   ├── Ship.java
│   ├── Fleet.java
│   ├── Shipyard.java
│   └── AllianceShipyard.java
└── GUI/
    └── GUI.java          # Game interface

```

## Features
- Character & Empire Creation with Tutorial
- Fleet Management & Ship Combat
- Resource Collection & Trading
- Research & Technology Advancement
- Alliance Formation & Management
- 72 Different Zone Areas across 4 Quadrants
- Dungeon Exploration System
- Battlefield PvP (3v3) and Raids (6 players)
- Empire Progression (Prestige & Ascension)
- Equipment & Inventory Management
- Empire Titles & Skills System
- Real-time Combat System
- Persistent Save System

## Building and Running
1. Build using Maven
2. Run Main.java to start the game
3. Use the GUI interface to navigate game systems

## Testing
Run tests using Maven: `mvn test`
