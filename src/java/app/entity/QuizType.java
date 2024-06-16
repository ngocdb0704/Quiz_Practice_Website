package app.entity;

public enum QuizType {
    SIMULATION("Simulation"),
    LESSON_QUIZ("Lesson Quiz");
    
    private String label;
    private QuizType(String label) {
        this.label = label;
    }
    
    public static QuizType fromInt(int value) {
        return switch (value) {
            case 0 -> SIMULATION;
            case 1 -> LESSON_QUIZ;
            default -> null;
        };
    }

    public int toInt() {
        return switch (this) {
            case SIMULATION -> 0;
            case LESSON_QUIZ -> 1;
            default -> -1;
        };
    }
    
    @Override
    public String toString() {
        return label;
    }
}
