package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.AccionDAO;
import co.gov.fonada.planeacion.dao.AccionDAOObjectify;
import co.gov.fonada.planeacion.dao.DivipolaDAO;
import co.gov.fonada.planeacion.dao.DivipolaDAOObjectify;
import co.gov.fonada.planeacion.mb.ContratoMB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ManagedProperty;
import java.util.List;
import java.util.Map;

public class AccionLazy extends LazyDataModel<Accion> {

    private static final long serialVersionUID = 1L;
    private List<Accion> accions;
    private AccionDAO dao;
//    @ManagedProperty("#{contratoMB}")
//    private ContratoMB contratoMB;

    private Long parentId;

    public AccionLazy(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<Accion> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new AccionDAOObjectify();
        DivipolaDAO divipolaDAO = new DivipolaDAOObjectify();
        Divipola divipolaTemp;
        try {
            System.out.println("AccionLazyList startingAt " + startingAt);
            System.out.println("AccionLazyList maxPerPage " + maxPerPage);
            System.out.println("AccionLazyList getIdSelecionado " + this.parentId);
            accions = dao.getParentRange(startingAt, maxPerPage, this.parentId);
            for (Accion a : accions) {
                try {
                    divipolaTemp = divipolaDAO.getByDivipola(a.getDivipola());
                    a.setDepartamento(divipolaTemp.getDepartamento());
                    a.setMunicipio(divipolaTemp.getMunicipio());
                } catch (Exception e) {
                    System.out.println("catch " + e.getMessage());
                }
            }
            System.out.println("AccionLazyList getRange " + accions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getParentCount(this.parentId));
        setPageSize(maxPerPage);
        return accions;
    }

    @Override
    public Object getRowKey(Accion accion) {
        return accion.getId();
    }

    @Override
    public Accion getRowData(String accionId) {
        Integer id = Integer.valueOf(accionId);
        for (Accion accion : accions) {
            if (id.equals(accion.getId())) {
                return accion;
            }
        }
        return null;
    }
}