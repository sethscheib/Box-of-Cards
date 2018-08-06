package edu.gvsu.boxofcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.net.Uri;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private enum player_e {singlePlayer, multiPlayer}
    private enum game_e {goFish, war, memory, other}

    player_e player = player_e.singlePlayer;
    game_e game = game_e.goFish;

    goFishActivity gofish;
    concentrationActivity concentration;
    warActivity war;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void backMainButtonPressed(View v) {
        setContentView(R.layout.activity_main);
    }

    public void backPlayButtonPressed(View v) {
        setContentView(R.layout.activity_play);
        updatePlayerView(player);
    }

    public void playButtonPressed(View v) {
        setContentView(R.layout.activity_play);
        updatePlayerView(player_e.singlePlayer);
    }

    public void settingsButtonPressed(View v) {
        setContentView(R.layout.activity_error);
    }

    public void leftPlayNextPressed(View v) {
        player = player_e.singlePlayer;
        updatePlayerView(player);
    }

    public void rightPlayNextPressed(View v) {
        player = player_e.multiPlayer;
        updatePlayerView(player);
    }

    public void leftGameNextPressed(View v) {
        if(game == game_e.goFish) game = game_e.goFish;
        else if(game == game_e.war) game = game_e.goFish;
        else if(game == game_e.memory) game = game_e.war;
        else if(game == game_e.other) game = game_e.memory;
        updateGameView(game);
    }

    public void rightGameNextPressed(View v) {
        if(game == game_e.goFish) game = game_e.war;
        else if(game == game_e.war) game = game_e.memory;
        else if(game == game_e.memory) game = game_e.other;
        else if(game == game_e.other) game = game_e.other;

        updateGameView(game);

        if(game == game_e.other) {
            Button b = findViewById(R.id.infoButton);
            b.setVisibility(View.INVISIBLE);
        }
    }

    public void gameBackButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.VISIBLE);
    }

    public void gameMainMenuButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_main);
    }

    public void gameNewGameButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.INVISIBLE);

        startGame();
    }

    public void gameContinueButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.INVISIBLE);
    }

    public void playerTypePressed(View v) {
        game = game_e.goFish;

        setContentView(R.layout.activity_game);

        updateGameView(game);
        
        if(player == player_e.singlePlayer) {
            //game.set_multiplayer(false);
        } else {
            //game.set_multiplayer(true);
        }
    }

    public void gameTypePressed(View v) {
        startGame();
    }

    private void startGame() {
        if(game == game_e.goFish) {
            setContentView(R.layout.gofish);
            gofish = new goFishActivity(findViewById(R.id.goFishContent));
            //game.startGoFish();
        } else if(game == game_e.war) {
            setContentView(R.layout.war);
            war = new warActivity(findViewById(R.id.warContent));
        } else if(game == game_e.memory) {
            setContentView(R.layout.memory);
            concentration = new concentrationActivity(findViewById(R.id.concentrationContent));
        } else {
            setContentView(R.layout.activity_error);
        }
    }

    public void gameInfoPressed(View v) {
        if(game == game_e.goFish) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://en.wikipedia.org/wiki/Go_Fish")));
        } else if (game == game_e.war) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://en.wikipedia.org/wiki/War_(card_game)")));
        } else if (game == game_e.memory) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://en.wikipedia.org/wiki/Concentration_(game)")));
        }
    }

    private void updatePlayerView(player_e i) {
        ImageView img;

        if(i == player_e.singlePlayer) {
            img = findViewById(R.id.comingSoon);
            img.setVisibility(View.VISIBLE);
            img = findViewById(R.id.multiPlayer);
            img.setVisibility(View.INVISIBLE);
        } else {
            img = findViewById(R.id.comingSoon);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.multiPlayer);
            img.setVisibility(View.VISIBLE);
        }
    }

    private void updateGameView(game_e i) {
        ImageView img;

        if(i == game_e.goFish) {
            img = findViewById(R.id.goFish);
            img.setVisibility(View.VISIBLE);
            img = findViewById(R.id.war);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.memory);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.comingSoon);
            img.setVisibility(View.INVISIBLE);
        } else if(i == game_e.war){
            img = findViewById(R.id.goFish);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.war);
            img.setVisibility(View.VISIBLE);
            img = findViewById(R.id.memory);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.comingSoon);
            img.setVisibility(View.INVISIBLE);
        } else if(i == game_e.memory) {
            img = findViewById(R.id.goFish);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.war);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.memory);
            img.setVisibility(View.VISIBLE);
            img = findViewById(R.id.comingSoon);
            img.setVisibility(View.INVISIBLE);
        } else {
            img = findViewById(R.id.goFish);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.war);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.memory);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.comingSoon);
            img.setVisibility(View.VISIBLE);
        }
    }
}