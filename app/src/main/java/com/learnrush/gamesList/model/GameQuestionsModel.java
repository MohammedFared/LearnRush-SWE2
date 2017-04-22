package com.learnrush.gamesList.model;

/**
 * LearnRush Created by Mohammed Fareed on 4/13/2017.
 */

public class GameQuestionsModel {
    private String question, answer;

    public GameQuestionsModel() {
    }

    public GameQuestionsModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
