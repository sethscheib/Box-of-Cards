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

public class concentrationActivity extends MainActivity {

    private enum turn_e {init, finishAnimation, handleCard}

    ;

    private View thisView;

    private boolean okToPlay = true;

    private Player user = new Player("User");
    private Player comp = new Player("Comp");
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayList<Integer> cardID = new ArrayList<>();

    private int x;

    private Game g;

    private boolean compTurn = false;
    private boolean one = true;

    /**
     * Sets up the concentration game
     *
     * @param v
     */

    public concentrationActivity(View v) {
        int id;

        //get context for views
        thisView = v;

        //set the game over visibility to invisible
        ImageView gameover = thisView.findViewById(R.id.gameOver);
        gameover.setVisibility(View.INVISIBLE);

        //start a new game
        g = new Game("MEMORY");

        //update player scores
        TextView text = thisView.findViewById(R.id.playerScore);
        text.setText(Integer.toString(user.score));
        text = thisView.findViewById(R.id.compScore);
        text.setText(Integer.toString(comp.score));

        //create the card layout (board)
        final LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(130, 200);
        LinearLayout layout;

        for (int i = 0; i < 4; i++) {
            if (i == 0) id = R.id.row0;
            else if (i == 1) id = R.id.row1;
            else if (i == 2) id = R.id.row2;
            else id = R.id.row3;

            layout = thisView.findViewById(id); // //layoutID is id of the linearLayout that defined in your main.xml file

            for (int j = 0; j < 8; j++) {

                final ImageButton img = new ImageButton(thisView.getContext());
                img.setBackgroundResource(R.drawable.back);
                img.setId((i * 10) + j);
                img.setLayoutParams(imgparams);
                img.setOnClickListener(cardClicked);
                layout.addView(img);
            }
        }
    }

    /**
     * Plays through the animations of the users hand
     * <p>
     * Has two states:  Animate the card clicked
     * Determine the state of the game based on cards
     *
     * @param finished
     */

