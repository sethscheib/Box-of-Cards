package edu.gvsu.boxofcards;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.widget.TextView;

import java.util.ArrayList;

public class warActivity extends MainActivity {
    private enum animationState_e {playCard, flipCard, finished}

    View thisView;

    Game g;

    final LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(150,200);

    ArrayList<Player> players = new ArrayList<>();

    Card compCard, playerCard;

    int x;

    int looper = 0;

    boolean okToPlay = true;

    int animationTime = 1000;

    int Ymodifier = 61;

    warActivity(View v) {
        ImageButton img;
        thisView = v;

        //set the game over visibility to invisible
        ImageView gameover = thisView.findViewById(R.id.gameOver);
        gameover.setVisibility(View.INVISIBLE);

        g = new Game("WAR");

        players.add(new Player("User"));
        players.add(new Player("Comp"));

        g.initWar(players);

        img = thisView.findViewById(R.id.playerNACard);
        img.setOnClickListener(cardClicked);

        TextView t = thisView.findViewById(R.id.playerScore);
        t.setText(Integer.toString(players.get(0).hand.size()));

        t = thisView.findViewById(R.id.compScore);
        t.setText(Integer.toString(players.get(1).hand.size()));
    }

    private void warTick(animationState_e state) {

         if (state == animationState_e.playCard) {
            x = g.tickWar(players);
            playerCard = g.getUserCardWar();
            compCard = g.getCompCardWar();
            playAnimationPlayCard();
             if(g.win == 43 || g.win == 13) {
                 gameOver();
                 return;
             }
        }
        else if (state == animationState_e.flipCard){
             ImageView img = thisView.findViewById(R.id.playerBattleC);
             img.setBackgroundResource(playerCard.image);
             img.setVisibility(View.VISIBLE);
             img = thisView.findViewById(R.id.compBattleC);
             img.setBackgroundResource(compCard.image);
             img.setVisibility(View.VISIBLE);
             warTick(animationState_e.finished);
        }
        else {
            if (x == 1) {
                //user won
            } else if (x == -1) {
                //computer won
            } else {
                //war
                okToPlay = false;
                playAnimationPlayWar();
            }

             TextView t = thisView.findViewById(R.id.playerScore);
             t.setText(Integer.toString(players.get(0).hand.size()));

             t = thisView.findViewById(R.id.compScore);
             t.setText(Integer.toString(players.get(1).hand.size()));
        }

    }

    private void gameOver() {
        okToPlay = false;
        //find the game over img view
        ImageView img = thisView.findViewById(R.id.gameOver);
        //find the game over text view
        TextView txt = thisView.findViewById(R.id.gameOverText);

        //determine who won and represent correct icon/text
        if (g.win == 42) {
            img.setBackgroundResource(R.drawable.winnericon);
            txt.setText("winner");
        } else {
            img.setBackgroundResource(R.drawable.losericon);
            txt.setText("loser");
        }

        //set the visibility of the img to true
        img.setVisibility(View.VISIBLE);
    }

    private void resetAnimation() {
        ImageView p = thisView.findViewById(R.id.playerACard);
        ImageView c = thisView.findViewById(R.id.compACard);

        p.setX(0);
        p.setY(0);

        p.setVisibility(View.INVISIBLE);

        c.setX(0);
        c.setY(0);

        c.setVisibility(View.INVISIBLE);

        p = thisView.findViewById(R.id.playerBattleS);
        p.setVisibility(View.INVISIBLE);

        c = thisView.findViewById(R.id.compBattleS);
        c.setVisibility(View.INVISIBLE);
    }

