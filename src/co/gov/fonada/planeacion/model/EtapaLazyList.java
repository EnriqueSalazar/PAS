package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.EtapaDAO;
import co.gov.fonada.planeacion.dao.EtapaDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class EtapaLazyList extends LazyDataModel<Etapa> {

    private static final long serialVersionUID = 1L;
    private List<Etapa> etapas;
    private EtapaDAO dao;

    @Override
    public List<Etapa> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new EtapaDAOObjectify();
        try {
            System.out.println("EtapaLazyList startingAt "+startingAt);
            System.out.println("EtapaLazyList maxPerPage "+maxPerPage);
            etapas = dao.getRange(startingAt, maxPerPage, filters);
            System.out.println("EtapaLazyList getRange "+etapas.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return etapas;
    }

    @Override
    public Object getRowKey(Etapa etapa) {
        return etapa.getId();
    }

    @Override
    public Etapa getRowData(String etapaId) {
        Integer id = Integer.valueOf(etapaId);
        for (Etapa etapa : etapas) {
            if (id.equals(etapa.getId())) {
                return etapa;
            }
        }
        return null;
    }
}