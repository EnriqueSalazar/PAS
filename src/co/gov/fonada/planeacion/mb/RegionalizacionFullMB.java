package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.PagoDAO;
import co.gov.fonada.planeacion.dao.PagoDAOObjectify;
import co.gov.fonada.planeacion.dao.RegionalizacionDAO;
import co.gov.fonada.planeacion.dao.RegionalizacionDAOObjectify;
import co.gov.fonada.planeacion.model.*;
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
public class RegionalizacionFullMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8149277299790273226L;


    private RegionalizacionDAO dao;


    private DivipolaMB divipolaMB = new DivipolaMB();

    private Regionalizacion Regionalizacion;

    private Long idSelecionado;

    private LinkedHashMap<Long, Regionalizacion> Regionalizacions;

    private Long parentId;
    private CSVExportOptions csvExportOptions;
    private List<Regionalizacion> regionalizacionList;

    public RegionalizacionFullMB() {

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

        out.println("");
        out.println("public RegionalizacionMB()");
        dao = new RegionalizacionDAOObjectify();
        // fillRegionalizacions();
    }

    public void actualizar() {
        out.println("public void actualizar()");
    }

    private LazyDataModel<Regionalizacion> lazyRegionalizacions = null;
    public LazyDataModel<Regionalizacion> getAllLazyRegionalizacions() {
        System.out.println("RegionalizacionsFullMB  getLazyRegionalizacions ");
        if (lazyRegionalizacions == null) lazyRegionalizacions = new RegionalizacionLazyList();
        System.out.println("RegionalizacionsFullMB  getLazyRegionalizacions ready to return "+lazyRegionalizacions.getPageSize());

        return lazyRegionalizacions;
    }
    private void fillRegionalizacions() {

        try {

            if (Regionalizacions == null) {
                List<Regionalizacion> qryRegionalizacions = new ArrayList<Regionalizacion>(
                        dao.getByParent(this.parentId));
                List<Divipola> qryDivipola = new ArrayList<Divipola>(divipolaMB.getDao().getAll());
                Regionalizacions = new LinkedHashMap<Long, Regionalizacion>();
                out.println("RegionalizacionsMB fillRegionalizacions this.parentId " + this.parentId);
                for (Regionalizacion a : qryRegionalizacions) {
                    out.println("RegionalizacionsMB getDivipola " + a.getDivipola());
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
                    Regionalizacions.put(a.getId(), a);

                }
                out.println("RegionalizacionsMB fillRegionalizacions new");

            } else {
                out.println("RegionalizacionsMB fillRegionalizacions reusing");
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


    public RegionalizacionDAO getDao() {
        out.println("public RegionalizacionDAO getDao()");

        return dao;
    }

    public void setDao(RegionalizacionDAO dao) {
        out.println("public RegionalizacionDAO setDao()");

        this.dao = dao;
    }

    public Regionalizacion getRegionalizacion() {
        out.println("public Regionalizacion getRegionalizacion()");

        return Regionalizacion;
    }

    public void setRegionalizacion(Regionalizacion Regionalizacion) {
        out.println("public void setRegionalizacion(Regionalizacion Regionalizacion))");

        this.Regionalizacion = Regionalizacion;
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

    public Map<Long, Regionalizacion> getRegionalizacions() {
        System.out
                .println("public Map<Long, Regionalizacion> getRegionalizacions()");

        return Regionalizacions;
    }

    public void setRegionalizacions(Map<Long, Regionalizacion> Regionalizacions) {
        System.out
                .println("public void setRegionalizacions(Map<Long, Regionalizacion> Regionalizacions)");

        this.Regionalizacions = (LinkedHashMap<Long, co.gov.fonada.planeacion.model.Regionalizacion>) Regionalizacions;
    }


    @PostConstruct
    public void reset() {

        Regionalizacions = null;

    }

    public void agregar() {
        System.out.println("public void agregar()");

        Regionalizacion = new Regionalizacion();
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
        out.println("Regionalizacion.getId " + theid);

    }

    public void buttonCrear(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();
        // Long actionPId = (Long) actionEvent.getComponent().getAttributes()
        // .get("pId");
        // out.println("buttonCrear actionPId " + actionPId);
        // this.parentId = actionPId;
        try {
            out.println("			Regionalizacion = new Regionalizacion();");
            Regionalizacion = new Regionalizacion();
            out.println("			Regionalizacion.setParent(actionPId);");
            Regionalizacion.setParent(Key.create(Contrato.class, this.parentId));
            out.println("			dao.save(Regionalizacion);");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Regionalizacion> key = f.allocateId(Regionalizacion.class);
            Regionalizacion.setId(key.getId());
            dao.save(Regionalizacion);
            // Thread.sleep(2000);
            out.println("			Regionalizacions.put(Regionalizacion.getId(), Regionalizacion); "
                    + Regionalizacion.getId());
            Regionalizacions.put(Regionalizacion.getId(), Regionalizacion);
            out.println("Guardado!!!");
            context.addMessage(null, new FacesMessage("Creado!",
                    Regionalizacion.getId() + " creado exitosamente."));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fillRegionalizacions();
        context.getPartialViewContext()
                .getRenderIds()
                .add("mainAccordion:formRegionalizacion:dataRegionalizacions");
    }

    public void buttonGuardar(RowEditEvent actionEvent) {
        System.out.println("debug buttonGuardar");

        FacesContext context = FacesContext.getCurrentInstance();
        Regionalizacion regionalizacionModel = (Regionalizacion) context
                .getELContext().getELResolver()
                .getValue(context.getELContext(), null, "regionalizacion");

        // this.idSelecionado = regionalizacionModel.getId();
        Regionalizacion = regionalizacionModel;

        try {
            System.out.println("debug buttonGuardar try");
            dao.save(Regionalizacion);
            // Thread.sleep(2000);
            Regionalizacions.put(Regionalizacion.getId(), Regionalizacion);
            context.addMessage(null, new FacesMessage("Guardado!",
                    "Regionalizacion almacenado exitosamente."));
        } catch (Exception ex) {
            System.out.println("debug buttonGuardar catch");
            ex.printStackTrace();

        }
        fillRegionalizacions();
    }

    public void buttonEliminar(ActionEvent actionEvent) {
        System.out.println("public String eliminar()");

        FacesContext context = FacesContext.getCurrentInstance();
        Regionalizacion regionalizacionModel = (Regionalizacion) context
                .getELContext().getELResolver()
                .getValue(context.getELContext(), null, "regionalizacion");

        // this.idSelecionado = regionalizacionModel.getId();
        Regionalizacion = regionalizacionModel;

        try {
            Regionalizacions.remove(Regionalizacion.getId());
            dao.remove(Regionalizacion);
            // Thread.sleep(2000);
            context.addMessage(null, new FacesMessage("Eliminado!",
                    "Regionalizacion eliminado exitosamente."));
            context.getPartialViewContext()
                    .getRenderIds()
                    .add("mainAccordion:formRegionalizacion:dataRegionalizacions");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Regionalizacion regionalizacionModel = (Regionalizacion) context
                .getELContext().getELResolver()
                .getValue(context.getELContext(), null, "regionalizacion");
        FacesMessage msg = new FacesMessage("Edici�n cancelada",
                regionalizacionModel.getId().toString());
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
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=regionalizacions.csv");
        externalContext.setResponseContentType("text/csv");

        try {

            dao = new RegionalizacionDAOObjectify();
            List<Regionalizacion> qryRegionalizacions = new ArrayList<Regionalizacion>(
                    dao.getAll());
            OutputStream csvOut = externalContext.getResponseOutputStream();
            BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(csvOut, StandardCharsets.ISO_8859_1));
            /*EL CSV nunca debe iniciar como ID porque Excel lo toma por formato SYLK*/
            csvWriter.append("Contrato|" +
                    "Divipola|" +
                    "Valor|" +
                    "Beneficiarios" + "\n");

            for (Regionalizacion a : qryRegionalizacions) {
                csvWriter.append(
                        a.getParent().getId() + "|"
                                + a.getDivipola() + "|"
                                + a.getValor() + "|"
                                + a.getBeneficiarios() + "\n");
            }
            csvWriter.flush();
            csvWriter.close();
            csvOut.close();
            context.responseComplete();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public DataModel<Regionalizacion> getDataRegionalizacions() {
        fillDataRegionalizacions();
        if (regionalizacionList!=null) return  new ListDataModel<Regionalizacion>(regionalizacionList);
        return new ListDataModel<>();
    }

    public void fillDataRegionalizacions() {
        RegionalizacionDAO dao = new RegionalizacionDAOObjectify();
        List<Regionalizacion> qryRegionalizacions = new ArrayList<Regionalizacion>(
                dao.getAll());
        List<Divipola> qryDivipola = new ArrayList<Divipola>(divipolaMB.getDao().getAll());
        for (Regionalizacion a : qryRegionalizacions) {
            a.setParentId(a.getParent().getId());
            if (a.getDivipola() != null) {
                System.out.println("RegionalizacionFullMB fillDataRegionalizacions getDivipola " + a.getDivipola());

                try {
                    Divipola divipolaTemp = searchDivipola(a.getDivipola(), qryDivipola);
                    if (divipolaTemp != null) {
                        System.out.println("RegionalizacionFullMB fillDataRegionalizacions " + divipolaTemp.getDepartamento() + " " + divipolaTemp.getMunicipio());

                        a.setDepartamento(divipolaTemp.getDepartamento());
                        a.setMunicipio(divipolaTemp.getMunicipio());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (qryRegionalizacions!=null) regionalizacionList= new ArrayList<Regionalizacion>(qryRegionalizacions);
    }
}
