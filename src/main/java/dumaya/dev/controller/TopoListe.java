package dumaya.dev.controller;


import dumaya.dev.model.Topo;

import java.util.ArrayList;
import java.util.List;

public class TopoListe {
    private List<Topo> topoListe;

    public TopoListe(List<Topo> topoListe) {
        this.topoListe = topoListe;
    }

    public List<Topo> getTopoListe() {
        return topoListe;
    }

    public TopoListe() {
        this.topoListe = new ArrayList<>();
    }

    public void setTopoListe(List<Topo> topoListe) {
        this.topoListe = topoListe;
    }
}
