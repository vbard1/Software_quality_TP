package org.example.sqbackend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;

    private String content;
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "id_poll")
    private Poll poll;

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return idQuestion == question.idQuestion &&
                Objects.equals(content, question.content) &&
                Objects.equals(expirationDate, question.expirationDate) &&
                Objects.equals(poll, question.poll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestion, content, expirationDate, poll);
    }

}
