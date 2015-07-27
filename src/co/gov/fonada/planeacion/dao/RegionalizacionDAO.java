package co.gov.fonada.planeacion.dao;

import java.util.List;

import co.gov.fonada.planeacion.model.Regionalizacion;

public interface RegionalizacionDAO {

	
	
	Long save(Regionalizacion regionalizacion);

	List<Regionalizacion> getAll();

	Boolean remove(Regionalizacion regionalizacion);
	

	Regionalizacion findById(Long id);

	List<Regionalizacion> getByParent(Long parent);

	List<Regionalizacion> getAllOrdered();

	void deleteAllRegionalizacion();

	void bulkSave(List<Regionalizacion> regionalizacion);

	List<Regionalizacion> getRange(int startingAt, int maxPerPage);

	Integer getAllCount();

	List<Regionalizacion> getParentRange(int startingAt, int maxPerPage,Long parentId);

	Integer getParentCount(Long parentId);
	Double getSumBeneficiario(Long parent);
	Double getSumValor(Long parent);
}
