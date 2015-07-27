package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Fuente;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

public interface FuenteDAO {

	
	
	Long save(Fuente fuente);

	List<Fuente> getAll();

	Boolean remove(Fuente fuente);
	

	Fuente findById(Long id);

	void deleteAllFuente();

	List<Fuente> getSome(String opc, String mod);

	List<Fuente> getAllOrdered();

	Boolean isInFilter(String filter, String value);

	void bulkSave(List<Fuente> fuente);

	Integer getAllCount();

	List<Fuente> getRange(int startingAt, int maxPerPage, Map<String, Object> filters);
	SelectItem[] getFuenteOption();
	List<String> getFuenteLista();
}
