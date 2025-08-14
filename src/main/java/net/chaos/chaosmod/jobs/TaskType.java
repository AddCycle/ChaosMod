package net.chaos.chaosmod.jobs;

public enum TaskType {
    HARVEST("harvest"),
    KILL("kill"),
    CRAFT("craft");
    
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