package mini_project.model;

public class TestCase {
    private int id;
    private int quizId;
    private String input;
    private String output;


    public TestCase(int id, int quizId, String input, String output) {
        this.id = id;
        this.quizId = quizId;
        this.input = input;
        this.output = output;
    }


   


    public int getQuizId() {
        return quizId;
    }


    public  int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }


    public String getInput() {
        return input;
    }


    public void setInput(String input) {
        this.input = input;
    }


    public String getOutput() {
        return output;
    }


    public void setOutput(String output) {
        this.output = output;
    }


    
    
}

