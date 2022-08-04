package model;

import config.ConfigManager;
import config.dto.DtoFavoris;
import config.dto.DtoStop;
import config.exception.RepositoryException;
import config.repository.RepositoryStop;
import util.Observable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marwa
 */
public class Model extends Observable {

    private final int DIRECTION = 2;
    private RepositoryStop repository;
    private int orderMin = -1;
    private Node nodeMin;
    private Graph graph;

    public Model() {
        try {
            ConfigManager.getInstance().load();
            repository = new RepositoryStop();
        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
    }

    public String correctDataBaseStation(String station) {
        if (station.equals("GARE DE L'OUEST")) {
            return "GARE DE L''OUEST";
        } else {
            return station;
        }
    }

    private int increment(boolean advance) {
        int increment = 1;
        if (!advance) {
            increment = -1;
        }
        return increment;
    }

    private boolean isContains(int ligne, int orderNext)
            throws RepositoryException {
        return repository.contains(ligne, orderNext);
    }

    private List listStationNext(String station) throws RepositoryException {
        boolean advance = false;
        List<String> stations = new ArrayList<>();
        List<DtoStop> dtos = repository.getAllStopBy(
                correctDataBaseStation(station));

        for (int cpt = 0; cpt < DIRECTION; ++cpt) {
            advance = !advance;
            for (DtoStop dtoStop : dtos) {
                int key = dtoStop.getIdLine().getKey();
                int orderNext = dtoStop.getOrder() + increment(advance);

                if (isContains(key, orderNext)) {
                    stations.add(repository.getStation(key, orderNext));
                }
            }
        }
        return stations;
    }

    private boolean containsFusionStation(List<String> stations, String station,
            int max) {
        boolean find = false;
        for (int i = 0; i < max; ++i) {
            if (stations.get(i).equals(station)) {
                find = true;
                break;
            }
        }
        return find;
    }

    private List fusionSameStation(List<String> stations, String stationParents)
            throws RepositoryException {
        if (stations.size() > 1) {
            for (int i = 0; i < stations.size(); ++i) {
                if (stationParents.equals(stations.get(i))
                        || containsFusionStation(stations, stations.get(i), i)) {
                    stations.remove(i);
                    --i;
                }
            }
        }
        return stations;
    }

    private List allNextStation(Node node, Node nodeParent)
            throws RepositoryException {
        List<String> stations = listStationNext(node.getStation());
        String stationParent = "";

        if (nodeParent != null) {
            stationParent = nodeParent.getStation();
        }
        return fusionSameStation(stations, stationParent);
    }

    private boolean isNodeArrived(String endStation, Node node) {
        return node.getStation().equals(endStation)
                || (orderMin != -1 && node.getOrder() > orderMin);
    }

    private void nodeArrived(String endStation, Node node, int index) {
        if (node.getStation().equals(endStation)
                && (orderMin == -1 || node.getOrder() < orderMin)) {
            orderMin = node.getOrder();
            nodeMin = node;
        }
        graph.removeNode(index);
    }

    private void modifieGraph(Node node, int index) throws RepositoryException {
        List<String> stations = allNextStation(graph.getNode(index),
                graph.getNode(index).getNodeParent());

        if (stations.isEmpty()) {
            graph.removeNode(index);
        } else {
            graph.modifiedNode(new Node(node, stations.get(0)), index);
            if (stations.size() > 1) {
                for (int i = 1; i < stations.size(); ++i) {
                    graph.addNode(new Node(node, stations.get(i)));
                }
            }
        }
    }

    public void calculShortRide(String startStation, String endStation)
            throws RepositoryException {
        graph = new Graph(startStation);
        while (!graph.finish()) {
            for (int i = 0; i < graph.getGraph().size()
                    && !graph.finish(); ++i) {
                if (isNodeArrived(endStation, graph.getNode(i))) {
                    nodeArrived(endStation, graph.getNode(i), i);
                } else {
                    modifieGraph(graph.getNode(i), i);
                }
            }
        }
        this.notifyObservers();
    }

    public int getOrderMin() {
        return orderMin;
    }

    public List<String> getShortRide() {
        List<String> temp = new ArrayList<>();
        List<String> listStation = new ArrayList<>();

        temp.add(nodeMin.getStation());
        Node nodeParent = nodeMin.getNodeParent();
        while (nodeParent != null) {
            temp.add(nodeParent.getStation());
            nodeParent = nodeParent.getNodeParent();
        }
        int max = temp.size() - 1;
        for (int i = max; i >= 0; --i) {
            listStation.add(temp.get(i));
        }
        return listStation;
    }

    public List<String> getStationAll() throws RepositoryException {
        return repository.getAllStation();
    }

    public List<Integer> getStationAll(String station) throws RepositoryException {
        return repository.getLines(station);
    }

    public void addFavoris(String name, String depart, String destination)
            throws RepositoryException {
        repository.addFavoris(name, depart, destination);
    }

    public DtoFavoris getAllFavoris(String nameFavoris) throws RepositoryException {
        return repository.getAllFavorisByName(nameFavoris);
    }

    public List<DtoFavoris> getAllFavoris() throws RepositoryException {
        return repository.getAllFavoris();
    }

    public List<String> getNamesFavoris() throws RepositoryException {
        return repository.getNamesFavoris();
    }

    public void deleteFavoris(String nameFavoris) throws RepositoryException {
        repository.deleteFavoris(nameFavoris);
        notifyObservers();
    }
}
