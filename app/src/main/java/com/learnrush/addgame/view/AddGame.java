package com.learnrush.addgame.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.learnrush.R;
import com.learnrush.addgame.model.GameModel;
import com.learnrush.addgame.model.GameQuestionsModel;
import com.learnrush.addgame.presenter.AddGamePresenterImpl;
import com.learnrush.addgame.presenter.IAddGamePresenter;
import com.learnrush.addgame.view.fragments.AddGameDetailsFragment;
import com.learnrush.addgame.view.fragments.AddMatchPictureQuestionsFragment;
import com.learnrush.addgame.view.fragments.AddMultipleChoiceQuestionsFragment;

import java.util.ArrayList;

public class AddGame extends AppCompatActivity implements AddGameDetailsFragment.AddGameDetailsFragmentListener
        , AddMultipleChoiceQuestionsFragment.AddGameQustionsFragmentListener
        , AddMatchPictureQuestionsFragment.OnFragmentMatchPictureListener {

    private ArrayList<GameQuestionsModel> mGameQuestionsArrayList;
    private final int minQuestionsRequired = 5;

    GameModel mGameDetailsModel;
    private int numberOfQuestions = 0;
    private String TAG = "AddGameLOG";
    private String addGameTag = "ADDGAMETAG";
    private TextView mQuestionsCounterTV;
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private String MultipleChoiceOption, MatchpicturesOption, RunCodeGameOption;
    private Button doneBtn;
    private IAddGamePresenter mIaddGamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        mGameQuestionsArrayList = new ArrayList<>();

        mIaddGamePresenter = new AddGamePresenterImpl(this);

        doneBtn = (Button) findViewById(R.id.btn_done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIaddGamePresenter.onAddGameClicked(mGameQuestionsArrayList, mGameDetailsModel);
                finish();
            }
        });
        mQuestionsCounterTV = (TextView) findViewById(R.id.tv_questions_counter);

        if (getActionBar() != null)
            getActionBar().setTitle("Add Game");
        MultipleChoiceOption = getResources().getStringArray(R.array.CATEGORY_ARRAY)[1];
        MatchpicturesOption = getResources().getStringArray(R.array.CATEGORY_ARRAY)[2];
        RunCodeGameOption = getResources().getStringArray(R.array.CATEGORY_ARRAY)[3];

        // Create new fragment and transaction
        Fragment addGameDetailsFragment = new AddGameDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.add_game_fragment_container, addGameDetailsFragment, addGameTag);
//        transaction.addToBackStack(addGameTag);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onGameDetailsContinueClicked(GameModel gameModel) {
        mGameDetailsModel = gameModel;

        if (gameModel.getGame_cateogry().equals(MultipleChoiceOption)){
            newMultipleChoiceFragment();
        } else if(gameModel.getGame_cateogry().equals(MatchpicturesOption)){
//            newMatchPictureFragment();
            Toast.makeText(this, "This game category, Is coming soon isa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "This game category, Is coming soon isa", Toast.LENGTH_SHORT).show();
        }
    }

    private void newMultipleChoiceFragment() {
        Fragment addGameQuestionsFragment;// Create new fragment and transaction
        addGameQuestionsFragment = new AddMultipleChoiceQuestionsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.add_game_fragment_container, addGameQuestionsFragment, addGameTag);
        transaction.addToBackStack(addGameTag);
        // Commit the transaction
        transaction.commit();
    }
    private void newMatchPictureFragment() {
        Fragment addGameQuestionsFragment;// Create new fragment and transaction
        addGameQuestionsFragment = new AddMatchPictureQuestionsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.add_game_fragment_container, addGameQuestionsFragment, addGameTag);
        transaction.addToBackStack(addGameTag);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            //remove last question
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                mGameQuestionsArrayList.remove(mGameQuestionsArrayList.size() - 1);
                updateQuestionsCounterView();
            }
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void updateQuestionsCounterView() {
        mQuestionsCounterTV.setText(getString(R.string.questions_counter_text) + " " + mGameQuestionsArrayList.size());
    }

    @Override
    public void onContinueMultipleChoiceClicked(GameQuestionsModel gameQuestionsModel) {
        if(mGameQuestionsArrayList.size() >= minQuestionsRequired) {
            doneBtn.setVisibility(View.VISIBLE);
        }
        mGameQuestionsArrayList.add(gameQuestionsModel);
        updateQuestionsCounterView();
        newMultipleChoiceFragment();
    }

    @Override
    public void onContinueMatchPictureClicked(GameQuestionsModel gameQuestionsModel) {
        if(mGameQuestionsArrayList.size() >= minQuestionsRequired) {
            doneBtn.setVisibility(View.VISIBLE);
        }
        mGameQuestionsArrayList.add(gameQuestionsModel);
        updateQuestionsCounterView();
        newMatchPictureFragment();
    }
}
