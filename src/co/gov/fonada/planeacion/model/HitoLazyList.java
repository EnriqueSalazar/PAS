package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.HitoDAO;
import co.gov.fonada.planeacion.dao.HitoDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class HitoLazyList extends LazyDataModel<Hito> {

    private static final long serialVersionUID = 1L;
    private List<Hito> hitos;
    private HitoDAO dao;
    private Long parentId;

    public HitoLazyList(Long parentId){
        this.parentId=parentId;
    }

    @Override
    public List<Hito> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new HitoDAOObjectify();
        try {
            System.out.println("HitoLazyList startingAt " + startingAt);
            System.out.println("HitoLazyList maxPerPage " + maxPerPage);
            hitos = dao.getRange(startingAt, maxPerPage, parentId);

            System.out.println("HitoLazyList getRange " + hitos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
     /*   for (Hito a : hitos) {
            try {
                a.setParentId(a.getParent().getId());
            } catch (Exception e) {
                e.printStackTrace();

            }
        }*/
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return hitos;
    }

    @Override
    public Object getRowKey(Hito hito) {
        return hito.getId();
    }

    @Override
    public Hito getRowData(String hitoId) {
        Integer id = Integer.valueOf(hitoId);
        for (Hito hito : hitos) {
            if (id.equals(hito.getId())) {
                return hito;
            }
        }
        return null;
    }
}