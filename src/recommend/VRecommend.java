package recommend;
import java.util.ArrayList;
import entities.Restaurant;

// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
// #[regen=yes,id=DCE.70829911-EEF4-89C4-B960-1CBD997541C8]
// </editor-fold>
public class VRecommend implements RStrategy {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.C4491C64-D50E-D733-C9D2-AAC5D398365B]
    // </editor-fold>
    private ArrayList<String> tables;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.167A300D-3F51-1A6E-7077-33A24B55BD1A]
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.B7E893A2-5DF5-CB88-9FC7-7C88EDDCEA09]
    // </editor-fold>
    public VRecommend () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.EB7C4AF7-AC16-70FA-AB73-EE943FAEE9F2]
    // </editor-fold>
    public Restaurant[] recommend (int ID, String xAxis, String yAxis, String values) {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,regenBody=yes,id=DCE.AE84833C-5A6F-1B57-B58A-93223D46F7F1]
    // </editor-fold>
    public ArrayList<String> getTables () {
        return tables;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,regenBody=yes,id=DCE.F99E0EE9-5649-6CAC-C4D1-1CBD206D1E12]
    // </editor-fold>
    public void setTables (ArrayList<String> val) {
        this.tables = val;
    }

}

