/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ai;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rohan
 */
public class Node 
{
 
    Node parent;
    List <Node> children=new ArrayList<Node>();
    OthelloMove move;
    int visited;
    float avgscore;
    OthelloState board;
    
    public Node(OthelloState state)
    {
        this.board=state;
    }
}
