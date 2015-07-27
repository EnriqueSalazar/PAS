package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.DivipolaDAO;
import co.gov.fonada.planeacion.dao.DivipolaDAOObjectify;
import co.gov.fonada.planeacion.dao.RegionalizacionDAO;
import co.gov.fonada.planeacion.dao.RegionalizacionDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class RegionalizacionLazyList extends LazyDataModel<Regionalizacion> {

    private static final long serialVersionUID = 1L;
    private List<Regionalizacion> regionalizacions;
    private RegionalizacionDAO dao;

    @Override
    public List<Regionalizacion> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new RegionalizacionDAOObjectify();
        DivipolaDAO divipolaDAO= new DivipolaDAOObjectify();
        Divipola divipolaTemp;
        try {
            System.out.println("RegionalizacionLazyList startingAt "+startingAt);
            System.out.println("RegionalizacionLazyList maxPerPage "+maxPerPage);
            regionalizacions = dao.getRange(startingAt, maxPerPage);
            for (Regionalizacion a : regionalizacions) {
                a.setParentId(a.getParent().getId());
                divipolaTemp=divipolaDAO.getByDivipola(a.getDivipola());
                a.setDepartamento(divipolaTemp.getDepartamento());
                a.setMunicipio(divipolaTemp.getMunicipio());
            }
            System.out.println("RegionalizacionLazyList getRange "+regionalizacions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return regionalizacions;
    }

    @Override
    public Object getRowKey(Regionalizacion regionalizacion) {
        return regionalizacion.getId();
    }

    @Override
    public Regionalizacion getRowData(String regionalizacionId) {
        Integer id = Integer.valueOf(regionalizacionId);
        for (Regionalizacion regionalizacion : regionalizacions) {
            if (id.equals(regionalizacion.getId())) {
                return regionalizacion;
            }
        }
        return null;
    }
}