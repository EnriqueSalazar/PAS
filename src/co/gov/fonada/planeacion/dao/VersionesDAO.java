package co.gov.fonada.planeacion.dao;

import java.util.List;

import co.gov.fonada.planeacion.model.Versiones;

public interface VersionesDAO {

	
	
	Long save(Versiones contrato);

	List<Versiones> getAll();

	Boolean remove(Versiones contrato);
	

	Versiones findById(Long id);

	List<Versiones> getAllOrdered();
	
	
}