    private void cardClicked(boolean finished) {
        int x;

        //check if cards animations are done
        if (finished) {
            //tick the game
            x = g.tickMemory(user, cardList);

            //determine game state
            if (x == 1) {
                //match found... hide cards, update score, and clear lists
                ImageButton b = thisView.findViewById(cardID.get(0));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);
                b = thisView.findViewById(cardID.get(1));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);

                TextView text = thisView.findViewById(R.id.playerScore);
                text.setText(Integer.toString(user.score));

                cardList.clear();
                cardID.clear();

                //start computer turn
                okToPlay = false;
                computersTurn(turn_e.init);
            } else if (x == 2) {
                //card not found so flip the cards back

                cardFlipBackAnimationFirst();
            } else if (x == 3) {
                //someone won because there are no more cards
                ImageButton b = thisView.findViewById(cardID.get(0));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);
                b = thisView.findViewById(cardID.get(1));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);

                gameOver();
            }

            //else only 1 card was played so play again

        } else {
            //play the card flipped animation
            cardFlipAnimationFirst(thisView.findViewById(cardID.get(cardID.size() - 1)));
        }
    }

    /**
     * Plays through the animations of the computers hand
     * <p>
     * Has three states: Ticking the game and animating the first car flip
     * Animating the second card flip
     * Determining the result of the tick and adjusting display
     *
     * @param turn
     */

    private void computersTurn(turn_e turn) {
        compTurn = true;

        if (turn == turn_e.init) {
            //tick the game with the computer and empty card list
            x = g.tickMemory(comp, cardList);
            //get the first card the computer played
            cardList.add(g.getCompCardList(0));
            cardID.add(g.findCard(cardList.get(0)));
            //play card flip animation
            cardFlipAnimationFirst(thisView.findViewById(cardID.get(0)));
        } else if (turn == turn_e.finishAnimation) {
            //get the second card the computer played
            cardList.add(g.getCompCardList(1));
            cardID.add(g.findCard(cardList.get(1)));
            //play card flip animation
            cardFlipAnimationFirst(thisView.findViewById(cardID.get(1)));
        } else {
            //determine the state of the game
            if (x == 1) {
                //match found... hide cards, update score, and clear lists
                ImageButton b = thisView.findViewById(cardID.get(0));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);
                b = thisView.findViewById(cardID.get(1));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);

                TextView text = thisView.findViewById(R.id.compScore);
                text.setText(Integer.toString(comp.score));

                cardList.clear();
                cardID.clear();

                compTurn = false;
            } else if (x == 2) {
                //card not found so flip the cards back
                cardFlipBackAnimationFirst();
            } else if (x == 3) {
                //someone one because no more cards
                ImageButton b = thisView.findViewById(cardID.get(0));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);
                b = thisView.findViewById(cardID.get(1));
                b.setVisibility(View.INVISIBLE);
                b.setOnClickListener(null);

                gameOver();
            }
        }
    }

    /**
     *
     */

    private void gameOver() {
        okToPlay = false;
        //find the game over img view
        ImageView img = thisView.findViewById(R.id.gameOver);
        //find the game over text view
        TextView txt = thisView.findViewById(R.id.gameOverText);

        //determine who won and represent correct icon/text
        if (user.score > comp.score) {
            img.setBackgroundResource(R.drawable.winnericon);
            txt.setText("winner");
        } else {
            img.setBackgroundResource(R.drawable.losericon);
            txt.setText("loser");
        }

        //set the visibility of the img to true
        img.setVisibility(View.VISIBLE);
    }

    /**
     * Starts the card flip to the face of the card
     *
     * @param v
     */

    private void cardFlipAnimationFirst(View v) {
        okToPlay = false;
        //define the animation to rotate about the y axis from the back to the side of the card
        ObjectAnimator a = ObjectAnimator.ofFloat(v, "rotationY", 180f, 90f);

        //half second animation
        a.setDuration(500);

        //set animation listener to a
        a.addListener(flo);

        //start the animation
        a.start();
    }

    /**
     * Finishes the card flip to the face of the card
     *
     * @param v
     */

    private void cardFlipAnimationSecond(View v) {
        okToPlay = false;
        //define the animation to rotate about the y axis from the side of the card to the font
        ObjectAnimator a = ObjectAnimator.ofFloat(v, "rotationY", 90f, 0f);

        //half second animation
        a.setDuration(500);

        //set animation listener to a
        a.addListener(flt);

        //start the animation
        a.start();
    }

    /**
     * Flips the card half way over to end up on the back
     */

    private void cardFlipBackAnimationFirst() {
        okToPlay = false;
        //find the two cards views
        View x = thisView.findViewById(cardID.get(0));
        View y = thisView.findViewById(cardID.get(1));

        //define the animation to rotate about the y axis from the front of the card to the side
        ObjectAnimator a = ObjectAnimator.ofFloat(x, "rotationY", 0f, 90f);
        ObjectAnimator b = ObjectAnimator.ofFloat(y, "rotationY", 0f, 90f);

        //half second animation
        a.setDuration(500);
        b.setDuration(500);

        //set the animation to a
        a.addListener(fblo);

        //start b then a so b will be finished when the listener on a hits
        b.start();
        a.start();
    }

    /**
     * Finishes the card flip to the back
     */

    private void cardFlipBackAnimationSecond() {
        okToPlay = false;
        //find the two cards views
        View x = thisView.findViewById(cardID.get(0));
        View y = thisView.findViewById(cardID.get(1));

        //define the animation to rotate about the y axis from the side of the card to the back
        ObjectAnimator a = ObjectAnimator.ofFloat(x, "rotationY", 90f, 180f);
        ObjectAnimator b = ObjectAnimator.ofFloat(y, "rotationY", 90f, 180f);

        //half second animation
        a.setDuration(500);
        b.setDuration(500);

        //set the animation to a
        a.addListener(fblt);

        //start b then a so b will be finished when the listener on a hits
        b.start();
        a.start();
    }

    /**
     * Waits for animation to end then sets the card background and starts the next animation
     */

    Animator.AnimatorListener flo = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            //remove the animation listener
            animation.removeListener(flo);
            //set the background to the face of the card
            thisView.findViewById(cardID.get(cardID.size() - 1)).setBackgroundResource(cardList.get(cardList.size() - 1).image);
            //finish the card flip
            cardFlipAnimationSecond(thisView.findViewById(cardID.get(cardID.size() - 1)));
        }
    };

    /**
     * Waits for animation to end then calls back caller function in animation finished state
     */

    Animator.AnimatorListener flt = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            //remove the animation listener
            animation.removeListener(flt);

            //if its not the computer turn call the move function stating the animation has
            //been done. Otherwise determine what state the computer animation is in.
            if (!compTurn) {
                cardClicked(true);
            } else {
                if (one) {
                    //fist computer card
                    one = false;
                    computersTurn(turn_e.finishAnimation);
                } else {
                    //second computer card
                    one = true;
                    computersTurn(turn_e.handleCard);
                }
            }
            okToPlay = true;
        }
    };

    /**
     * Waits for animation to end then changes background of card to the back of the card
     */

    Animator.AnimatorListener fblo = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            //remove animation listener
            animation.removeListener(fblo);
            //set the first cards background to the back
            thisView.findViewById(cardID.get(0)).setBackgroundResource(R.drawable.back);
            //set the second cards background to the back
            thisView.findViewById(cardID.get(1)).setBackgroundResource(R.drawable.back);
            //finish flipping the card
            cardFlipBackAnimationSecond();
        }
    };

    /**
     * Waits for animation to end then clears the lists and starts computers turn
     */

    Animator.AnimatorListener fblt = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            //remove the animation listener
            animation.removeListener(fblt);
            //clear the lists
            cardList.clear();
            cardID.clear();

            //start the computer turn or computers turn just finished
            if (!compTurn) {
                computersTurn(turn_e.init);
            } else {
                compTurn = false;
            }
            okToPlay = true;
        }
    };

    /**
     * On click listener for all playable cards
     */

    View.OnClickListener cardClicked = new View.OnClickListener() {

        public void onClick(View v) {
            if (!compTurn && okToPlay) {

                //card location
                cardID.add(v.getId());

                //if theres at least one card and the same card was clicked dont allow move
                if ((cardID.size() > 1) && (cardID.get(0) == cardID.get(1))) {
                    cardID.remove(cardID.get(1));
                    return;
                }

                //add card to the list
                cardList.add(g.getCardIndex(cardID.get(cardID.size() - 1) / 10, cardID.get(cardID.size() - 1) % 10));

                //call the move function
                cardClicked(false);
            }
        }
    };
}
