
import java.util.*;

public class EmpireSkillSystem {
    private Map<String, SkillTree> empireTrees;
    private Map<String, Integer> skillPoints;

    public EmpireSkillSystem() {
        this.empireTrees = new HashMap<>();
        this.skillPoints = new HashMap<>();
        initializeSkillTrees();
    }

    private void initializeSkillTrees() {
        // Initialize different skill trees
        SkillTree militaryTree = new SkillTree("Military");
        militaryTree.addSkill(new Skill("Fleet Command", "Increases fleet size capacity", 5));
        militaryTree.addSkill(new Skill("Advanced Weapons", "Improves weapon damage", 3));
        
        SkillTree economyTree = new SkillTree("Economy");
        economyTree.addSkill(new Skill("Trade Routes", "Enhances trade income", 5));
        economyTree.addSkill(new Skill("Resource Management", "Improves resource gathering", 3));
        
        empireTrees.put("military", militaryTree);
        empireTrees.put("economy", economyTree);
    }

    public boolean learnSkill(String empireId, String treeId, String skillName) {
        SkillTree tree = empireTrees.get(treeId);
        if (tree != null && skillPoints.getOrDefault(empireId, 0) > 0) {
            if (tree.upgradeSkill(skillName)) {
                skillPoints.merge(empireId, -1, Integer::sum);
                return true;
            }
        }
        return false;
    }

    public void addSkillPoints(String empireId, int points) {
        skillPoints.merge(empireId, points, Integer::sum);
    }
}

class SkillTree {
    private String name;
    private Map<String, Skill> skills;

    public SkillTree(String name) {
        this.name = name;
        this.skills = new HashMap<>();
    }

    public void addSkill(Skill skill) {
        skills.put(skill.getName(), skill);
    }

    public boolean upgradeSkill(String skillName) {
        Skill skill = skills.get(skillName);
        if (skill != null && !skill.isMaxLevel()) {
            skill.levelUp();
            return true;
        }
        return false;
    }
}

class Skill {
    private String name;
    private String description;
    private int level;
    private int maxLevel;

    public Skill(String name, String description, int maxLevel) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.level = 0;
    }

    public void levelUp() {
        if (level < maxLevel) {
            level++;
        }
    }

    public String getName() { return name; }
    public boolean isMaxLevel() { return level >= maxLevel; }
}
