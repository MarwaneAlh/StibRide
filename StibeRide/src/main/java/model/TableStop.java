package model;

import java.util.List;

/**
 *
 * @author Marwa
 */
public class TableStop {

    private String station;
    private String lignes;

    public TableStop(String station, List<Integer> lignes) {
        this.station = station;
        constructLignes(lignes);
    }

    private void constructLignes(List<Integer> lignes) {
        this.lignes = "[" + lignes.get(0);
        for (int i = 1; i < lignes.size(); ++i) {
            this.lignes += ", " + lignes.get(i);
        }
        this.lignes += "]";
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLignes() {
        return lignes;
    }
}
