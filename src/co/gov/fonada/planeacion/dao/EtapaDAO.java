package co.gov.fonada.planeacion.dao;

import java.util.List;
import java.util.Map;

import co.gov.fonada.planeacion.model.Etapa;

import javax.faces.model.SelectItem;

public interface EtapaDAO {

	
	
	Long save(Etapa etapa);

	List<Etapa> getAll();

	Boolean remove(Etapa etapa);
	

	Etapa findById(Long id);

	void deleteAllEtapa();

	List<Etapa> getSome(String opc, String mod);
	List<Etapa> getSomeToCheck(String opc, String mod);

	List<Etapa> getAllOrdered();

	Boolean isInFilter(String filter, String value);

	void bulkSave(List<Etapa> etapa);

	Integer getAllCount();

	List<Etapa> getRange(int startingAt, int maxPerPage, Map<String, Object> filters);
	SelectItem[] getOpcionOption();
	SelectItem[] getModalidadOption();
	SelectItem[] getEtapaOption();
	List<String> getModalidadLista();
	List<String> getOpcionLista();
}
