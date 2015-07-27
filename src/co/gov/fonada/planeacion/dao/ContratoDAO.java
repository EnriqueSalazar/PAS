package co.gov.fonada.planeacion.dao;

import java.util.List;
import java.util.Map;

import co.gov.fonada.planeacion.model.Contrato;

public interface ContratoDAO {


    Long save(Contrato contrato);

    List<Contrato> getAll();

    Boolean remove(Contrato contrato);


    Contrato findById(Long id);

    List<Contrato> getAllOrdered();

    void deleteAllContrato();

    List<Contrato> getAllVersionsOf(Long versionOf);

    void bulkSave(List<Contrato> contrato);

    Boolean isContrato(Long id);

    Map<Integer, List<Contrato>> getRange(int startingAt, int maxPerPage, Map<String, Object> filters);
    Integer getAllCount();

}
