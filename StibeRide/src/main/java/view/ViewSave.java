package view;

import config.dto.DtoFavoris;
import config.exception.RepositoryException;
import model.TableFavoris;
import presenter.Presenter;
import presenter.PresenterSave;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewSave {

    public void initialize(PresenterSave presenter) throws RepositoryException {
        presenter.getName().setCellValueFactory(new PropertyValueFactory<>("name"));
        presenter.getDepart().setCellValueFactory(new PropertyValueFactory<>("depart"));
        presenter.getDestination().setCellValueFactory(new PropertyValueFactory<>("destination"));
        initializeComboBox(presenter);
        initializeTable(presenter);
    }

    public void useFavoris(ActionEvent event, PresenterSave presenter) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"
                + "presentation.fxml"));
        Parent root = (Parent) loader.load();
        Presenter controller = loader.getController();
        controller.setValue(presenter.getListFavoris().getSelectionModel()
                .getSelectedItem());
        if (controller.getNameFav() == null) {
            controller.setValue("");
        }
        controller.actionSearch(event);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        Stage stagee = (Stage) presenter.getButtonUse().getScene().getWindow();
        stagee.close();
        stage.show();
    }

    private void initializeTable(PresenterSave presenter) throws RepositoryException {
        presenter.getTableFavoris().getItems().clear();
        for (DtoFavoris favoris : presenter.getAllFavoris()) {
            String nameFavoris = favoris.getNamefavoris();
            String departFavoris = favoris.getOriginefavoris();
            String destinationFavoris = favoris.getDestinationfavoris();
            presenter.getTableFavoris().getItems()
                    .add(new TableFavoris(nameFavoris, departFavoris,
                            destinationFavoris));
        }
    }

    private void initializeComboBox(PresenterSave presenter) throws RepositoryException {
        ObservableList<String> searchStations = FXCollections.observableArrayList();
        for (String station : presenter.getNamesFavoris()) {
            searchStations.add(station);
        }
        presenter.getListFavoris().setItems(searchStations);
    }

    public void deleteFavoris(PresenterSave presenter) throws RepositoryException {
        if (!presenter.getListFavoris().getSelectionModel().isEmpty()) {
            presenter.deleteFavoris(presenter.getListFavoris()
                    .getSelectionModel().getSelectedItem());
            initializeTable(presenter);
            initializeComboBox(presenter);
        }
    }

    public void actionReturn(PresenterSave presenter) throws Exception {
        FXMLLoader openSave = new FXMLLoader(getClass().getResource("/fxml/"
                + "presentation.fxml"));
        Parent root1 = (Parent) openSave.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        Stage stagee = (Stage) presenter.getButtonUse().getScene().getWindow();
        stagee.close();
        stage.show();

    }

}
