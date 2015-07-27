package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.ListaDAO;
import co.gov.fonada.planeacion.dao.ListaDAOObjectify;
import co.gov.fonada.planeacion.model.Accion;
import co.gov.fonada.planeacion.model.Lista;
import co.gov.fonada.planeacion.model.ListaLazyList;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.component.selectonebutton.SelectOneButton;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * Created by esalazar on 23/04/2015.
 */
@ManagedBean(name = "listaMB")
@ViewScoped
public class ListaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277260790273299L;


    private ListaDAO dao;

    private co.gov.fonada.planeacion.model.Lista Lista;

    private Long idSelecionado;

    @ManagedProperty("#{contratoMB}")
    private ContratoMB contratoMB;


    private LinkedHashMap<Long, Lista> listaSectores;
    private LinkedHashMap<Long, Lista> listaPostulaciones;

    private LinkedHashMap<Long, Lista> listaIntervenciones;
    private LinkedHashMap<Long, Lista> listaProductos;

    private List<String> sectorStringLista;
    private List<String> productoStringLista;
    private List<String> postulacionStringLista;
    private List<String> intervencionStringLista;
    private List<String> intervencionStringListaFiltered;

    private SelectItem[] sectorOption;
    private SelectItem[] productoOption;
    private SelectItem[] postulacionOption;
    private SelectItem[] intervencionOption;

    private List<Lista> filterListas;

    private List<Lista> sectorLista;
    private List<Lista> productoLista;
    private List<Lista> postulacionLista;
    private List<Lista> intervencionLista;

    CSVExportOptions csvExportOptions;

    public ListaMB() {
        dao = new ListaDAOObjectify();
//        fillListas();
    }

    public void actualizar() {

    }


    /*Usamos LinkedHash porque fuerzan el orden y que los elementos no se repitan*/
