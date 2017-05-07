package com.learnrush.addgame.presenter;

import com.learnrush.addgame.model.GameModel;
import com.learnrush.addgame.model.GameQuestionsModel;

import java.util.ArrayList;

/**
 * LearnRush Created by Mohammed Fareed on 4/18/2017.
 */

public interface IAddGamePresenter {
    void onAddGameClicked(ArrayList<GameQuestionsModel> mGameQuestionsArrayList, GameModel gameDetailsModel);
}
