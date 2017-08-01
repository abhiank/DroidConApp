package com.abhiank.droidconapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abhiank.droidconapp.R;
import com.abhiank.droidconapp.data.DiscussItem;
import com.abhiank.droidconapp.util.Utils;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscussActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ArrayList<String> commentList = new ArrayList<>();
    private EditText commentEditText;
    private ImageButton sendButton;
    DiscussItem parseObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        commentEditText = (EditText) findViewById(R.id.comment_edit_text);
        sendButton = (ImageButton) findViewById(R.id.id_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(commentEditText.getText().toString().trim().equals("")){
                    Toast.makeText(DiscussActivity.this, "No comment entered", Toast.LENGTH_SHORT).show();
                    return;
                }

                Utils.showProgressDialogue(DiscussActivity.this,"");
                parseObject.put("comments",commentEditText.getText().toString()+ "," + parseObject.getComments());
                Utils.showProgressDialogue(DiscussActivity.this, "");
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Utils.dismissProgressDialogue();
                        getLookingForFromParse();
                        commentEditText.setText("");
                        findViewById(R.id.divider).requestFocus();
                        mRecyclerView.scrollToPosition(0);
                        View view = DiscussActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                });
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        getLookingForFromParse();
    }

    public void getLookingForFromParse(){

        if(Utils.isNetworkAvailable(this)){

            Utils.showProgressDialogue(this, "");

            ParseQuery<DiscussItem> placeParseQuery = ParseQuery.getQuery(DiscussItem.class);
            placeParseQuery.getInBackground(getIntent().getStringExtra("id"), new GetCallback<DiscussItem>() {
                @Override
                public void done(DiscussItem object, ParseException e) {
                    if (e == null) {
                        parseObject = object;
                        commentList.clear();

                        if(object.getComments()!=null)
                            commentList = new ArrayList(Arrays.asList(object.getComments().split(",")));

                        adapter = new MyRecyclerAdapter(DiscussActivity.this, commentList);
                        mRecyclerView.setAdapter(adapter);

                        Utils.dismissProgressDialogue();
                    } else {
                        Utils.dismissProgressDialogue();
                        Toast.makeText(DiscussActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "No Internet Available",Toast.LENGTH_LONG).show();
        }
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        private List<String> feedItemList;
        private Context mContext;

        public MyRecyclerAdapter(Context context, List<String> feedItemList) {
            this.feedItemList = feedItemList;
            this.mContext = context;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discuss_list_row, null);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            String feedItem = feedItemList.get(i);

            customViewHolder.titleTextView.setText(feedItem);
        }

        @Override
        public int getItemCount() {
            return (null != feedItemList ? feedItemList.size() : 0);
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleTextView;

        public CustomViewHolder(View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        }
    }
}
