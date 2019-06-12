package dumaya.dev.exception;

public class TopoErrorException extends RuntimeException {
    public TopoErrorException() {
        super("le topo n'est plus disponible");
    }
}
