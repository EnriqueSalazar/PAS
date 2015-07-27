package co.gov.fonada.planeacion.mb;

import static java.lang.System.out;
import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import co.gov.fonada.planeacion.model.EtapaLazyList;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;

import co.gov.fonada.planeacion.dao.EtapaDAO;
import co.gov.fonada.planeacion.dao.EtapaDAOObjectify;
import co.gov.fonada.planeacion.model.Etapa;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "etapaMB")
@ViewScoped
public class EtapaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private EtapaDAO dao;

    private Etapa Etapa;

    private Long idSelecionado;

    private LinkedHashMap<Long, Etapa> Etapas;

    private List<String> etapaList;
    private List<String> opcionList;
    private List<String> modalidadList;

    private SelectItem[] etapaOption;
    private SelectItem[] opcionOption; //Esto tambien es referencia
    private SelectItem[] modalidadOption;
    private List<Etapa> filterEtapas;


    CSVExportOptions csvExportOptions;

    public EtapaMB() {
        dao = new EtapaDAOObjectify();
//		fillEtapas();
    }

    public void actualizar() {
        //fillEtapas();
    }
    private LazyDataModel<Etapa> lazyEtapas = null;
    public LazyDataModel<Etapa> getAllLazyEtapas() {
        System.out.println("EtapasFullMB  getLazyEtapas ");
        if (lazyEtapas == null) lazyEtapas = new EtapaLazyList();
        System.out.println("EtapasFullMB  getLazyEtapas ready to return "+lazyEtapas.getPageSize());

        return lazyEtapas;
    }
    private void fillEtapas() {
        System.out.println("EtapaMB starting fillEtapas");
        try {
            // Changed to return decremental ordered by key list
            List<Etapa> qryEtapas = new ArrayList<Etapa>(dao.getAllOrdered());
            // if (qryEtapas.isEmpty()) {
            // System.out.println("EtapaMB qryEtapas is Empty");
            // loadEtapas();
            // fillEtapas();
            // } else {
            System.out.println("EtapaMB qryEtapas is " + qryEtapas.size());
            Etapas = new LinkedHashMap<Long, Etapa>();

            LinkedHashSet<String> etapaSet = new LinkedHashSet<String>();
            LinkedHashSet<String> opcionSet = new LinkedHashSet<String>();
            LinkedHashSet<String> modalidadSet = new LinkedHashSet<String>();
            for (Etapa a : qryEtapas) {
//				System.out.println("EtapaMB fillEtapas Etapa id " + a.getId());
                Etapas.put(a.getId(), a);
                if (a.getEtapas() != null)
                    etapaSet.add(a.getEtapas());
                if (a.getOpcion() != null)
                    opcionSet.add(a.getOpcion());
                if (a.getModalidad() != null)
                    modalidadSet.add(a.getModalidad());
            }

//			Iterator<Entry<Long, Etapa>> it = Etapas.entrySet().iterator();
//		    while (it.hasNext()) {
//		        Map.Entry<Long, Etapa> pair = (Map.Entry<Long, Etapa>)it.next();
//		        System.out.println("EtapaMB fillEtapas Etapas "+ pair.getKey() + " = " + pair.getValue().getId().toString());
//		    }

            etapaList = new ArrayList<String>(etapaSet);
            // System.out.println("EtapaMB etapaList is " +
            // etapaList.size()+etapaList);

            opcionList = new ArrayList<String>(opcionSet);
            // System.out.println("EtapaMB opcionList is " +
            // opcionList.size()+opcionList);

            modalidadList = new ArrayList<String>(modalidadSet);
            // System.out.println("EtapaMB modalidadList is " +
            // modalidadList.size()+modalidadList);


            etapaOption = new SelectItem[etapaList.size() + 1];
            etapaOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < etapaList.size(); i++) {
                etapaOption[i + 1] = new SelectItem(etapaList.get(i),
                        etapaList.get(i));
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
        }
    }

    public boolean isOpcion(String search) {
        if (Etapas == null) fillEtapas();

        for (String a : opcionList) {
            if (a.equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }

    public boolean isModalidad(String search) {
        if (Etapas == null) fillEtapas();
        for (String a : modalidadList) {
            if (a.equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }


    public EtapaDAO getDao() {
        return dao;
    }

    public void setDao(EtapaDAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public Etapa getEtapa() {
        return Etapa;
    }

    public void setEtapa(Etapa Etapa) {
        System.out.println("debug 3");

        this.Etapa = Etapa;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Etapa> getEtapas() {
        return Etapas;
    }

    public void setEtapas(Map<Long, Etapa> Etapas) {

        this.Etapas = (LinkedHashMap<Long, Etapa>) Etapas;
    }

    public DataModel<Etapa> getDataEtapas() {
        if (Etapas == null) fillEtapas();

        if (Etapas.values() != null) {
            return new ListDataModel<Etapa>(new ArrayList<Etapa>(Etapas.values()));

        } else {
            return new ListDataModel<Etapa>(new ArrayList<Etapa>());

        }
    }

    public void reset() {
        Etapa = null;
        idSelecionado = null;
    }

    public void agregar() {
        Etapa = new Etapa();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        Etapa = Etapas.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(Etapa.getId() + " public String guardar()");
        try {
            dao.save(Etapa);
            Thread.sleep(2000);
            Etapas.put(Etapa.getId(), Etapa);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.etapa"),
                    ex.getMessage());
            return "";
        }
        return "listaEtapas";
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("EtapaMB buttonGuardar");
        FacesContext context = FacesContext.getCurrentInstance();
        Etapa etapaModel = (Etapa) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "etapa");
//        this.idSelecionado = etapaModel.getId();
        this.Etapa = etapaModel;
        try {
//            System.out.println("EtapaMB buttonGuardar try idSelecionado "
//                    + idSelecionado);
            System.out.println("EtapaMB buttonGuardar try " + Etapa.getOpcion()
                    + Etapa.getModalidad());
            dao.save(Etapa);
            // Thread.sleep(2000);
//            Etapas.put(Etapa.getId(), Etapa);

            context.addMessage(null, new FacesMessage("Guardado!",
                    "Etapa almacenado exitosamente."));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // fillEtapas();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        Etapa = (Etapa) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "etapa");
        try {
            dao.remove(Etapa);
            Etapas.remove(Etapa.getId());
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Etapa eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String eliminar() {
        try {
            dao.remove(Etapa);
            Etapas.remove(Etapa.getId());
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.eliminar.etapa"),
                    ex.getMessage());
            return "";
        }
        return "listaEtapas";
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
            Etapa = new Etapa();
            // Etapa.setOpcion("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Etapa> key = f.allocateId(Etapa.class);
            Etapa.setId(key.getId());
            dao.save(Etapa);
            // Thread.sleep(2000);
            Etapas.put(Etapa.getId(), Etapa);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Etapa creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataEtapas");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillEtapas();

    }

    public void buttonReset(ActionEvent actionEvent) {

        out.println("EtapaMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllEtapa();

            Thread.sleep(2000);
            fillEtapas();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "Etapas reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }

    // public List<String> getEtapaList(String query) {
    // List<String> results = Arrays.asList(
    // "Radicación inicial estudios previos", "Reunión",
    // "Solicitud CDR", "Expedición CDR",
    // "Radicación a estructuración", "Sale de estructuración",
    // "Radicación a jurídica", "EP aprobados", "Publicación de TCC",
    // "Recepción observaciones", "Envío invitaciones", "Pliegos",
    // "Recepción observaciones", "Cierre", "Evaluación",
    // "Traslado y respuesta", "Apertura sobre económico",
    // "Evaluación económica", "Traslado y respuesta", "Adjudicación",
    // "Firma de contrato / otrosí", "Acta de inicio (PI contratado)");
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
    // List<String> results = Arrays.asList("Firma del contrato",
    // "Radicación estudios previos");
    // return results;
    // }
    //
    // public List<String> referenciaList(String query) {
    // out.println("Dentro de getReferenciaList");
    //
    // List<String> results = Arrays.asList("Firma del contrato",
    // "Radicación estudios previos");
    // return results;
    // }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Etapa etapaModel = (Etapa) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "etapa");
        FacesMessage msg = new FacesMessage("Edición cancelada", etapaModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Etapa ID seleccionado",
                ((Etapa) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("EtapaMB onRowSelect " + msg);

    }

    /**
     * @return the etapaList
     */
    public List<String> getEtapaList() {
        if (Etapas == null) fillEtapas();
        System.out.println("EtapaMB getEtapaList " + etapaList.size());
        return etapaList;
    }

    /**
     * @param etapaList the etapaList to set
     */
    public void setEtapaList(List<String> etapaList) {
        this.etapaList = etapaList;
    }

    /**
     * @return the opcionList
     */
    public List<String> getOpcionList() {
        if (this.opcionList == null) this.opcionList= dao.getOpcionLista();

        return this.opcionList;
    }

    /**
     * @param opcionList the opcionList to set
     */
    public void setOpcionList(List<String> opcionList) {
        this.opcionList = opcionList;
    }

    /**
     * @return the modalidadList
     */
    public List<String> getModalidadList() {
            if (this.modalidadList == null) this.modalidadList= dao.getModalidadLista();

            return this.modalidadList;
      
    }

    /**
     * @param modalidadList the modalidadList to set
     */
    public void setModalidadList(List<String> modalidadList) {
        this.modalidadList = modalidadList;
    }

    /**
     * @return the etapaOption
     */
    public SelectItem[] getEtapaOption() {
        System.out.println("getEtapaOption");
        if (this.etapaOption == null) this.etapaOption=dao.getEtapaOption();
        return this.etapaOption;
    }

    /**
     * @param etapaOption the etapaOption to set
     */
    public void setEtapaOption(SelectItem[] etapaOption) {
        this.etapaOption = etapaOption;
    }

    /**
     * @return the opcionOption
     */
    public SelectItem[] getOpcionOption() {
        System.out.println("getOpcionOption");
        if (this.opcionOption == null) this.opcionOption=dao.getOpcionOption();
        return this.opcionOption;
    }

    /**
     * @param opcionOption the opcionOption to set
     */
    public void setOpcionOption(SelectItem[] opcionOption) {
        this.opcionOption = opcionOption;
    }

    /**
     * @return the modalidadOption
     */
    public SelectItem[] getModalidadOption() {
        System.out.println("getModalidadOption");
        if (this.modalidadOption == null) this.modalidadOption=dao.getModalidadOption();
        return this.modalidadOption;
    }

    /**
     * @param modalidadOption the modalidadOption to set
     */
    public void setModalidadOption(SelectItem[] modalidadOption) {
        this.modalidadOption = modalidadOption;
    }

    /**
     * @return the filterEtapas
     */
    public List<Etapa> getFilterEtapas(String opc, String mod) {
        filterEtapas = new ArrayList<Etapa>(dao.getSome(opc, mod));
        return filterEtapas;
    }

    /**
     * @param filterEtapas the filterEtapas to set
     */
    public void setFilterEtapas(List<Etapa> filterEtapas) {
        this.filterEtapas = filterEtapas;
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
