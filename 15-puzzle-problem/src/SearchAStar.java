import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class SearchAStar {
    private List<PuzzleMatrix> checkedList;
    private List<PuzzleMatrix> nonCheckedList;



    public SearchAStar(PuzzleMatrix puzzleMatrix) {
        checkedList = new ArrayList<>();
        nonCheckedList = new ArrayList<>();
        nonCheckedList.add(puzzleMatrix);
    }

    public PuzzleMatrix search(int type) {




        while (!nonCheckedList.isEmpty()){
            PuzzleMatrix minMatrix;
            //find the min f wala matrix
            int minimum = Integer.MAX_VALUE;
            int position = -1;
            int heuVal;
            for (int i = 0; i<nonCheckedList.size(); i++) {
                if (type==1){
                    heuVal = nonCheckedList.get(i).getNodesTraversed() + nonCheckedList.get(i).countMisplaced();
                    //System.out.println("HeuVal : " + heuVal);
                }
                else if (type==2) {
                    heuVal = nonCheckedList.get(i).getNodesTraversed() + nonCheckedList.get(i).manhattanSum();
                    //System.out.println("HeuVal : " + heuVal);
                }
                else {
                    System.out.println("Invalid Heuristic type");
                    return null;
                }
                if (heuVal<minimum) {
                    position = i;
                    minimum = heuVal;
                }
            }
            minMatrix = nonCheckedList.get(position);
            //if it is goal, if goal return
            if (minMatrix.isGoalReached()) {
                System.out.println("Goal Reached");
                System.out.println("Explored Nodes : " + checkedList.size());
                return minMatrix;
            }
            //if not goal push it in checked list removing from non checked list
            checkedList.add(minMatrix);
            nonCheckedList.remove(minMatrix);
            //nonCheckedList.clear();
            //System.out.println("Found a minmatrix ");
            //Functions.printState(minMatrix.getState());
            //find out its possible states and push them in non checked list
            List<PuzzleMatrix> possibles = minMatrix.possibleStates();
            for (PuzzleMatrix pm: possibles
                 ) {
                if (!checkedList.contains(pm) ){
                    pm.setPrevious(minMatrix);
                    pm.setNodesTraversed(minMatrix.getNodesTraversed()+1);

                    if (!nonCheckedList.contains(pm)){
                        nonCheckedList.add(pm);
                    }
                }
            }
        }
        return null;
    }

    }