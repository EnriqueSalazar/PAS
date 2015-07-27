package co.gov.fonada.planeacion.mb;

import static java.lang.System.out;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import co.gov.fonada.planeacion.model.PagoLazyList;
import com.lapis.jsfexporter.csv.CSVExportOptions;


import co.gov.fonada.planeacion.dao.PagoDAO;
import co.gov.fonada.planeacion.dao.PagoDAOObjectify;
import co.gov.fonada.planeacion.model.Pago;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class PagoFullMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277299790273226L;

    private PagoDAO dao;

    private Pago Pago;

    private Long idSelecionado;

    private LinkedHashMap<Long, Pago> Pagos;
    private List<Pago> pagoList;
    private Long parentId;
    private CSVExportOptions csvExportOptions;

/*    public LazyDataModel<co.gov.fonada.planeacion.model.Pago> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<co.gov.fonada.planeacion.model.Pago> lazyModel) {
        this.lazyModel = lazyModel;
    }

    private LazyDataModel<Pago> lazyModel;*/
//    private DataModel<Pago> dataPagos;

    public PagoFullMB() {
        dao = new PagoDAOObjectify();
    }

    //    public void editarPagos() {
//    }
/*    @PostConstruct
    public void init() {
        try {
            this.lazyModel = new LazyDataModel<Pago>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<Pago> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Pago> result = dao.getRange(startingAt, maxPerPage);
                    lazyModel.setRowCount();
                    return result;
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
    private LazyDataModel<Pago> lazyPagos = null;
    public LazyDataModel<Pago> getAllLazyPagos() {
        System.out.println("PagosFullMB  getLazyPagos ");
        if (lazyPagos == null) lazyPagos = new PagoLazyList();
        System.out.println("PagosFullMB  getLazyPagos ready to return "+lazyPagos.getPageSize());

        return lazyPagos;
    }

    public PagoDAO getDao() {
        out.println("public PagoDAO getDao()");

        return dao;
    }

    public void setDao(PagoDAO dao) {
        out.println("public PagoDAO setDao()");

        this.dao = dao;
    }

    public Pago getPago() {
        out.println("public Pago getPago()");

        return Pago;
    }

    public void setPago(Pago Pago) {
        out.println("public void setPago(Pago Pago))");

        this.Pago = Pago;
    }

    public Long getIdSelecionado() {
        System.out.println("PagosMB getIdSelecionado() " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        System.out.println("PagosMB setIdSelecionado(Long idSelecionado) "
                + idSelecionado);
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Pago> getPagos() {
        System.out.println("PagosFullMB getPagos()");

        return Pagos;
    }

    public void setPagos(Map<Long, Pago> Pagos) {
        System.out.println("PagosMB Pagos)");

        this.Pagos = (LinkedHashMap<Long, co.gov.fonada.planeacion.model.Pago>) Pagos;
    }


    public DataModel<Pago> getDataPagos() {
        System.out.println("PagosFullMB getDataPagos()");

        try {
            if (pagoList == null) fillDataPagos();
            return new ListDataModel<Pago>(new ArrayList<Pago>(pagoList));
        } catch (Exception e) {
            e.printStackTrace();
            return new ListDataModel<Pago>(new ArrayList<Pago>());

        }
      /*  if (pagoList!=null){
            return new ListDataModel<Pago>(new ArrayList<Pago>(pagoList));

        }else{
            return new ListDataModel<Pago>(new ArrayList<Pago>());

        }*/
    }

    public void fillDataPagos() {
        System.out.println("PagosFullMB fillDataPagos()");
        if (this.pagoList == null || this.pagoList.size() == 0) {
            PagoDAO dao = new PagoDAOObjectify();
            List<Pago> qryPagos = new ArrayList<Pago>(
                    dao.getAll());
            try {
                for (Pago a : qryPagos) {
                    a.setParentId(a.getParent().getId());
                }
                this.pagoList = new ArrayList<Pago>(qryPagos);
            } catch (Exception e) {
                e.printStackTrace();
                this.pagoList = new ArrayList<Pago>();
            }
        }

    }

    @PostConstruct
    public void reset() {

        Pagos = null;
    }

    public void agregar() {
        System.out.println("PagosMB agregar()");

        Pago = new Pago();
    }


    /**
     * @return the parentId
     */
    public Long getParentId() {
        // try {
        // FacesContext context = FacesContext.getCurrentInstance();
        // Map<String, String> requestMap = context.getExternalContext()
        // .getRequestParameterMap();
        // String parentString = requestMap.get("id");
        // out.println("parentString " + parentString + " " + this.parentId);
        // if (parentString != null) {
        // Long parentLong = Long.parseLong(parentString);
        // this.parentId = parentLong;
        // }
        //
        // System.out.println("getRequestParameterMap id " + this.parentId);
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        return this.parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Long parentId) {
        System.out.println("PagosMB setParentId " + parentId);
        this.parentId = parentId;
    }

    public void editar(String id) {
        if (id != null) {
            this.parentId = Long.valueOf(id);
            System.out.println("PagosMB Setting parentId " + this.parentId);
        }

    }

    public CSVExportOptions getCsvExportOptions() {
        CSVExportOptions options = new CSVExportOptions();
        options.setSeparatorCharacter('|');
        options.setCharacterEncoding("UTF-8-with-bom");
        return options;
    }


    public void setCsvExportOptions(CSVExportOptions csvExportOptions) {
        this.csvExportOptions = csvExportOptions;
    }

    public void CSVExport() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=pagos.csv");
        externalContext.setResponseContentType("text/csv");

        try {

            dao = new PagoDAOObjectify();
            List<Pago> qryPagos = new ArrayList<Pago>(
                    dao.getAll());
            OutputStream csvOut = externalContext.getResponseOutputStream();
            BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(csvOut, StandardCharsets.ISO_8859_1));
            /*EL CSV nunca debe iniciar como ID porque Excel lo toma por formato SYLK*/
            csvWriter.append("Contrato|Fuente|Aporte|" +
                    "Vigencia2015|" + "Vigencia2016|" + "Vigencia2017|" +
                    "Vigencia2018|" +
                    "Enero2015|" + "Febrero2015|" + "Marzo2015|" +
                    "Abril2015|" + "Mayo2015|" + "Junio2015|" +
                    "Julio2015|" + "Agosto2015|" + "Septiembre2015|" +
                    "Octubre2015|" + "Noviembre2015|" + "Diciembre2015|" +
                    "Enero2016|" + "Febrero2016|" + "Marzo2016|" +
                    "Abril2016|" + "Mayo2016|" + "Junio2016|" +
                    "Julio2016|" + "Agosto2016|" + "Septiembre2016|" +
                    "Octubre2016|" + "Noviembre2016|" + "Diciembre2016|" +
                    "Enero2017|" + "Febrero2017|" + "Marzo2017|" +
                    "Abril2017|" + "Mayo2017|" + "Junio2017|" +
                    "Julio2017|" + "Agosto2017|" + "Septiembre2017|" +
                    "Octubre2017|" + "Noviembre2017|" + "Diciembre2017|" +
                    "Enero2018|" + "Febrero2018|" + "Marzo2018|" +
                    "Abril2018|" + "Mayo2018|" + "Junio2018|" +
                    "Julio2018|" + "Agosto2018|" + "Septiembre2018|" +
                    "Octubre2018|" + "Noviembre2018|" + "Diciembre2018" +
                    "\n");

            for (Pago a : qryPagos) {
                csvWriter.append(
                        a.getParent().getId() + "|" + a.getFuente() + "|" + a.getAporte() + "|"
                                + a.getVigencia2015() + "|" + a.getVigencia2016() + "|" + a.getVigencia2017() + "|"
                                + a.getVigencia2018() + "|"
                                + a.getEnero2015() + "|" + a.getFebrero2015() + "|" + a.getMarzo2015() + "|"
                                + a.getAbril2015() + "|" + a.getMayo2015() + "|" + a.getJunio2015() + "|"
                                + a.getJulio2015() + "|" + a.getAgosto2015() + "|" + a.getSeptiembre2015() + "|"
                                + a.getOctubre2015() + "|" + a.getNoviembre2015() + "|" + a.getDiciembre2015() + "|"
                                + a.getEnero2016() + "|" + a.getFebrero2016() + "|" + a.getMarzo2016() + "|"
                                + a.getAbril2016() + "|" + a.getMayo2016() + "|" + a.getJunio2016() + "|"
                                + a.getJulio2016() + "|" + a.getAgosto2016() + "|" + a.getSeptiembre2016() + "|"
                                + a.getOctubre2016() + "|" + a.getNoviembre2016() + "|" + a.getDiciembre2016() + "|"
                                + a.getEnero2017() + "|" + a.getFebrero2017() + "|" + a.getMarzo2017() + "|"
                                + a.getAbril2017() + "|" + a.getMayo2017() + "|" + a.getJunio2017() + "|"
                                + a.getJulio2017() + "|" + a.getAgosto2017() + "|" + a.getSeptiembre2017() + "|"
                                + a.getOctubre2017() + "|" + a.getNoviembre2017() + "|" + a.getDiciembre2017() + "|"
                                + a.getEnero2018() + "|" + a.getFebrero2018() + "|" + a.getMarzo2018() + "|"
                                + a.getAbril2018() + "|" + a.getMayo2018() + "|" + a.getJunio2018() + "|"
                                + a.getJulio2018() + "|" + a.getAgosto2018() + "|" + a.getSeptiembre2018() + "|"
                                + a.getOctubre2018() + "|" + a.getNoviembre2018() + "|" + a.getDiciembre2018() + "\n");
            }

            csvWriter.flush();
            csvWriter.close();
            csvOut.close();
            context.responseComplete();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

}
