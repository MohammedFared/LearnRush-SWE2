package com.learnrush.gameplay.presenter;

import android.content.Context;

import com.learnrush.gameplay.view.IGamePlayView;

/**
 * LearnRush Created by Mohammed Fareed on 4/22/2017.
 */

public class GamePlayPresenterImpl implements IGamePlayPresenter {
    Context mContext;
    IGamePlayView mIView;
    GamePlayPresenterImpl(Context context, IGamePlayView iView){
        mContext = context;
        mIView = iView;
    }
}
