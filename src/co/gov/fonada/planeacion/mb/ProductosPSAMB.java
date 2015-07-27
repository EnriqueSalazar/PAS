package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.ProductosPSADAO;
import co.gov.fonada.planeacion.dao.ProductosPSADAOObjectify;
import co.gov.fonada.planeacion.model.ProductosPSA;
import co.gov.fonada.planeacion.model.ProductosPSALazyList;
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

@ManagedBean(name = "productosPSAMB")
@ViewScoped
public class ProductosPSAMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private ProductosPSADAO dao;

    private ProductosPSA ProductosPSA;

    private Long idSelecionado;

    private LinkedHashMap<Long, ProductosPSA> ProductosPSAs;

    private List<String> productosPSAList;
    private List<String> opcionList;
    private List<String> modalidadList;

    private SelectItem[] productosPSAOption;
    private SelectItem[] opcionOption; //Esto tambien es referencia
    private SelectItem[] modalidadOption;
    private List<ProductosPSA> filterProductosPSAs;
    private List<ProductosPSA> productosPSAsList;


    CSVExportOptions csvExportOptions;

    public ProductosPSAMB() {
        dao = new ProductosPSADAOObjectify();
//		fillProductosPSAs();
    }

    public void actualizar() {
        //fillProductosPSAs();
    }
    private LazyDataModel<ProductosPSA> lazyProductosPSAs = null;
    public LazyDataModel<ProductosPSA> getAllLazyProductosPSAs() {
        System.out.println("ProductosPSAsFullMB  getLazyProductosPSAs ");
        if (lazyProductosPSAs == null) lazyProductosPSAs = new ProductosPSALazyList();
        System.out.println("ProductosPSAsFullMB  getLazyProductosPSAs ready to return "+lazyProductosPSAs.getPageSize());

        return lazyProductosPSAs;
    }
    private void fillProductosPSAs() {
      /*  System.out.println("ProductosPSAMB starting fillProductosPSAs");
        try {
            // Changed to return decremental ordered by key list
            List<ProductosPSA> qryProductosPSAs = new ArrayList<ProductosPSA>(dao.getAllOrdered());
            // if (qryProductosPSAs.isEmpty()) {
            // System.out.println("ProductosPSAMB qryProductosPSAs is Empty");
            // loadProductosPSAs();
            // fillProductosPSAs();
            // } else {
            System.out.println("ProductosPSAMB qryProductosPSAs is " + qryProductosPSAs.size());
            ProductosPSAs = new LinkedHashMap<Long, ProductosPSA>();

            LinkedHashSet<String> productosPSASet = new LinkedHashSet<String>();
            LinkedHashSet<String> opcionSet = new LinkedHashSet<String>();
            LinkedHashSet<String> modalidadSet = new LinkedHashSet<String>();
            for (ProductosPSA a : qryProductosPSAs) {
//				System.out.println("ProductosPSAMB fillProductosPSAs ProductosPSA id " + a.getId());
                ProductosPSAs.put(a.getId(), a);
                if (a.getProductosPSAs() != null)
                    productosPSASet.add(a.getProductosPSAs());
                if (a.getTCCpub() != null)
                    opcionSet.add(a.getTCCpub());
                if (a.getContratoSig() != null)
                    modalidadSet.add(a.getContratoSig());
            }

//			Iterator<Entry<Long, ProductosPSA>> it = ProductosPSAs.entrySet().iterator();
//		    while (it.hasNext()) {
//		        Map.Entry<Long, ProductosPSA> pair = (Map.Entry<Long, ProductosPSA>)it.next();
//		        System.out.println("ProductosPSAMB fillProductosPSAs ProductosPSAs "+ pair.getKey() + " = " + pair.getValue().getId().toString());
//		    }

            productosPSAList = new ArrayList<String>(productosPSASet);
            // System.out.println("ProductosPSAMB productosPSAList is " +
            // productosPSAList.size()+productosPSAList);

            opcionList = new ArrayList<String>(opcionSet);
            // System.out.println("ProductosPSAMB opcionList is " +
            // opcionList.size()+opcionList);

            modalidadList = new ArrayList<String>(modalidadSet);
            // System.out.println("ProductosPSAMB modalidadList is " +
            // modalidadList.size()+modalidadList);


            productosPSAOption = new SelectItem[productosPSAList.size() + 1];
            productosPSAOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < productosPSAList.size(); i++) {
                productosPSAOption[i + 1] = new SelectItem(productosPSAList.get(i),
                        productosPSAList.get(i));
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



    public ProductosPSADAO getDao() {
        return dao;
    }

    public void setDao(ProductosPSADAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public ProductosPSA getProductosPSA() {
        return ProductosPSA;
    }

    public void setProductosPSA(ProductosPSA ProductosPSA) {
        System.out.println("debug 3");

        this.ProductosPSA = ProductosPSA;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, ProductosPSA> getProductosPSAs() {
        return ProductosPSAs;
    }

    public void setProductosPSAs(Map<Long, ProductosPSA> ProductosPSAs) {

        this.ProductosPSAs = (LinkedHashMap<Long, ProductosPSA>) ProductosPSAs;
    }

    public DataModel<ProductosPSA> getDataProductosPSAs() {
        if (ProductosPSAs == null) fillProductosPSAs();

        if (ProductosPSAs.values() != null) {
            return new ListDataModel<ProductosPSA>(new ArrayList<ProductosPSA>(ProductosPSAs.values()));

        } else {
            return new ListDataModel<ProductosPSA>(new ArrayList<ProductosPSA>());

        }
    }

    public void reset() {
        ProductosPSA = null;
        idSelecionado = null;
    }

    public void agregar() {
        ProductosPSA = new ProductosPSA();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        ProductosPSA = ProductosPSAs.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(ProductosPSA.getId() + " public String guardar()");
        try {
            dao.save(ProductosPSA);
            Thread.sleep(2000);
            ProductosPSAs.put(ProductosPSA.getId(), ProductosPSA);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.productosPSA"),
                    ex.getMessage());
            return "";
        }
        return "listaProductosPSAs";
    }



    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        ProductosPSA = (ProductosPSA) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "productosPSA");
        try {
            dao.remove(ProductosPSA);
            ProductosPSAs.remove(ProductosPSA.getId());
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "ProductosPSA eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String eliminar() {
        try {
            dao.remove(ProductosPSA);
            ProductosPSAs.remove(ProductosPSA.getId());
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.eliminar.productosPSA"),
                    ex.getMessage());
            return "";
        }
        return "listaProductosPSAs";
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
            ProductosPSA = new ProductosPSA();
            // ProductosPSA.setTCCpub("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<ProductosPSA> key = f.allocateId(ProductosPSA.class);
            ProductosPSA.setId(key.getId());
            dao.save(ProductosPSA);
            // Thread.sleep(2000);
//            ProductosPSAs.put(ProductosPSA.getId(), ProductosPSA);
            context.addMessage(null, new FacesMessage("Creado!",
                    "ProductosPSA creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataProductosPSAs");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillProductosPSAs();

    }

    public void buttonReset(ActionEvent actionEvent) {

        out.println("ProductosPSAMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllProductosPSA();

            Thread.sleep(2000);
            fillProductosPSAs();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "ProductosPSAs reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }

    // public List<String> getProductosPSAList(String query) {
    // List<String> results = Arrays.asList(
    // "Radicación inicial estudios previos", "Reunión",
    // "Solicitud CDR", "Expedición CDR",
    // "Radicación a estructuración", "Sale de estructuración",
    // "Radicación a jurídica", "EP aprobados", "Publicación de TCC",
    // "Recepción observaciones", "Envío invitaciones", "Pliegos",
    // "Recepción observaciones", "Cierre", "Evaluación",
    // "Traslado y respuesta", "Apertura sobre económico",
    // "Evaluación económica", "Traslado y respuesta", "Adjudicación",
    // "Firma de productosPSA / otrosí", "Acta de inicio (PI contratado)");
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
    // List<String> results = Arrays.asList("Firma del productosPSA",
    // "Radicación estudios previos");
    // return results;
    // }
    //
    // public List<String> referenciaList(String query) {
    // out.println("Dentro de getReferenciaList");
    //
    // List<String> results = Arrays.asList("Firma del productosPSA",
    // "Radicación estudios previos");
    // return results;
    // }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ProductosPSA productosPSAModel = (ProductosPSA) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "productosPSA");
        FacesMessage msg = new FacesMessage("Edición cancelada", productosPSAModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("ProductosPSA ID seleccionado",
                ((ProductosPSA) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("ProductosPSAMB onRowSelect " + msg);

    }

    /**
     * @return the productosPSAList
     */
    public List<String> getProductosPSAList() {
        if (ProductosPSAs == null) fillProductosPSAs();
        System.out.println("ProductosPSAMB getProductosPSAList " + productosPSAList.size());
        return productosPSAList;
    }

    /**
     * @param productosPSAList the productosPSAList to set
     */
    public void setProductosPSAList(List<String> productosPSAList) {
        this.productosPSAList = productosPSAList;
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
     * @return the productosPSAOption
     *//*
    public SelectItem[] getProductosPSAOption() {
        System.out.println("getProductosPSAOption");
        if (this.productosPSAOption == null) this.productosPSAOption=dao.getProductosPSAOption();
        return this.productosPSAOption;
    }
*/
    /**
     * @param productosPSAOption the productosPSAOption to set
     */
    public void setProductosPSAOption(SelectItem[] productosPSAOption) {
        this.productosPSAOption = productosPSAOption;
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
     * @return the filterProductosPSAs
     */
    public List<ProductosPSA> getFilterProductosPSAs(String opc, String mod) {
        filterProductosPSAs = new ArrayList<ProductosPSA>(dao.getSome(opc, mod));
        return filterProductosPSAs;
    }

    /**
     * @param filterProductosPSAs the filterProductosPSAs to set
     */
    public void setFilterProductosPSAs(List<ProductosPSA> filterProductosPSAs) {
        this.filterProductosPSAs = filterProductosPSAs;
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