    private void playAnimationPlayWar() {
        ImageView pCard;
        ImageView cCard;
        int[] locStart = new int[2];
        int[] locEnd = new int[2];
        float i, j;
        ObjectAnimator a, b, c, d;

        //setup animator and animated card for the player
        pCard = thisView.findViewById(R.id.playerACard);
        thisView.findViewById(R.id.playerNACard).getLocationOnScreen(locStart);
        thisView.findViewById(R.id.playerBattleS).getLocationOnScreen(locEnd);
        pCard.setX(locStart[0]);
        pCard.setY(locStart[1]-Ymodifier);
        i = locEnd[0] - locStart[0];
        j = locEnd[1] - locStart[1];
        a = ObjectAnimator.ofFloat(pCard, "translationX", i);
        b = ObjectAnimator.ofFloat(pCard, "translationY", j);

        //setup animator and the animated card for the computer
        cCard = thisView.findViewById(R.id.compACard);
        thisView.findViewById(R.id.compNACard).getLocationOnScreen(locStart);
        thisView.findViewById(R.id.compBattleS).getLocationOnScreen(locEnd);
        cCard.setX(locStart[0]);
        cCard.setY(locStart[1]-Ymodifier);
        i = locEnd[0] - locStart[0];
        j = locEnd[1] - locStart[1];
        c = ObjectAnimator.ofFloat(cCard, "translationX", i);
        d = ObjectAnimator.ofFloat(cCard, "translationY", j);

        pCard.setVisibility(View.VISIBLE);
        cCard.setVisibility(View.VISIBLE);

        a.setDuration(animationTime);
        b.setDuration(animationTime);
        c.setDuration(animationTime);
        d.setDuration(animationTime);
        a.addListener(pw);

        d.start();
        c.start();
        b.start();
        a.start();
    }

    private void playAnimationPlayCard() {
        ImageView pCard;
        ImageView cCard;
        int[] locStart = new int[2];
        int[] locEnd = new int[2];
        float i, j;
        ObjectAnimator a, b, c, d;

        //make the current displayed cards invisable
        pCard = thisView.findViewById(R.id.playerBattleC);
        pCard.setVisibility(View.INVISIBLE);
        cCard = thisView.findViewById(R.id.compBattleC);
        cCard.setVisibility(View.INVISIBLE);

        //setup animator and animated card for the player
        pCard = thisView.findViewById(R.id.playerACard);
        thisView.findViewById(R.id.playerNACard).getLocationOnScreen(locStart);
        thisView.findViewById(R.id.playerBattleC).getLocationOnScreen(locEnd);
        pCard.setX(locStart[0]);
        pCard.setY(locStart[1]-Ymodifier);
        i = locEnd[0] - locStart[0];
        j = locEnd[1] - locStart[1];
        a = ObjectAnimator.ofFloat(pCard, "translationX", i);
        b = ObjectAnimator.ofFloat(pCard, "translationY", j);

        //setup animator and the animated card for the computer
        cCard = thisView.findViewById(R.id.compACard);
        thisView.findViewById(R.id.compNACard).getLocationOnScreen(locStart);
        thisView.findViewById(R.id.compBattleC).getLocationOnScreen(locEnd);
        cCard.setX(locStart[0]);
        cCard.setY(locStart[1]-Ymodifier);
        i = locEnd[0] - locStart[0];
        j = locEnd[1] - locStart[1];
        c = ObjectAnimator.ofFloat(cCard, "translationX", i);
        d = ObjectAnimator.ofFloat(cCard, "translationY", j);

        pCard.setVisibility(View.VISIBLE);
        cCard.setVisibility(View.VISIBLE);

        a.setDuration(animationTime);
        b.setDuration(animationTime);
        c.setDuration(animationTime);
        d.setDuration(animationTime);
        a.addListener(pc);

        d.start();
        c.start();
        b.start();
        a.start();
    }

    Animator.AnimatorListener pw = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            animation.removeListener(pw);
            if(looper < 2) {
                ImageView p = thisView.findViewById(R.id.playerBattleS);
                ImageView c = thisView.findViewById(R.id.compBattleS);
                p.setVisibility(View.VISIBLE);
                c.setVisibility(View.VISIBLE);
                looper++;
                playAnimationPlayWar();
            } else {
                looper=0;
                warTick(animationState_e.playCard);
            }
        }
    };

    Animator.AnimatorListener pc = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            animation.removeListener(pc);
            resetAnimation();
            okToPlay = true;
            warTick(animationState_e.flipCard);
        }
    };

    View.OnClickListener cardClicked = new View.OnClickListener() {

        public void onClick(View v) {
            if(okToPlay) {
                okToPlay = false;

                warTick(animationState_e.playCard);
            }
        }
    };
}
