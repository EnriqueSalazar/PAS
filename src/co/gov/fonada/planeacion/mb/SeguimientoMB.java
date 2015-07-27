package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.SeguimientoDAO;
import co.gov.fonada.planeacion.dao.SeguimientoDAOObjectify;
import co.gov.fonada.planeacion.model.Contrato;
import co.gov.fonada.planeacion.model.Seguimiento;
import co.gov.fonada.planeacion.model.SeguimientoLazyList;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;
import static javax.faces.context.FacesContext.getCurrentInstance;

@ManagedBean(name = "seguimientoMB")
@ViewScoped
public class SeguimientoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private SeguimientoDAO dao;

    private Seguimiento Seguimiento;

    private Long idSelecionado;

    private LinkedHashMap<Long, Seguimiento> Seguimientos;

    private List<String> seguimientoList;
    private List<String> opcionList;
    private List<String> modalidadList;

    private SelectItem[] seguimientoOption;
    private SelectItem[] opcionOption; //Esto tambien es referencia
    private SelectItem[] modalidadOption;
    private List<Seguimiento> filterSeguimientos;
    private List<Seguimiento> seguimientosList;


    CSVExportOptions csvExportOptions;

    public SeguimientoMB() {
        dao = new SeguimientoDAOObjectify();
//		fillSeguimientos();
    }

    public void actualizar() {
        //fillSeguimientos();
    }
    private LazyDataModel<Seguimiento> lazySeguimientos = null;
    public LazyDataModel<Seguimiento> getAllLazySeguimientos() {
        System.out.println("SeguimientosFullMB  getLazySeguimientos ");
        if (lazySeguimientos == null) lazySeguimientos = new SeguimientoLazyList();
        System.out.println("SeguimientosFullMB  getLazySeguimientos ready to return "+lazySeguimientos.getPageSize());

        return lazySeguimientos;
    }
    private void fillSeguimientos() {
      /*  System.out.println("SeguimientoMB starting fillSeguimientos");
        try {
            // Changed to return decremental ordered by key list
            List<Seguimiento> qrySeguimientos = new ArrayList<Seguimiento>(dao.getAllOrdered());
            // if (qrySeguimientos.isEmpty()) {
            // System.out.println("SeguimientoMB qrySeguimientos is Empty");
            // loadSeguimientos();
            // fillSeguimientos();
            // } else {
            System.out.println("SeguimientoMB qrySeguimientos is " + qrySeguimientos.size());
            Seguimientos = new LinkedHashMap<Long, Seguimiento>();

            LinkedHashSet<String> seguimientoSet = new LinkedHashSet<String>();
            LinkedHashSet<String> opcionSet = new LinkedHashSet<String>();
            LinkedHashSet<String> modalidadSet = new LinkedHashSet<String>();
            for (Seguimiento a : qrySeguimientos) {
//				System.out.println("SeguimientoMB fillSeguimientos Seguimiento id " + a.getId());
                Seguimientos.put(a.getId(), a);
                if (a.getSeguimientos() != null)
                    seguimientoSet.add(a.getSeguimientos());
                if (a.getTCCpub() != null)
                    opcionSet.add(a.getTCCpub());
                if (a.getContratoSig() != null)
                    modalidadSet.add(a.getContratoSig());
            }

//			Iterator<Entry<Long, Seguimiento>> it = Seguimientos.entrySet().iterator();
//		    while (it.hasNext()) {
//		        Map.Entry<Long, Seguimiento> pair = (Map.Entry<Long, Seguimiento>)it.next();
//		        System.out.println("SeguimientoMB fillSeguimientos Seguimientos "+ pair.getKey() + " = " + pair.getValue().getId().toString());
//		    }

            seguimientoList = new ArrayList<String>(seguimientoSet);
            // System.out.println("SeguimientoMB seguimientoList is " +
            // seguimientoList.size()+seguimientoList);

            opcionList = new ArrayList<String>(opcionSet);
            // System.out.println("SeguimientoMB opcionList is " +
            // opcionList.size()+opcionList);

            modalidadList = new ArrayList<String>(modalidadSet);
            // System.out.println("SeguimientoMB modalidadList is " +
            // modalidadList.size()+modalidadList);


            seguimientoOption = new SelectItem[seguimientoList.size() + 1];
            seguimientoOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < seguimientoList.size(); i++) {
                seguimientoOption[i + 1] = new SelectItem(seguimientoList.get(i),
                        seguimientoList.get(i));
            }

            opcionOption = new SelectItem[opcionList.size() + 1];
            opcionOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < opcionList.size(); i++) {
                opcionOption[i + 1] = new SelectItem(opcionList.get(i),
                        opcionList.get(i));
            }

            modalidadOption = new SelectItem[modalidadList.size() + 1];
            modalidadOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < modalidadList.size(); i++) {
                modalidadOption[i + 1] = new SelectItem(modalidadList.get(i),
                        modalidadList.get(i));
            }

            // }

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }



    public SeguimientoDAO getDao() {
        return dao;
    }

    public void setDao(SeguimientoDAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public Seguimiento getSeguimiento() {
        return Seguimiento;
    }

    public void setSeguimiento(Seguimiento Seguimiento) {
        System.out.println("debug 3");

        this.Seguimiento = Seguimiento;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Seguimiento> getSeguimientos() {
        return Seguimientos;
    }

    public void setSeguimientos(Map<Long, Seguimiento> Seguimientos) {

        this.Seguimientos = (LinkedHashMap<Long, Seguimiento>) Seguimientos;
    }

    public DataModel<Seguimiento> getDataSeguimientos() {
        if (Seguimientos == null) fillSeguimientos();

        if (Seguimientos.values() != null) {
            return new ListDataModel<Seguimiento>(new ArrayList<Seguimiento>(Seguimientos.values()));

        } else {
            return new ListDataModel<Seguimiento>(new ArrayList<Seguimiento>());

        }
    }

    public void reset() {
        Seguimiento = null;
        idSelecionado = null;
    }

    public void agregar() {
        Seguimiento = new Seguimiento();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        Seguimiento = Seguimientos.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(Seguimiento.getId() + " public String guardar()");
        try {
            dao.save(Seguimiento);
            Thread.sleep(2000);
            Seguimientos.put(Seguimiento.getId(), Seguimiento);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.seguimiento"),
                    ex.getMessage());
            return "";
        }
        return "listaSeguimientos";
    }



    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        Seguimiento = (Seguimiento) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "seguimiento");
        try {
            dao.remove(Seguimiento);
            Seguimientos.remove(Seguimiento.getId());
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Seguimiento eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String eliminar() {
        try {
            dao.remove(Seguimiento);
            Seguimientos.remove(Seguimiento.getId());
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.eliminar.seguimiento"),
                    ex.getMessage());
            return "";
        }
        return "listaSeguimientos";
    }

    private String getMessageFromI18N(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages_labels",
                getCurrentInstance().getViewRoot().getLocale());
        return bundle.getString(key);
    }

    private void addMessage(String summary, String detail) {
        getCurrentInstance().addMessage(
                null,
                new FacesMessage(summary, summary.concat("<br/>")
                        .concat(detail)));
    }

    public void buttonCrear(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Seguimiento = new Seguimiento();
            // Seguimiento.setTCCpub("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Seguimiento> key = f.allocateId(Seguimiento.class);
            Seguimiento.setId(key.getId());
            dao.save(Seguimiento);
            // Thread.sleep(2000);
//            Seguimientos.put(Seguimiento.getId(), Seguimiento);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Seguimiento creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataSeguimientos");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillSeguimientos();

    }

    public void buttonReset(ActionEvent actionEvent) {

        out.println("SeguimientoMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllSeguimiento();

            Thread.sleep(2000);
            fillSeguimientos();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "Seguimientos reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }

    // public List<String> getSeguimientoList(String query) {
    // List<String> results = Arrays.asList(
    // "Radicación inicial estudios previos", "Reunión",
    // "Solicitud CDR", "Expedición CDR",
    // "Radicación a estructuración", "Sale de estructuración",
    // "Radicación a jurídica", "EP aprobados", "Publicación de TCC",
    // "Recepción observaciones", "Envío invitaciones", "Pliegos",
    // "Recepción observaciones", "Cierre", "Evaluación",
    // "Traslado y respuesta", "Apertura sobre económico",
    // "Evaluación económica", "Traslado y respuesta", "Adjudicación",
    // "Firma de seguimiento / otrosí", "Acta de inicio (PI contratado)");
    // return results;
    // }
    //
    // public List<String> getModalidadList(String query) {
    // List<String> results = Arrays.asList("Abierta", "Cerrada", "Directa",
    // "Convenio", "Convenio derivado", "Otrosí", "Gerencia",
    // "Adición");
    // return results;
    // }
    //
    // public List<String> getReferenciaList(String query) {
    // out.println("Dentro de getReferenciaList");
    //
    // List<String> results = Arrays.asList("Firma del seguimiento",
    // "Radicación estudios previos");
    // return results;
    // }
    //
    // public List<String> referenciaList(String query) {
    // out.println("Dentro de getReferenciaList");
    //
    // List<String> results = Arrays.asList("Firma del seguimiento",
    // "Radicación estudios previos");
    // return results;
    // }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Seguimiento seguimientoModel = (Seguimiento) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "seguimiento");
        FacesMessage msg = new FacesMessage("Edición cancelada", seguimientoModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Seguimiento ID seleccionado",
                ((Seguimiento) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("SeguimientoMB onRowSelect " + msg);

    }

    /**
     * @return the seguimientoList
     */
    public List<String> getSeguimientoList() {
        if (Seguimientos == null) fillSeguimientos();
        System.out.println("SeguimientoMB getSeguimientoList " + seguimientoList.size());
        return seguimientoList;
    }

    /**
     * @param seguimientoList the seguimientoList to set
     */
    public void setSeguimientoList(List<String> seguimientoList) {
        this.seguimientoList = seguimientoList;
    }

  /*  *//**
     * @return the opcionList
     *//*
    public List<String> getOpcionList() {
        if (this.opcionList == null) this.opcionList= dao.getOpcionLista();

        return this.opcionList;
    }*/

    /**
     * @param opcionList the opcionList to set
     */
    public void setOpcionList(List<String> opcionList) {
        this.opcionList = opcionList;
    }

  /*  *//**
     * @return the modalidadList
     *//*
    public List<String> getModalidadList() {
            if (this.modalidadList == null) this.modalidadList= dao.getModalidadLista();

            return this.modalidadList;
      
    }*/

    /**
     * @param modalidadList the modalidadList to set
     */
    public void setModalidadList(List<String> modalidadList) {
        this.modalidadList = modalidadList;
    }

   /* *//**
     * @return the seguimientoOption
     *//*
    public SelectItem[] getSeguimientoOption() {
        System.out.println("getSeguimientoOption");
        if (this.seguimientoOption == null) this.seguimientoOption=dao.getSeguimientoOption();
        return this.seguimientoOption;
    }
*/
    /**
     * @param seguimientoOption the seguimientoOption to set
     */
    public void setSeguimientoOption(SelectItem[] seguimientoOption) {
        this.seguimientoOption = seguimientoOption;
    }

  /*  *//**
     * @return the opcionOption
     *//*
    public SelectItem[] getOpcionOption() {
        System.out.println("getOpcionOption");
        if (this.opcionOption == null) this.opcionOption=dao.getOpcionOption();
        return this.opcionOption;
    }*/

    /**
     * @param opcionOption the opcionOption to set
     */
    public void setOpcionOption(SelectItem[] opcionOption) {
        this.opcionOption = opcionOption;
    }

  /*  *//**
     * @return the modalidadOption
     *//*
    public SelectItem[] getModalidadOption() {
        System.out.println("getModalidadOption");
        if (this.modalidadOption == null) this.modalidadOption=dao.getModalidadOption();
        return this.modalidadOption;
    }
*/
    /**
     * @param modalidadOption the modalidadOption to set
     */
    public void setModalidadOption(SelectItem[] modalidadOption) {
        this.modalidadOption = modalidadOption;
    }

    /**
     * @return the filterSeguimientos
     */
    public List<Seguimiento> getFilterSeguimientos(String opc, String mod) {
        filterSeguimientos = new ArrayList<Seguimiento>(dao.getSome(opc, mod));
        return filterSeguimientos;
    }

    /**
     * @param filterSeguimientos the filterSeguimientos to set
     */
    public void setFilterSeguimientos(List<Seguimiento> filterSeguimientos) {
        this.filterSeguimientos = filterSeguimientos;
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


    
    
}
