package com.pacman.ui.jpanels.model;


import java.util.Comparator;
/**
 * Class used as Comparator in Collections.sort in EnterNickNamePanel
 */
public class SortByPoints implements Comparator<TopScorer>
{
    /**
     * Used for sorting in descending order of TopScorer.points
     */
    public int compare(TopScorer a, TopScorer b)
    {
        return b.points - a.points;
    }
}