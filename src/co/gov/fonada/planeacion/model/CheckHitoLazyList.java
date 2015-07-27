package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.*;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CheckHitoLazyList extends LazyDataModel<Hito> {

    private static final long serialVersionUID = 1L;
    private List<Hito> hitos;
    private HitoDAO dao;
    private ContratoDAO contratoDAO;
    private EtapaDAO etapaDAO;
    private Long parentId;

    public CheckHitoLazyList(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<Hito> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new HitoDAOObjectify();
        contratoDAO = new ContratoDAOObjectify();
        etapaDAO = new EtapaDAOObjectify();
        try {
            System.out.println("HitoLazyList startingAt " + startingAt);
            System.out.println("HitoLazyList maxPerPage " + maxPerPage);
            hitos = dao.getRange(startingAt, maxPerPage, parentId);

            System.out.println("HitoLazyList getRange " + hitos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Contrato contratoTemp;
        List<Etapa> etapas = new ArrayList<>();

        for (Hito a : hitos) {
//            System.out.println("HitoLazyList contratoTemp " + contratoDAO.isContrato(a.getContrato()));

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
        }
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