package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.ProductoPSADAO;
import co.gov.fonada.planeacion.dao.ProductoPSADAOObjectify;
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

@ManagedBean(name = "productoPSAMB")
@ViewScoped
public class productoPSAMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private ProductoPSADAO dao;

    private ProductoPSA ProductoPSA;

    private Long idSelecionado;

    private LinkedHashMap<Long, ProductoPSA> ProductoPSAs;

    private List<String> productoPSAList;
    private List<String> opcionList;
    private List<String> modalidadList;

    private SelectItem[] productoPSAOption;
    private SelectItem[] opcionOption; //Esto tambien es referencia
    private SelectItem[] modalidadOption;
    private List<ProductoPSA> filterProductoPSAs;
    private List<ProductoPSA> productoPSAsList;


    CSVExportOptions csvExportOptions;

    public productoPSAMB() {
        dao = new ProductoPSADAOObjectify();
//		fillProductoPSAs();
    }

    public void actualizar() {
        //fillProductoPSAs();
    }

    private LazyDataModel<ProductoPSA> lazyProductoPSAs = null;

    public LazyDataModel<ProductoPSA> getAllLazyProductoPSAs() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = context.getExternalContext()
                .getRequestParameterMap();
        String activeURL = requestMap.get("id");
        Long seguimientoId = 0L;
        try {
            seguimientoId = Long.parseLong(activeURL);

        } catch (Exception e) {
            ExternalContext ec = context.getExternalContext();
            System.out.println("hmmm... productosPSAId es null o incorrecto");
            try {
                ec.redirect("/");
            } catch (IOException ioe) {
                ioe.printStackTrace();

            }
        }
        System.out.println("ProductoPSAsFullMB  getLazyProductoPSAs " + activeURL);
        if (lazyProductoPSAs == null) lazyProductoPSAs = new ProductoPSALazyList(seguimientoId);
        System.out.println("ProductoPSAsFullMB  getLazyProductoPSAs ready to return " + lazyProductoPSAs.getPageSize());

        return lazyProductoPSAs;
    }

    private LazyDataModel<CheckProductoPSA> lazyCheckProductoPSAs = null;

    public LazyDataModel<CheckProductoPSA> getAllLazyCheckProductoPSAs() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = context.getExternalContext()
                .getRequestParameterMap();
        String activeURL = requestMap.get("id");
        Long seguimientoId = 0L;
        try {
            seguimientoId = Long.parseLong(activeURL);

        } catch (Exception e) {
            ExternalContext ec = context.getExternalContext();
            System.out.println("hmmm... productosPSAId es null o incorrecto");
            try {
                ec.redirect("/");
            } catch (IOException ioe) {
                ioe.printStackTrace();

            }
        }
        System.out.println("ProductoPSAsFullMB  getLazyProductoPSAs " + activeURL);
        if (lazyCheckProductoPSAs == null) lazyCheckProductoPSAs = new CheckProductosPSALazyList(seguimientoId);
        System.out.println("ProductoPSAsFullMB  getLazyProductoPSAs ready to return " + lazyCheckProductoPSAs.getPageSize());

        return lazyCheckProductoPSAs;
    }


    private void fillProductoPSAs() {
      /*  System.out.println("ProductoPSAMB starting fillProductoPSAs");
        try {
            // Changed to return decremental ordered by key list
            List<ProductoPSA> qryProductoPSAs = new ArrayList<ProductoPSA>(dao.getAllOrdered());
            // if (qryProductoPSAs.isEmpty()) {
            // System.out.println("ProductoPSAMB qryProductoPSAs is Empty");
            // loadProductoPSAs();
            // fillProductoPSAs();
            // } else {
            System.out.println("ProductoPSAMB qryProductoPSAs is " + qryProductoPSAs.size());
            ProductoPSAs = new LinkedHashMap<Long, ProductoPSA>();

            LinkedHashSet<String> productoPSASet = new LinkedHashSet<String>();
            LinkedHashSet<String> opcionSet = new LinkedHashSet<String>();
            LinkedHashSet<String> modalidadSet = new LinkedHashSet<String>();
            for (ProductoPSA a : qryProductoPSAs) {
//				System.out.println("ProductoPSAMB fillProductoPSAs ProductoPSA id " + a.getId());
                ProductoPSAs.put(a.getId(), a);
                if (a.getProductoPSAs() != null)
                    productoPSASet.add(a.getProductoPSAs());
                if (a.getTCCpub() != null)
                    opcionSet.add(a.getTCCpub());
                if (a.getContratoSig() != null)
                    modalidadSet.add(a.getContratoSig());
            }

//			Iterator<Entry<Long, ProductoPSA>> it = ProductoPSAs.entrySet().iterator();
//		    while (it.hasNext()) {
//		        Map.Entry<Long, ProductoPSA> pair = (Map.Entry<Long, ProductoPSA>)it.next();
//		        System.out.println("ProductoPSAMB fillProductoPSAs ProductoPSAs "+ pair.getKey() + " = " + pair.getValue().getId().toString());
//		    }

            productoPSAList = new ArrayList<String>(productoPSASet);
            // System.out.println("ProductoPSAMB productoPSAList is " +
            // productoPSAList.size()+productoPSAList);

            opcionList = new ArrayList<String>(opcionSet);
            // System.out.println("ProductoPSAMB opcionList is " +
            // opcionList.size()+opcionList);

            modalidadList = new ArrayList<String>(modalidadSet);
            // System.out.println("ProductoPSAMB modalidadList is " +
            // modalidadList.size()+modalidadList);


            productoPSAOption = new SelectItem[productoPSAList.size() + 1];
            productoPSAOption[0] = new SelectItem("", "Select");
            for (int i = 0; i < productoPSAList.size(); i++) {
                productoPSAOption[i + 1] = new SelectItem(productoPSAList.get(i),
                        productoPSAList.get(i));
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


    public ProductoPSADAO getDao() {
        return dao;
    }

    public void setDao(ProductoPSADAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public ProductoPSA getProductoPSA() {
        return ProductoPSA;
    }

    public void setProductoPSA(ProductoPSA ProductoPSA) {
        System.out.println("debug 3");

        this.ProductoPSA = ProductoPSA;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, ProductoPSA> getProductoPSAs() {
        return ProductoPSAs;
    }

    public void setProductoPSAs(Map<Long, ProductoPSA> ProductoPSAs) {

        this.ProductoPSAs = (LinkedHashMap<Long, ProductoPSA>) ProductoPSAs;
    }

    public DataModel<ProductoPSA> getDataProductoPSAs() {
        if (ProductoPSAs == null) fillProductoPSAs();

        if (ProductoPSAs.values() != null) {
            return new ListDataModel<ProductoPSA>(new ArrayList<ProductoPSA>(ProductoPSAs.values()));

        } else {
            return new ListDataModel<ProductoPSA>(new ArrayList<ProductoPSA>());

        }
    }

    public void reset() {
        ProductoPSA = null;
        idSelecionado = null;
    }

    public void agregar() {
        ProductoPSA = new ProductoPSA();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        ProductoPSA = ProductoPSAs.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(ProductoPSA.getId() + " public String guardar()");
        try {
            dao.save(ProductoPSA);
            Thread.sleep(2000);
            ProductoPSAs.put(ProductoPSA.getId(), ProductoPSA);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.productoPSA"),
                    ex.getMessage());
            return "";
        }
        return "listaProductoPSAs";
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("ProductoPSAMB buttonGuardar");
        FacesContext context = FacesContext.getCurrentInstance();
        ProductoPSA productoPSAModel = (ProductoPSA) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "productoPSA");
//        this.idSelecionado = productoPSAModel.getId();
        this.ProductoPSA = productoPSAModel;
        try {
//            System.out.println("ProductoPSAMB buttonGuardar try idSelecionado "
//                    + idSelecionado);
//            System.out.println("ProductoPSAMB buttonGuardar try " + ProductoPSA.getTCCpub()
//                    + ProductoPSA.getContratoSig());
            ObjectifyFactory f = new ObjectifyFactory();
            Key<ProductosPSA> key = Key.create(ProductosPSA.class, ProductoPSA.getParentId());
//            ProductoPSA.setId(key.g  etId());
            ProductoPSA.setParent(key);
            dao.save(ProductoPSA);
            // Thread.sleep(2000);
//            ProductoPSAs.put(ProductoPSA.getId(), ProductoPSA);

            context.addMessage(null, new FacesMessage("Guardado!",
                    "ProductoPSA almacenado exitosamente."));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // fillProductoPSAs();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        ProductoPSA = (ProductoPSA) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "productoPSA");
        try {
            dao.remove(ProductoPSA);
            ProductoPSAs.remove(ProductoPSA.getId());
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "ProductoPSA eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String eliminar() {
        try {
            dao.remove(ProductoPSA);
            ProductoPSAs.remove(ProductoPSA.getId());
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.eliminar.productoPSA"),
                    ex.getMessage());
            return "";
        }
        return "listaProductoPSAs";
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
            ProductoPSA = new ProductoPSA();
            // ProductoPSA.setTCCpub("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<ProductoPSA> key = f.allocateId(ProductoPSA.class);
            ProductoPSA.setId(key.getId());
            dao.save(ProductoPSA);
            // Thread.sleep(2000);
//            ProductoPSAs.put(ProductoPSA.getId(), ProductoPSA);
            context.addMessage(null, new FacesMessage("Creado!",
                    "ProductoPSA creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataProductoPSAs");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillProductoPSAs();

    }

    public void buttonReset(ActionEvent actionEvent) {

        out.println("ProductoPSAMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllProductoPSA();

            Thread.sleep(2000);
            fillProductoPSAs();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "ProductoPSAs reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }

    // public List<String> getProductoPSAList(String query) {
    // List<String> results = Arrays.asList(
    // "Radicación inicial estudios previos", "Reunión",
    // "Solicitud CDR", "Expedición CDR",
    // "Radicación a estructuración", "Sale de estructuración",
    // "Radicación a jurídica", "EP aprobados", "Publicación de TCC",
    // "Recepción observaciones", "Envío invitaciones", "Pliegos",
    // "Recepción observaciones", "Cierre", "Evaluación",
    // "Traslado y respuesta", "Apertura sobre económico",
    // "Evaluación económica", "Traslado y respuesta", "Adjudicación",
    // "Firma de productoPSA / otrosí", "Acta de inicio (PI contratado)");
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
    // List<String> results = Arrays.asList("Firma del productoPSA",
    // "Radicación estudios previos");
    // return results;
    // }
    //
    // public List<String> referenciaList(String query) {
    // out.println("Dentro de getReferenciaList");
    //
    // List<String> results = Arrays.asList("Firma del productoPSA",
    // "Radicación estudios previos");
    // return results;
    // }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ProductoPSA productoPSAModel = (ProductoPSA) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "productoPSA");
        FacesMessage msg = new FacesMessage("Edición cancelada", productoPSAModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("ProductoPSA ID seleccionado",
                ((ProductoPSA) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("ProductoPSAMB onRowSelect " + msg);

    }

    /**
     * @return the productoPSAList
     */
    public List<String> getProductoPSAList() {
        if (ProductoPSAs == null) fillProductoPSAs();
        System.out.println("ProductoPSAMB getProductoPSAList " + productoPSAList.size());
        return productoPSAList;
    }

    /**
     * @param productoPSAList the productoPSAList to set
     */
    public void setProductoPSAList(List<String> productoPSAList) {
        this.productoPSAList = productoPSAList;
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
     * @return the productoPSAOption
     *//*
    public SelectItem[] getProductoPSAOption() {
        System.out.println("getProductoPSAOption");
        if (this.productoPSAOption == null) this.productoPSAOption=dao.getProductoPSAOption();
        return this.productoPSAOption;
    }
*/

    /**
     * @param productoPSAOption the productoPSAOption to set
     */
    public void setProductoPSAOption(SelectItem[] productoPSAOption) {
        this.productoPSAOption = productoPSAOption;
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
     * @return the filterProductoPSAs
     */
    public List<ProductoPSA> getFilterProductoPSAs(String opc, String mod) {
        filterProductoPSAs = new ArrayList<ProductoPSA>(dao.getSome(opc, mod));
        return filterProductoPSAs;
    }

    /**
     * @param filterProductoPSAs the filterProductoPSAs to set
     */
    public void setFilterProductoPSAs(List<ProductoPSA> filterProductoPSAs) {
        this.filterProductoPSAs = filterProductoPSAs;
    }

    public CSVExportOptions getCsvExportOptions() {
        CSVExportOptions options = new CSVExportOptions();
        options.setSeparatorCharacter('|');
        options.setCharacterEncoding("UTF-8-with-bom");
        return options;
    }



}
