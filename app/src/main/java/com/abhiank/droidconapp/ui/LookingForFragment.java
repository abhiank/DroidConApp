package com.abhiank.droidconapp.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abhiank.droidconapp.data.LookingForItem;
import com.abhiank.droidconapp.R;
import com.abhiank.droidconapp.util.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhimanyuagrawal on 17/12/15.
 */
public class LookingForFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ArrayList<LookingForItem> mLookingForDataList = new ArrayList<>();

    private static LookingForFragment lookingForFragment = null;

    public static LookingForFragment newInstance() {
        if(lookingForFragment==null)
            lookingForFragment = new LookingForFragment();
        return lookingForFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_looking_for, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.looking_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLookingForFromParse();

        return rootView;
    }

    public void getLookingForFromParse(){

        if(Utils.isNetworkAvailable(getActivity())) {

            Utils.showProgressDialogue(getActivity(), "Getting Data..");

            ParseQuery<LookingForItem> placeParseQuery = ParseQuery.getQuery(LookingForItem.class);
            placeParseQuery.orderByDescending("createdAt");
            placeParseQuery.findInBackground(new FindCallback<LookingForItem>() {
                @Override
                public void done(List<LookingForItem> list, ParseException e) {
                    if (e == null) {
                        mLookingForDataList.clear();
                        for (LookingForItem lookingForItem : list)
                            mLookingForDataList.add(lookingForItem); //// TODO: 17/12/15

                        adapter = new MyRecyclerAdapter(getActivity(), mLookingForDataList);
                        mRecyclerView.setAdapter(adapter);

                        Utils.dismissProgressDialogue();
                    } else {
                        Utils.dismissProgressDialogue();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "No Internet Available",Toast.LENGTH_LONG).show();
        }
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        private List<LookingForItem> feedItemList;
        private Context mContext;

        public MyRecyclerAdapter(Context context, List<LookingForItem> feedItemList) {
            this.feedItemList = feedItemList;
            this.mContext = context;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.looking_list_row, null);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            LookingForItem feedItem = feedItemList.get(i);

            customViewHolder.titleTextView.setText(feedItem.getLookingFor());
            customViewHolder.contactTextView.setText(feedItem.getContact());
            customViewHolder.nameTextView.setText(feedItem.getName());
            customViewHolder.linkTextView.setText(feedItem.getMoreAboutMe());
        }

        @Override
        public int getItemCount() {
            return (null != feedItemList ? feedItemList.size() : 0);
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleTextView;
        protected TextView nameTextView;
        protected TextView contactTextView;
        protected TextView linkTextView;

        public CustomViewHolder(View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.title_text_view);
            this.nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            this.contactTextView = (TextView) view.findViewById(R.id.contact_text_view);
            this.linkTextView = (TextView) view.findViewById(R.id.link_text_view);
        }
    }

}
