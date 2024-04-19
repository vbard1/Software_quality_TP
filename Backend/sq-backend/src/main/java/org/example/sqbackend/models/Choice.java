package org.example.sqbackend.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChoice;

    private String content;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question question;

    public int getIdChoice() {
        return idChoice;
    }

    public void setIdChoice(int idChoice) {
        this.idChoice = idChoice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return idChoice == choice.idChoice &&
                Objects.equals(content, choice.content) &&
                Objects.equals(question, choice.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idChoice, content, question);
    }
}
