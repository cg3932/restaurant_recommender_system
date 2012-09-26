//THIS SHOULD BE ON THE SERVER SIDE

package recommend;

// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
// #[regen=yes,id=DCE.1A9E51E5-2CD9-EAF0-C084-B4CE50342B81]
// </editor-fold>
public class RecommendPolicy {

    //Implemented policies: PCC
    //Might implement Vector if we need to

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.2AB61885-B0D3-29B2-786C-8EAC3B26D518]
    // </editor-fold>
    static private String Policy = "PCC";

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.EE54F9CC-6532-FE55-2AA7-68005AD553FD]
    // </editor-fold>
    public RecommendPolicy () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,regenBody=yes,id=DCE.D58DE485-7C28-96B0-D2DF-069E8489F7F1]
    // </editor-fold>
    public static String getPolicy () {
        return Policy;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,regenBody=yes,id=DCE.1C7989CC-AE1F-9D47-5D27-B9743A93DA66]
    // </editor-fold>
    public static void setPolicy (String val) throws RecPolicyException {
        if (val.equals("PCC") || val.equals("Vector"))
        {
            Policy = val;
        }
        else throw new RecPolicyException();
    }
    public static class RecPolicyException extends Exception
{
}
}

