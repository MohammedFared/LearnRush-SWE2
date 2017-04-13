package com.learnrush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learnrush.model.GamesModel;
import com.learnrush.presenter.GamesPresenter;
import com.learnrush.presenter.GamesViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GamesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GamesFragment extends Fragment {
    private View mView;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private OnFragmentInteractionListener mListener;
    private FirebaseRecyclerAdapter mGamesAdapter;
    private String TAG = "GamesFragmentLOG";
    private static FloatingActionButton fab;
    static String CLICKED_GAME_KEY = "game_key";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Query mQuery;

    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_games, container, false);

        fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);

        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_games);
        mRecycler.setHasFixedSize(true);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GamesPresenter mGamesPresenter = new GamesPresenter(mListener, mView);
        mListener = mGamesPresenter;
        FirebaseRecyclerAdapter<GamesModel, GamesViewHolder> mAdapter;

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        mDatabase.child("games").child("game_id").child("added_by").setValue("teacherID");

        mRecycler.setLayoutManager(mManager);

        // Fetches the data from the firebase DataBase
        mQuery = mDatabase.child("games");
        getGames(mQuery);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddGame.class));
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGames(mQuery);
            }
        });
    }

    private void getGames(final Query query) {
        FirebaseRecyclerAdapter<GamesModel, GamesViewHolder> mAdapter;
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter = new FirebaseRecyclerAdapter<GamesModel, GamesViewHolder>
                (GamesModel.class, R.layout.single_game_item, GamesViewHolder.class, query) {
            @Override
            protected void populateViewHolder(GamesViewHolder viewHolder, GamesModel model, int position) {
                mSwipeRefreshLayout.setRefreshing(false);
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
                // sets the values to the UI
                viewHolder.bindViewHolder(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.games, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            mListener.onLogOut();
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void showAddGameFAB(){
        fab.setVisibility(View.VISIBLE);
    }

    public static void hideAddGameFAB(){
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        void onLogOut();
    }
}
