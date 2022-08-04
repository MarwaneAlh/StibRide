/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import config.dto.DtoFavoris;
import config.exception.RepositoryException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.TableStop;
import org.controlsfx.control.SearchableComboBox;
import util.Observer;
import view.View;

/**
 *
 * @author Marwa
 */
public class Presenter implements Initializable, Observer {

    @FXML
    private SearchableComboBox<String> listStationDestination;

    @FXML
    private SearchableComboBox<String> listStationDepart;

    @FXML
    private TableView<TableStop> tableStops;

    @FXML
    private TableColumn<TableStop, String> stations;

    @FXML
    private TableColumn<TableStop, Integer> lignes;

    @FXML
    private Label progress;

    @FXML
    private Label nbStation;

    private Model model;
    private View view;
    private String nameFav;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.nameFav = "";
            this.model = new Model();
            this.model.addObserver(this);
            this.view = new View();
            this.view.initialize(this);
        } catch (RepositoryException ex) {
            System.out.println(ex);
        }
    }

    public void calculShortRide(String startStation, String endStation)
            throws RepositoryException {
        model.calculShortRide(startStation, endStation);
    }

    public List<String> getStationsAll() throws RepositoryException {
        return model.getStationAll();
    }

    @FXML
    void actionOpenSave(ActionEvent event) throws Exception {
        view.openSave(this);
    }

    @FXML
    void actionSave(ActionEvent event) throws Exception {
        view.save(this);
    }

    @FXML
    public void actionSearch(ActionEvent event) throws Exception {
        view.search(this);
    }

    public TableColumn<TableStop, String> getStations() {
        return stations;
    }

    public TableColumn<TableStop, Integer> getLignes() {
        return lignes;
    }

    public SearchableComboBox<String> getListStationDestination() {
        return listStationDestination;
    }

    public SearchableComboBox<String> getListStationDepart() {
        return listStationDepart;
    }

    public TableView<TableStop> getTableStops() {
        return tableStops;
    }

    public Label getProgress() {
        return progress;
    }

    public Label getNbStation() {
        return nbStation;
    }

    @Override
    public void update() {
        model.getShortRide().forEach((station) -> {
            List<Integer> lines = new ArrayList<>();
            try {
                lines = model.getStationAll(
                        model.correctDataBaseStation(station));
            } catch (RepositoryException ex) {
                System.out.println(
                        "impossible d'avoir les lignes de la station");
            }
            tableStops.getItems().add(new TableStop(station, lines));
        });
        progress.setText("Recherche terminer");
        nbStation.setText("Nombre de stations : " + model.getOrderMin());
    }

    public void setValue(String value) {
        nameFav = value;
    }

    public String getNameFav() {
        return nameFav;
    }

    public DtoFavoris getAllFavoris(String nameFavoris) throws RepositoryException {
        return model.getAllFavoris(nameFavoris);
    }

    public boolean isNameFav() {
        return nameFav.equals("");
    }

}
