/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ai;

import java.util.List;

/**
 *
 * @author Rohan
 */
public class MyAgent extends OthelloPlayer {

    int depth;
    double bestscoremax;
    double bestscoremin;
    int scoremax;
    int scoremin;

    public MyAgent(int depth) {
        this.depth = depth;
    }

    @Override
    public OthelloMove getMove(OthelloState state) {
        List<OthelloMove> moves = state.generateMoves(0);
        OthelloMove move = null;
        double best = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < moves.size(); i++) {
            int result = (int) MinValue(state, depth-1);
            if (result > best) {
                best = result;
                move = moves.get(i);
            }
        }
        return move;
    }

    public double Maxvalue(OthelloState state, int depth) {

        if (state.gameOver()) {
            return state.score();
        } 
            List<OthelloMove> moves = state.generateMoves(0);
            if (depth != 0) {
                if (!moves.isEmpty()) {
                    bestscoremax = Double.NEGATIVE_INFINITY;
                    for (int i = 0; i < moves.size(); i++) {

                        OthelloState temp = state.applyMoveCloning(moves.get(i));

                        scoremax = (int) MinValue(temp, depth - 1);
                        if (scoremax > bestscoremax) {

                            bestscoremax = scoremax;
                        }

                    }
                    moves.clear();
                    return bestscoremax;
                } else {
                    return state.score();
                }

            } else {
                return state.score();
            }
        }
    

    public double MinValue(OthelloState state, int depth) {

        if (state.gameOver()) {
            return state.score();
        } 
            if (depth != 0) {
                List<OthelloMove> movesmin = state.generateMoves(1);
                if (!movesmin.isEmpty()) {
                    bestscoremin = Double.POSITIVE_INFINITY;
                    for (int i = 0; i < movesmin.size(); i++) {
                        OthelloState temp = state.applyMoveCloning(movesmin.get(i));
                        scoremin = (int) Maxvalue(temp, depth - 1);
                        if (scoremin < bestscoremin) {
                            bestscoremin = scoremin;

                        }

                    }
                    movesmin.clear();
                    return bestscoremin;
                } else {
                    return state.score();
                }
            } else {
                return state.score();
            }

        }
    }

