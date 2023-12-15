package modele;

import java.util.List;
import java.util.Optional;

/**
 * Classe abstraite DAO
 * @param <T>
 * @param <T1>
 */
public abstract class DAO<T, T1> {

    public List<T> getTout() throws Exception {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }

    @SuppressWarnings("unchecked")
    public Optional<T> getParId(T1... id) throws Exception {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }

    public boolean ajouter(T valeur) throws Exception {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }

    public boolean modifier(T valeur) throws Exception {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }

    public boolean supprimer(T valeur) throws Exception {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }
}