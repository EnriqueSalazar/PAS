package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.HitoDAO;
import co.gov.fonada.planeacion.dao.HitoDAOObjectify;
import co.gov.fonada.planeacion.model.*;
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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;
import static javax.faces.context.FacesContext.getCurrentInstance;

@ManagedBean(name = "hitoMB")
@ViewScoped
public class HitoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private HitoDAO dao;

    private Hito Hito;

    private Long idSelecionado;

    private LinkedHashMap<Long, Hito> Hitos;

    private List<String> hitoList;
    private List<String> opcionList;
    private List<String> modalidadList;

    private SelectItem[] hitoOption;
    private SelectItem[] opcionOption; //Esto tambien es referencia
    private SelectItem[] modalidadOption;
    private List<Hito> filterHitos;
    private List<Hito> hitosList;


    CSVExportOptions csvExportOptions;

    public HitoMB() {
        dao = new HitoDAOObjectify();
//		fillHitos();
    }

    public void actualizar() {
        //fillHitos();
    }

    private LazyDataModel<Hito> lazyHitos = null;

    public LazyDataModel<Hito> getAllLazyHitos() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = context.getExternalContext()
                .getRequestParameterMap();
        String activeURL = requestMap.get("id");
        Long seguimientoId = 0L;
        try {
            seguimientoId = Long.parseLong(activeURL);

        } catch (Exception e) {
            ExternalContext ec = context.getExternalContext();
            System.out.println("hmmm... seguimientoId es null o incorrecto");
            try {
                ec.redirect("/");
            } catch (IOException ioe) {
                ioe.printStackTrace();

            }
        }
        System.out.println("HitosFullMB  getLazyHitos " + activeURL);
        if (lazyHitos == null) lazyHitos = new HitoLazyList(seguimientoId);
        System.out.println("HitosFullMB  getLazyHitos ready to return " + lazyHitos.getPageSize());

        return lazyHitos;
    }

    private LazyDataModel<Hito> lazyCheckHitos = null;

    public LazyDataModel<Hito> getAllLazyCheckHitos() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = context.getExternalContext()
                .getRequestParameterMap();
        String activeURL = requestMap.get("id");
        Long seguimientoId = 0L;
        try {
            seguimientoId = Long.parseLong(activeURL);

        } catch (Exception e) {
            ExternalContext ec = context.getExternalContext();
            System.out.println("hmmm... seguimientoId es null o incorrecto");
            try {
                ec.redirect("/");
            } catch (IOException ioe) {
                ioe.printStackTrace();

            }
        }
        System.out.println("HitosFullMB  getLazyHitos " + activeURL);
        if (lazyCheckHitos == null) lazyCheckHitos = new CheckHitoLazyList(seguimientoId);
        System.out.println("HitosFullMB  getLazyHitos ready to return " + lazyCheckHitos.getPageSize());

        return lazyCheckHitos;
    }


    private void fillHitos() {
      /*  System.out.println("HitoMB starting fillHitos");
        try {
            // Changed to return decremental ordered by key list
            List<Hito> qryHitos = new ArrayList<Hito>(dao.getAllOrdered());
            // if (qryHitos.isEmpty()) {
            // System.out.println("HitoMB qryHitos is Empty");
            // loadHitos();
            // fillHitos();
            // } else {
            System.out.println("HitoMB qryHitos is " + qryHitos.size());
            Hitos = new LinkedHashMap<Long, Hito>();

            LinkedHashSet<String> hitoSet = new LinkedHashSet<String>();
            LinkedHashSet<String> opcionSet = new LinkedHashSet<String>();
            LinkedHashSet<String> modalidadSet = new LinkedHashSet<String>();
            for (Hito a : qryHitos) {
//				System.out.println("HitoMB fillHitos Hito id " + a.getId());
                Hitos.put(a.getId(), a);
                if (a.getHitos() != null)
                    hitoSet.add(a.getHitos());
                if (a.getTCCpub() != null)
                    opcionSet.add(a.getTCCpub());
                if (a.getContratoSig() != null)
                    modalidadSet.add(a.getContratoSig());
            }

//			Iterator<Entry<Long, Hito>> it = Hitos.entrySet().iterator();
//		    while (it.hasNext()) {
//		        Map.Entry<Long, Hito> pair = (Map.Entry<Long, Hito>)it.next();
//		        System.out.println("HitoMB fillHitos Hitos "+ pair.getKey() + " = " + pair.getValue().getId().toString());
//		    }

            hitoList = new ArrayList<String>(hitoSet);
            // System.out.println("HitoMB hitoList is " +
            // hitoList.size()+hitoList);

            opcionList = new ArrayList<String>(opcionSet);
            // System.out.println("HitoMB opcionList is " +
            // opcionList.size()+opcionList);

            modalidadList = new ArrayList<String>(modalidadSet);
            // System.out.println("HitoMB modalidadList is " +
            // modalidadList.size()+modalidadList);


            hitoOption = new SelectItem[hitoList.size() + 1];
            hitoOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < hitoList.size(); i++) {
                hitoOption[i + 1] = new SelectItem(hitoList.get(i),
                        hitoList.get(i));
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


    public HitoDAO getDao() {
        return dao;
    }

    public void setDao(HitoDAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public Hito getHito() {
        return Hito;
    }

    public void setHito(Hito Hito) {
        System.out.println("debug 3");

        this.Hito = Hito;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Hito> getHitos() {
        return Hitos;
    }

    public void setHitos(Map<Long, Hito> Hitos) {

        this.Hitos = (LinkedHashMap<Long, Hito>) Hitos;
    }

    public DataModel<Hito> getDataHitos() {
        if (Hitos == null) fillHitos();

        if (Hitos.values() != null) {
            return new ListDataModel<Hito>(new ArrayList<Hito>(Hitos.values()));

        } else {
            return new ListDataModel<Hito>(new ArrayList<Hito>());

        }
    }

    public void reset() {
        Hito = null;
        idSelecionado = null;
    }

    public void agregar() {
        Hito = new Hito();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        Hito = Hitos.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(Hito.getId() + " public String guardar()");
        try {
            dao.save(Hito);
            Thread.sleep(2000);
            Hitos.put(Hito.getId(), Hito);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.hito"),
                    ex.getMessage());
            return "";
        }
        return "listaHitos";
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("HitoMB buttonGuardar");
        FacesContext context = FacesContext.getCurrentInstance();
        Hito hitoModel = (Hito) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "hito");
//        this.idSelecionado = hitoModel.getId();
        this.Hito = hitoModel;
        try {
//            System.out.println("HitoMB buttonGuardar try idSelecionado "
//                    + idSelecionado);
            System.out.println("HitoMB buttonGuardar try " + Hito.getTCCpub()
                    + Hito.getContratoSig());
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Seguimiento> key = Key.create(Seguimiento.class, Hito.getParentId());
//            Hito.setId(key.g  etId());
            Hito.setParent(key);
            dao.save(Hito);
            // Thread.sleep(2000);
//            Hitos.put(Hito.getId(), Hito);

            context.addMessage(null, new FacesMessage("Guardado!",
                    "Hito almacenado exitosamente."));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // fillHitos();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        Hito = (Hito) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "hito");
        try {
            dao.remove(Hito);
            Hitos.remove(Hito.getId());
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Hito eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String eliminar() {
        try {
            dao.remove(Hito);
            Hitos.remove(Hito.getId());
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.eliminar.hito"),
                    ex.getMessage());
            return "";
        }
        return "listaHitos";
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
            Hito = new Hito();
            // Hito.setTCCpub("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Hito> key = f.allocateId(Hito.class);
            Hito.setId(key.getId());
            dao.save(Hito);
            // Thread.sleep(2000);
//            Hitos.put(Hito.getId(), Hito);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Hito creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataHitos");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillHitos();

    }

    public void buttonReset(ActionEvent actionEvent) {

        out.println("HitoMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllHito();

            Thread.sleep(2000);
            fillHitos();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "Hitos reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }

    // public List<String> getHitoList(String query) {
    // List<String> results = Arrays.asList(
    // "Radicación inicial estudios previos", "Reunión",
    // "Solicitud CDR", "Expedición CDR",
    // "Radicación a estructuración", "Sale de estructuración",
    // "Radicación a jurídica", "EP aprobados", "Publicación de TCC",
    // "Recepción observaciones", "Envío invitaciones", "Pliegos",
    // "Recepción observaciones", "Cierre", "Evaluación",
    // "Traslado y respuesta", "Apertura sobre económico",
    // "Evaluación económica", "Traslado y respuesta", "Adjudicación",
    // "Firma de hito / otrosí", "Acta de inicio (PI contratado)");
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
    // List<String> results = Arrays.asList("Firma del hito",
    // "Radicación estudios previos");
    // return results;
    // }
    //
    // public List<String> referenciaList(String query) {
    // out.println("Dentro de getReferenciaList");
    //
    // List<String> results = Arrays.asList("Firma del hito",
    // "Radicación estudios previos");
    // return results;
    // }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Hito hitoModel = (Hito) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "hito");
        FacesMessage msg = new FacesMessage("Edición cancelada", hitoModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Hito ID seleccionado",
                ((Hito) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("HitoMB onRowSelect " + msg);

    }

    /**
     * @return the hitoList
     */
    public List<String> getHitoList() {
        if (Hitos == null) fillHitos();
        System.out.println("HitoMB getHitoList " + hitoList.size());
        return hitoList;
    }

    /**
     * @param hitoList the hitoList to set
     */
    public void setHitoList(List<String> hitoList) {
        this.hitoList = hitoList;
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
     * @return the hitoOption
     *//*
    public SelectItem[] getHitoOption() {
        System.out.println("getHitoOption");
        if (this.hitoOption == null) this.hitoOption=dao.getHitoOption();
        return this.hitoOption;
    }
*/

    /**
     * @param hitoOption the hitoOption to set
     */
    public void setHitoOption(SelectItem[] hitoOption) {
        this.hitoOption = hitoOption;
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
     * @return the filterHitos
     */
    public List<Hito> getFilterHitos(String opc, String mod) {
        filterHitos = new ArrayList<Hito>(dao.getSome(opc, mod));
        return filterHitos;
    }

    /**
     * @param filterHitos the filterHitos to set
     */
    public void setFilterHitos(List<Hito> filterHitos) {
        this.filterHitos = filterHitos;
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
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=hitos.csv");
        externalContext.setResponseContentType("text/csv");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            OutputStream csvOut = externalContext.getResponseOutputStream();
            BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(csvOut, StandardCharsets.ISO_8859_1));
            /*Nunca puede ir ID al inicio de un CSV*/
            csvWriter.append("Hito|Contrato|Publicación de TCC|Firma del contrato\n");
            if (hitosList == null) hitosList = new ArrayList<Hito>(dao.getAll());
            for (Hito entry : hitosList) {
                csvWriter.append(entry.getId() + "|" + entry.getParent().getId() + "|" + entry.getTCCpub() + "|" + entry.getContratoSig() + "\n");
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
