package view;

import config.exception.RepositoryException;
import model.Model;
import presenter.Presenter;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class View {

    public void search(Presenter presenter) throws Exception {
        if (!presenter.getNameFav().equals("")) {
            String depart = presenter.getAllFavoris(presenter.getNameFav())
                    .getOriginefavoris();
            String destination = presenter.getAllFavoris(presenter.getNameFav())
                    .getDestinationfavoris();
            presenter.calculShortRide(depart, destination);
            presenter.setValue("");
        } else {
            presenter.getTableStops().getItems().clear();
            presenter.getProgress().setText("");
            presenter.getNbStation().setText("");
            if (!presenter.getListStationDepart().getSelectionModel().isEmpty()
                    && !presenter.getListStationDestination()
                            .getSelectionModel().isEmpty()) {
                String depart = presenter.getListStationDepart()
                        .getSelectionModel().getSelectedItem();
                String destination = presenter.getListStationDestination()
                        .getSelectionModel().getSelectedItem();
                presenter.calculShortRide(depart, destination);
            }
        }
    }

    public void openSave(Presenter presenter) throws Exception {
        FXMLLoader newFxml = new FXMLLoader(getClass().getResource("/fxml/"
                + "savewindows.fxml"));
        Parent root1 = (Parent) newFxml.load();
        Stage stageNext = new Stage();
        stageNext.setScene(new Scene(root1));
        Stage stageActual = (Stage) presenter.getTableStops().getScene().getWindow();
        stageActual.close();
        stageNext.show();
    }

    public void save(Presenter presenter) throws Exception {
        if (!presenter.getListStationDepart().getSelectionModel().isEmpty()
                && !presenter.getListStationDestination().getSelectionModel().
                        isEmpty()) {
            TextInputDialog td = new TextInputDialog("Exemple : Trajet1");
            td.setHeaderText("Entrer le nom de votre trajet");
            Optional a = td.showAndWait();
            if (a.isPresent() && !a.get().equals("")) {
                String depart = presenter.getListStationDepart().
                        getSelectionModel().getSelectedItem();
                String destination = presenter.getListStationDestination().
                        getSelectionModel().getSelectedItem();
                new Model().correctDataBaseStation(depart);
                new Model().correctDataBaseStation(destination);
                new Model().addFavoris((String) a.get(), depart, destination);
            }
        }
    }

    private void initializeComboBox(Presenter presenter)
            throws RepositoryException {
        ObservableList<String> searchStations
                = FXCollections.observableArrayList();
        for (String station : presenter.getStationsAll()) {
            searchStations.add(station);
        }
        presenter.getListStationDepart().setItems(searchStations);
        presenter.getListStationDestination().setItems(searchStations);
    }

    public void initialize(Presenter presenter) throws RepositoryException {
        presenter.getStations().setCellValueFactory(new PropertyValueFactory<>(
                "station"));
        presenter.getLignes().setCellValueFactory(new PropertyValueFactory<>(
                "lignes"));
        initializeComboBox(presenter);
    }
}
