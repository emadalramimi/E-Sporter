package modele.DAO;

import java.util.List;
import java.util.Optional;

/**
 * Interface DAO
 * @param <T> Type class métier
 * @param <T1> Type clé primaire
 */
public interface DAO<T, T1> {

    public List<T> getTout() throws Exception;

    @SuppressWarnings("unchecked")
    public Optional<T> getParId(T1... id) throws Exception;

    public boolean ajouter(T valeur) throws Exception;

    public boolean modifier(T valeur) throws Exception;

    public boolean supprimer(T valeur) throws Exception;
    
}