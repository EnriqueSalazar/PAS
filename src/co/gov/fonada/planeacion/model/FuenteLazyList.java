package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.FuenteDAO;
import co.gov.fonada.planeacion.dao.FuenteDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class FuenteLazyList extends LazyDataModel<Fuente> {

    private static final long serialVersionUID = 1L;
    private List<Fuente> fuentes;
    private FuenteDAO dao;

    @Override
    public List<Fuente> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new FuenteDAOObjectify();
        try {
            System.out.println("FuenteLazyList startingAt "+startingAt);
            System.out.println("FuenteLazyList maxPerPage "+maxPerPage);
            fuentes = dao.getRange(startingAt, maxPerPage, filters);
            System.out.println("FuenteLazyList getRange "+fuentes.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return fuentes;
    }

    @Override
    public Object getRowKey(Fuente fuente) {
        return fuente.getId();
    }

    @Override
    public Fuente getRowData(String fuenteId) {
        Integer id = Integer.valueOf(fuenteId);
        for (Fuente fuente : fuentes) {
            if (id.equals(fuente.getId())) {
                return fuente;
            }
        }
        return null;
    }
}