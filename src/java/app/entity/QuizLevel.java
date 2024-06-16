package app.entity;

public enum QuizLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");
    
    private String label;
    private QuizLevel(String label) {
        this.label = label;
    }
    
    public static QuizLevel fromInt(int value) {
        return switch (value) {
            case 0 -> EASY;
            case 1 -> MEDIUM;
            case 2 -> HARD;
            default -> null;
        };
    }

    public int toInt() {
        return switch (this) {
            case EASY -> 0;
            case MEDIUM -> 1;
            case HARD -> 2;
            default -> -1;
        };
    }
    
    @Override
    public String toString() {
        return label;
    }
}
