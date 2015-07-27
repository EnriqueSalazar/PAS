package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.FuenteDAO;
import co.gov.fonada.planeacion.dao.FuenteDAOObjectify;
import co.gov.fonada.planeacion.model.Fuente;
import co.gov.fonada.planeacion.model.FuenteLazyList;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;
import static javax.faces.context.FacesContext.getCurrentInstance;

@ManagedBean(name = "fuenteMB")
@ViewScoped
public class FuenteMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private FuenteDAO dao;

    private Fuente Fuente;

    private Long idSelecionado;

    private LinkedHashMap<Long, Fuente> Fuentes;

    private List<String> fuenteList;

    private SelectItem[] fuenteOption; //Esto tambien es referencia
    private List<Fuente> filterFuentes;


    CSVExportOptions csvExportOptions;

    public FuenteMB() {
        dao = new FuenteDAOObjectify();
//		fillFuentes();
    }

    public void actualizar() {
        //fillFuentes();
    }
    private LazyDataModel<Fuente> lazyFuentes = null;
    public LazyDataModel<Fuente> getAllLazyFuentes() {
        System.out.println("FuentesFullMB  getLazyFuentes ");
        if (lazyFuentes == null) lazyFuentes = new FuenteLazyList();
        System.out.println("FuentesFullMB  getLazyFuentes ready to return "+lazyFuentes.getPageSize());

        return lazyFuentes;
    }





    public FuenteDAO getDao() {
        return dao;
    }

    public void setDao(FuenteDAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public Fuente getFuente() {
        return Fuente;
    }

    public void setFuente(Fuente Fuente) {
        System.out.println("debug 3");

        this.Fuente = Fuente;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Fuente> getFuentes() {
        return Fuentes;
    }

    public void setFuentes(Map<Long, Fuente> Fuentes) {

        this.Fuentes = (LinkedHashMap<Long, Fuente>) Fuentes;
    }



    public void reset() {
        Fuente = null;
        idSelecionado = null;
    }

    public void agregar() {
        Fuente = new Fuente();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        Fuente = Fuentes.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(Fuente.getId() + " public String guardar()");
        try {
            dao.save(Fuente);
            Thread.sleep(2000);
            Fuentes.put(Fuente.getId(), Fuente);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.fuente"),
                    ex.getMessage());
            return "";
        }
        return "listaFuentes";
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("FuenteMB buttonGuardar");
        FacesContext context = FacesContext.getCurrentInstance();
        Fuente fuenteModel = (Fuente) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "fuente");
//        this.idSelecionado = fuenteModel.getId();
        this.Fuente = fuenteModel;
        try {
//            System.out.println("FuenteMB buttonGuardar try idSelecionado "
//                    + idSelecionado);

            dao.save(Fuente);
            // Thread.sleep(2000);

            context.addMessage(null, new FacesMessage("Guardado!",
                    "Fuente almacenado exitosamente."));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // fillFuentes();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        Fuente = (Fuente) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "fuente");
        try {
            dao.remove(Fuente);
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Fuente eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String eliminar() {
        try {
            dao.remove(Fuente);
            Fuentes.remove(Fuente.getId());
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.eliminar.fuente"),
                    ex.getMessage());
            return "";
        }
        return "listaFuentes";
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
            Fuente = new Fuente();
            // Fuente.setFuente("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Fuente> key = f.allocateId(Fuente.class);
            Fuente.setId(key.getId());
            dao.save(Fuente);
            // Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Fuente creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataFuentes");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");

    }



    // public List<String> getFuenteList(String query) {
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
        Fuente fuenteModel = (Fuente) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "fuente");
        FacesMessage msg = new FacesMessage("Edición cancelada", fuenteModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Fuente ID seleccionado",
                ((Fuente) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("FuenteMB onRowSelect " + msg);

    }




    /**
     * @return the fuenteList
     */
    public List<String> getFuenteList() {
        if (this.fuenteList == null) this.fuenteList= dao.getFuenteLista();

        return this.fuenteList;
    }

    /**
     * @param fuenteList the fuenteList to set
     */
    public void setFuenteList(List<String> fuenteList) {
        this.fuenteList = fuenteList;
    }

    /**
     * @return the fuenteOption
     */
    public SelectItem[] getFuenteOption() {
        System.out.println("getFuenteOption");
        if (this.fuenteOption == null) this.fuenteOption=dao.getFuenteOption();
        return this.fuenteOption;
    }

    /**
     * @param fuenteOption the fuenteOption to set
     */
    public void setFuenteOption(SelectItem[] fuenteOption) {
        this.fuenteOption = fuenteOption;
    }

    /**
     * @return the filterFuentes
     */
    public List<Fuente> getFilterFuentes(String opc, String mod) {
        filterFuentes = new ArrayList<Fuente>(dao.getSome(opc, mod));
        return filterFuentes;
    }

    /**
     * @param filterFuentes the filterFuentes to set
     */
    public void setFilterFuentes(List<Fuente> filterFuentes) {
        this.filterFuentes = filterFuentes;
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
