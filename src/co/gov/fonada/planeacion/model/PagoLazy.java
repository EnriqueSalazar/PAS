package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.PagoDAO;
import co.gov.fonada.planeacion.dao.PagoDAOObjectify;
import co.gov.fonada.planeacion.mb.ContratoMB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ManagedProperty;
import java.util.List;
import java.util.Map;

public class PagoLazy extends LazyDataModel<Pago> {

    private static final long serialVersionUID = 1L;
    private List<Pago> pagos;
    private PagoDAO dao;
    private Long parentId;

    public PagoLazy(Long parentId){
        this.parentId=parentId;
    }

    @Override
    public List<Pago> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new PagoDAOObjectify();
        try {
            System.out.println("PagoLazyList startingAt "+startingAt);
            System.out.println("PagoLazyList maxPerPage "+maxPerPage);
            pagos = dao.getParentRange(startingAt, maxPerPage, parentId);
            System.out.println("PagoLazyList getRange "+pagos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getParentCount(parentId));
        setPageSize(maxPerPage);
        return pagos;
    }

    @Override
    public Object getRowKey(Pago pago) {
        return pago.getId();
    }

    @Override
    public Pago getRowData(String pagoId) {
        Integer id = Integer.valueOf(pagoId);
        for (Pago pago : pagos) {
            if (id.equals(pago.getId())) {
                return pago;
            }
        }
        return null;
    }
}