package config.repository;

import config.dto.DtoFavoris;
import config.dto.DtoStop;
import config.exception.RepositoryException;
import java.util.List;

/**
 *
 * @author Marwa
 */
public interface Repository<K, T extends DtoStop> {

    List<T> getAllStopBy(String station) throws RepositoryException;

    String getStation(int line, int order) throws RepositoryException;

    boolean contains(int line, int order) throws RepositoryException;

    public List<String> getAllStation() throws RepositoryException;

    public List getLines(String station) throws RepositoryException;

    public void addFavoris(String name, String depart, String destination)
            throws RepositoryException;

    public void deleteFavoris(String nameFavoris) throws RepositoryException;

    public DtoFavoris getAllFavorisByName(String nameFavoris) throws RepositoryException;

    public List<DtoFavoris> getAllFavoris() throws RepositoryException;

    public List<String> getNamesFavoris() throws RepositoryException;

}
