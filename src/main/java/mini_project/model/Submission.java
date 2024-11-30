package mini_project.model;

import java.sql.Date;

public class Submission {
    private int id;
    private int userId;
    private int quizId;
    private String submissionCode;
    private Date submissionDate;
    private int score;
    private Boolean status;


    public Submission(int id, int userId, int quizId, String submissionCode, Date submissionDate, int score, Boolean status) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.submissionCode = submissionCode;
        this.submissionDate = submissionDate;
        this.score = score;
        this.status = status;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getQuizId() {
        return quizId;
    }


    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }


    public String getSubmissionCode() {
        return submissionCode;
    }


    public void setSubmissionCode(String submissionCode) {
        this.submissionCode = submissionCode;
    }


    public Date getSubmissionDate() {
        return submissionDate;
    }


    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }


    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }


    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    

    
}
