package com.learnrush.gameplay.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.learnrush.R;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionsAndAnswersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionsAndAnswersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionsAndAnswersFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Question = "param1";
    private static final String ARG_Answers = "param2";
    private String TAG = "QuesAndAnsFragmentLOG";

    private String mQuestion;
    private String mAnswers[];

    TextView questionTV;
    RadioGroup answersRG;

    private OnFragmentInteractionListener mListener;
    private String mSelectedAnswer;

    public QuestionsAndAnswersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param question Parameter 1.
     * @param answers Parameter 2.
     * @return A new instance of fragment QuestionsAndAnswersFragment.
     */
    public static QuestionsAndAnswersFragment newInstance(String question, String answers[]) {
        QuestionsAndAnswersFragment fragment = new QuestionsAndAnswersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Question, question);
        args.putStringArray(ARG_Answers, answers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = getArguments().getString(ARG_Question);
            mAnswers = getArguments().getStringArray(ARG_Answers);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questionsand_answers, container, false);

        answersRG = (RadioGroup) view.findViewById(R.id.rg_answers);
        questionTV = (TextView) view.findViewById(R.id.tv_question);
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = answersRG.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                mSelectedAnswer = radioButton.getText().toString();
                onSubmitPressed(mSelectedAnswer);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        questionTV.setText(mQuestion);
        Log.d(TAG, "onActivityCreated: " + mQuestion);
        for (int i = 0; i < answersRG.getChildCount(); i++) {
            ((RadioButton) answersRG.getChildAt(i)).setText(mAnswers[i]);
        }
    }

    public void onSubmitPressed(String answerSelected) {
        if (mListener != null) {
            mListener.onSubmitButtonClicked(answerSelected);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        void onSubmitButtonClicked(String answerSelected);
    }
}
