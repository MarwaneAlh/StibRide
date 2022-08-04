package config.dto;

/**
 *
 * @author Marwa
 */
public class DtoStop {

    private final int order;
    private final DtoLine idLine;
    private final DtoStation idStation;

    public DtoStop(int keyLine, int keyStation, int order) {
        idLine = new DtoLine(keyLine);
        idStation = new DtoStation(keyStation);
        this.order = order;
    }

    public DtoLine getIdLine() {
        return idLine;
    }

    public DtoStation getIdStation() {
        return idStation;
    }

    public int getOrder() {
        return order;
    }
}
