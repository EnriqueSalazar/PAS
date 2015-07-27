package co.gov.fonada.planeacion.mb;

import static java.lang.System.out;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;


import co.gov.fonada.planeacion.dao.AccionDAO;
import co.gov.fonada.planeacion.model.ContratoLazyList;
import org.primefaces.component.selectonebutton.SelectOneButton;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineModel;
import org.primefaces.event.SelectEvent;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;

import co.gov.fonada.planeacion.dao.*;
import co.gov.fonada.planeacion.model.Contrato;
import co.gov.fonada.planeacion.model.Etapa;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "contratoMB")
@ViewScoped
public class ContratoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273226L;


    private ContratoDAO dao;


    private Contrato contrato;

    private Long idSelecionado;

    private Map<Long, Contrato> contratos;
    private Map<Long, Contrato> contratosVersion;
    private List<Contrato> contratosList;

    private TimelineModel timelineModel;
    private Date fechaRefTemp;
    private String modalidadTemp;
    private String opcionTemp;
    private String cCambio;

    public ContratoMB() {
        out.println("");
        out.println("contratoMB");
        try {
            dao = new ContratoDAOObjectify();

            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> requestMap = context.getExternalContext()
                    .getRequestParameterMap();
            String parentString = requestMap.get("id");
            if (parentString != null) {
                Long parentLong = Long.parseLong(parentString);
                this.idSelecionado = parentLong;
                contrato = dao.findById(this.idSelecionado);
                out.println("contratoMB Constructor contratoMB idseleccionado "
                        + idSelecionado);
                fechaRefTemp = contrato.getFechaRef();
                modalidadTemp = contrato.getModalidad();
                opcionTemp = contrato.getReferencia();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar() {
        out.println("contratoMB actualizar");
        fillcontratos();
    }


    public void emergencia() {
        out.println("contratoMB emergencia");
        dao.deleteAllContrato();
        out.println("contratoMB deleteAllcontrato");

        AccionDAO aDAO = new AccionDAOObjectify();
        PagoDAO pDAO = new PagoDAOObjectify();
        RegionalizacionDAO rDAO = new RegionalizacionDAOObjectify();
        aDAO.deleteAllAccion();
        out.println("contratoMB deleteAllAccion");

        pDAO.deleteAllPago();
        out.println("contratoMB deleteAllPago");

        rDAO.deleteAllRegionalizacion();
        out.println("contratoMB deleteAllRegionalizacion");
    }

    private void fillcontratos() {
        try {
            if (contratos == null) {
                List<Contrato> qrycontratos = new ArrayList<>(
                        dao.getAll());
                contratos = new HashMap<>();
                for (Contrato a : qrycontratos) {
                    contratos.put(a.getId(), a);
                }
                out.println("contratoMB fillcontratos new");
                out.println("contratoMB fillcontratos [" + contratos.size()
                        + "]");
            } else {
                out.println("contratoMB fillcontratos reusing");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void CSVExport() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=contratos.csv");
        externalContext.setResponseContentType("text/csv");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            OutputStream csvOut = externalContext.getResponseOutputStream();
            BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(csvOut, StandardCharsets.ISO_8859_1));
            /*Nunca puede ir ID al inicio de un CSV*/
            csvWriter.append("Contrato|Sector|Postulacion|Codigocontrato|Duracion|Valor|Modalidad|Referencia|FechaRef|Descripcion\n");
            if (contratosList == null) contratosList = new ArrayList<Contrato>(dao.getAll());
            for (Contrato entry : contratosList) {
                csvWriter.append(entry.getId() + "|" + entry.getSector() + "|" + entry.getPostulacion() + "|" + entry.getCodigoContrato() + "|" + entry.getDuracion() + "|" +entry.getValor() + "|" + entry.getModalidad() + "|" + entry.getReferencia() + "|" + df.format(entry.getFechaRef()) + "|" + entry.getDescripcion() + "\n");
            }
            csvWriter.flush();
            csvWriter.close();
            csvOut.close();
            context.responseComplete();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void simulador() {

        out.println("contratoMB simulador ");

        FacesContext context = FacesContext.getCurrentInstance();

        UIViewRoot viewRoot = context.getViewRoot();
        UIInput modalidadComponent = (UIInput) viewRoot.findComponent("mainAccordion:form:modalidadButton");
        UIInput referenciaComponent = (UIInput) viewRoot.findComponent("mainAccordion:form:referenciaButton");
        UIInput calendarComponent = (UIInput) viewRoot.findComponent("mainAccordion:form:calendar");

        out.println("contratoMB simulador " + modalidadComponent.getValue().toString());
        modalidadTemp = (String) modalidadComponent.getValue();
        out.println("contratoMB simulador " + referenciaComponent.getValue().toString());
        opcionTemp = (String) referenciaComponent.getValue();
        out.println("contratoMB simulador " + calendarComponent.getValue().toString());
        fechaRefTemp = (Date) calendarComponent.getValue();

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("mainAccordion:timeline");
        context.getPartialViewContext().getRenderIds().add("mainAccordion:timeline");
        try {
            viewRoot.findComponent("mainAccordion:timeline").encodeAll(context);

        } catch (IOException IOE) {

        }

    }

    public void selectOpcion(AjaxBehaviorEvent event) {
        out.println("contratoMB selectOpcion ");

        SelectOneButton temp = (SelectOneButton) event.getSource();

        opcionTemp = (String) temp.getValue();

        out.println("contratoMB selectOpcion " + opcionTemp);
        //fillCalendar();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getPartialViewContext().getRenderIds()
                .add("mainAccordion:timeline");
    }

    public void selectModalidad(AjaxBehaviorEvent event) {
        out.println("contratoMB selectModalidad ");

        SelectOneButton temp = (SelectOneButton) event.getSource();

        modalidadTemp = (String) temp.getValue();

        out.println("contratoMB selectModalidad AjaxBehaviorEvent " + modalidadTemp);
        //fillCalendar();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getPartialViewContext().getRenderIds()
                .add("mainAccordion:timeline");
    }

    public void selectDate(SelectEvent event) {
        fechaRefTemp = (Date) event.getObject();

        out.println("contratoMB selectDate " + fechaRefTemp.toString());
        //fillCalendar();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getPartialViewContext().getRenderIds()
                .add("mainAccordion:timeline");

    }

/*    public void selectModalidad(ValueChangeEvent event) {
        SelectOneButton temp = (SelectOneButton) event.getSource();

        modalidadTemp = (String) temp.getValue();

        out.println("contratoMB selectModalidad " + modalidadTemp);
        //fillCalendar();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getPartialViewContext().getRenderIds()
                .add("mainAccordion:timeline");
    }*/


    public ContratoDAO getDao() {
        out.println("contratoMB getDao");

        return dao;
    }

    public void setDao(ContratoDAO dao) {
        out.println("contratoMB setDao");

        this.dao = dao;
    }

    public Contrato getContrato() {
//        out.println("contratoMB getcontrato start");

        return contrato;
    }

    public void setcontrato(Contrato contrato) {
        out.println("contratoMB setcontrato" + contrato.getCodigoContrato());

        this.contrato = contrato;
    }

    public Long getIdSelecionado() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = context.getExternalContext()
                .getRequestParameterMap();
        String activeURL = requestMap.get("active");
        if (activeURL != null) if (activeURL.equalsIgnoreCase("pagos")) return 0L;
        if (contrato == null) {
            out.println("contratoMB getcontrato contrato == null");

            ExternalContext ec = context.getExternalContext();
            System.out.println("hmmm... contrato es null");

            try {
                ec.redirect("/");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("hmmm... contrato no es null");
            System.out.println("contratoMB getIdSelecionado --> "
                    + idSelecionado);

        }
        out.println("contratoMB getcontrato end");
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        System.out.println("contratoMB setIdSelecionado --> " + idSelecionado);
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Contrato> getcontratos() {
        System.out.println("contratoMB getcontratos");

        return contratos;
    }

    public void setContratos(Map<Long, Contrato> contratos) {
        System.out.println("contratoMB setcontratos");

        this.contratos = contratos;
    }

    public DataModel<Contrato> getDatacontratos() {
        fillcontratos();
        System.out.println("contratoMB getDatacontratos");
        return new ListDataModel<Contrato>(new ArrayList<Contrato>(
                contratos.values()));
    }


    private void fillcontratosVersion() {
        try {
            if (this.idSelecionado != null && contratosVersion == null) {
                List<Contrato> qrycontratos = new ArrayList<Contrato>(
                        dao.getAllVersionsOf(idSelecionado));
                contratosVersion = new HashMap<Long, Contrato>();
                for (Contrato a : qrycontratos) {
                    contratosVersion.put(a.getId(), a);
                }
                out.println("contratoMB fillcontratosVersion new");
                out.println("contratoMB fillcontratosVersion [" + contratosVersion.size()
                        + "]");
            } else {
                out.println("contratoMB fillcontratos reusing");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public DataModel<Contrato> getDataContratosVersion() {
      /*  FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = context.getExternalContext()
                .getRequestParameterMap();
        String idParam = requestMap.get("id");
        System.out.println("contratoMB idParam " + idParam);*/
        fillcontratosVersion();
        return new ListDataModel<Contrato>(new ArrayList<Contrato>(
                contratosVersion.values()));
    }

    public void reset() {
        System.out.println("contratoMB reset");
    }

    public void agregar() {
        System.out.println("contratoMB agregar");

        contrato = new Contrato();
        ObjectifyFactory f = new ObjectifyFactory();
        Key<Contrato> key = f.allocateId(Contrato.class);
        contrato.setId(key.getId());
    }

    public void editar() {
        System.out.println("contratoMB editar");

        if (idSelecionado == null) {
            return;
        }
        contrato = contratos.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("contratoMB guardar");

        try {
//			out.println("contratoMB guardar Sector " + contrato.getSector());
//			out.println("contratoMB guardar Id " + contrato.getId());
            dao.save(contrato);
            contratos.put(contrato.getId(), contrato);

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
//		out.println("contratoMB contrato guardado con id  " + contrato.getId());
        return "listacontrato";
    }

    public void buttonGuardar(ActionEvent actionEvent) {
        System.out.println("contratoMB buttonGuardar");
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            out.println("contratoMB buttonGuardar Sector "
                    + contrato.getSector());
            out.println("contratoMB buttonGuardar Id " + contrato.getId());
            dao.save(contrato);
//            contratos.put(contrato.getId(), contrato);
            context.addMessage(null, new FacesMessage("Guardado!",
                    "contrato almacenado exitosamente."));
//            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            context.getPartialViewContext().getRenderIds()
                    .add("mainAccordion:formAccion:dataAccions");
        } catch (Exception ex) {
            out.println("contratoMB mainAccordion:formAccion:dataAccions falló");

        }
        out.println("contratoMB contrato guardado con id  " + contrato.getId());
    }

    public String eliminar() {
        System.out.println("contratoMB eliminar");
        try {
            dao.remove(contrato);
            contratos.remove(contrato.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        out.println("contratoMB contrato " + contrato.getId() + " eliminado");
        return "listacontrato?active=lista";
    }


    public void onRowSelect(SelectEvent event, String theid) {
        out.println("contratoMB onRowSelect");
        out.println("contratoMB contrato.getId " + theid);
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("editarcontrato.jsf?id=" + contrato.getId());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the timelineModel
     */
    public TimelineModel getTimelineModel() {
        timelineModel = new TimelineModel();
        EtapaMB etapaMB = new EtapaMB();

        List<Etapa> qryEtapas = etapaMB.getFilterEtapas(opcionTemp,
                modalidadTemp);
        out.println("contratoMB getTimelineModel qryEtapas " + qryEtapas.size());
//                Map<String, Calendar> schedule = new HashMap<String, Calendar>();
        for (Etapa a : qryEtapas) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaRefTemp);
            cal.add(Calendar.DATE, a.getDuracion());
//                    schedule.put(a.getEtapas(), cal);
            TimelineEvent timelineEvent = new TimelineEvent(a.getEtapas(), cal
                    .getTime());
            timelineModel.add(timelineEvent);

        }

        return timelineModel;
    }

    /**
     * @param timelineModel the timelineModel to set
     */
    public void setTimelineModel(TimelineModel timelineModel) {
        this.timelineModel = timelineModel;
    }

    public void createVersion() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(cCambio,
                "Version de " + contrato.getId() + " creado."));
        ObjectifyFactory f = new ObjectifyFactory();

        Key<Contrato> key = f.allocateId(Contrato.class);
        contrato.setVersionOf(contrato.getId());
        contrato.setIsVersion(Boolean.TRUE);
        contrato.setId(key.getId());
        contrato.setControlCambio(cCambio);
        contrato.setTimeStamp(new Date());
        dao.save(contrato);

        AccionMB accionMB = new AccionMB();
        accionMB.duplicate(Key.create(Contrato.class, contrato.getVersionOf()), Key.create(Contrato.class, contrato.getId()));

        PagoMB pagoMB = new PagoMB();
        pagoMB.duplicate(Key.create(Contrato.class, contrato.getVersionOf()), Key.create(Contrato.class, contrato.getId()));

        RegionalizacionMB regionalizacionMB = new RegionalizacionMB();
        regionalizacionMB.duplicate(Key.create(Contrato.class, contrato.getVersionOf()), Key.create(Contrato.class, contrato.getId()));

        ExternalContext ec = context.getExternalContext();
        try {
            ec.redirect("listaVersionescontrato.jsf?id=" + contrato.getVersionOf());

        } catch (IOException ioe) {
        }
    }

    public String getcCambio() {
        return cCambio;
    }

    public void setcCambio(String cCambio) {
        this.cCambio = cCambio;
    }

    private LazyDataModel<Contrato> lazyContratos = null;

    public LazyDataModel<Contrato> getAllLazyContratos() {
        System.out.println("contratosFullMB  getlazyContratos ");
        if (lazyContratos == null) lazyContratos = new ContratoLazyList();
        System.out.println("contratosFullMB  getlazyContratos ready to return " + lazyContratos.getPageSize());
        return lazyContratos;
    }
}
