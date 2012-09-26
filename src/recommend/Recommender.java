//THIS SHOULD BE ON THE SERVER SIDE

package recommend;
import entities.Restaurant;

// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
// #[regen=yes,id=DCE.38909085-245F-9F82-55C7-87A4A5E7B58C]
// </editor-fold>
public class Recommender {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.54A6A2FC-BDC4-B586-B469-633280948B1F]
    // </editor-fold>
    private RStrategy recommender;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.08CD213A-2478-0F23-79F6-3CA9123ADA37]
    // </editor-fold>
    public Recommender () {
    }

    public Restaurant[] recommend(int ID, String xAxis, String yAxis, String values)
    {
        String policy = RecommendPolicy.getPolicy();
	Restaurant[] returnArray;
        setRecommender(policy);
        returnArray = recommender.recommend(ID,xAxis,yAxis,values);
        return returnArray;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,regenBody=yes,id=DCE.A1F412D0-C047-3996-9E80-11C5F472BA4C]
    // </editor-fold>
    private void setRecommender (String strat) {
        if (strat.equals("PCC")) recommender = new PCCRecommend();
        else if (strat.equals("Vector")) recommender = new VRecommend();
        else recommender = null;
    }

}

