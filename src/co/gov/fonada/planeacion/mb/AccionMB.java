package co.gov.fonada.planeacion.mb;

import static java.lang.System.out;

import java.io.Serializable;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
//import javax.faces.event.AjaxBehaviorEvent;

import co.gov.fonada.planeacion.model.*;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import co.gov.fonada.planeacion.dao.AccionDAO;
import co.gov.fonada.planeacion.dao.AccionDAOObjectify;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class AccionMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277299790273226L;

    private AccionDAO dao;

    @ManagedProperty("#{contratoMB}")
    private ContratoMB contratoMB;

    private DivipolaMB divipolaMB = new DivipolaMB();

    private Accion Accion;

    private Long idSelecionado;

    private LinkedHashMap<Long, Accion> Accions;

    private Long parentId;

    public AccionMB() {

        // try {
        // FacesContext context = FacesContext.getCurrentInstance();
        // Map<String, String> requestMap = context.getExternalContext()
        // .getRequestParameterMap();
        // String parentString = requestMap.get("id");
        // out.println("parentString " + parentString + " " + this.parentId);
        // // if (parentString != null) {
        // // Long parentLong = Long.parseLong(parentString);
        // // // this.parentId = parentLong;
        // // }
        //
        // System.out.println("getRequestParameterMap id " + this.parentId);
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        //
        // out.println("");
        out.println("public AccionMB()");
        dao = new AccionDAOObjectify();
//		fillAccions();
    }

    public void actualizar() {
        out.println("public void actualizar()");
        fillAccions();
    }

    private LazyDataModel<Accion> lazyAccions = null;

    public LazyDataModel<Accion> getLazyAccions() {
        System.out.println("AccionsMB  getLazyAccions ");
        if (lazyAccions == null) lazyAccions = new AccionLazy(parentId);
        System.out.println("AccionsFullMB  getLazyAccions ready to return " + lazyAccions.getPageSize());
        return lazyAccions;
    }

    private void fillAccions() {
        try {
            if (Accions == null) {
                List<Accion> qryAccions = new ArrayList<Accion>(
                        dao.getByParent(this.parentId));
                List<Divipola> qryDivipola = new ArrayList<Divipola>(divipolaMB.getDao().getAll());
                Accions = new LinkedHashMap<Long, Accion>();
                out.println("AccionMB fillAccions this.parentId " + this.parentId);
                for (Accion a : qryAccions) {
                    out.println("AccionsMB getDivipola " + a.getDivipola());
                    if (a.getDivipola() != null) {
                        try {
                            Divipola divipolaTemp = searchDivipola(a.getDivipola(), qryDivipola);
                            if (divipolaTemp != null) {
                                a.setDepartamento(divipolaTemp.getDepartamento());
                                a.setMunicipio(divipolaTemp.getMunicipio());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    Accions.put(a.getId(), a);
                }
                out.println("AccionsMB fillPagos new");
            } else {
                out.println("AccionsMB fillPagos reusing");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Divipola searchDivipola(Integer search, List<Divipola> list) {
        for (Divipola d : list) {
            if (d.getDivipola() != null) {
//                System.out.println("DivipolaDAOObjectify searchDivipola search string" + search);


                if (d.getDivipola() == search) {
//                    System.out.println("DivipolaDAOObjectify searchDivipola " + search + d.getDepartamento() + d.getMunicipio());
                    return d;
                }
            }
        }
        System.out.println("DivipolaDAOObjectify searchDivipola null");
        return null;
    }

    public AccionDAO getDao() {
        out.println("public AccionDAO getDao()");

        return dao;
    }

    public void setDao(AccionDAO dao) {
        out.println("public AccionDAO setDao()");

        this.dao = dao;
    }

    public Accion getAccion() {
        out.println("public Accion getAccion()");

        return Accion;
    }

    public void setAccion(Accion Accion) {
        out.println("public void setAccion(Accion Accion))");

        this.Accion = Accion;
    }

    public Long getIdSelecionado() {
        System.out.println("public Long getIdSelecionado() " + idSelecionado);

        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        System.out.println("public void setIdSelecionado(Long idSelecionado) "
                + idSelecionado);
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Accion> getAccions() {
        System.out.println("public Map<Long, Accion> getAccions()");

        return Accions;
    }

    public void setAccions(Map<Long, Accion> Accions) {
        System.out.println("public void setAccions(Map<Long, Accion> Accions)");

        this.Accions = (LinkedHashMap<Long, co.gov.fonada.planeacion.model.Accion>) Accions;
    }

    public DataModel<Accion> getDataAccions() {

        System.out.println("public DataModel<Accion> getDataAccions()");

        return new ListDataModel<Accion>(
                new ArrayList<Accion>(Accions.values()));
    }

    @PostConstruct
    public void reset() {
        System.out.println("public void reset()");
        this.parentId = contratoMB.getIdSelecionado();

        Accions = null;
//        fillAccions();
    }

    public void agregar() {
        System.out.println("public void agregar()");

        Accion = new Accion();
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
        out.println("Dentro de onRowSelect");
        out.println("Accion.getId " + theid);

    }

    public void buttonCrear(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();
//		Long actionPId = (Long) actionEvent.getComponent().getAttributes()
//				.get("pId");
//		out.println("buttonCrear actionPId " + actionPId);
//		this.parentId = actionPId;
        try {
            out.println("			Accion = new Accion();");
            Accion = new Accion();
            out.println("			Accion.setParent(actionPId);");
            Accion.setParent(Key.create(Contrato.class, this.parentId));
            out.println("			dao.save(Accion);");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Accion> key = f.allocateId(Accion.class);
            Accion.setId(key.getId());
            dao.save(Accion);
            // Thread.sleep(2000);
            out.println("			Accions.put(Accion.getId(), Accion); "
                    + Accion.getId());
            out.println("Guardado!!!");
            context.addMessage(null, new FacesMessage("Creado!", Accion.getId()
                    + " creado exitosamente."));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        context.getPartialViewContext().getRenderIds()
                .add("mainAccordion:formAccion:dataAccions");
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("debug buttonGuardar");

        FacesContext context = FacesContext.getCurrentInstance();
        Accion accionModel = (Accion) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "accion");

        // this.idSelecionado = accionModel.getId();
        Accion = accionModel;

        try {
            System.out.println("debug buttonGuardar try");
            dao.save(Accion);
            // Thread.sleep(2000);
//            Accions.put(Accion.getId(), Accion);
            context.addMessage(null, new FacesMessage("Guardado!",
                    "Producto almacenado exitosamente."));


            if (Accion.getVigencia2015() > (Accion.getEnero2015() + Accion.getFebrero2015() + Accion.getMarzo2015() + Accion.getAbril2015() + Accion.getMayo2015() + Accion.getJunio2015() + Accion.getJulio2015() + Accion.getAgosto2015() + Accion.getSeptiembre2015() + Accion.getOctubre2015() + Accion.getNoviembre2015() + Accion.getDiciembre2015())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2015 es mayor que la suma mensual");
                context.addMessage(null, alert);
            }
            if (Accion.getVigencia2015() < (Accion.getEnero2015() + Accion.getFebrero2015() + Accion.getMarzo2015() + Accion.getAbril2015() + Accion.getMayo2015() + Accion.getJunio2015() + Accion.getJulio2015() + Accion.getAgosto2015() + Accion.getSeptiembre2015() + Accion.getOctubre2015() + Accion.getNoviembre2015() + Accion.getDiciembre2015())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2015 es menor que la suma mensual");
                context.addMessage(null, alert);
            }

            if (Accion.getVigencia2016() > (Accion.getEnero2016() + Accion.getFebrero2016() + Accion.getMarzo2016() + Accion.getAbril2016() + Accion.getMayo2016() + Accion.getJunio2016() + Accion.getJulio2016() + Accion.getAgosto2016() + Accion.getSeptiembre2016() + Accion.getOctubre2016() + Accion.getNoviembre2016() + Accion.getDiciembre2016())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2016 es mayor que la suma mensual");
                context.addMessage(null, alert);
            }
            if (Accion.getVigencia2016() < (Accion.getEnero2016() + Accion.getFebrero2016() + Accion.getMarzo2016() + Accion.getAbril2016() + Accion.getMayo2016() + Accion.getJunio2016() + Accion.getJulio2016() + Accion.getAgosto2016() + Accion.getSeptiembre2016() + Accion.getOctubre2016() + Accion.getNoviembre2016() + Accion.getDiciembre2016())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2016 es menor que la suma mensual");
                context.addMessage(null, alert);
            }

            if (Accion.getVigencia2017() > (Accion.getEnero2017() + Accion.getFebrero2017() + Accion.getMarzo2017() + Accion.getAbril2017() + Accion.getMayo2017() + Accion.getJunio2017() + Accion.getJulio2017() + Accion.getAgosto2017() + Accion.getSeptiembre2017() + Accion.getOctubre2017() + Accion.getNoviembre2017() + Accion.getDiciembre2017())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2017 es mayor que la suma mensual");
                context.addMessage(null, alert);
            }
            if (Accion.getVigencia2017() < (Accion.getEnero2017() + Accion.getFebrero2017() + Accion.getMarzo2017() + Accion.getAbril2017() + Accion.getMayo2017() + Accion.getJunio2017() + Accion.getJulio2017() + Accion.getAgosto2017() + Accion.getSeptiembre2017() + Accion.getOctubre2017() + Accion.getNoviembre2017() + Accion.getDiciembre2017())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2017 es menor que la suma mensual");
                context.addMessage(null, alert);
            }

            if (Accion.getVigencia2018() > (Accion.getEnero2018() + Accion.getFebrero2018() + Accion.getMarzo2018() + Accion.getAbril2018() + Accion.getMayo2018() + Accion.getJunio2018() + Accion.getJulio2018() + Accion.getAgosto2018() + Accion.getSeptiembre2018() + Accion.getOctubre2018() + Accion.getNoviembre2018() + Accion.getDiciembre2018())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2018 es mayor que la suma mensual");
                context.addMessage(null, alert);
            }
            if (Accion.getVigencia2018() < (Accion.getEnero2018() + Accion.getFebrero2018() + Accion.getMarzo2018() + Accion.getAbril2018() + Accion.getMayo2018() + Accion.getJunio2018() + Accion.getJulio2018() + Accion.getAgosto2018() + Accion.getSeptiembre2018() + Accion.getOctubre2018() + Accion.getNoviembre2018() + Accion.getDiciembre2018())) {
                FacesMessage alert = new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor incorrecto", "El valor de la vigencia 2018 es menor que la suma mensual");
                context.addMessage(null, alert);
            }
        } catch (Exception ex) {
            System.out.println("debug buttonGuardar catch");
            ex.printStackTrace();

        }
//        fillAccions();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        System.out.println("public String eliminar()");

        FacesContext context = FacesContext.getCurrentInstance();
        Accion accionModel = (Accion) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "accion");

        // this.idSelecionado = accionModel.getId();
        Accion = accionModel;

        try {
            dao.remove(Accion);
            // Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Producto eliminado exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("mainAccordion:formAccion:dataAccions");



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Accion accionModel = (Accion) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "accion");
        FacesMessage msg = new FacesMessage("Edici�n cancelada", accionModel
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
        this.parentId = parentId;
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

    public void duplicate(Key<Contrato> oldParent, Key<Contrato> newParent) {
        List<Accion> qryAccions = new ArrayList<Accion>(
                dao.getByParent(oldParent.getId()));
        for (Accion a : qryAccions) {
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Accion> key = f.allocateId(Accion.class);
            a.setId(key.getId());
            a.setParent(newParent);
            a.setIsVersion(Boolean.TRUE);
        }
        dao.bulkSave(qryAccions);

    }

}
