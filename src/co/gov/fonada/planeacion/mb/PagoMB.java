package co.gov.fonada.planeacion.mb;

import static java.lang.System.out;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import co.gov.fonada.planeacion.model.PagoLazy;
import co.gov.fonada.planeacion.model.PagoLazyList;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;

import co.gov.fonada.planeacion.dao.PagoDAO;
import co.gov.fonada.planeacion.dao.PagoDAOObjectify;
import co.gov.fonada.planeacion.model.Contrato;
import co.gov.fonada.planeacion.model.Pago;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class PagoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277299790273226L;


    private PagoDAO dao;

    @ManagedProperty("#{contratoMB}")
    private ContratoMB contratoMB;

    private Pago Pago;

    private Long idSelecionado;

    private LinkedHashMap<Long, Pago> Pagos;

    private Long parentId;
    private CSVExportOptions csvExportOptions;

    public PagoMB() {
        out.println("PagoMB public starts");

        // try {
        // out.println("PagoMB public contratoMB "+contratoMB.getIdSelecionado());
        //
        // // FacesContext context = FacesContext.getCurrentInstance();
        // Map<String, String> requestMap = context.getExternalContext()
        // .getRequestParameterMap();
        // String parentString = requestMap.get("id");
        // out.println("PagoMB parentString " + parentString
        // + " this.parentId " + this.parentId);
        // // if (parentString != null) {
        // // Long parentLong = Long.parseLong(parentString);
        // // // this.parentId = parentLong;
        // // }
        //
        // System.out.println("PagoMB getRequestParameterMap this.parentId "
        // + this.parentId);
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }

        out.println("");
        dao = new PagoDAOObjectify();
        // fillPagos();
        out.println("PagoMB public ends");

    }

    private LazyDataModel<Pago> lazyPagos = null;

    public LazyDataModel<Pago> getLazyPagos() {
        System.out.println("PagosMB  getLazyPagos ");
        if (lazyPagos == null) lazyPagos = new PagoLazy(parentId);
        System.out.println("PagosFullMB  getLazyPagos ready to return " + lazyPagos.getPageSize());

        return lazyPagos;
    }


    public void actualizar() {
        out.println("public void actualizar()");
        fillPagos();
    }

    public void editarPagos() {

    }

    public void fillPagos() {
        try {
            if (Pagos == null && this.parentId != null && this.parentId != 0) {
                out.println("PagosMB starting fillPagos");
                // this.parentId=contratoMB.getIdSelecionado();
                out.println("PagosMB fillPagos this.parentId " + this.parentId);
                List<Pago> qryPagos = new ArrayList<Pago>(
                        dao.getByParent(this.parentId));
                out.println("PagosMB qryPagos.size " + qryPagos.size());
//				out.println("PagosMB qryPagos.toString " + qryPagos.toString());
                Pagos = new LinkedHashMap<Long, Pago>();
                for (Pago a : qryPagos) {
                    // try {
                    // if (a.getParent().equals(this.parentId)) {
                    Pagos.put(a.getId(), a);
                    // out.println("PagosMB " + a.getId() + " "
                    // + a.getParent() + " si");
                    // } else {
                    // out.println("PagosMB " + a.getId() + " "
                    // + a.getParent() + " no");
                    // }
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                }
                out.println("PagosMB fillPagos new");
            } else {
                out.println("PagosMB fillPagos reusing");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @return the contratoMB
     */
    public ContratoMB getContratoMB() {
        return contratoMB;
    }

    /**
     * @param contratoMB the contratoMB to set
     */
    public void setContratoMB(ContratoMB contratoMB) {
        this.contratoMB = contratoMB;
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
        System.out.println("PagosMB getPagos()");

        return Pagos;
    }

    public void setPagos(Map<Long, Pago> Pagos) {
        System.out.println("PagosMB Pagos)");

        this.Pagos = (LinkedHashMap<Long, co.gov.fonada.planeacion.model.Pago>) Pagos;
    }

    public DataModel<Pago> getDataPagos() {

//		System.out.println("PagosMB getDataPagos()");
        fillPagos();
//		System.out.println("PagosMB getDataPagos() Pagos.values "+Pagos.values().size());
        if (Pagos.values() != null) {
            return new ListDataModel<Pago>(new ArrayList<Pago>(Pagos.values()));

        } else {
            return new ListDataModel<Pago>(new ArrayList<Pago>());

        }
//        ListDataModel<Pago> modelPago = new ListDataModel<Pago>(new ArrayList<Pago>(Pagos.values()));
//
//        return modelPago;
    }

    public DataModel<Pago> getDataPagosFull() {
        System.out.println("PagosMB getDataPagosFull");

        PagoDAO dao = new PagoDAOObjectify();
        List<Pago> qryPagos = new ArrayList<Pago>(
                dao.getAll());
        for (Pago a : qryPagos) {
            a.setParentId(a.getParent().getId());
        }
        ListDataModel<Pago> modelPago = new ListDataModel<Pago>(qryPagos);
        return modelPago;
    }

    @PostConstruct
    public void reset() {
        System.out.println("PagosMB Pagos reset contratoMB.getIdSelecionado "
                + contratoMB.getIdSelecionado());
        this.parentId = contratoMB.getIdSelecionado();
        Pagos = null;
//        fillPagos();
    }

    public void agregar() {
        System.out.println("PagosMB agregar()");

        Pago = new Pago();
    }

    public List<String> sectorsList(String query) {
        List<String> results = Arrays.asList("Acueducto", "Educaci�n",
                "Reactivaci�n", "Riesgos", "Salud", "Ambiente", "Transporte",
                "Vivienda");
        return results;
    }

    public List<String> getListaSectores() {
        List<String> results = Arrays.asList("Acueducto", "Educaci�n",
                "Reactivaci�n", "Riesgos", "Salud", "Ambiente", "Transporte",
                "Vivienda");
        return results;
    }

    public void onRowSelect(SelectEvent event, String theid) {
        out.println("PagosMB Dentro de onRowSelect");
        out.println("PagosMB Pago.getId " + theid);

    }

    public void buttonCrear(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UIInput modalidadComponent = (UIInput) viewRoot.findComponent("mainAccordion:form:modalidadButton");
        // Long actionPId = (Long) actionEvent.getComponent().getAttributes()
        // .get("pId");
        // out.println("buttonCrear actionPId " + actionPId);
        // this.parentId = actionPId;
        try {
            out.println("			Pago = new Pago();");
            Pago = new Pago();
            out.println("			Pago.setParent(actionPId);");
            Pago.setParent(Key.create(Contrato.class, this.parentId));
            out.println("			dao.save(Pago);");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Pago> key = f.allocateId(Pago.class);
            Pago.setId(key.getId());
            dao.save(Pago);
            // // Thread.sleep(2000);
            out.println("			Pagos.put(Pago.getId(), Pago); " + Pago.getId());
            Pagos.put(Pago.getId(), Pago);
            out.println("Guardado!!!");
            context.addMessage(null, new FacesMessage("Creado!", Pago.getId()
                    + " creado exitosamente."));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fillPagos();
        context.getPartialViewContext().getRenderIds()
                .add("mainAccordion:formPago:dataPagos");
        /*try {
            viewRoot.findComponent("mainAccordion:formPago:dataPagos").encodeAll(context);

		} catch (IOException IOE) {

		}*/
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("debug buttonGuardar");

        FacesContext context = FacesContext.getCurrentInstance();
        Pago pagoModel = (Pago) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "pago");
        // this.idSelecionado = pagoModel.getId();
        Pago = pagoModel;

        try {
            System.out.println("debug buttonGuardar try");
            dao.save(Pago);
            // Thread.sleep(2000);
            //   Pagos.put(Pago.getId(), Pago);
            context.addMessage(null, new FacesMessage("Guardado!",
                    "Pago almacenado exitosamente."));

            if (Pago.getVigencia2015() > (Pago.getEnero2015() + Pago.getFebrero2015() + Pago.getMarzo2015() + Pago.getAbril2015() + Pago.getMayo2015() + Pago.getJunio2015() + Pago.getJulio2015() + Pago.getAgosto2015() + Pago.getSeptiembre2015() + Pago.getOctubre2015() + Pago.getNoviembre2015() + Pago.getDiciembre2015())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2015 es mayor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }
            if (Pago.getVigencia2015() < (Pago.getEnero2015() + Pago.getFebrero2015() + Pago.getMarzo2015() + Pago.getAbril2015() + Pago.getMayo2015() + Pago.getJunio2015() + Pago.getJulio2015() + Pago.getAgosto2015() + Pago.getSeptiembre2015() + Pago.getOctubre2015() + Pago.getNoviembre2015() + Pago.getDiciembre2015())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2015 es menor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }
            if (Pago.getVigencia2016() > (Pago.getEnero2016() + Pago.getFebrero2016() + Pago.getMarzo2016() + Pago.getAbril2016() + Pago.getMayo2016() + Pago.getJunio2016() + Pago.getJulio2016() + Pago.getAgosto2016() + Pago.getSeptiembre2016() + Pago.getOctubre2016() + Pago.getNoviembre2016() + Pago.getDiciembre2016())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2016 es mayor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }
            if (Pago.getVigencia2016() < (Pago.getEnero2016() + Pago.getFebrero2016() + Pago.getMarzo2016() + Pago.getAbril2016() + Pago.getMayo2016() + Pago.getJunio2016() + Pago.getJulio2016() + Pago.getAgosto2016() + Pago.getSeptiembre2016() + Pago.getOctubre2016() + Pago.getNoviembre2016() + Pago.getDiciembre2016())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2016 es menor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }

            if (Pago.getVigencia2017() > (Pago.getEnero2017() + Pago.getFebrero2017() + Pago.getMarzo2017() + Pago.getAbril2017() + Pago.getMayo2017() + Pago.getJunio2017() + Pago.getJulio2017() + Pago.getAgosto2017() + Pago.getSeptiembre2017() + Pago.getOctubre2017() + Pago.getNoviembre2017() + Pago.getDiciembre2017())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2017 es mayor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }
            if (Pago.getVigencia2017() < (Pago.getEnero2017() + Pago.getFebrero2017() + Pago.getMarzo2017() + Pago.getAbril2017() + Pago.getMayo2017() + Pago.getJunio2017() + Pago.getJulio2017() + Pago.getAgosto2017() + Pago.getSeptiembre2017() + Pago.getOctubre2017() + Pago.getNoviembre2017() + Pago.getDiciembre2017())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2017 es menor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }

            if (Pago.getVigencia2018() > (Pago.getEnero2018() + Pago.getFebrero2018() + Pago.getMarzo2018() + Pago.getAbril2018() + Pago.getMayo2018() + Pago.getJunio2018() + Pago.getJulio2018() + Pago.getAgosto2018() + Pago.getSeptiembre2018() + Pago.getOctubre2018() + Pago.getNoviembre2018() + Pago.getDiciembre2018())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2018 es mayor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }
            if (Pago.getVigencia2018() < (Pago.getEnero2018() + Pago.getFebrero2018() + Pago.getMarzo2018() + Pago.getAbril2018() + Pago.getMayo2018() + Pago.getJunio2018() + Pago.getJulio2018() + Pago.getAgosto2018() + Pago.getSeptiembre2018() + Pago.getOctubre2018() + Pago.getNoviembre2018() + Pago.getDiciembre2018())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor de la vigencia 2018 es menor que la suma de los pagos mensuales");
                context.addMessage(null, alert);
            }
            if (Pago.getAporte() > (Pago.getVigencia2015() + Pago.getVigencia2016() + Pago.getVigencia2017() + Pago.getVigencia2018())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor del aporte es mayor que la suma de los pagos en las vigencias");
                context.addMessage(null, alert);
            }
            if (Pago.getAporte() < (Pago.getVigencia2015() + Pago.getVigencia2016() + Pago.getVigencia2017() + Pago.getVigencia2018())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, Pago.getFuente() + " valor incorrecto", "El valor del aporte es menor que la suma de los pagos en las vigencias");
                context.addMessage(null, alert);
            }
            Double sumAportes = dao.getSumAportes(parentId);
            if (contratoMB.getContrato().getValor() < sumAportes) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_ERROR, "valor incorrecto", "El valor de la suma de los aportes es mayor que el valor del contrato");
                context.addMessage(null, alert);
            }

            if (contratoMB.getContrato().getValor() > sumAportes) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_ERROR, "valor incorrecto", "El valor de la suma de los aportes es menor que el valor del contrato");
                context.addMessage(null, alert);
            }


        } catch (Exception ex) {
            System.out.println("debug buttonGuardar catch");
            ex.printStackTrace();
        }
