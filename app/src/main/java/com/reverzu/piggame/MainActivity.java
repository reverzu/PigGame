package com.reverzu.piggame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rlLeftPanel, rlRightPanel;
    Button newGame, rollDice, hold;
    TextView mainScorePlayer1, mainScorePlayer2, currentScorePlayer1, currentScorePlayer2, winnerBanner;
    ImageView diceImage;
    EditText player1NameLabel, player2NameLabel;

    ObjectAnimator animation_fade_in, animation_fade_out, animation_dice_roll;

    private int player1MainScore = 0, player2MainScore = 0, player1CurrentScore = 0, player2CurrentScore = 0, imageIndex = 0, rotationDirection = 1;
    private Boolean togglePlayer = false;
    private String player1Name, player2Name;
    private

    int[] images = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlLeftPanel = (RelativeLayout) findViewById(R.id.rl_player_1);
        rlRightPanel = (RelativeLayout) findViewById(R.id.rl_player_2);

        newGame = (Button) findViewById(R.id.btn_new_game);
        rollDice = (Button) findViewById(R.id.btn_roll_dice);
        hold = (Button) findViewById(R.id.btn_hold);

        mainScorePlayer1 = (TextView) findViewById(R.id.tv_main_score_player_1);
        mainScorePlayer2 = (TextView) findViewById(R.id.tv_main_score_player_2);
        currentScorePlayer1 = (TextView) findViewById(R.id.tv_current_score_player_1);
        currentScorePlayer2 = (TextView) findViewById(R.id.tv_current_score_player_2);
        winnerBanner = (TextView) findViewById(R.id.tv_winner_banner);

        player1NameLabel = (EditText) findViewById(R.id.et_player_1_name);
        player2NameLabel = (EditText) findViewById(R.id.et_player_2_name);

        diceImage = (ImageView) findViewById(R.id.dice_image);

        newGame();

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });

        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceRoll();
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdScore();
            }
        });


    }

    private void startAnimation(View v1, View v2) {
        animation_fade_in = ObjectAnimator.ofFloat(v1, "alpha", 0.5f, 1.0f);
        animation_fade_in.setDuration(1000);

        animation_fade_out = ObjectAnimator.ofFloat(v2, "alpha", 1.0f, 0.5f);
        animation_fade_out.setDuration(1000);

        animation_fade_in.start();
        animation_fade_out.start();

    }

    private void toggle() {
        togglePlayer = !togglePlayer;
        if (!togglePlayer) {
            startAnimation(rlLeftPanel, rlRightPanel);
            player2CurrentScore = 0;
            currentScorePlayer2.setText("0");
            player1NameLabel.setTypeface(player1NameLabel.getTypeface(), Typeface.BOLD);
            player2NameLabel.setTypeface(player2NameLabel.getTypeface(), Typeface.NORMAL);
        } else if (togglePlayer) {
            startAnimation(rlRightPanel, rlLeftPanel);
            player1CurrentScore = 0;
            currentScorePlayer1.setText("0");
            player1NameLabel.setTypeface(player1NameLabel.getTypeface(), Typeface.NORMAL);
            player2NameLabel.setTypeface(player2NameLabel.getTypeface(), Typeface.BOLD);
        }
    }

    private void newGame(){
        player1CurrentScore = 0;
        player2CurrentScore = 0;
        player1MainScore = 0;
        player2MainScore = 0;
        mainScorePlayer1.setText("0");
        mainScorePlayer2.setText("0");
        diceImage.setVisibility(View.GONE);
        player1NameLabel.clearFocus();
        player2NameLabel.clearFocus();
        currentScorePlayer1.setText("0");
        currentScorePlayer2.setText("0");
        winnerBanner.setText("");
        rollDice.setClickable(true);
        rollDice.setActivated(true);
        hold.setClickable(true);
        hold.setActivated(true);
        togglePlayer = false;
        startAnimation(rlLeftPanel, rlRightPanel);
    }

    private void diceRoll(){
        diceImage.setVisibility(View.VISIBLE);
        player1NameLabel.clearFocus();
        player2NameLabel.clearFocus();

        animation_dice_roll = ObjectAnimator.ofFloat(diceImage, "rotation", 0, 360 * 6 * rotationDirection);

        rotationDirection = (rotationDirection==1)?(-1):(1);

        animation_dice_roll.setDuration(50);
        animation_dice_roll.setRepeatCount(6);
        animation_dice_roll.setRepeatMode(ObjectAnimator.REVERSE);
        animation_dice_roll.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                diceImage.setImageResource(images[imageIndex]);
                imageIndex = (imageIndex + 1) % images.length;
            }
        });

        animation_dice_roll.start();


        int diceRoll = (int) Math.ceil((Math.random()*10)%6);
        switch (diceRoll){
            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceImage.setImageResource(R.drawable.dice6);
                break;
            default:
                break;
        }
        if(diceRoll==1){
            toggle();
        }else {
            if(!togglePlayer){
                player1CurrentScore+=diceRoll;
                currentScorePlayer1.setText(player1CurrentScore+"");
            }else if(togglePlayer){
                player2CurrentScore+=diceRoll;
                currentScorePlayer2.setText(player2CurrentScore+"");
            }
        }
    }

    private void holdScore(){
        player1NameLabel.clearFocus();
        player2NameLabel.clearFocus();
        if (!togglePlayer) {
            player1MainScore  = player1MainScore + Integer.parseInt(currentScorePlayer1.getText() + "");
            mainScorePlayer1.setText(player1MainScore + "");
            if (player1MainScore >= 100) {
                player1Name = (player1NameLabel.getText().toString().isEmpty()) ? "Player 1" : player1NameLabel.getText().toString();
                player2Name = (player2NameLabel.getText().toString().isEmpty()) ? "Player 2" : player2NameLabel.getText().toString();
                winnerBanner.setText(player1Name + " Wins 🎉");
                block(1);
            }
            toggle();
        } else if (togglePlayer) {
            player2MainScore  = player2MainScore + Integer.parseInt(currentScorePlayer2.getText() + "");
            mainScorePlayer2.setText(player2MainScore + "");
            if (player2MainScore >= 100) {
                player1Name = (player1NameLabel.getText().toString().isEmpty()) ? "Player 1" : player1NameLabel.getText().toString();
                player2Name = (player2NameLabel.getText().toString().isEmpty()) ? "Player 2" : player2NameLabel.getText().toString();
                winnerBanner.setText(player2Name + " Wins 🎉");
                block(2);
            }
            toggle();
        }
    }

    private void block(int player){
        rollDice.setClickable(false);
        rollDice.setActivated(false);
        hold.setClickable(false);
        hold.setActivated(false);
        player1NameLabel.clearFocus();
        player2NameLabel.clearFocus();
        togglePlayer = player != 1;
        toggle();
    }

}