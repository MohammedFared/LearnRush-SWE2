package com.learnrush.gameplay.presenter;

import android.content.Context;

import com.learnrush.addgame.model.GameQuestionsModel;
import com.learnrush.gameplay.view.IGamePlayView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * LearnRush Created by Mohammed Fareed on 4/22/2017.
 */

public class GamePlayPresenterImpl implements IGamePlayPresenter {
    private IGamePlayView mIView;
    private ArrayList<String> mQuestionsArrayList = new ArrayList<String>();
    private ArrayList<String> mAnswersArrayList = new ArrayList<String>();
    private int currentQuestionIndex = -1;

    private int mScore = 0;

    public GamePlayPresenterImpl(Context context, IGamePlayView iView){
        mIView = iView;
    }

    @Override
    public int getmScore() {
        return mScore;
    }

    @Override
    public void onDataFetch(ArrayList<GameQuestionsModel> gameQuestionsModelArrayList) {
        for (GameQuestionsModel gameQuestionsModel : gameQuestionsModelArrayList){
            mQuestionsArrayList.add(gameQuestionsModel.getQuestion());
            mAnswersArrayList.add(gameQuestionsModel.getAnswer());
        }
    }

    @Override
    public String getRightAnswer(String answerSelected) {
        if (answerSelected.equals(mAnswersArrayList.get(currentQuestionIndex)))
            mScore++;
        return mAnswersArrayList.get(currentQuestionIndex);
    }

//
//    @Override
//    public void fetchData() {
//        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
//        mRef.child("game_questions").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                GameQuestionsModel questionsModel;
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // Read the questions and put it in the arrayList
//                    questionsModel = snapshot.getValue(GameQuestionsModel.class);
//                    mQuestionsArrayList.add(questionsModel.getQuestion());
//                    mAnswersArrayList.add(questionsModel.getAnswer());
//                    Log.d(TAG, "onDataChange: ");
//                }
//                requestNextQuestion();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: ");
//            }
//        });
//    }

    @Override
    public String[] requestNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < mQuestionsArrayList.size()) {
            String nextQuestion[] = new String[5];
            nextQuestion[0] = mQuestionsArrayList.get(currentQuestionIndex);
            String answers[] = generateAnswers(currentQuestionIndex);
            System.arraycopy(answers, 0, nextQuestion, 1, 4);
            return nextQuestion;
        } else {
            return null;
        }
    }

    public String[] generateAnswers(int questionIndex) {
        int size = mQuestionsArrayList.size();
        Random random = new Random();
        String answers[] = new String[4];
        int rightAnswerRandomIndex = random.nextInt(4); // generates a random int between 0~3
        answers[rightAnswerRandomIndex] = mAnswersArrayList.get(questionIndex); // gets the right answer of the question

        /* Create an indices arrayList and shuffle it
         * so there will be no duplicates
         */
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0 ; i < size ; i++)
            indices.add(i);
        Collections.shuffle(indices);

        for (int i = 0; i<3 ; i++) {
            if(i != rightAnswerRandomIndex) {
                if (indices.get(i) == questionIndex) {
                    answers[i] = mAnswersArrayList.get(indices.get(3));
                } else {
                    answers[i] = mAnswersArrayList.get(indices.get(i));
                }
            }
        }
        return answers;
    }

}
