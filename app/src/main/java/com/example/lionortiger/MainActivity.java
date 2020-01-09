package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //create own customised data types hence enum is used and enum can be created inside or outside of the class.
    enum Player {
        ONE, TWO, NO;
    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoices = new Player[9];
    ArrayList<String> fullPlayer = new ArrayList<>(); // to check for the fullness of the gridView blocks

    int[][] winnerRowsColumns = {{0,1,2},{3,4,5} ,{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}}; //tags numbers of possibilities of the 2 dimen array.

    private boolean gameOver = false;

    private Button btnReset;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        playerChoices[0] = Player.NO;
//        playerChoices[1] = Player.NO;
//        playerChoices[2] = Player.NO;
//        playerChoices[3] = Player.NO;
//        playerChoices[4] = Player.NO;
//        playerChoices[5] = Player.NO;
//        playerChoices[6] = Player.NO;
//        playerChoices[7] = Player.NO;
//        playerChoices[8] = Player.NO;

        for (int index = 0 ; index<playerChoices.length ; index++) {
            playerChoices[index] = Player.NO;
        }

        gridLayout = findViewById(R.id.gridlayout);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSetTheGame();
            }
        });

    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
         int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

         if ((playerChoices[tiTag] == Player.NO) && (gameOver == false)) { //onclick of the imageView again is not considered.

                playerChoices[tiTag] = currentPlayer;
             tappedImageView.setTranslationX(-1000); //-1000 instead of -2000 coz images take long time to come back upon clicking.
             tappedImageView.animate().translationXBy(1000).alpha(1).rotation(3600).setDuration(1000);
                if (currentPlayer == Player.ONE) {
                    tappedImageView.setImageResource(R.drawable.lion);
                    currentPlayer = Player.TWO;

                } else if (currentPlayer == Player.TWO) {
                    tappedImageView.setImageResource(R.drawable.tiger);
                    currentPlayer = Player.ONE;
                }
                fullPlayer.add(tiTag+ " "); // if we add this line at top before if loop then whenever an image view is clicked again (after setting image) its getting added in the array.
                String winnerOfGame = " ";
                for (int[] winnerColumns : winnerRowsColumns) {
                    Log.d("test", "for loop: " + winnerColumns.length);
                    if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] &&
                            playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] && playerChoices[winnerColumns[0]] != Player.NO
                    ) {
                        gameOver = true;
                        btnReset.setVisibility(View.VISIBLE);
                        Log.d("test", "if loop");
                        if (currentPlayer == Player.ONE) {
                            winnerOfGame = "Player TWO : " + "Tiger";
                        } else if (currentPlayer == Player.TWO) {
                            winnerOfGame = "Player ONE : " + " Lion";
                        }
                        Log.d("test", "winnerOfGame:" + winnerOfGame);
                        Toast.makeText(getApplicationContext(), winnerOfGame + " is the  Winner", Toast.LENGTH_SHORT).show();
                    } else if (fullPlayer.size() == playerChoices.length){
                        btnReset.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Nobody is the  Winner", Toast.LENGTH_SHORT).show();
                    }


            }

        }

    }

    //Reset Game Function
    private void reSetTheGame(){
        for (int index=0; index < gridLayout.getChildCount(); index++){
                ImageView imageView = (ImageView) gridLayout.getChildAt(index);
                imageView.setImageDrawable(null);
                imageView.setAlpha(0.2f); // for showing the dark yellow color blocks when no images are set.
        }
        fullPlayer.clear();
        currentPlayer = Player.ONE;
//        playerChoices[0] = Player.NO;
//        playerChoices[1] = Player.NO;
//        playerChoices[2] = Player.NO;
//        playerChoices[3] = Player.NO;
//        playerChoices[4] = Player.NO;
//        playerChoices[5] = Player.NO;
//        playerChoices[6] = Player.NO;
//        playerChoices[7] = Player.NO;
//        playerChoices[8] = Player.NO;

        for (int index = 0 ; index< playerChoices.length ; index++) {
            playerChoices[index] = Player.NO;
        }
        gameOver = false;
        btnReset.setVisibility(View.INVISIBLE); //after resetting the game make the button invisible.
    }

}
