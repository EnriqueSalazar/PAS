package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Divipola;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

/**
 * Created by esalazar on 29/04/2015.
 */
public interface DivipolaDAO {

    Long save(Divipola divipola);

    List<Divipola> getAll();

    Boolean remove(Divipola divipola);


    Divipola findById(Long id);

    void deleteAllDivipola();

    void deleteSomeDivipola(String valor);

    List<Divipola> getSome(String menu);

    Divipola getByDivipola(Integer divipola);

    List<Divipola> getAllOrdered();

    void bulkSave(List<Divipola> divipola);

    List<Divipola> getRange(int startingAt, int maxPerPage, Map<String, Object> filters);

    Integer getAllCount();

    SelectItem[] getMunicipioOption();

    SelectItem[] getDepartamentoOption();

    SelectItem[] getEntidadOption();

    Boolean isDivipola(int value);
    Map<String,String> getDepartamentoMap();
    Map<String,Integer> getMunicipioMap(String departamento);
}
