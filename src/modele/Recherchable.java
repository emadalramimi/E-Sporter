package modele;

import java.util.List;

public interface Recherchable<T> {
    
    public List<T> getParNom(String nom) throws Exception;

}
