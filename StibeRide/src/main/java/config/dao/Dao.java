package config.dao;

import config.dto.DtoFavoris;
import config.dto.DtoStop;
import config.exception.RepositoryException;
import java.util.List;

/**
 *
 * @author Marwa
 */
public interface Dao<K, T extends DtoStop> {

    List<T> selectAllStopByNameStation(String station)
            throws RepositoryException;

    String selectNameStationByLineAndOrder(int line, int order)
            throws RepositoryException;

    public List<String> selectAllStation() throws RepositoryException;

    public List<Integer> selectLignesByStation(String station) throws RepositoryException;

    public void addFavoris(String nomFavoris, String depart, String destination) throws RepositoryException;

    public void deleteFavoris(String name) throws RepositoryException;

    public List<DtoFavoris> getAllFavoris() throws RepositoryException;
}
