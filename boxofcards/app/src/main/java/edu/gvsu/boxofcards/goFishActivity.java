package edu.gvsu.boxofcards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class goFishActivity extends MainActivity {

    private boolean okToPlay = true;

    View thisView;

    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Card> cardsToMove;

    Game g;
    Card currentCard;

    public goFishActivity(View v) {
        Card c;

        thisView = v;

        //set the game over visibility to invisible
        ImageView gameover = thisView.findViewById(R.id.gameOver);
        gameover.setVisibility(View.INVISIBLE);

        g = new Game("GOFISH");

        players.add(new Player("User"));
        players.add(new Player("Computer"));

        TextView t;

        t = thisView.findViewById(R.id.playerScore);
        t.setText(Integer.toString(players.get(0).score));
        t = thisView.findViewById(R.id.compScore);
        t.setText(Integer.toString(players.get(1).score));

        for(Player p: players) {
            for(int i = 0; i < 5; i++){
                g.deal(p);
            }

            p.sortHand();
        }

        for(int i = 0; i < 5; i++) {
            c = players.get(0).hand.get(i);
            addCard(c, true);

            c = players.get(1).hand.get(i);
            addCard(c, false);
        }

        ArrayList<Card> card = g.checkScore(players.get(0));
        for(Card ca: card) {
            removeCard(ca);
        }

        card = g.checkScore(players.get(1));
        for(Card ca: card) {
            removeCard(ca);
        }

        updateScore();
    }

    private void playCard(boolean player) {
        int gameState;

        gameState = g.tickGoFish(players, currentCard, player);

        if(player) {   //player turn
            if (gameState == 0){
                //go fish
                currentCard = g.deal(players.get(0));
                playerDrawCardDeck(false);
            } else if(gameState == 1){
                //draw from computer
                cardsToMove = g.getMovedCards();
                ArrayList<Card> card = g.checkScore(players.get(0));
                for(Card c: cardsToMove) {
                    removeCard(c);
                    if(!card.contains(c)) addCard(c, player); //true = player takes comp cards
                    else card.remove(c);
                    okToPlay = true;
                }

                for(Card c: card) {
                    removeCard(c);
                }

                updateScore();
                playCard(false);
            } else {
                gameOver();
            }
        } else {             //computer turn
            if (gameState == 0){
                //go fish
                currentCard = g.deal(players.get(1));
                compDrawCardDeck(false);
            } else if(gameState == 1){
                //draw from player
                cardsToMove = g.getMovedCards();
                ArrayList<Card> card = g.checkScore(players.get(1));
                for(Card c: cardsToMove) {
                    removeCard(c);
                    if(!card.contains(c)) addCard(c, player); //true = player takes comp cards
                    else card.remove(c);
                    okToPlay = true;
                }

                for(Card c: card) {
                    removeCard(c);
                }

                if(players.get(0).hand.size() == 0) playCard(true);
            } else {
                gameOver();
            }
            updateScore();
        }
    }

    private void updateScore() {
        TextView t;

        t = thisView.findViewById(R.id.playerScore);
        t.setText(Integer.toString(players.get(0).score));
        t = thisView.findViewById(R.id.compScore);
        t.setText(Integer.toString(players.get(1).score));
    }
/*
    private void gameOver() {
        if(players.get(0).score > players.get(1).score) {
            Snackbar.make(thisView, "Player Wins!", Snackbar.LENGTH_SHORT).show();
        } else if(players.get(0).score < players.get(1).score){
            Snackbar.make(thisView, "Computer Wins!", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(thisView, "Its a Tie!", Snackbar.LENGTH_SHORT).show();
        }

        LinearLayout l = thisView.findViewById(R.id.playerHand);
        l.setVisibility(View.GONE);
        l = thisView.findViewById(R.id.computerHand);
        l.setVisibility(View.GONE);
    }
    */

    private void gameOver() {
        okToPlay = false;

        LinearLayout l = thisView.findViewById(R.id.computerHand);
        l.setVisibility(View.INVISIBLE);
        l = thisView.findViewById(R.id.playerHand);
        l.setVisibility(View.INVISIBLE);

        ImageView v = thisView.findViewById(R.id.deck);
        v.setVisibility(View.INVISIBLE);
        v = thisView.findViewById(R.id.deckl);
        v.setVisibility(View.INVISIBLE);

        //find the game over img view
        ImageView img = thisView.findViewById(R.id.gameOver);
        //find the game over text view
        TextView txt = thisView.findViewById(R.id.gameOverText);

        //determine who won and represent correct icon/text
        if(players.get(0).score > players.get(1).score) {
            img.setBackgroundResource(R.drawable.winnericon);
            txt.setText("winner");
        } else if(players.get(0).score < players.get(1).score){
            img.setBackgroundResource(R.drawable.losericon);
            txt.setText("loser");
        } else if(players.get(0).hand.size() == 0) {
            img.setBackgroundResource(R.drawable.winnericon);
            txt.setText("winner");
        } else {
            img.setBackgroundResource(R.drawable.losericon);
            txt.setText("loser");
        }

        //set the visibility of the img to true
        img.setVisibility(View.VISIBLE);
    }



    private void addCard(Card card, boolean player) {
        final LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(150,200);
        LinearLayout layout;

        if(player) {
            layout = thisView.findViewById(R.id.playerHand); // //layoutID is id of the linearLayout that defined in your main.xml file
        } else {
            layout = thisView.findViewById(R.id.computerHand); // //layoutID is id of the linearLayout that defined in your main.xml file
        }

        final ImageButton img = new ImageButton(thisView.getContext());
        if(player) {
            img.setBackgroundResource(card.getCardImage());
            img.setOnClickListener(cardClicked);
        } else {
            //img.setBackgroundResource(R.drawable.back);
            img.setBackgroundResource(card.getCardImage());
        }
        img.setId(card.getCardInt());
        img.setLayoutParams(imgparams);
        layout.addView(img);
    }

    private void removeCard(Card card) {
        ImageButton img = thisView.findViewById(card.getCardInt());
        img.setOnClickListener(null);
        img.setVisibility(View.GONE);
    }

    private void playerDrawCardDeck(boolean finished) {
        if(finished) {
            resetDeckAnimation(thisView.findViewById(R.id.deckl));
            addCard(currentCard, true);

            ArrayList<Card> card = g.checkScore(players.get(0));
            for(Card c: card) {
                removeCard(c);
            }

            updateScore();
            //playCard(false);
        } else {
            playerDeckAnimation(thisView.findViewById(R.id.deckl));
        }
    }

    private void compDrawCardDeck(boolean finished) {
        if(finished) {
            resetDeckAnimation(thisView.findViewById(R.id.deckl));
            addCard(currentCard, false);

            ArrayList<Card> card = g.checkScore(players.get(1));
            for(Card c: card) {
                removeCard(c);
            }

            updateScore();

            if(players.get(0).hand.size() == 0) playCard(true);
        } else {
            compDeckAnimation(thisView.findViewById(R.id.deckl));
        }
    }

    private void playerDeckAnimation(View v) {
        okToPlay = false;
        ObjectAnimator a = ObjectAnimator.ofFloat(v, "translationY", 400f);
        ObjectAnimator b = ObjectAnimator.ofFloat(v, "rotation", 90f, 0f);

        v.setVisibility(View.VISIBLE);

        a.setDuration(2000);
        a.addListener(playerDraw);
        b.setDuration(1000);

        a.start();
        b.start();
    }

    private void compDeckAnimation(View v) {
        okToPlay = false;
        ObjectAnimator a = ObjectAnimator.ofFloat(v, "translationY", -400f);
        ObjectAnimator b = ObjectAnimator.ofFloat(v, "rotation", 90f, 0f);

        v.setVisibility(View.VISIBLE);

        a.setDuration(2000);
        a.addListener(compDraw);
        b.setDuration(1000);

        a.start();
        b.start();
    }

    private void resetDeckAnimation(View v) {
        v.setX(thisView.findViewById(R.id.deck).getX());
        v.setY(thisView.findViewById(R.id.deck).getY());
        v.setRotation(thisView.findViewById(R.id.deck).getRotation());
    }

    Animator.AnimatorListener playerDraw = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            animation.removeListener(playerDraw);
            playerDrawCardDeck(true);

            playCard(false);
        }
    };

    Animator.AnimatorListener compDraw = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            animation.removeListener(compDraw);
            compDrawCardDeck(true);
            okToPlay = true;
        }
    };

    View.OnClickListener cardClicked = new View.OnClickListener() {

        public void onClick(View v) {
            if (okToPlay) {
                Card tempCard;
                tempCard = getCardFromID(v.getId());
                for (Card c : players.get(0).hand) {
                    //check tempCard is in user's hand. Value and suit
                    if (c.getCardInt() == tempCard.getCardInt()) {
                        currentCard = c;
                    }
                }

                if (currentCard != null) {
                    playCard(true);
                }
            }
        }
    };


    public Card getCardFromID(int cardID) {
        int v, s;
        s = cardID/13;
        v = cardID%13;
        if(v==0){ //ace
            v = 13;
        }

        return new Card(v+1, s);
    }
}