//        fillPagos();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        System.out.println("public String eliminar()");

        FacesContext context = FacesContext.getCurrentInstance();
        Pago pagoModel = (Pago) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "pago");

        // this.idSelecionado = pagoModel.getId();
        Pago = pagoModel;

        try {
            //  Pagos.remove(Pago.getId());
            dao.remove(Pago);
            // Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Pago eliminado exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("mainAccordion:formPago:dataPagos");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Pago pagoModel = (Pago) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "pago");
        FacesMessage msg = new FacesMessage("Edici�n cancelada", pagoModel
                .getId().toString());
        context.addMessage(null, msg);
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
            csvWriter.append("ID|Fuente|Aporte|" +
                    "Vigencia2015|" + "Vigencia2016|" + "Vigencia2017|" +
                    "Vigencia2018|" + "Vigencia2019|" + "Vigencia2020|" +
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
                    "Octubre2018|" + "Noviembre2018|" + "Diciembre2018|" +
                    "Enero2019|" + "Febrero2019|" + "Marzo2019|" +
                    "Abril2019|" + "Mayo2019|" + "Junio2019|" +
                    "Julio2019|" + "Agosto2019|" + "Septiembre2019|" +
                    "Octubre2019|" + "Noviembre2019|" + "Diciembre2019|" +
                    "Enero2020|" + "Febrero2020|" + "Marzo2020|" +
                    "Abril2020|" + "Mayo2020|" + "Junio2020|" +
                    "Julio2020|" + "Agosto2020|" + "Septiembre2020|" +
                    "Octubre2020|" + "Noviembre2020|" + "Diciembre2020" + "\n");

            for (Pago a : qryPagos) {
                csvWriter.append(
                        a.getParent().getId() + "|" + a.getFuente() + "|" + a.getAporte() + "|"
                                + a.getVigencia2015() + "|" + a.getVigencia2016() + "|" + a.getVigencia2017() + "|"
                                + a.getVigencia2018() + "|" + a.getVigencia2019() + "|" + a.getVigencia2020() + "|"
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
                                + a.getOctubre2018() + "|" + a.getNoviembre2018() + "|" + a.getDiciembre2018() + "|"
                                + a.getEnero2019() + "|" + a.getFebrero2019() + "|" + a.getMarzo2019() + "|"
                                + a.getAbril2019() + "|" + a.getMayo2019() + "|" + a.getJunio2019() + "|"
                                + a.getJulio2019() + "|" + a.getAgosto2019() + "|" + a.getSeptiembre2019() + "|"
                                + a.getOctubre2019() + "|" + a.getNoviembre2019() + "|" + a.getDiciembre2019() + "|"
                                + a.getEnero2020() + "|" + a.getFebrero2020() + "|" + a.getMarzo2020() + "|"
                                + a.getAbril2020() + "|" + a.getMayo2020() + "|" + a.getJunio2020() + "|"
                                + a.getJulio2020() + "|" + a.getAgosto2020() + "|" + a.getSeptiembre2020() + "|"
                                + a.getOctubre2020() + "|" + a.getNoviembre2020() + "|" + a.getDiciembre2020() + "\n");
            }

            csvWriter.flush();
            csvWriter.close();
            csvOut.close();
            context.responseComplete();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void duplicate(Key<Contrato> oldParent, Key<Contrato> newParent) {
        List<Pago> qryPagos = new ArrayList<Pago>(
                dao.getByParent(oldParent.getId()));
        System.out.println("duplicate " + qryPagos.size());

        for (Pago a : qryPagos) {
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Pago> key = f.allocateId(Pago.class);
            a.setId(key.getId());
            a.setParent(newParent);
            a.setIsVersion(Boolean.TRUE);
        }
        dao.bulkSave(qryPagos);

    }

}
