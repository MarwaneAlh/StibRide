package model;

/**
 *
 * @author Marwa
 */
public class TableFavoris {

    private String name;
    private String depart;
    private String destination;

    public TableFavoris(String name, String depart, String destination) {
        this.name = name;
        this.depart = depart;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public String getDepart() {
        return depart;
    }

    public String getDestination() {
        return destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