//    @PostConstruct
    private void fillListas(String menu) {
        System.out.println("ListaMB starting fillListas " + menu);

        listaSectores = new LinkedHashMap<Long, Lista>();
        listaPostulaciones = new LinkedHashMap<Long, Lista>();
        listaIntervenciones = new LinkedHashMap<Long, Lista>();
        listaProductos = new LinkedHashMap<Long, Lista>();

        intervencionStringListaFiltered = new ArrayList<>();

        LinkedHashSet<String> sectorSet = new LinkedHashSet<String>();
        LinkedHashSet<String> postulacionSet = new LinkedHashSet<String>();
        LinkedHashSet<String> intervencionSet = new LinkedHashSet<String>();
        LinkedHashSet<String> productoSet = new LinkedHashSet<String>();
        try {

            List<Lista> qryListas = new ArrayList<Lista>(dao.getSome(menu));
            System.out.println("ListaMB qryListas is " + qryListas.size());


            switch (menu) {
                case "sector":
                    for (Lista a : qryListas) {

                        listaSectores.put(a.getId(), a);
                        if (a.getValor() != null)
                            sectorSet.add(a.getValor());
                    }
                    sectorStringLista = new ArrayList<String>(sectorSet);
                    sectorOption = new SelectItem[sectorStringLista.size() + 1];
                    sectorOption[0] = new SelectItem("", "");
                    for (int i = 0; i < sectorStringLista.size(); i++) {
                        sectorOption[i + 1] = new SelectItem(sectorStringLista.get(i),
                                sectorStringLista.get(i));
                    }

                    break;
                case "postulacion":
                    for (Lista a : qryListas) {

                        listaPostulaciones.put(a.getId(), a);
                        if (a.getPostulacion() != null) {
                            postulacionSet.add(a.getPostulacion());
                            intervencionSet.add(a.getIntervencion());
                            try {
                                if (a.getPostulacion().equalsIgnoreCase(contratoMB.getContrato().getPostulacion()))
                                    intervencionStringListaFiltered.add(a.getIntervencion());
                            } catch (Exception e) {
                            }
                        }
                    }
                    postulacionStringLista = new ArrayList<String>(postulacionSet);
                    intervencionStringLista = new ArrayList<String>(intervencionSet);
                    postulacionOption = new SelectItem[postulacionStringLista.size() + 1];
                    postulacionOption[0] = new SelectItem("", "");
                    for (int i = 0; i < postulacionStringLista.size(); i++) {
                        postulacionOption[i + 1] = new SelectItem(postulacionStringLista.get(i),
                                postulacionStringLista.get(i));
                    }

                    intervencionOption = new SelectItem[intervencionStringLista.size() + 1];
                    intervencionOption[0] = new SelectItem("", "");
                    for (int i = 0; i < intervencionStringLista.size(); i++) {
                        intervencionOption[i + 1] = new SelectItem(intervencionStringLista.get(i),
                                intervencionStringLista.get(i));
                    }
                    break;
                case "producto":
                    for (Lista a : qryListas) {

                        listaProductos.put(a.getId(), a);
                        if (a.getProductoPND() != null)
                            productoSet.add(a.getProductoPND());
                    }
                    productoStringLista = new ArrayList<String>(productoSet);
                    productoOption = new SelectItem[productoStringLista.size() + 1];
                    productoOption[0] = new SelectItem("", "");
                    for (int i = 0; i < productoStringLista.size(); i++) {
                        productoOption[i + 1] = new SelectItem(productoStringLista.get(i),
                                productoStringLista.get(i));
                    }

                    break;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public boolean isPostulacion(String search) {
        if (postulacionStringLista == null) fillListas("postulacion");
        for (String a : postulacionStringLista) {
            if (a.equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIntervencion(String search) {
        if (intervencionStringLista == null) fillListas("postulacion");
        for (String a : intervencionStringLista) {
            if (a.equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSector(String search) {
        if (sectorStringLista == null) fillListas("sector");

        for (String a : sectorStringLista) {
            if (a.equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProducto(String search) {
        if (productoStringLista == null) fillListas("producto");

        for (String a : productoStringLista) {
            if (a.equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }


    public DataModel<Lista> getDataSectores() {
        if (listaSectores == null || listaSectores.size() == 0) fillListas("sector");
        return new ListDataModel<Lista>(new ArrayList<Lista>(listaSectores.values()));
    }

    public DataModel<Lista> getDataProductos() {
        if (listaProductos == null || listaProductos.size() == 0) fillListas("producto");

        return new ListDataModel<Lista>(new ArrayList<Lista>(listaProductos.values()));
    }

    public DataModel<Lista> getDataPostulaciones() {
        if (listaPostulaciones == null || listaPostulaciones.size() == 0) fillListas("postulacion");

        return new ListDataModel<Lista>(new ArrayList<Lista>(listaPostulaciones.values()));
    }

    public void reset() {
        Lista = null;
        idSelecionado = null;
    }

    public void agregar() {
        Lista = new Lista();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }

        if (listaSectores.get(idSelecionado) != null) Lista = listaSectores.get(idSelecionado);
        else if (listaPostulaciones.get(idSelecionado) != null) Lista = listaPostulaciones.get(idSelecionado);
        else if (listaProductos.get(idSelecionado) != null) Lista = listaProductos.get(idSelecionado);
    }

    private LazyDataModel<Lista> lazySector = null;

    public LazyDataModel<Lista> getLazySector() {
        System.out.println("ListasMB  getLazyListas ");
        if (lazySector == null) lazySector = new ListaLazyList("sector");
        System.out.println("ListasFullMB  getLazyListas ready to return " + lazySector.getPageSize());

        return lazySector;
    }

    private LazyDataModel<Lista> lazyProducto = null;

    public LazyDataModel<Lista> getLazyProducto() {
        System.out.println("ListasMB  getLazyListas ");
        if (lazyProducto == null) lazyProducto = new ListaLazyList("producto");
        System.out.println("ListasFullMB  getLazyListas ready to return " + lazyProducto.getPageSize());

        return lazyProducto;
    }

    private LazyDataModel<Lista> lazyPostulacion = null;

    public LazyDataModel<Lista> getLazyPostulacion() {
        System.out.println("ListasMB  getLazyListas ");
        if (lazyPostulacion == null) lazyPostulacion = new ListaLazyList("postulacion");
        System.out.println("ListasFullMB  getLazyListas ready to return " + lazyPostulacion.getPageSize());

        return lazyPostulacion;
    }


//    public String guardar() {
//        System.out.println("debug 1");
//        System.out.println(Lista.getId() + " public String guardar()");
//        try {
//            dao.save(Lista);
//            Thread.sleep(2000);
//            Listas.put(Lista.getId(), Lista);
//        } catch (Exception ex) {
//            addMessage(getMessageFromI18N("msg.error.guardar.lista"),
//                    ex.getMessage());
//            return "";
//        }
//        return "listaListas";
//    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("ListaMB buttonGuardar");
        FacesContext context = FacesContext.getCurrentInstance();
        Lista listaModel = (Lista) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "lista");
//        this.idSelecionado = listaModel.getId();
        this.Lista = listaModel;
        try {
/*            System.out.println("ListaMB buttonGuardar try idSelecionado "
                    + idSelecionado);*/

            dao.save(Lista);
            switch (Lista.getList()) {
                case "sector":
                    listaSectores.put(Lista.getId(), Lista);
                    break;
                case "postulacion":
                    listaPostulaciones.put(Lista.getId(), Lista);
                    break;
                case "producto":
                    listaProductos.put(Lista.getId(), Lista);
                    break;
            }

            context.addMessage(null, new FacesMessage("Guardado!",
                    "Lista almacenado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        Lista = (Lista) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "lista");
        try {
            dao.remove(Lista);
            switch (Lista.getList()) {
                case "sector":
                    listaSectores.remove(Lista.getId());
                    break;
                case "postulacion":
                    listaPostulaciones.remove(Lista.getId());
                    break;
                case "producto":
                    listaProductos.remove(Lista.getId());
                    break;
            }
            Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Lista eliminada exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public String eliminar() {
//        try {
//            dao.remove(Lista);
//            Listas.remove(Lista.getId());
//        } catch (Exception ex) {
//            addMessage(getMessageFromI18N("msg.error.eliminar.lista"),
//                    ex.getMessage());
//            return "";
//        }
//        return "listaListas";
//    }

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

    public void buttonCrearSector(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Lista = new Lista();
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Lista> key = f.allocateId(Lista.class);
            Lista.setId(key.getId());
            Lista.setList("sector");
            dao.save(Lista);
            listaSectores.put(Lista.getId(), Lista);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Sector creado exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form1:dataSectores");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillListas("sector");
    }

    public void buttonCrearProducto(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Lista = new Lista();
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Lista> key = f.allocateId(Lista.class);
            Lista.setId(key.getId());
            Lista.setList("producto");
            dao.save(Lista);
            listaProductos.put(Lista.getId(), Lista);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Producto creado exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form3:dataProductos");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillListas("producto");
    }

    public void buttonCrearPostulacion(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Lista = new Lista();
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Lista> key = f.allocateId(Lista.class);
            Lista.setId(key.getId());
            Lista.setList("postulacion");
            dao.save(Lista);
            listaPostulaciones.put(Lista.getId(), Lista);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Postulacion creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form2:dataPostulaciones");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
        fillListas("postulacion");
    }


    public void buttonReset(ActionEvent actionEvent) {

        out.println("ListaMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllLista();

            Thread.sleep(2000);
//            fillListas();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "Listas reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }


    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Lista listaModel = (Lista) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "lista");
        FacesMessage msg = new FacesMessage("Edición cancelada", listaModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Lista ID seleccionado",
                ((Lista) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("ListaMB onRowSelect " + msg);
    }

    public void onPostulacionChange(AjaxBehaviorEvent event) {
        HtmlSelectOneMenu temp = (HtmlSelectOneMenu) event.getSource();
        System.out.println("ListaMB onPostulacionChange " + event.getSource().toString());

        //Obtener objeto del row, del tipo del modelo de la datatable
        FacesContext context = FacesContext.getCurrentInstance();
        Accion accionModel = (Accion) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "accion");
        System.out.println("ListaMB onPostulacionChange " + accionModel.getId());

        String tempPost = (String) temp.getValue();

        System.out.println("ListaMB onPostulacionChange " + tempPost);
    }


    public List<String> sectorsDropdown(String query) {

        return getSectorStringLista();
    }

    public List<String> productosDropdown(String query) {
        return getProductoStringLista();
    }

    public List<String> postulacionesDropdown(String query) {
        return getPostulacionStringLista();
    }

    public List<String> getIntervencionesFilteredDropdown() {
        System.out.println("ListaMB intervencionesFilteredDropdown");

        if (intervencionStringListaFiltered == null) fillListas("postulacion");
        return intervencionStringListaFiltered;
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

    public ListaDAO getDao() {
        return dao;
    }

    public void setDao(ListaDAO dao) {
        this.dao = dao;
    }

    public List<co.gov.fonada.planeacion.model.Lista> getFilterListas() {
        return filterListas;
    }

    public void setFilterListas(List<co.gov.fonada.planeacion.model.Lista> filterListas) {
        this.filterListas = filterListas;
    }

    public Long getIdSelecionado() {
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public co.gov.fonada.planeacion.model.Lista getLista() {
        return Lista;
    }

    public void setLista(co.gov.fonada.planeacion.model.Lista lista) {
        Lista = lista;
    }

    public LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> getListaPostulaciones() {
        if (listaPostulaciones == null) fillListas("postulacion");
        return listaPostulaciones;
    }

    public void setListaPostulaciones(LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> listaPostulaciones) {
        this.listaPostulaciones = listaPostulaciones;
    }

    public LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> getListaProductos() {

        if (listaProductos == null) fillListas("producto");
        return listaProductos;
    }

    public void setListaProductos(LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> getListaSectores() {
        if (listaSectores == null) fillListas("sector");

        return listaSectores;
    }

    public void setListaSectores(LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> listaSectores) {
        this.listaSectores = listaSectores;
    }

    public List<co.gov.fonada.planeacion.model.Lista> getPostulacionLista() {

        if (postulacionLista == null) fillListas("postulacion");

        return postulacionLista;
    }

    public void setPostulacionLista(List<co.gov.fonada.planeacion.model.Lista> postulacionLista) {
        this.postulacionLista = postulacionLista;
    }

    public SelectItem[] getPostulacionOption() {
        System.out.println("getPostulacionOption");
        if (this.postulacionOption == null) this.postulacionOption = dao.getPostulacionOption();
        return this.postulacionOption;
    }

    public void setPostulacionOption(SelectItem[] postulacionOption) {
        this.postulacionOption = postulacionOption;
    }



    public void setPostulacionStringLista(List<String> postulacionStringLista) {
        this.postulacionStringLista = postulacionStringLista;
    }

    public List<co.gov.fonada.planeacion.model.Lista> getProductoLista() {
        if (productoLista == null) fillListas("producto");


        return productoLista;
    }

    public void setProductoLista(List<co.gov.fonada.planeacion.model.Lista> productoLista) {
        this.productoLista = productoLista;
    }

    public SelectItem[] getProductoOption() {
        System.out.println("getProductoOption");
        if (this.productoOption == null) this.productoOption = dao.getProductoOption();
        return this.productoOption;
    }

    public void setProductoOption(SelectItem[] productoOption) {
        this.productoOption = productoOption;
    }

    public List<String> getProductoStringLista() {
        if (this.productoStringLista == null) this.productoStringLista= dao.getProductoStringLista();

        return this.productoStringLista;
    }

    public List<String> getSectorStringLista() {
        if (this.sectorStringLista == null) this.sectorStringLista= dao.getSectorStringLista();

        return this.sectorStringLista;
    }

    public List<String> getPostulacionStringLista() {
        if (this.postulacionStringLista == null) this.postulacionStringLista= dao.getPostulacionStringLista();

        return this.postulacionStringLista;
    }

    public List<String> getIntervencionStringLista() {
        if (this.intervencionStringLista == null) this.intervencionStringLista= dao.getIntervencionStringLista(contratoMB.getContrato().getPostulacion());

        return this.intervencionStringLista;
    }

    public void setProductoStringLista(List<String> productoStringLista) {
        this.productoStringLista = productoStringLista;
    }

    public List<co.gov.fonada.planeacion.model.Lista> getSectorLista() {
        if (sectorLista == null) fillListas("sector");
        return sectorLista;
    }

    public void setSectorLista(List<co.gov.fonada.planeacion.model.Lista> sectorLista) {
        this.sectorLista = sectorLista;
    }

    public SelectItem[] getSectorOption() {
        System.out.println("getSectorOption");
        if (this.sectorOption == null) this.sectorOption = dao.getSectorOption();
        return this.sectorOption;
    }

    public void setSectorOption(SelectItem[] sectorOption) {
        this.sectorOption = sectorOption;
    }



    public void setSectorStringLista(List<String> sectorStringLista) {
        this.sectorStringLista = sectorStringLista;
    }

    public LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> getListaIntervenciones() {
        if (listaIntervenciones == null) fillListas("postulacion");

        return listaIntervenciones;
    }

    public void setListaIntervenciones(LinkedHashMap<Long, co.gov.fonada.planeacion.model.Lista> listaIntervenciones) {
        this.listaIntervenciones = listaIntervenciones;
    }

    public List<co.gov.fonada.planeacion.model.Lista> getIntervencionLista() {
        if (intervencionLista == null) fillListas("postulacion");
        return intervencionLista;
    }

    public void setIntervencionLista(List<co.gov.fonada.planeacion.model.Lista> intervencionLista) {
        this.intervencionLista = intervencionLista;
    }

    public SelectItem[] getIntervencionOption() {
        System.out.println("getIntervencionOption");
        if (this.intervencionOption == null) this.intervencionOption = dao.getIntervencionOption();
        return this.intervencionOption;
    }

    public void setIntervencionOption(SelectItem[] intervencionOption) {
        this.intervencionOption = intervencionOption;
    }



    public void setIntervencionStringLista(List<String> intervencionStringLista) {
        this.intervencionStringLista = intervencionStringLista;
    }

    public List<String> getIntervencionStringListaFiltered() {
        if (intervencionStringListaFiltered == null) fillListas("postulacion");
        return intervencionStringListaFiltered;
    }

    public void setIntervencionStringListaFiltered(List<String> intervencionStringListaFiltered) {
        this.intervencionStringListaFiltered = intervencionStringListaFiltered;
    }

    public ContratoMB getContratoMB() {
        return contratoMB;
    }

    public void setContratoMB(ContratoMB contratoMB) {
        this.contratoMB = contratoMB;
    }
}
