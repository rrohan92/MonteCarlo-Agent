//package ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author santi
 */
public class Test {
    
    public static int iterations;
    public static void main(String args[]) throws IOException {
        // Create the game state with the initial position for an 8x8 board:
        OthelloState state = new OthelloState(8);
       BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter iterations");
        iterations=Integer.parseInt(br.readLine());
        OthelloPlayer players[] = {  new MonteCarlo(),
                                 new OthelloRandomPlayer()};
        
        do{
            // Display the current state in the console:
            System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
            System.out.print(state);
            
            // Get the move from the player:
            OthelloMove move = players[state.nextPlayerToMove].getMove(state);            
            System.out.println(move);
            state = state.applyMoveCloning(move);            
        }while(!state.gameOver());

        // Show the result of the game:
        System.out.println("\nFinal state with score: " + state.score());
        System.out.println(state);
    }    
    
}
