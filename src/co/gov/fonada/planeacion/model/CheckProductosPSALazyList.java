package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.*;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CheckProductosPSALazyList extends LazyDataModel<CheckProductoPSA> {

    private static final long serialVersionUID = 1L;
    private List<CheckProductoPSA> productoPSAs;
    private ProductoPSADAO dao;
    private ContratoDAO contratoDAO;
    private EtapaDAO etapaDAO;
    private Long parentId;

    public CheckProductosPSALazyList(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<CheckProductoPSA> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new ProductoPSADAOObjectify();
        contratoDAO = new ContratoDAOObjectify();
        etapaDAO = new EtapaDAOObjectify();
        try {
            System.out.println("ProductoPSALazyList startingAt " + startingAt);
            System.out.println("ProductoPSALazyList maxPerPage " + maxPerPage);
            productoPSAs = dao.getCheckRange(startingAt, maxPerPage, parentId);

            System.out.println("ProductoPSALazyList getRange " + productoPSAs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Contrato contratoTemp;
        List<Etapa> etapas = new ArrayList<>();

/*        for (ProductoPSA a : productoPSAs) {
//            System.out.println("ProductoPSALazyList contratoTemp " + contratoDAO.isContrato(a.getContrato()));

            try {
                contratoTemp = contratoDAO.findById(a.getContrato());

                etapas = etapaDAO.getSomeToCheck(contratoTemp.getReferencia(), contratoTemp.getModalidad());
                Calendar cal = Calendar.getInstance();

                cal.setTime(contratoTemp.getFechaRef());
                cal.add(Calendar.DATE, etapas.get(0).getDuracion());
                a.setTCC1(cal.getTime());

                cal.setTime(contratoTemp.getFechaRef());
                cal.add(Calendar.DATE, etapas.get(1).getDuracion());
                a.setSig1(cal.getTime());

                a.setDeltaTCC(TimeUnit.DAYS.convert(a.getTCCpub().getTime() - a.getTCC1().getTime(), TimeUnit.MILLISECONDS));
                a.setDeltaSig(TimeUnit.DAYS.convert(a.getContratoSig().getTime() - a.getSig1().getTime(), TimeUnit.MILLISECONDS));

            } catch (Exception e) {
                e.printStackTrace();

            }
        }*/
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return productoPSAs;
    }

    @Override
    public Object getRowKey(CheckProductoPSA productoPSA) {
        return productoPSA.getId();
    }

    @Override
    public CheckProductoPSA getRowData(String productoPSAId) {
        Integer id = Integer.valueOf(productoPSAId);
        for (CheckProductoPSA productoPSA : productoPSAs) {
            if (id.equals(productoPSA.getId())) {
                return productoPSA;
            }
        }
        return null;
    }
}