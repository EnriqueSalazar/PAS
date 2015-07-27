package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.SeguimientoDAO;
import co.gov.fonada.planeacion.dao.SeguimientoDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class SeguimientoLazyList extends LazyDataModel<Seguimiento> {

    private static final long serialVersionUID = 1L;
    private List<Seguimiento> seguimientos;
    private SeguimientoDAO dao;

    @Override
    public List<Seguimiento> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new SeguimientoDAOObjectify();
        try {
            System.out.println("SeguimientoLazyList startingAt " + startingAt);
            System.out.println("SeguimientoLazyList maxPerPage " + maxPerPage);
            seguimientos = dao.getRange(startingAt, maxPerPage, filters);

            System.out.println("SeguimientoLazyList getRange " + seguimientos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return seguimientos;
    }

    @Override
    public Object getRowKey(Seguimiento seguimiento) {
        return seguimiento.getId();
    }

    @Override
    public Seguimiento getRowData(String seguimientoId) {
        Integer id = Integer.valueOf(seguimientoId);
        for (Seguimiento seguimiento : seguimientos) {
            if (id.equals(seguimiento.getId())) {
                return seguimiento;
            }
        }
        return null;
    }
}