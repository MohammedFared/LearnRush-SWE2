package com.learnrush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learnrush.model.GamesModel;
import com.learnrush.presenter.GamesPresenterImpl;

import static com.learnrush.presenter.GamesPresenterImpl.CLICKED_GAME_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GamesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesFragment extends Fragment implements GamesPresenterImpl.GamePresenter {
    private View mView;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private OnFragmentInteractionListener mListener;
    private FirebaseRecyclerAdapter mGamesAdapter;
    private GamesPresenterImpl mGamesPresenter;
    private String TAG = "GamesFragmentLOG";

    public GamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesFragment newInstance(String param1, String param2) {
        GamesFragment fragment = new GamesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_games, container, false);
        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_games);
        mRecycler.setHasFixedSize(true);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseRecyclerAdapter<GamesModel, GamesPresenterImpl.GamesViewHolder> mAdapter;

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        mDatabase.child("games").child("game_id").child("added_by").setValue("teacherID");

        mRecycler.setLayoutManager(mManager);

        Query query = mDatabase.child("games");
        mAdapter = new FirebaseRecyclerAdapter<GamesModel, GamesPresenterImpl.GamesViewHolder>
                (GamesModel.class, R.layout.single_game_item, GamesPresenterImpl.GamesViewHolder.class, query) {
            @Override
            protected void populateViewHolder(GamesPresenterImpl.GamesViewHolder viewHolder, GamesModel model, int position) {
//                Utils.hideProgressDialog();
                Log.d(TAG, "populateViewHolder: ");
                // On Game Item Click, Go to game Details
                final DatabaseReference clickedGameRef = getRef(position);
                final String gameKey = clickedGameRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mView.getContext(), GameDetails.class);
                        intent.putExtra(CLICKED_GAME_KEY, gameKey);
                        mView.getContext().startActivity(intent);
                    }
                });
                viewHolder.bindViewHolder(model);
//                mGamePresenter.OnAdapterLoaded(mAdapter);
            }
        };
        mRecycler.setAdapter(mAdapter);
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

    @Override
    public void OnAdapterLoaded(FirebaseRecyclerAdapter adapter) {
        mRecycler.setAdapter(adapter);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
