package mini_project.view.Professor;

public class Student {
    private String name;
    private String averageScore;
    private int quizzesTaken;

    public Student(String name, String averageScore, int quizzesTaken) {
        this.name = name;
        this.averageScore = averageScore;
        this.quizzesTaken = quizzesTaken;
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public int getQuizzesTaken() {
        return quizzesTaken;
    }

    public void setQuizzesTaken(int quizzesTaken) {
        this.quizzesTaken = quizzesTaken;
    }
}


