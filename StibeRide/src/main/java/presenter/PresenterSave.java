package presenter;

import config.dto.DtoFavoris;
import config.exception.RepositoryException;
import model.Model;
import model.TableFavoris;
import view.ViewSave;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.control.SearchableComboBox;

public class PresenterSave implements Initializable {

    @FXML
    private Button buttonUse;

    @FXML
    private TableView<TableFavoris> tableFavoris;

    @FXML
    private TableColumn<String, TableFavoris> name;

    @FXML
    private TableColumn<String, TableFavoris> depart;

    @FXML
    private TableColumn<String, TableFavoris> destination;

    @FXML
    private SearchableComboBox<String> listFavoris;

    private Model model;
    private ViewSave view;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.model = new Model();
            this.view = new ViewSave();
            view.initialize(this);
        } catch (RepositoryException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void actionDeleteFavoris(ActionEvent event) throws RepositoryException {
        view.deleteFavoris(this);
    }

    @FXML
    void actionUse(ActionEvent event) throws Exception {
        view.useFavoris(event, this);
    }

    public TableColumn<String, TableFavoris> getName() {
        return name;
    }

    public TableColumn<String, TableFavoris> getDepart() {
        return depart;
    }

    public TableColumn<String, TableFavoris> getDestination() {
        return destination;
    }

    public List<String> getNamesFavoris() throws RepositoryException {
        return model.getNamesFavoris();
    }

    public SearchableComboBox<String> getListFavoris() {
        return listFavoris;
    }

    public TableView<TableFavoris> getTableFavoris() {
        return tableFavoris;
    }

    public List<DtoFavoris> getAllFavoris() throws RepositoryException {
        return model.getAllFavoris();
    }

    public void deleteFavoris(String nameFavoris) throws RepositoryException {
        model.deleteFavoris(nameFavoris);
    }

    public Button getButtonUse() {
        return buttonUse;
    }

    @FXML
    void actionReturn(ActionEvent event) throws Exception {
        view.actionReturn(this);

    }
}
