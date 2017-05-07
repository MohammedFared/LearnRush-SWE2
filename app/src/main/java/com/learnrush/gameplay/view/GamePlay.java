package com.learnrush.gameplay.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learnrush.R;
import com.learnrush.addgame.model.GameQuestionsModel;
import com.learnrush.gameplay.presenter.GamePlayPresenterImpl;
import com.learnrush.gameplay.presenter.IGamePlayPresenter;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity implements IGamePlayView,
        QuestionsAndAnswersFragment.OnFragmentInteractionListener, CustomDialog.NoticeDialogListener{

    IGamePlayPresenter mIGamePlayPresenter;
    private TextView scoreTextView;
    private String TAG = "GAMEPLAYLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        scoreTextView = (TextView) findViewById(R.id.tv_score);

        mIGamePlayPresenter = new GamePlayPresenterImpl(this, this);
        fetchData();
    }

    public void fetchData() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("game_questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameQuestionsModel questionsModel;
                ArrayList<GameQuestionsModel> gameQuestionsArrayList = new ArrayList<GameQuestionsModel>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // Read the questions and put it in the arrayList
                    questionsModel = snapshot.getValue(GameQuestionsModel.class);
                    gameQuestionsArrayList.add(questionsModel);
                }
                mIGamePlayPresenter.onDataFetch(gameQuestionsArrayList);
                Log.d(TAG, "onDataChange: ");
                startNextQuestion();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GamePlay.this, databaseError.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // call getRightAnswer with the selected answer
        String answerSelected = ((RadioButton) view).getText().toString();
        mIGamePlayPresenter.getRightAnswer(answerSelected);
    }

    @Override
    public void startNextQuestion() {
        // start new fragment with the new question and options
        String nextQuestionArray[] = mIGamePlayPresenter.requestNextQuestion();
        if (nextQuestionArray != null) {
            String question = nextQuestionArray[0];
            String options[] = new String[4];
            System.arraycopy(nextQuestionArray, 1, options, 0, nextQuestionArray.length - 1);

            startNewQuestion(options, question);
        } else
            onGameFinished();
    }

    private void startNewQuestion(String[] answersArray, String question) {
        QuestionsAndAnswersFragment questionsAndAnswersFragment = QuestionsAndAnswersFragment
                .newInstance(question, answersArray);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_game_play_fragment, questionsAndAnswersFragment)
                .commit();
    }

    @Override
    public void onSubmitButtonClicked(String answerSelected) {
        String rightAnswer = mIGamePlayPresenter.getRightAnswer(answerSelected);
        boolean isRightAnswer = answerSelected.equals(rightAnswer);

        updateScoreView(mIGamePlayPresenter.getmScore());

        DialogFragment dialogFragment = CustomDialog.newInstance(isRightAnswer, rightAnswer);
        dialogFragment.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    private void updateScoreView(int score) {
        scoreTextView.setText(String.valueOf(score));
    }

    @Override
    public void onDialogPositiveClick() {
        //todo: start new Question if there is more questions
        startNextQuestion();
    }

    private void onGameFinished() {
        //TODO: Show dialog to go to end the game and show him his score
        Toast.makeText(this, "Game Done", Toast.LENGTH_SHORT).show();
    }
}
