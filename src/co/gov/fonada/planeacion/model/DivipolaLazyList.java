package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.DivipolaDAO;
import co.gov.fonada.planeacion.dao.DivipolaDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class DivipolaLazyList extends LazyDataModel<Divipola> {

    private static final long serialVersionUID = 1L;
    private List<Divipola> divipolas;
    private DivipolaDAO dao;

    @Override
    public List<Divipola> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new DivipolaDAOObjectify();
        try {
            System.out.println("DivipolaLazyList startingAt " + startingAt);
            System.out.println("DivipolaLazyList maxPerPage " + maxPerPage);
            divipolas = dao.getRange(startingAt, maxPerPage, filters);
            System.out.println("DivipolaLazyList getRange " + divipolas.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return divipolas;
    }

    @Override
    public Object getRowKey(Divipola divipola) {
        return divipola.getId();
    }

    @Override
    public Divipola getRowData(String divipolaId) {
        Integer id = Integer.valueOf(divipolaId);
        for (Divipola divipola : divipolas) {
            if (id.equals(divipola.getId())) {
                return divipola;
            }
        }
        return null;
    }
}