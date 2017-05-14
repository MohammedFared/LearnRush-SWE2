package com.learnrush.gamesList.presenter;

import com.learnrush.gamesList.model.CommentsModel;

/**
 * LearnRush Created by Mohammed Fareed on 4/22/2017.
 */

public interface IGameDetailsPresenter {
    void getGameDetails(String gameKey);
    void onStartGameClicked(String gameKey);

    void onAddCommentClicked(CommentsModel commentBody, String gameKey);
}
