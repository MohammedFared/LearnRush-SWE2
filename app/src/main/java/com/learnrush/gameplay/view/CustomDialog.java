package com.learnrush.gameplay.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnrush.R;

/**
 * LearnRush Created by Mohammed Fareed on 5/6/2017.
 */

public class CustomDialog extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IS_RIGHT_ANSWER = "param1";
    private static final String ARG_RIGHT_ANSWER = "param2";

    private boolean mIsRightAnswer;
    private String mRightAnswer;
    private View view;

    NoticeDialogListener mListener;

    // Factory method
    public static CustomDialog newInstance(boolean isRightAnswer, String rightAnswer) {
        CustomDialog fragment = new CustomDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_RIGHT_ANSWER, isRightAnswer);
        args.putString(ARG_RIGHT_ANSWER, rightAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsRightAnswer = getArguments().getBoolean(ARG_IS_RIGHT_ANSWER);
            mRightAnswer = getArguments().getString(ARG_RIGHT_ANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, container);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_dialog);
        TextView textView = (TextView) view.findViewById(R.id.tv_dialog_message);
        if (mIsRightAnswer) {
            imageView.setImageResource(R.drawable.ic_right_black_24dp);
            textView.setText(R.string.right_answer_dialog_message);
        } else {
            imageView.setImageResource(R.drawable.ic_wrong_black_24dp);
            textView.setText(String.format(getString(R.string.wrog_answer_dialog_message), mRightAnswer));
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogPositiveClick();
                    }
                });
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    public interface NoticeDialogListener {
        public void onDialogPositiveClick();
    }
}
