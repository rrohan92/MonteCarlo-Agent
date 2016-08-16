/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Rohan
 */
public class MonteCarlo extends OthelloPlayer
{
    Test test=new Test();
    public ArrayList open=new ArrayList();
    @Override
    public OthelloMove getMove(OthelloState state)
    {
        return Search(state, test.iterations);

    }
    public OthelloMove Search(OthelloState state, int iterations)
    {
        Node root=new Node(state);
        open.add(root);
        for (int i=0; i<iterations; i++)
        {
            Node node=treepolicy(root);
            if(node!=null)
            {
                Node node2=defaultpolicy(node);
                int Node2score=score(node2);
                backup(node, Node2score);
            
            }
               
        }
        Node best=BestChild(root);
        open.clear();
        if(best!=null)
        return best.move;
        else
        return null;
    }
    public Node treepolicy(Node node)
    {
                List<OthelloMove> moves = node.board.generateMoves(0);
                if(moves.isEmpty())
                    return node;
                int count =0;
                for(int i=0; i<moves.size(); i++)
                {
                    OthelloState temp=node.board.applyMoveCloning(moves.get(i));
                    Node child=new Node(temp);
                    
                    if(!open.contains(child))
                    {    
                     count++;   
                    open.add(child);
                    node.children.add(child);
                    child.parent=node;
                    child.move=moves.get(i);
                    return child;
                    }
                }
                if(count==0)
                {
                     double number=Math.random();
                     if(number> 0.1)
                     {
                         Node temp=BestChild(node);
                         return treepolicy(temp);
                     }
                     else
                     {
                         Random r=new Random();
                         if(!node.children.isEmpty())
                         { 
                             Node temp= node.children.get(r.nextInt());
                             return treepolicy(temp);
                         }
                     }
                    
                }
              moves.clear();
              return null;
    }
    
    public Node defaultpolicy(Node node)
    {

         OthelloPlayer players[] = {  new OthelloRandomPlayer(),
                                 new OthelloRandomPlayer()};
         
        do{
            // Get the move from the player:
            OthelloMove move = players[node.board.nextPlayerToMove].getMove(node.board);            
            node.board = node.board.applyMoveCloning(move);            
        }while(!node.board.gameOver());
        
       Node finalstate=new Node(node.board);
       return finalstate;
    }
    
    public int score(Node node)
    {
        return node.board.score();
    }
    
    public void backup(Node node, int score)
    {
        node.visited++;
        node.avgscore=score/node.visited;
        if(node.parent!=null)
            backup(node.parent,score);
    }
    public Node BestChild(Node node)
    { 
        if(!node.children.isEmpty())
        {
            Node temp=node.children.get(0);
        
        for(int i=1; i<node.children.size(); i++)
        {
            if(temp.avgscore <= node.children.get(i).avgscore )
            {
                temp=node.children.get(i);
            }
        }
        return temp;
    }
        return null;
    }
}

