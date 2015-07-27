package co.gov.fonada.planeacion.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.gov.fonada.planeacion.model.Etapa;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import javax.faces.model.SelectItem;

public class EtapaDAOObjectify implements Serializable, EtapaDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Etapa etapa) {
        ofy().save().entity(etapa);
        return etapa.getId();
    }

    @Override
    public List<Etapa> getAll() {

        return ofy().load().type(Etapa.class).list();
    }

    @Override
    public List<Etapa> getAllOrdered() {
        return ofy().load().type(Etapa.class).orderKey(true).list();
    }

    @Override
    public Boolean remove(Etapa etapa) {
        ofy().delete().entity(etapa).now();
        return true;
    }

    @Override
    public Etapa findById(Long id) {
        Key<Etapa> k = Key.create(Etapa.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllEtapa() {
        ofy().clear();
        List<Key<Etapa>> keys = ofy().load().type(Etapa.class).keys().list();
        System.out
                .println("EtapaDAOObjectify deleteAllEtapa keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllEtapa();

        }
    }

    @Override
    public List<Etapa> getSome(String opc, String mod) {
        List<Etapa> toReturn = new ArrayList<Etapa>();
        List<Etapa> toReturn2 = new ArrayList<Etapa>();

        toReturn = ofy().load().type(Etapa.class).filter("opcion", opc).filter("modalidad", mod).project("etapas", "duracion").list();

/*		System.out.println("EtapaDAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public List<Etapa> getSomeToCheck(String opc, String mod) {
        List<Etapa> toReturn = new ArrayList<Etapa>();
toReturn.add(ofy().load().type(Etapa.class).filter("opcion", opc).filter("modalidad", mod).filter("etapas", "Publicación de TCC").project("duracion").first().now());
toReturn.add(ofy().load().type(Etapa.class).filter("opcion", opc).filter("modalidad", mod).filter("etapas","Firma del contrato").project("duracion").first().now());

/*		System.out.println("EtapaDAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public Boolean isInFilter(String filter, String value) {
        return ofy().load().type(Etapa.class).filter(filter, value).count() != 0;
    }

    @Override
    public void bulkSave(List<Etapa> etapa) {
        try {
            System.out.println("EtapaDAOObjectify save success " + ofy().save().entities(etapa).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("EtapaDAOOBjectify getAllCount()");
        return ofy().load().type(Etapa.class).count();
    }

    @Override
    public List<Etapa> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
        System.out.println("EtapaDAOOBjectify getRange()");
        Query<Etapa> query = ofy().load().type(Etapa.class);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query = query.filter(entry.getKey(), entry.getValue());
        }
        List<Etapa> range = query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }

    @Override
    public SelectItem[] getModalidadOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        System.out.println("EtapaDAOOBjectify getModalidadOption()");

        Query<Etapa> modalidadQuery = ofy().load().type(Etapa.class).project("modalidad").distinct(Boolean.TRUE);
        List<Etapa> modalidadList = modalidadQuery.list();
        SelectItem[] modalidadOption = new SelectItem[modalidadList.size() + 1];
        modalidadOption[0] = new SelectItem("", "");
        for (int i = 0; i < modalidadList.size(); i++) {
            modalidadOption[i + 1] = new SelectItem(modalidadList.get(i).getModalidad(), modalidadList.get(i).getModalidad());
        }
        return modalidadOption;
    }


    @Override
    public List<String> getModalidadLista() {
        Query<Etapa> modalidadQuery = ofy().load().type(Etapa.class).project("modalidad").distinct(Boolean.TRUE);
        List<Etapa> modalidadList = modalidadQuery.list();
        List<String> modalidadStringEtapa = new ArrayList<>();
        for (Etapa a : modalidadList) modalidadStringEtapa.add(a.getModalidad());
        return modalidadStringEtapa;
    }

    @Override
    public List<String> getOpcionLista() {
        Query<Etapa> opcionQuery = ofy().load().type(Etapa.class).project("opcion").distinct(Boolean.TRUE);
        List<Etapa> opcionList = opcionQuery.list();
        List<String> opcionStringEtapa = new ArrayList<>();
        for (Etapa a : opcionList) opcionStringEtapa.add(a.getOpcion());
        return opcionStringEtapa;
    }


    @Override
    public SelectItem[] getOpcionOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        System.out.println("EtapaDAOOBjectify getOpcionOption()");

        Query<Etapa> opcionQuery = ofy().load().type(Etapa.class).project("opcion").distinct(Boolean.TRUE);
        List<Etapa> opcionList = opcionQuery.list();
        SelectItem[] opcionOption = new SelectItem[opcionList.size() + 1];
        opcionOption[0] = new SelectItem("", "");
        for (int i = 0; i < opcionList.size(); i++) {
            opcionOption[i + 1] = new SelectItem(opcionList.get(i).getOpcion(), opcionList.get(i).getOpcion());

        }
        return opcionOption;
    }

    @Override
    public SelectItem[] getEtapaOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        System.out.println("EtapaDAOOBjectify getEtapaOption()");

        Query<Etapa> etapaQuery = ofy().load().type(Etapa.class).project("etapas").distinct(Boolean.TRUE);
        List<Etapa> etapaList = etapaQuery.list();
        SelectItem[] etapaOption = new SelectItem[etapaList.size() + 1];
        etapaOption[0] = new SelectItem("", "");
        for (int i = 0; i < etapaList.size(); i++) {
            etapaOption[i + 1] = new SelectItem(etapaList.get(i).getEtapas(), etapaList.get(i).getEtapas());

        }
        return etapaOption;
    }

}
