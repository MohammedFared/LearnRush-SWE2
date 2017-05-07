package com.learnrush.addgame.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.learnrush.R;
import com.learnrush.addgame.model.GameQuestionsModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddGameQustionsFragmentListener} interface
 * to handle interaction events.
 * Use the {@link AddMultipleChoiceQuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMultipleChoiceQuestionsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View mView;
    Button continueBtn;
    EditText questionET, answerET;
    private String mParam1;
    private String mParam2;

    private AddGameQustionsFragmentListener mListener;

    public AddMultipleChoiceQuestionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMultipleChoiceQuestionsFragment.
     */
    public static AddMultipleChoiceQuestionsFragment newInstance(String param1, String param2) {
        AddMultipleChoiceQuestionsFragment fragment = new AddMultipleChoiceQuestionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_game_questions, container, false);
        //bind the views
        continueBtn = (Button) mView.findViewById(R.id.btn_continue_add_multible_choice_questions);
        questionET = (EditText) mView.findViewById(R.id.et_question_multiple_choice);
        answerET = (EditText) mView.findViewById(R.id.et_right_answer_multiple_choice);

        // onCLickListener
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameQuestionsModel gameQuestionsModel
                        = new GameQuestionsModel(questionET.getText().toString(),
                                answerET.getText().toString());
                if (mListener != null)
                    mListener.onContinueMultipleChoiceClicked(gameQuestionsModel);
            }
        });
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddGameQustionsFragmentListener) {
            mListener = (AddGameQustionsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IGamesPresenter");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AddGameQustionsFragmentListener {
        void onContinueMultipleChoiceClicked(GameQuestionsModel gameQuestionsModel);
    }
}
