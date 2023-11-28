package modele;

import java.util.List;
import java.util.Optional;

public interface DAO<T, T1> {

	List<T> getTout() throws Exception;
	
	@SuppressWarnings("unchecked")
	Optional<T> getParId(T1... id) throws Exception;

    boolean ajouter(T valeur) throws Exception;

    boolean modifier(T valeur) throws Exception;

    boolean supprimer(T valeur) throws Exception;
	
}
