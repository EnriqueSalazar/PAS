package co.gov.fonada.planeacion.mb;

import co.gov.fonada.planeacion.dao.DivipolaDAO;
import co.gov.fonada.planeacion.dao.DivipolaDAOObjectify;
import co.gov.fonada.planeacion.model.Divipola;
import co.gov.fonada.planeacion.model.DivipolaLazyList;
import co.gov.fonada.planeacion.model.PagoLazyList;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;
import static javax.faces.context.FacesContext.getCurrentInstance;

import com.lapis.jsfexporter.csv.CSVExportOptions;
import org.primefaces.model.LazyDataModel;

/**
 * Created by esalazar on 29/04/2015.
 */
@ManagedBean(name = "divipolaMB")
@ViewScoped
public class DivipolaMB implements Serializable {

    private static final long serialVersionUID = 8149777260790273299L;

    private DivipolaDAO dao;
    private Divipola Divipola;
    private Long idSelecionado;
    private LinkedHashMap<Long, Divipola> Divipolas;

    private List<SelectItem> divipolaGroup;

    private List<String> entidadList;
    private List<String> departamentoList;
    private List<String> municipioList;
    private List<Integer> divipolaList;

    private SelectItem[] entidadOption;
    private SelectItem[] departamentoOption;
    private SelectItem[] municipioOption;
    private SelectItem[] divipolaOption;
    private String municipioName;
    private String departamentoName;
    private String departamentoTemp;
    private Map<String,String> departamentoMap  = new HashMap<String, String>();
    private Map<String,Integer> municipioMap  = new HashMap<String, Integer>();

    CSVExportOptions csvExportOptions;

    public DivipolaMB() {
        dao = new DivipolaDAOObjectify();
    }

