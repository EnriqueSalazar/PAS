package co.gov.fonada.planeacion.dao;

import java.util.List;

import co.gov.fonada.planeacion.model.Accion;

public interface AccionDAO {

	
	
	Long save(Accion accion);

	List<Accion> getAll();

	Boolean remove(Accion accion);
	

	Accion findById(Long id);

	List<Accion> getByParent(Long parent);

	List<Accion> getAllOrdered();

	void deleteAllAccion();

	void bulkSave(List<Accion> accion);

	List<Accion> getRange(int startingAt, int maxPerPage);

	Integer getAllCount();

	List<Accion> getParentRange(int startingAt, int maxPerPage,Long parentId);

	Integer getParentCount(Long parentId);
}
