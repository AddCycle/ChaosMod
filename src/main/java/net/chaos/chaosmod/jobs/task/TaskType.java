package net.chaos.chaosmod.jobs.task;

public enum TaskType {
    HARVEST("harvest"), // explorer
    KILL("kill"), // hunter/fighter
    CRAFT("craft"),
	TAME("tame"), // tamer
	CATCH("catch"), // fisherman
	BREW("brew"), // alchemist
	MINE("mine"), // miner
	SMELT("smelt"),
	TRAVEL("travel");
    
    public String name;

    TaskType(String name) {
        this.name = name;
    }

    public static TaskType fromName(String name) {
        for (TaskType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TaskType: " + name);
    }
}