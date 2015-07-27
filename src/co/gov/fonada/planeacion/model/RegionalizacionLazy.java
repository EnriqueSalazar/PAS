package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.DivipolaDAO;
import co.gov.fonada.planeacion.dao.DivipolaDAOObjectify;
import co.gov.fonada.planeacion.dao.RegionalizacionDAO;
import co.gov.fonada.planeacion.dao.RegionalizacionDAOObjectify;
import co.gov.fonada.planeacion.mb.ContratoMB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ManagedProperty;
import java.util.List;
import java.util.Map;

public class RegionalizacionLazy extends LazyDataModel<Regionalizacion> {

    private static final long serialVersionUID = 1L;
    private List<Regionalizacion> regionalizacions;
    private RegionalizacionDAO dao;

    private Long parentId;

    public RegionalizacionLazy(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<Regionalizacion> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new RegionalizacionDAOObjectify();
        DivipolaDAO divipolaDAO = new DivipolaDAOObjectify();
        Divipola divipolaTemp;
        try {
            System.out.println("RegionalizacionLazyList startingAt " + startingAt);
            System.out.println("RegionalizacionLazyList maxPerPage " + maxPerPage);
            System.out.println("RegionalizacionLazyList getIdSelecionado " + parentId);

            regionalizacions = dao.getParentRange(startingAt, maxPerPage, parentId);
            for (Regionalizacion a : regionalizacions) {
                try {
                    divipolaTemp = divipolaDAO.getByDivipola(a.getDivipola());
                    a.setDepartamento(divipolaTemp.getDepartamento());
                    a.setMunicipio(divipolaTemp.getMunicipio());
                }catch (Exception e){
                    System.out.println("catch "+e.getMessage());
                }
            }
            System.out.println("RegionalizacionLazyList getRange " + regionalizacions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getParentCount(parentId));
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