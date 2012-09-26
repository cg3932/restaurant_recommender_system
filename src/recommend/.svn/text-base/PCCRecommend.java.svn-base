//THIS PROGRAM SHOULD BE ON THE SERVER
package recommend;

import java.util.ArrayList;
//import java.lang.Math;
//import java.util.TreeSet;
//import java.util.Comparator;
import java.util.TreeMap;
//import java.util.HashSet;
import entities.*;
import networkInterface.*;
import java.sql.*;

// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
// #[regen=yes,id=DCE.BB20129F-6938-B3A5-D5F2-A49984AA1309]
// </editor-fold>
public class PCCRecommend implements RStrategy {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.13EFF9E9-9AF7-BE90-E7F3-A4DBCB93E585]
    // </editor-fold>
    private int[] valueVector;
    private int[] xAxisVector;
    private int[] yAxisVector;
    private int IDxStart = -1;
    private int IDxEnd = -1;
    private float IDaverageValue = -1;
    private float XaverageValue = -1;
    private float CORRELTHRESH = 0.5f;
    private TreeMap<Integer, Float> yAxisPredicts = new TreeMap();
    private ArrayList<Integer> keySet = new ArrayList();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.C61EAA05-9164-2500-275C-F4FDD32B4B44]
    // </editor-fold>
    public PCCRecommend() {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.926C638B-4A5D-CF01-B4E3-B79A58DE6BF7]
    // </editor-fold>
    public Restaurant[] recommend(int ID, String xAxis, String yAxis, String values) {

        IDxStart = -1;
        IDxEnd = -1;

        //xAxis are users
        //yAxis are restaurants
        //values are ratings

        //First we get the table:
        getTables();

        //Then we operate on it.
        //Instead of making a sparse 2d array, we will vectorize.
        //valueVector will contain values (ie Ratings)
        //xAxisVector will contain xAxis IDs (ie User ID)
        //yAxisVector will contain yAxis IDs (ie Restaurant ID)

        //Have the xAxisVector be sorted.

        makeMatrix();

        //Once we're here we apply the pearson correlation coefficient method
        //To measure how similar the current user is to each other user.

        //We build two vectors alike and unlike which will be filled with all xAxis objects
        //With a coefficient of CORRELTHRSH

        //To avoid doing redundant work we already compute things like
        //IDxStart, IDxEnd and IDaverageValue
        for (int x = 0; x < xAxisVector.length; x++) {
            if (xAxisVector[x] == ID && IDxStart == -1) {
                IDxStart = x;
            }
            if (xAxisVector[x] == ID)// && (xAxisVector.length != x - 1 || xAxisVector[x+1] != ID))
            {
                if ((x + 1) == xAxisVector.length) {
                    IDxEnd = x;
                    break;
                } else if (xAxisVector[x + 1] != ID) {
                    IDxEnd = x;
                    break;
                }
            }
        }
        IDaverageValue = computeIDaverage();

        float correlation = 0;
        int lastRating;

        for (int x = 0; x < xAxisVector.length; x++) {

            lastRating = x;
            while (lastRating < xAxisVector.length - 1 && xAxisVector[x] == xAxisVector[lastRating + 1]) {
                lastRating++;
            }
            XaverageValue = computeAverage(x, lastRating);
            correlation = pearson(ID, x, lastRating);

            //Then we want to add correlation * (rating - averageRating) to an HashMap that stores every restaurant

            if (Math.abs(correlation) > CORRELTHRESH && xAxisVector[x] != ID) {
                //Then loop through every restaurant that user rates.
                for (int y = x; y <= lastRating; y++) {
                    int yAxisID = yAxisVector[y];
                    if (yAxisPredicts.get(yAxisID) == null) {
                        yAxisPredicts.put(yAxisID, correlation * (valueVector[y] - XaverageValue));
                        keySet.add(new Integer(yAxisID));
                    } else {
                        float previousValue = yAxisPredicts.get(yAxisID).floatValue();
                        yAxisPredicts.put(yAxisID, new Float(previousValue + correlation * (valueVector[y] - XaverageValue)));
                    }
                }

            }
            x = lastRating;
        }

        if (IDxStart != -1) {
            for (int i = IDxStart; i <= IDxEnd; i++) {
                int index = keySet.indexOf(yAxisVector[i]);
                if (index >= 0) {
                    keySet.remove(index);	//This removes all "restaurants" that the "user" "rated".
                }
            }
        }
        //Then we want to find the 10 biggest values in yAxisPredicts, if any.

        ArrayList<yValPair> tempArrayList = new ArrayList();
        for (int x = 0; x < keySet.size(); x++) {
            yValPair node = new yValPair(keySet.get(x), yAxisPredicts.get(keySet.get(x)));
            tempArrayList.add(node);
        }
        yValPair[] tempArray = tempArrayList.toArray(new yValPair[1]);
        java.util.Arrays.sort(tempArray);

        //yValPair[] reverse = new yValPair[tempArray.length];
        //int i;
        //for (i = 0; i < tempArray.length; i++){
        //    reverse[tempArray.length-i-1] = tempArray[]
        //}


        //Then return the top 10.
        Restaurant[] returnArray = new Restaurant[10];

        Restaurant tempResto = new Restaurant();

        DBStatsControl dbstats;
        DBSearchControl dbsc;
        try {
            dbstats = new DBStatsControl();
        } catch (SQLException ex) {
            return null;
        }
        Restaurant[] top10;
        int numRestos = dbstats.getNumberRestaurants();
        if (numRestos < 10) {
            top10 = dbstats.getTopRated(numRestos);
        } else {
            top10 = dbstats.getTopRated(10);
        }
        try {
            dbsc = new DBSearchControl();
        } catch (SQLException ex) {
            return null;
        }
        String[] fields = {"restaurantid"};
        String[] comps = {"="};
        String[] vals = new String[1];
        String[] links = {";"};
        dbsc.setNoFieldsInSearch(1);
        dbsc.setSearchComparisons(comps);
        dbsc.setSearchFields(fields);
        dbsc.setSearchLinkers(links);
        int i;
        for (i = 0; i < 10; i++) {
            if (i >= tempArray.length) {
                break;
            }
            if (tempArray[i] == null) {
                break;
            }
            vals[0] = tempArray[i].y + "";
            dbsc.setSearchValues(vals);
            tempResto = dbsc.searchRestaurant()[0];

            returnArray[i] = tempResto;
        }

        int k = 0;
        while (k < top10.length && i < returnArray.length) {
            returnArray[i] = top10[k];
            k++;
            i++;
        }
        while (i < 10) {
            returnArray[i] = null;
            i++;
        }
        
        return returnArray;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.FB1CF125-4660-A0CE-E6EF-E16E5421555D]
    // </editor-fold>
    private void getTables() {

        DBStatsControl dbsc;
        DBRecommendControl dbrc;
        try {
            dbsc = new DBStatsControl();
        } catch (SQLException ex) {
            return;
        }

        dbrc = new DBRecommendControl();

        int numRatings = dbsc.getNumberRatings();
        int[][] matrix = new int[3][numRatings];

        matrix = dbrc.getMatrix();

        xAxisVector = matrix[0];
        yAxisVector = matrix[1];
        valueVector = matrix[2];

        return;
    }

    private void makeMatrix() {
        //Depends on what Network interface gives us...
    }

    private float pearson(int ID, int start, int end) {
        if (IDxStart == -1) {
            return 0;
        }
        //Where start and end are the index of the current object
        //of the xAxisVector being compared to the ID object
        int idPointer = IDxStart;
        int xPointer = start;
        float topSum = 0;
        float idBotSum = 0;
        float xBotSum = 0;
        //We need to find the set of pairs (idPointer,xPointer)
        //Such that yAxis[idPointer] == yAxis[xPointer]
        while (idPointer <= IDxEnd && xPointer <= end) {
            if (yAxisVector[idPointer] < yAxisVector[xPointer]) {
                idPointer++;
            } else if (yAxisVector[idPointer] > yAxisVector[xPointer]) {
                xPointer++;
            } else {
                //Then we have a match
                float meanIDvalue = valueVector[idPointer] - IDaverageValue;
                float meanXvalue = valueVector[xPointer] - XaverageValue;
                topSum += meanIDvalue * meanXvalue;
                idBotSum += meanIDvalue * meanIDvalue;
                xBotSum += meanXvalue * meanXvalue;
                idPointer++;
                xPointer++;
            }
        }
        return (float) (topSum / Math.sqrt(idBotSum) / Math.sqrt(xBotSum));
    }

    private float computeIDaverage() {
        if (IDxStart == -1) {
            return -1;
        }

        float count = IDxEnd - IDxStart + 1;
        int sum = 0;
        int x = IDxStart;
        while (x <= IDxEnd) {
            sum += valueVector[x];
            x++;
        }
        return sum / count;
    }

    private float computeAverage(int start, int end) {
        float count = end - start + 1;
        int sum = 0;
        int x = start;
        while (x <= end) {
            sum += valueVector[x];
            x++;
        }
        return sum / count;
    }

    private class xCorrelPair {

        int x;
        float correlation;

        public xCorrelPair(int x, float correl) {
            this.x = x;
            this.correlation = correl;
        }
    }

    private class yValPair implements Comparable {

        int y;
        float value;

        public yValPair(int y, float value) {
            this.y = y;
            this.value = value;
        }

        public int compareTo(Object o) {
            return yValPComparator.compare(this, o);
        }
    }

    private static class yValPComparator {

        public static int compare(Object o1, Object o2) {
            yValPair pair1 = (yValPair) o1;
            yValPair pair2 = (yValPair) o2;
            if (pair1.value < pair2.value) {
                return 1;
            } else if (pair1.value > pair2.value) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}


