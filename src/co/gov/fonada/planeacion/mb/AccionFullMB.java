package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.AccionDAO;
import co.gov.fonada.planeacion.dao.AccionDAOObjectify;
import co.gov.fonada.planeacion.model.Accion;
import co.gov.fonada.planeacion.model.AccionLazyList;
import co.gov.fonada.planeacion.model.Contrato;
import co.gov.fonada.planeacion.model.Divipola;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.System.out;

//import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@ViewScoped
public class AccionFullMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277299790273226L;

    private AccionDAO dao;

    private DivipolaMB divipolaMB = new DivipolaMB();

    private Accion Accion;

    private Long idSelecionado;

    private LinkedHashMap<Long, Accion> Accions;

    private Long parentId;

    private CSVExportOptions csvExportOptions;
    private List<Accion> accionList;
    private LazyDataModel<Accion> lazyAccions = null;


    public AccionFullMB() {

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
        dao = new AccionDAOObjectify();
//		fillAccions();
    }

    public void actualizar() {
        out.println("public void actualizar()");
    }


    public LazyDataModel<Accion> getAllLazyAccions() {
        System.out.println("AccionsFullMB  getLazyAccions ");
        if (lazyAccions == null) lazyAccions = new AccionLazyList();
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
//                System.out.println("DivipolaDAOObjectify searchDivipola search int " + search+" "+d.getDivipola());

                if (d.getDivipola().intValue() == search.intValue()) {
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


    @PostConstruct
    public void reset() {

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
            Accions.put(Accion.getId(), Accion);
            out.println("Guardado!!!");
            context.addMessage(null, new FacesMessage("Creado!", Accion.getId()
                    + " creado exitosamente."));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fillAccions();
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
            Accions.put(Accion.getId(), Accion);
            context.addMessage(null, new FacesMessage("Guardado!",
                    "Accion almacenado exitosamente."));
        } catch (Exception ex) {
            System.out.println("debug buttonGuardar catch");
            ex.printStackTrace();

        }
        fillAccions();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        System.out.println("public String eliminar()");

        FacesContext context = FacesContext.getCurrentInstance();
        Accion accionModel = (Accion) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "accion");

        // this.idSelecionado = accionModel.getId();
        Accion = accionModel;

        try {
            Accions.remove(Accion.getId());
            dao.remove(Accion);
            // Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Accion eliminado exitosamente."));
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
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=accions.csv");
        externalContext.setResponseContentType("text/csv");

        try {

            dao = new AccionDAOObjectify();
            List<Accion> qryAccions = new ArrayList<Accion>(
                    dao.getAll());
            OutputStream csvOut = externalContext.getResponseOutputStream();
            BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(csvOut, StandardCharsets.ISO_8859_1));
            /*EL CSV nunca debe iniciar como ID porque Excel lo toma por formato SYLK*/
            csvWriter.append("Contrato|Producto|Divipola|Intervención|" +
                    "Vigencia2015|" + "Vigencia2016|" + "Vigencia2017|" +
                    "Vigencia2018|" +
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
                    "Octubre2018|" + "Noviembre2018|" + "Diciembre2018" + "\n");

            for (Accion a : qryAccions) {
                csvWriter.append(
                        a.getParent().getId() + "|" + a.getProducto() + "|" + a.getDivipola() + "|" + a.getIntervencion() + "|"
                                + a.getVigencia2015() + "|" + a.getVigencia2016() + "|" + a.getVigencia2017() + "|"
                                + a.getVigencia2018() + "|"
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
                                + a.getOctubre2018() + "|" + a.getNoviembre2018() + "|" + a.getDiciembre2018() + "\n");
            }

            csvWriter.flush();
            csvWriter.close();
            csvOut.close();
            context.responseComplete();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public DataModel<Accion> getDataAccions() {
        System.out.println("AccionFullMB getDataAccions");

        fillDataAccions();
        if (accionList != null) return new ListDataModel<Accion>(accionList);

        return new ListDataModel<>();
    }

    public void fillDataAccions() {
        System.out.println("AccionFullMB fillDataAccions");

        AccionDAO dao = new AccionDAOObjectify();
        List<Accion> qryAccions = new ArrayList<Accion>(
                dao.getAll());
        List<Divipola> qryDivipola = new ArrayList<Divipola>(divipolaMB.getDao().getAll());


        for (Accion a : qryAccions) {
            a.setParentId(a.getParent().getId());
            if (a.getDivipola() != null) {
                System.out.println("AccionFullMB fillDataAccions getDivipola " + a.getDivipola());

                try {
                    Divipola divipolaTemp = searchDivipola(a.getDivipola(), qryDivipola);
                    if (divipolaTemp != null) {
                        System.out.println("AccionFullMB fillDataAccions " + divipolaTemp.getDepartamento() + " " + divipolaTemp.getMunicipio());

                        a.setDepartamento(divipolaTemp.getDepartamento());
                        a.setMunicipio(divipolaTemp.getMunicipio());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (qryAccions != null) accionList = new ArrayList<Accion>(qryAccions);


    }
}
