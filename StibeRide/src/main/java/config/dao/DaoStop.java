package config.dao;

import config.dto.DtoFavoris;
import config.dto.DtoStop;
import config.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marwa
 */
public class DaoStop implements Dao<Integer, DtoStop> {

    private final Connection connexion;

    private DaoStop() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static DaoStop getInstance() throws RepositoryException {
        return DaoHolderStop.getInstance();
    }

    @Override
    public List selectAllStopByNameStation(String station)
            throws RepositoryException {
        String sql
                = "SELECT sto.id_line, sto.id_station,sto.id_order "
                + "FROM STOPS sto JOIN STATIONS sta ON sto.id_station = sta.id "
                + "WHERE sta.name LIKE '" + station + "'";

        List<DtoStop> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DtoStop dto = new DtoStop(rs.getInt(1), rs.getInt(2),
                        rs.getInt(3));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public String selectNameStationByLineAndOrder(int line, int order)
            throws RepositoryException {
        String station = null;
        String sql
                = "SELECT sta.name "
                + "FROM STOPS sto JOIN STATIONS sta ON sto.id_station = sta.id "
                + "WHERE sto.id_line = " + line + " AND sto.id_order = "
                + order + " ";

        try (Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isClosed()) {
                station = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return station;
    }

    @Override
    public List<String> selectAllStation() throws RepositoryException {
        List<String> stations = new ArrayList<>();
        String sql = "SELECT name FROM STATIONS";

        try (Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stations.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stations;
    }

    @Override
    public void addFavoris(String name, String depart, String destination)
            throws RepositoryException {
        if (name == null || depart == null || destination == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "INSERT INTO FAVORIS(name,origine,destination) "
                + "values(?, ?, ? )";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, depart);
            pstmt.setString(3, destination);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteFavoris(String name) throws RepositoryException {
        if (name == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        try {
            Statement stmt = connexion.createStatement();
            String sql = "DELETE FROM FAVORIS WHERE name='" + name + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("DEMO_DELETE | Erreur " + ex.getMessage()
                    + " SQLState " + ex.getSQLState());
        }
    }

    @Override
    public List<DtoFavoris> getAllFavoris() throws RepositoryException {
        List<DtoFavoris> fav = new ArrayList<>();
        String sql = "SELECT * FROM FAVORIS";

        try (Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                fav.add(new DtoFavoris(rs.getString(1), rs.getString(2),
                        rs.getString(3)));

            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return fav;
    }

    @Override
    public List<Integer> selectLignesByStation(String station)
            throws RepositoryException {
        List<Integer> lines = new ArrayList<>();
        String sql = "SELECT sto.id_line "
                + "FROM STOPS sto JOIN STATIONS sta ON sto.id_station = sta.id "
                + "WHERE sta.name LIKE '" + station + "'";

        try (Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lines.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return lines;
    }

    private static class DaoHolderStop {

        private static DaoStop getInstance() throws RepositoryException {
            return new DaoStop();
        }
    }
}
