package net.ent.etrs.boutik.model.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum EtatCommande {
    
    VALIDEE,
    CONFIRMEE,
    EXPEDIEE,
    RECUE,
    ANNULEE;
    
    public List<EtatCommande> getProchains() {
        List<EtatCommande> etats = new ArrayList<>();
        
        switch (this) {
            case VALIDEE:
                etats = List.of(ANNULEE, CONFIRMEE);
                break;
            case CONFIRMEE:
                etats = List.of(ANNULEE, EXPEDIEE);
                break;
            case EXPEDIEE:
                etats = List.of(RECUE);
                break;
            case RECUE:
            case ANNULEE:
                etats = List.of();
                break;
        }
        return etats;
    }
}