    private void fillDivipolas() {
        System.out.println("DivipolaMB starting fillDivipolas");
        try {
            List<Divipola> qryDivipolas = new ArrayList<>(dao.getAllOrdered());

/*
            Organiza la lista
*/
/*            Collections.sort(qryDivipolas, new Comparator<Divipola>() {
                @Override
                public int compare(final Divipola object1, final Divipola object2) {
                    return object1.getDepartamento().compareTo(object2.getDepartamento());
                }
            } );*/
            System.out.println("DivipolaMB qryDivipolas is " + qryDivipolas.size());
            Divipolas = new LinkedHashMap<>();

            LinkedHashSet<String> entidadSet = new LinkedHashSet<>();
            LinkedHashSet<String> departamentoSet = new LinkedHashSet<>();
            LinkedHashSet<String> municipioSet = new LinkedHashSet<>();
            LinkedHashSet<Integer> divipolaSet = new LinkedHashSet<>();
            for (Divipola a : qryDivipolas) {
                Divipolas.put(a.getId(), a);
/*                try {
                    System.out.println("DivipolaMB Divipolas put " + a.getId() + " " + a.getEntidad() + " " + a.getDepartamento() + " " + a.getMunicipio() + " " + a.getDivipola());

                } catch (Exception e) {

                }*/
                if (a.getDepartamento() != null && a.getEntidad() != null && a.getMunicipio() != null && a.getDivipola() != null && a.getDivipola() != 0 && !a.getMunicipio().equalsIgnoreCase("")) {
                    entidadSet.add(a.getEntidad());
                    departamentoSet.add(a.getDepartamento());
                    municipioSet.add(a.getMunicipio());
                    divipolaSet.add(a.getDivipola());
                }
            }
            System.out.println("DivipolaMB Divipolas is " + Divipolas.size());

            entidadList = new ArrayList<>(entidadSet);

            departamentoList = new ArrayList<>(departamentoSet);

            municipioList = new ArrayList<>(municipioSet);
            divipolaList = new ArrayList<>(divipolaSet);

            entidadOption = new SelectItem[entidadList.size() + 1];
            entidadOption[0] = new SelectItem("", "");
            for (int i = 0; i < entidadList.size(); i++) {
                entidadOption[i + 1] = new SelectItem(entidadList.get(i),
                        entidadList.get(i));
            }

            departamentoOption = new SelectItem[departamentoList.size() + 1];
            departamentoOption[0] = new SelectItem("", "");
            divipolaGroup = new ArrayList<SelectItem>();
            for (int i = 0; i < departamentoList.size(); i++) {
                departamentoOption[i + 1] = new SelectItem(departamentoList.get(i),
                        departamentoList.get(i));
                SelectItemGroup itemGroup = new SelectItemGroup(departamentoList.get(i));
                int j = 0;
                for (Divipola a : qryDivipolas) {
                    if (a.getDepartamento() != null) {
                        if (a.getDepartamento().equalsIgnoreCase(departamentoList.get(i))) {
                            if (a.getMunicipio() != null && a.getDivipola() != null && a.getDivipola() != 0 && !a.getMunicipio().equalsIgnoreCase("")) {
                                j++;
                            }

                        }
                    }
                }

                SelectItem[] municipioTemp = new SelectItem[j];
                int k = 0;
                for (Divipola a : qryDivipolas) {
                    if (a.getDepartamento() != null) {
                        if (a.getDepartamento().equalsIgnoreCase(departamentoList.get(i))) {
                            if (a.getMunicipio() != null && a.getDivipola() != null && a.getDivipola() != 0 && !a.getMunicipio().equalsIgnoreCase("")) {
//                                municipioTemp[k] = new SelectItem( a.getMunicipio(), a.getMunicipio());
                                municipioTemp[k] = new SelectItem(a.getDivipola().toString(), a.getMunicipio());
//                                System.out.println("DivipolaMB fillDivipolass " + i+" "+j+" "+k+" "+a.getDepartamento()+" | "+a.getMunicipio()+" | "+a.getDivipola());
                                k++;
                            }

                        }
                    }
                }
                try {
                    itemGroup.setSelectItems(municipioTemp);
                } catch (Exception e) {
                }
                try {
                    divipolaGroup.add(itemGroup);
                } catch (Exception e) {
                }
            }
            municipioOption = new SelectItem[municipioList.size() + 1];
            municipioOption[0] = new SelectItem("", "");
            for (int i = 0; i < municipioList.size(); i++) {
                municipioOption[i + 1] = new SelectItem(municipioList.get(i),
                        municipioList.get(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public DivipolaDAO getDao() {
        return dao;
    }

    public void setDao(DivipolaDAO dao) {
        System.out.println("debug 4");

        this.dao = dao;
    }

    public Divipola getDivipola() {
        return Divipola;
    }

    public void setDivipola(Divipola Divipola) {
        System.out.println("debug 3");

        this.Divipola = Divipola;
    }

    public Long getIdSelecionado() {
        System.out.println("getIdSelecionado " + idSelecionado);
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public Map<Long, Divipola> getDivipolas() {
        return Divipolas;
    }

    public void setDivipolas(Map<Long, Divipola> Divipolas) {

        this.Divipolas = (LinkedHashMap<Long, Divipola>) Divipolas;
    }

    public DataModel<Divipola> getDataDivipolas() {
        System.out.println("getDataDivipolas");
        if (divipolaList == null) fillDivipolas();
        System.out.println("DivipolaMB  getDataDivipolas " + Divipolas.values().size());
        return new ListDataModel<Divipola>(new ArrayList<Divipola>(Divipolas.values()));
    }

    public void reset() {
        Divipola = null;
        idSelecionado = null;
    }

    public void agregar() {
        Divipola = new Divipola();
    }

    public void editar() {
        if (idSelecionado == null) {
            return;
        }
        Divipola = Divipolas.get(idSelecionado);
    }

    public String guardar() {
        System.out.println("debug 1");
        System.out.println(Divipola.getId() + " public String guardar()");
        try {
            dao.save(Divipola);
            Thread.sleep(2000);
            Divipolas.put(Divipola.getId(), Divipola);
        } catch (Exception ex) {
            addMessage(getMessageFromI18N("msg.error.guardar.divipola"),
                    ex.getMessage());
            return "";
        }
        return "listaDivipolas";
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
            Divipola = new Divipola();
            // Divipola.setDepartamento("Vacío");
            out.println("Dentro de buttonGuardar Id ");
            ObjectifyFactory f = new ObjectifyFactory();
            Key<Divipola> key = f.allocateId(Divipola.class);
            Divipola.setId(key.getId());
            dao.save(Divipola);
            // Thread.sleep(2000);
            Divipolas.put(Divipola.getId(), Divipola);
            context.addMessage(null, new FacesMessage("Creado!",
                    "Divipola creada exitosamente."));
            context.getPartialViewContext().getRenderIds()
                    .add("form:dataDivipolas");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
//        fillDivipolas();

    }

    public void buttonReset(ActionEvent actionEvent) {

        out.println("DivipolaMB buttonReset");
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            dao.deleteAllDivipola();

            Thread.sleep(2000);
            fillDivipolas();
            context.addMessage(null, new FacesMessage("Reiniciado!",
                    "Divipolas reiniciado exitosamente."));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        // RequestContext rContext = RequestContext.getCurrentInstance();
        // rContext.update("mainAccordion:formPago:dataPagos");
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Divipola divipolaModel = (Divipola) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "divipola");
        FacesMessage msg = new FacesMessage("Edición cancelada", divipolaModel
                .getId().toString());
        context.addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Divipola ID seleccionado",
                ((Divipola) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("DivipolaMB onRowSelect " + msg);

    }

    public void setDivipolas(LinkedHashMap<Long, co.gov.fonada.planeacion.model.Divipola> divipolas) {
        Divipolas = divipolas;
    }


    public List<String> getEntidadList() {
        System.out.println("getEntidadList");
        if (divipolaList == null) fillDivipolas();
        return entidadList;
    }

    public void setEntidadList(List<String> entidadList) {
        System.out.println("setEntidadList");
        if (divipolaList == null) fillDivipolas();
        this.entidadList = entidadList;
    }

    public List<String> getDepartamentoList() {
        System.out.println("getDepartamentoList");
        if (divipolaList == null) fillDivipolas();
        return departamentoList;
    }

    public void setDepartamentoList(List<String> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<String> getMunicipioList() {
        System.out.println("getMunicipioList");

        if (divipolaList == null) fillDivipolas();
        return municipioList;
    }

    public void setMunicipioList(List<String> municipioList) {
        this.municipioList = municipioList;
    }

    public List<Integer> getDivipolaList() {
        System.out.println("getDivipolaList");

        if (divipolaList == null) fillDivipolas();
        return divipolaList;
    }

    public void setDivipolaList(List<Integer> divipolaList) {
        this.divipolaList = divipolaList;
    }

    public SelectItem[] getEntidadOption() {
        System.out.println("getEntidadOption");
        if (this.entidadOption == null) this.entidadOption=dao.getEntidadOption();
        return this.entidadOption;
    }

    public void setEntidadOption(SelectItem[] entidadOption) {
        this.entidadOption = entidadOption;
    }

    public SelectItem[] getDepartamentoOption() {
        System.out.println("getDepartamentoOption");
        if (this.departamentoOption == null) this.departamentoOption=dao.getDepartamentoOption();
        return this.departamentoOption;
    }

    public void setDepartamentoOption(SelectItem[] departamentoOption) {
        this.departamentoOption = departamentoOption;
    }

    public SelectItem[] getMunicipioOption() {
        System.out.println("getMunicipioOption");
        if (this.municipioOption == null) this.municipioOption=dao.getMunicipioOption();
        return this.municipioOption;
    }

    public void setMunicipioOption(SelectItem[] municipioOption) {
        this.municipioOption = municipioOption;
    }

    public SelectItem[] getDivipolaOption() {
        if (divipolaList == null) fillDivipolas();
        return divipolaOption;
    }

    public void setDivipolaOption(SelectItem[] divipolaOption) {
        this.divipolaOption = divipolaOption;
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

    public List<SelectItem> getDivipolaGroup() {
/*        out.println("DivipolaMB getDivipolaGroup size" + divipolaGroup.size());
        List<SelectItem> testGroup;
        SelectItemGroup g1 = new SelectItemGroup("German Cars");
        g1.setSelectItems(new SelectItem[]{new SelectItem("BMW", "BMW"), new SelectItem("Mercedes", "Mercedes"), new SelectItem("Volkswagen", "Volkswagen")});

        SelectItemGroup g2 = new SelectItemGroup("American Cars");
        g2.setSelectItems(new SelectItem[]{new SelectItem("Chrysler", "Chrysler"), new SelectItem("GM", "GM"), new SelectItem("Ford", "Ford")});

        testGroup = new ArrayList<SelectItem>();
        testGroup.add(g1);
        testGroup.add(g2);*/
//        if (divipolaList == null) fillDivipolas();

        return divipolaGroup;
    }

    public void setDivipolaGroup(List<SelectItem> divipolaGroup) {
        this.divipolaGroup = divipolaGroup;
    }


    public String getMunicipioName(Integer divipola) {
        municipioName = dao.getByDivipola(divipola).getMunicipio();
        return municipioName;
    }

    public void setMunicipioName(String municipioName) {
        this.municipioName = municipioName;
    }

    public String getDepartamentoName(Integer divipola) {
//        dao.getByDivipola()
        departamentoName = dao.getByDivipola(divipola).getDepartamento();
        return departamentoName;
    }

    public void setDepartamentoName(String departamentoName) {
        this.departamentoName = departamentoName;
    }


    public boolean isDivipola(Integer search) {
        System.out.println("isDivipola");
        if (divipolaList == null) fillDivipolas();
        for (Integer a : divipolaList) {
            if (a.intValue()==search.intValue()) {
                return true;
            }
        }
        return false;
    }

    private LazyDataModel<Divipola> lazyDivipolas = null;
    public LazyDataModel<Divipola> getAllLazyDivipolas() {
        System.out.println("DivipolasFullMB  getLazyDivipolas ");
        if (lazyDivipolas == null) lazyDivipolas = new DivipolaLazyList();
        System.out.println("DivipolasFullMB  getLazyDivipolas ready to return "+lazyDivipolas.getPageSize());

        return lazyDivipolas;
    }

    public Map<String, String> getDepartamentoMap() {
        if (this.departamentoMap!=null) this.departamentoMap=dao.getDepartamentoMap();
        return this.departamentoMap;
    }

    public void setDepartamentoMap(Map<String, String> departamentoMap) {
        this.departamentoMap = departamentoMap;
    }

    public Map<String, Integer> getMunicipioMap() {
        municipioMap=dao.getMunicipioMap(departamentoTemp);
        return municipioMap;
    }

    public void setMunicipioMap(Map<String, Integer> municipioMap) {
        this.municipioMap = municipioMap;
    }

    public String getDepartamentoTemp() {
        return departamentoTemp;
    }

    public void setDepartamentoTemp(String departamentoTemp) {
        this.departamentoTemp = departamentoTemp;
    }

    public void divipolaListener(AjaxBehaviorEvent e){
        SelectOneMenu temp = (SelectOneMenu) e.getSource();
        this.departamentoTemp = (String) temp.getValue();
    }
}
