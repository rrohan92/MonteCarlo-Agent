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
public class AlphaBeta extends OthelloPlayer {
    int maxtime ;
    double bestscoremax;
    double bestscoremin;
    int scoremax;
    int scoremin;

    public AlphaBeta(int time) {
        this.maxtime = time;
    }
    @Override
    public OthelloMove getMove(OthelloState state) {
        long starttime=System.currentTimeMillis();
         List<OthelloMove> moves = state.generateMoves(0);
        OthelloMove move = null;
        double best = Double.NEGATIVE_INFINITY;
        double worst=Double.POSITIVE_INFINITY;
        for (int i = 0; i < moves.size(); i++) {
            int result = (int) Maxvalue(state, maxtime,starttime,(int) best,(int)worst);
            if (result > best) {
                best = result;
                move = moves.get(i);
            }
        }
        return move;
    }

    public double Maxvalue(OthelloState state, int maxtime, long starttime, int alpha, int beta) {

        if (state.gameOver()) {
            return (1000*CornerHeuristic(state) + 100*Heuristic(state) + 600*CornerClose(state));
        } 
            List<OthelloMove> moves = state.generateMoves(0);
            if (System.currentTimeMillis()-starttime<=maxtime) {
                if (!moves.isEmpty()) {
                    bestscoremax = Double.NEGATIVE_INFINITY;
                    for (int i = 0; i < moves.size(); i++) {

                        OthelloState temp = state.applyMoveCloning(moves.get(i));

                        scoremax = (int) MinValue(temp, maxtime,starttime, alpha, beta);
                        if (scoremax > bestscoremax) {

                            bestscoremax = scoremax;
                        }
                       if(bestscoremax>=beta)
                           return bestscoremax;
                       if(bestscoremax>alpha)
                           alpha=(int)bestscoremax;
                           
                    }
                    moves.clear();
                    return bestscoremax;
                } else {
            return (1000*CornerHeuristic(state) + 100*Heuristic(state) + 600*CornerClose(state));
                }

            } else {
            return (1000*CornerHeuristic(state) + 100*Heuristic(state) + 600*CornerClose(state));
            }
        }
    

    public double MinValue(OthelloState state, int maxtime,long starttime, int alpha, int beta) {

        if (state.gameOver())
        {
            return (1000*CornerHeuristic(state) + 100*Heuristic(state) + 600*CornerClose(state));
        } 
            if (System.currentTimeMillis()-starttime<=maxtime) {
                List<OthelloMove> movesmin = state.generateMoves(1);
                if (!movesmin.isEmpty()) {
                    bestscoremin = Double.POSITIVE_INFINITY;
                    for (int i = 0; i < movesmin.size(); i++) {
                        OthelloState temp = state.applyMoveCloning(movesmin.get(i));
                        scoremin = (int) Maxvalue(temp, maxtime,starttime, alpha,beta);
                        if (scoremin < bestscoremin) {
                            bestscoremin = scoremin;
                        }
                        if(bestscoremin<=alpha)
                            return bestscoremin;
                        if(bestscoremin<beta)
                            beta=(int)bestscoremin;
                    }
                    movesmin.clear();
                    return bestscoremin;
                } else {
            return (1000*CornerHeuristic(state) + 100*Heuristic(state) + 600*CornerClose(state));
                }
            } else {
            return (10*CornerHeuristic(state) + Heuristic(state) + 9*CornerClose(state));
            }

        }
    
    public double Heuristic(OthelloState state)
    {
             List<OthelloMove> player = state.generateMoves(0);
             List<OthelloMove> opponent = state.generateMoves(1);
            if(player.size() + opponent.size()!=0 && !player.isEmpty() && !opponent.isEmpty())
            {
                if(player.size()>opponent.size())
                    return (player.size())/player.size()+opponent.size();
                else if(opponent.size()>player.size())
                    return -1*(opponent.size())/player.size()+opponent.size();
                else
                    return 0;
            }
             else
                return state.score();

    }
    public double CornerHeuristic(OthelloState state)
    {
         List<OthelloMove> player = state.generateMoves(0);
         List<OthelloMove> opponent = state.generateMoves(1);
         if(!player.isEmpty() && !opponent.isEmpty())
        return (numCorners(state, 0)- numCorners(state, 1));
         else
             return state.score();
    }
    
    public double numCorners(OthelloState state, int player)
    {
        int corners=0;
        if(state.board[0][0]==player)
            corners++;
        if(state.board[0][7]==player)
            corners++;
        if(state.board[7][0]==player)
            corners++;
        if(state.board[7][7]==player)
            corners++;
        
        return corners;
    }
    
    public double CornerClose(OthelloState state)
    {
        int player=0;
        int opponent=0;
        if(state.board[0][0]==-1)
        {
            if(state.board[0][1] == 0)
                player++;
		else if(state.board[0][1] == 1) 
                opponent++;
		if(state.board[1][1] == 0) 
                 player++;
		else if(state.board[1][1] == 1)
                opponent++;
		if(state.board[1][0] == 0)
                player++;
		else if(state.board[1][0] == 1)
                opponent++;
        }
        if(state.board[0][7]==-1)
        {
            if(state.board[0][6] == 0)
                player++;
		else if(state.board[0][6] == 1) 
                opponent++;
		if(state.board[1][6] == 0) 
                 player++;
		else if(state.board[1][6] == 1)
                opponent++;
		if(state.board[1][7] == 0)
                player++;
		else if(state.board[1][7] == 1)
                opponent++;
        }
         if(state.board[7][0]==-1)
        {
            if(state.board[7][1] == 0)
                player++;
		else if(state.board[7][1] == 1) 
                opponent++;
		if(state.board[6][1] == 0) 
                 player++;
		else if(state.board[6][1] == 1)
                opponent++;
		if(state.board[6][0] == 0)
                player++;
		else if(state.board[6][0] == 1)
                opponent++;
        }
          if(state.board[7][7]==-1)
        {
            if(state.board[6][7] == 0)
                player++;
		else if(state.board[6][7] == 1) 
                opponent++;
		if(state.board[6][6] == 0) 
                 player++;
		else if(state.board[6][6] == 1)
                opponent++;
		if(state.board[7][6] == 0)
                player++;
		else if(state.board[7][6] == 1)
                opponent++;
        }
          return -1*(player-opponent);
    }
    }
    

