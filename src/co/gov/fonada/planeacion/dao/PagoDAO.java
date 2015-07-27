package co.gov.fonada.planeacion.dao;

import java.util.List;

import co.gov.fonada.planeacion.model.Pago;

public interface PagoDAO {

	
	
	Long save(Pago pago);

	List<Pago> getAll();

	Boolean remove(Pago pago);

	Double getSumAportes(Long parent);

	Pago findById(Long id);

	List<Pago> getByParent(Long parent);

	List<Pago> getAllOrdered();

	void deleteAllPago();

	void bulkSave(List<Pago> pago);

	List<Pago> getRange(int startingAt, int maxPerPage);

	Integer getAllCount();

	List<Pago> getParentRange(int startingAt, int maxPerPage,Long parentId);

	Integer getParentCount(Long parentId);

}
