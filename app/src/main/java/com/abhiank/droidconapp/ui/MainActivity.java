package com.abhiank.droidconapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abhiank.droidconapp.R;
import com.abhiank.droidconapp.data.AnnouncementItem;
import com.abhiank.droidconapp.data.DiscussItem;
import com.abhiank.droidconapp.data.LookingForItem;
import com.abhiank.droidconapp.util.Utils;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton fab;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mViewPager.getCurrentItem()){
                    case 0:
                        showLookingForDialog();
                        break;
                    case 1:
                        showAnnouncementDialog();
                        break;
                    case 2:
                        showDiscussDialog();
                        break;
                }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));
                        break;
                    case 1:
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.icon_announce));
                        break;
                    case 2:
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.icon_discuss));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Utils.showMaterialDialog(MainActivity.this, "About",
                    "A app just for Droidcon which I thought would be useful... Hope it helped\n\nAbhimanyu Agrawal\nabhi.rocks209@gmail.com",null,null,null,null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){
                case 0:
                    return LookingForFragment.newInstance();
                case 1:
                    return AnnouncementFragment.newInstance();
                case 2:
                    return DiscussFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Looking For";
                case 1:
                    return "Announce";
                case 2:
                    return "Discuss";
            }
            return null;
        }
    }

    public void showAnnouncementDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle("Create Announcement");
        builder.setView(R.layout.dialog_announcement);
        builder.setPositiveButton("Post", null);
        builder.setNegativeButton("Dismiss", null);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText announcementEditText = (EditText) dialog.findViewById(R.id.announcement_edit_text);
                        EditText postByEditText = (EditText) dialog.findViewById(R.id.postby_edit_text);
                        TextInputLayout textInputLayout1 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout1);
                        TextInputLayout textInputLayout2 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout2);

                        boolean error = false;
                        if (announcementEditText.getText().toString().trim().equals("")) {
                            textInputLayout1.setError("Enter Some Text..");
                            error = true;
                        }
                        if (postByEditText.getText().toString().trim().equals("")) {
                            textInputLayout2.setError("Enter Some Name");
                            error = true;
                        }

                        if(!error) {
                            alertDialog.dismiss();
                            ParseObject newAnnouncement = new AnnouncementItem();
                            newAnnouncement.put("announcementText", announcementEditText.getText().toString());
                            newAnnouncement.put("announceBy", postByEditText.getText().toString());
                            ParseACL defaultACL = new ParseACL();
                            defaultACL.setPublicReadAccess(true);
                            defaultACL.setPublicWriteAccess(true);
                            newAnnouncement.setACL(defaultACL);
                            Utils.showProgressDialogue(MainActivity.this, "");
                            newAnnouncement.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Utils.dismissProgressDialogue();
                                    AnnouncementFragment.newInstance().getLookingForFromParse();
                                }
                            });
                        }
                    }
                });
            }
        });
        AppCompatDialog forgotDialog = alertDialog;
        forgotDialog.show();

    }

    public void showDiscussDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle("Create Discussion");
        builder.setView(R.layout.dialog_discuss);
        builder.setPositiveButton("Create", null);
        builder.setNegativeButton("Dismiss", null);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText announcementEditText = (EditText) dialog.findViewById(R.id.announcement_edit_text);
                        EditText postByEditText = (EditText) dialog.findViewById(R.id.postby_edit_text);
                        TextInputLayout textInputLayout1 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout1);
                        TextInputLayout textInputLayout2 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout2);

                        boolean error = false;
                        if (announcementEditText.getText().toString().trim().equals("")) {
                            textInputLayout1.setError("Enter Some Title..");
                            error = true;
                        }
                        if (postByEditText.getText().toString().trim().equals("")) {
                            textInputLayout2.setError("Enter Some Description");
                            error = true;
                        }

                        if(!error) {
                            alertDialog.dismiss();
                            ParseObject newAnnouncement = new DiscussItem();
                            newAnnouncement.put("topicTitle", announcementEditText.getText().toString());
                            newAnnouncement.put("creatorName", postByEditText.getText().toString());
                            ParseACL defaultACL = new ParseACL();
                            defaultACL.setPublicReadAccess(true);
                            defaultACL.setPublicWriteAccess(true);
                            newAnnouncement.setACL(defaultACL);
                            Utils.showProgressDialogue(MainActivity.this, "");
                            newAnnouncement.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Utils.dismissProgressDialogue();
                                    DiscussFragment.newInstance().getLookingForFromParse();
                                }
                            });
                        }
                    }
                });
            }
        });
        AppCompatDialog forgotDialog = alertDialog;
        forgotDialog.show();

    }

    public void showLookingForDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle("Create Post");
        builder.setView(R.layout.dialog_looking_for);
        builder.setPositiveButton("Post", null);
        builder.setNegativeButton("Dismiss", null);

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText lookingForEditText = (EditText) dialog.findViewById(R.id.looking_for_edit_text);
                        EditText nameEditText = (EditText) dialog.findViewById(R.id.name_edit_text);
                        EditText contactEditText = (EditText) dialog.findViewById(R.id.contact_edit_text);
                        EditText moreEditText = (EditText) dialog.findViewById(R.id.more_edit_text);
                        TextInputLayout textInputLayout1 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout1);
                        TextInputLayout textInputLayout2 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout2);
                        TextInputLayout textInputLayout3 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout3);
                        TextInputLayout textInputLayout4 = (TextInputLayout) dialog.findViewById(R.id.text_input_layout4);

                        boolean error = false;
                        if (lookingForEditText.getText().toString().trim().equals("")) {
                            textInputLayout1.setError("Enter Some Title..");
                            error = true;
                        }
                        if (nameEditText.getText().toString().trim().equals("")) {
                            textInputLayout2.setError("Enter Your Name");
                            error = true;
                        }
                        if (contactEditText.getText().toString().trim().equals("")) {
                            textInputLayout3.setError("Enter Contact Detail");
                            error = true;
                        }

                        if (!error) {
                            alertDialog.dismiss();
                            ParseObject newLookingForPost = new LookingForItem();
                            newLookingForPost.put("lookingFor", lookingForEditText.getText().toString());
                            newLookingForPost.put("name", nameEditText.getText().toString());
                            newLookingForPost.put("contactMeAt", contactEditText.getText().toString());
                            newLookingForPost.put("linkToMe", moreEditText.getText().toString());
                            ParseACL defaultACL = new ParseACL();
                            defaultACL.setPublicReadAccess(true);
                            defaultACL.setPublicWriteAccess(true);
                            newLookingForPost.setACL(defaultACL);
                            Utils.showProgressDialogue(MainActivity.this, "");
                            newLookingForPost.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Utils.dismissProgressDialogue();
                                    LookingForFragment.newInstance().getLookingForFromParse();
                                }
                            });
                        }

                    }
                });
            }
        });
        AppCompatDialog forgotDialog = alertDialog;
        forgotDialog.show();

    }
}
