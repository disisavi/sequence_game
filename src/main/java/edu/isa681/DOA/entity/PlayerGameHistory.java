package edu.isa681.DOA.entity;

import java.awt.*;
import java.util.ArrayList;


// Player is fkey Player has one to many rel with history
public class PlayerGameHistory {
    Integer ID;
    Player player;
    ArrayList<Point> moves;
    Boolean Won;
}
