package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Seguimiento;

import java.util.List;
import java.util.Map;

public interface SeguimientoDAO {

	
	
	Long save(Seguimiento seguimiento);

	List<Seguimiento> getAll();

	Boolean remove(Seguimiento seguimiento);
	

	Seguimiento findById(Long id);

	void deleteAllSeguimiento();

	List<Seguimiento> getSome(String opc, String mod);

	List<Seguimiento> getAllOrdered();

	Boolean isInFilter(String filter, String value);

	void bulkSave(List<Seguimiento> seguimiento);

	Integer getAllCount();

	List<Seguimiento> getRange(int startingAt, int maxPerPage, Map<String, Object> filters);

}
