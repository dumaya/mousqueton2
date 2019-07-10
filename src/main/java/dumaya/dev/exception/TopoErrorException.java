package dumaya.dev.exception;

public class TopoErrorException extends RuntimeException {
    /**
     * Cas de contention pour lequel on essaye de reserver un site qui a déjà été réservé depuis notre dernier refresh)
     */
    public TopoErrorException() {
        super("le topo n'est plus disponible");
    }
}
