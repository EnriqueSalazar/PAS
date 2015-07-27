package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Lista;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

/**
 * Created by esalazar on 23/04/2015.
 */
public interface ListaDAO {
    Long save(Lista lista);

    List<Lista> getAll();

    Boolean remove(Lista lista);


    Lista findById(Long id);

    void deleteAllLista();

    void deleteSomeLista(String valor);

    List<Lista> getSome(String menu);

    List<Lista> getAllOrdered();

    Boolean isInFilter(String lista, String filter, String value);
    Lista getItem(String lista, String filter, String value, String project);
    void bulkSave(List<Lista> lista);
    SelectItem[] getPostulacionOption();
    SelectItem[] getProductoOption();
    SelectItem[] getSectorOption();
    SelectItem[] getIntervencionOption();
    Integer getListaCount(String lista);
    List<String> getProductoStringLista();
    List<String> getSectorStringLista();
    List<String> getPostulacionStringLista();
    List<String> getIntervencionStringLista(String postulacion);
    List<Lista> getListaRange(int startingAt, int maxPerPage, String lista, Map<String, Object> filters);
}
