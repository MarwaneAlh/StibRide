package config.repository;

import java.util.List;
import config.dao.DaoStop;
import config.dto.DtoFavoris;
import config.dto.DtoStop;
import config.exception.RepositoryException;
import java.util.ArrayList;

/**
 *
 * @author Marwa
 */
public class RepositoryStop implements Repository<Integer, DtoStop> {

    private final DaoStop dao;

    public RepositoryStop() throws RepositoryException {
        dao = DaoStop.getInstance();
    }

    @Override
    public List<String> getAllStation() throws RepositoryException {
        return dao.selectAllStation();
    }

    @Override
    public List<DtoStop> getAllStopBy(String station) throws RepositoryException {
        return dao.selectAllStopByNameStation(station);
    }

    @Override
    public String getStation(int line, int order) throws RepositoryException {
        return dao.selectNameStationByLineAndOrder(line, order);
    }

    @Override
    public List getLines(String station) throws RepositoryException {
        return dao.selectLignesByStation(station);
    }

    @Override
    public boolean contains(int line, int order) throws RepositoryException {
        String contains = dao.selectNameStationByLineAndOrder(line, order);
        return contains != null;
    }

    @Override
    public void addFavoris(String name, String depart, String destination)
            throws RepositoryException {
        dao.addFavoris(name, depart, destination);
    }

    @Override
    public void deleteFavoris(String nameFavoris) throws RepositoryException {
        dao.deleteFavoris(nameFavoris);
    }

    @Override
    public List<DtoFavoris> getAllFavoris()
            throws RepositoryException {
        List<DtoFavoris> fav = dao.getAllFavoris();
        return fav;
    }

    @Override
    public DtoFavoris getAllFavorisByName(String nameFavorie)
            throws RepositoryException {
        DtoFavoris fav = new DtoFavoris();
        for (DtoFavoris dtofav : dao.getAllFavoris()) {
            if (nameFavorie.equals(dtofav.getNamefavoris())) {
                fav = dtofav;
            }
        }
        return fav;
    }

    @Override
    public List<String> getNamesFavoris() throws RepositoryException {
        List<String> fav = new ArrayList<>();
        for (DtoFavoris dtofav : dao.getAllFavoris()) {
            fav.add(dtofav.getNamefavoris());
        }
        return fav;
    }
}
