package com.learnrush.gamesList.presenter.CommentsViewHolder;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.learnrush.R;
import com.learnrush.gamesList.model.CommentsModel;

/**
 * LearnRush Created by Mohammed Fareed on 5/14/2017.
 */

public class CommentsViewHolder extends RecyclerView.ViewHolder {
    TextView commentBodyTV, commentAuthorTV, commentTimeTV;

    public CommentsViewHolder(View itemView) {
        super(itemView);
        commentAuthorTV = (TextView) itemView.findViewById(R.id.tv_comment_author);
        commentBodyTV = (TextView) itemView.findViewById(R.id.tv_comment_message);
        commentTimeTV = (TextView) itemView.findViewById(R.id.tv_comment_time);
    }

    public void bind(CommentsModel model) {
        commentAuthorTV.setText(model.getCommentAuthor());
        commentBodyTV.setText(model.getCommentBody());
        commentTimeTV.setText(DateFormat.format("(HH:mm)",
                model.getCommentTime()));
    }
}
