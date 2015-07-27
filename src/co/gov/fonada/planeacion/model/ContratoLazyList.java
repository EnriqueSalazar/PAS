package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.ContratoDAO;
import co.gov.fonada.planeacion.dao.ContratoDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class ContratoLazyList extends LazyDataModel<Contrato> {

    private static final long serialVersionUID = 1L;
    private List<Contrato> contratos;
    private ContratoDAO dao;

    @Override
    public List<Contrato> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new ContratoDAOObjectify();
        try {
            System.out.println("ContratoLazyList startingAt " + startingAt);
            System.out.println("ContratoLazyList maxPerPage " + maxPerPage);
            Map<Integer, List<Contrato>> contratoMap = dao.getRange(startingAt, maxPerPage, filters);
            Map.Entry<Integer, List<Contrato>> entry = contratoMap.entrySet().iterator().next();
            contratos = entry.getValue();
            setRowCount(entry.getKey());
            System.out.println("ContratoLazyList getRange " + contratos.size());
        } catch (Exception e) {
            setRowCount(dao.getAllCount());
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return contratos;
    }

    @Override
    public Object getRowKey(Contrato contrato) {
        return contrato.getId();
    }

    @Override
    public Contrato getRowData(String contratoId) {
        Integer id = Integer.valueOf(contratoId);
        for (Contrato contrato : contratos) {
            if (id.equals(contrato.getId())) {
                return contrato;
            }
        }
        return null;
    }
}