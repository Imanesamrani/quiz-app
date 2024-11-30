package mini_project.model;




import java.time.LocalDate;

import java.util.Iterator;
import java.util.List;



public class Quiz {
    private int id;
    private String title;
    private String objective;
    private String starterCode;
    private LocalDate date;
    
    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    private List<TestCase> testCases;
    private int maxScore;
    private int attempts;
    private boolean isSubmitted;
    private String deadLine;

    public Quiz(int id, String title, String objective, String  starterCode,List<TestCase> testCases, int maxScore, int attempts, boolean isSubmitted, String deadLine) {
        this.id = id;
        this.title = title;
        this.objective = objective;
        this.starterCode = starterCode;
        this.testCases = testCases;
        this.maxScore = maxScore;
        this.attempts = attempts;
        this.isSubmitted = isSubmitted;
        this.deadLine = deadLine;
    }


    public Quiz(int id, String title, String objective, List<TestCase> testCases2, int maxScore, LocalDate date, String deadLine, String  starterCode) {
        this.id = id;
        this.title = title;
        this.objective = objective;
        this.starterCode = starterCode;
        this.maxScore = maxScore;
        this.deadLine = deadLine;
        this.date= date;
        
        }

       
    
    
        public String getTitle() {
            return title;
        }
    
        public void setTitle(String title) {
            this.title = title;
        }
    
        public String getObjective() {
            return objective;
        }
    
        public void setObjective(String objective) {
            this.objective = objective;
        }
    
        public List<TestCase> getTestCases() {
            return testCases;
        }
    
        public void setTestCases(List<TestCase> testCases) {
            this.testCases = testCases;
        }
    
        public int getMaxScore() {
            return maxScore;
        }
    
        public void setMaxScore(int maxScore) {
            this.maxScore = maxScore;
        }
    
        public int getAttempts() {
            return attempts;
        }
    
        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }
    
        public boolean isSubmitted() {
            return isSubmitted;
        }
    
        public void setSubmitted(boolean isSubmitted) {
            this.isSubmitted = isSubmitted;
        }
    
        public String getDeadLine() {
            return deadLine;
        }
    
        public void setDeadLine(String deadLine) {
            this.deadLine = deadLine;
        }
    
    
        public int getId() {
            return id;
        }
    
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getStarterCode() {
            return starterCode;
        }
    
    
        public void setStarterCode(String starterCode) {
            this.starterCode = starterCode;
        }
       
     public void ViewQuiz() {
        System.out.println("Quiz title: " + this.title);
        System.out.println("Objective: " + objective);
        System.out.println("Starter Code: " + starterCode);
        System.out.println("Max Score: " + maxScore);
        System.out.println("Attempts: " + attempts);

        Iterator<TestCase> it = testCases.iterator();
        System.out.println("Test cases: ");
        while (it.hasNext()) {
            TestCase testCase = it.next();
            System.out.println(" -Quiz id: " + testCase.getQuizId());
            System.out.println(" -Input: " + testCase.getInput());
            System.out.println(" -Output: " + testCase.getOutput());
            System.out.println("---------------------------------");
        }
    }

}

