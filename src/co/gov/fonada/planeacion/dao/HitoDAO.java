package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Hito;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

public interface HitoDAO {


    Long save(Hito hito);

    List<Hito> getAll();

    Boolean remove(Hito hito);


    Hito findById(Long id);

    void deleteAllHito();

    List<Hito> getSome(String opc, String mod);

    List<Hito> getAllOrdered();

    Boolean isInFilter(String filter, String value);

    void bulkSave(List<Hito> hito);

    Integer getAllCount();

    List<Hito> getRange(int startingAt, int maxPerPage, Long parentId);
}
