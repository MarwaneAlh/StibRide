/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.dto;

/**
 *
 * @author Marwa
 */
public class DtoFavoris {

    private final String namefavoris;
    private final String originefavoris;
    private final String destinationfavoris;

    public DtoFavoris(String namefavoris, String originefavoris, String destinationfavoris) {
        this.namefavoris = namefavoris;
        this.originefavoris = originefavoris;
        this.destinationfavoris = destinationfavoris;
    }

    public String getNamefavoris() {
        return namefavoris;
    }

    public String getOriginefavoris() {
        return originefavoris;
    }

    public String getDestinationfavoris() {
        return destinationfavoris;
    }

    public DtoFavoris() {
        this.namefavoris = "";
        this.originefavoris = "";
        this.destinationfavoris = "";
    }

}
