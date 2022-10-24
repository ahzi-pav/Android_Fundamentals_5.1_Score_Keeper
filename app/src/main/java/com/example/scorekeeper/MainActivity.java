package com.example.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mScore1;
    private TextView mScore2;

    private TextView team1TextView;
    private  TextView team2TextView;

    private TextView clickedTextView;

    private int score1;
    private int score2;

    private String team1;
    private String team2;

    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    static final String STATE_TITLE_1 = "Team 1 Title";
    static final String STATE_TITLE_2 = "Team 2 Title";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.night_mode) {
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        clickedTextView = (TextView) view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            showEditDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore1 = findViewById(R.id.score_1);
        mScore2 = findViewById(R.id.score_2);

        score1 = 0;
        score2 = 0;

        team1TextView = findViewById(R.id.team1);
        team2TextView = findViewById(R.id.team2);

        team1 = team1TextView.getText().toString();
        team2 = team2TextView.getText().toString();

        registerForContextMenu(team1TextView);
        registerForContextMenu(team2TextView);

        if (savedInstanceState != null) {
            score1 = savedInstanceState.getInt(STATE_SCORE_1);
            score2 = savedInstanceState.getInt(STATE_SCORE_2);
            team1 = savedInstanceState.getString(STATE_TITLE_1);
            team2 = savedInstanceState.getString(STATE_TITLE_2);

            mScore1.setText(String.valueOf(score1));
            mScore2.setText(String.valueOf(score2));
            team1TextView.setText(team1);
            team2TextView.setText(team2);
        }
    }

    public void onClickAddButton (View view) {
      int viewId = view.getId();

      switch (viewId) {
          case R.id.increaseTeam1:
              score1++;
              mScore1.setText(String.valueOf(score1));
              break;
          case R.id.increaseTeam2:
              score2++;
              mScore2.setText(String.valueOf(score2));
              break;
      }
    }

    public void onCLickMinusButton (View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.decreaseTeam1:
                if (score1 > 0) {
                    score1--;
                    mScore1.setText(String.valueOf(score1));
                }
                break;
            case R.id.decreaseTeam2:
                if (score2 > 0) {
                    score2--;
                    mScore2.setText(String.valueOf(score2));
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_SCORE_1, score1);
        outState.putInt(STATE_SCORE_2, score2);
        outState.putString(STATE_TITLE_1, team1TextView.getText().toString());
        outState.putString(STATE_TITLE_2, team2TextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void showEditDialog() {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        final EditText editText = new EditText(this);
        myAlertBuilder.setMessage("Please enter new name: ");

        myAlertBuilder.setView(editText);

        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickedTextView.setText(editText.getText().toString());
                Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Edit Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.show();
    }
}