package com.learnrush.gameplay.presenter;

import com.learnrush.addgame.model.GameQuestionsModel;

import java.util.ArrayList;

/**
 * LearnRush Created by Mohammed Fareed on 4/22/2017.
 */

public interface IGamePlayPresenter {
    String getRightAnswer(String answerSelected);
    int getmScore();
    void onDataFetch(ArrayList<GameQuestionsModel> gameQuestionsModelArrayList);
    String[] requestNextQuestion();
}
