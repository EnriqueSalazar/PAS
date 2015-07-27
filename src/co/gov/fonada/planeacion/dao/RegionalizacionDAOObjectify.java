package co.gov.fonada.planeacion.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import co.gov.fonada.planeacion.model.Contrato;
import co.gov.fonada.planeacion.model.Regionalizacion;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

public class RegionalizacionDAOObjectify implements Serializable,
        RegionalizacionDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Regionalizacion regionalizacion) {
        System.out.println("RegionalizacionDAOOBjectify save");

        ofy().save().entity(regionalizacion).now();
        return regionalizacion.getId();
    }

    @Override
    public List<Regionalizacion> getAll() {
        System.out.println("RegionalizacionDAOOBjectify getAll");

        try {
            return ofy().load().type(Regionalizacion.class).filter("isVersion", Boolean.FALSE).list();
        } catch (Exception ex) {
            System.out
                    .println("List<Regionalizacion> getAll() catch exception");
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void bulkSave(List<Regionalizacion> regionalizacion) {

        try {

            System.out.println("RegionalizacionDAOObjectify save success " + ofy().save().entities(regionalizacion));

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }


    @Override
    public Integer getParentCount(Long parentId) {
        System.out.println("RegionalizacionDAOOBjectify getAll()");
        return ofy().load().type(Regionalizacion.class).ancestor(Key.create(Contrato.class, parentId)).filter("isVersion", Boolean.FALSE).count();
    }

    @Override
    public List<Regionalizacion> getParentRange(int startingAt, int maxPerPage, Long parentId) {
        System.out.println("RegionalizacionDAOOBjectify getRange()");
        List<Regionalizacion> range = ofy().load().type(Regionalizacion.class).ancestor(Key.create(Contrato.class, parentId)).filter("isVersion", Boolean.FALSE).limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }


    @Override
    public Integer getAllCount() {
        System.out.println("RegionalizacionDAOOBjectify getAll()");
        return ofy().load().type(Regionalizacion.class).filter("isVersion", Boolean.FALSE).count();
    }

    @Override
    public List<Regionalizacion> getRange(int startingAt, int maxPerPage) {
        System.out.println("RegionalizacionDAOOBjectify getRange()");
        List<Regionalizacion> range = ofy().load().type(Regionalizacion.class).filter("isVersion", Boolean.FALSE).limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }

    @Override
    public List<Regionalizacion> getAllOrdered() {
        System.out.println("RegionalizacionDAOOBjectify getAll");

        try {
            return ofy().load().type(Regionalizacion.class).filter("isVersion", Boolean.FALSE).orderKey(true).list();
        } catch (Exception ex) {
            System.out
                    .println("List<Regionalizacion> getAll() catch exception");
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Regionalizacion> getByParent(Long parent) {
        // System.out.println("RegionalizacionDAOOBjectify getByParent ["+parent+"] ");
        List<Regionalizacion> list = null;
        try {
            System.out.println("RegionalizacionDAOOBjectify getByParent [" + parent);
            Query<Regionalizacion> query = ofy().load().type(Regionalizacion.class)
                    .ancestor(Key.create(Contrato.class, parent));
            list = query.list();
            System.out.println("RegionalizacionDAOOBjectify getByParent [" + parent
                    + "] full load size: " + list.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // query= query.filter("parent ==", parent);
        // System.out.println("RegionalizacionDAOOBjectify getByParent ["+parent+"] filtered size: "+query.list().size());
        return list;
    }

    @Override
    public Boolean remove(Regionalizacion regionalizacion) {
        System.out.println("RegionalizacionDAOOBjectify remove");
        try {
            ofy().delete().entity(regionalizacion).now();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }

    }

    @Override
    public Regionalizacion findById(Long id) {
        Key<Regionalizacion> k = Key.create(Regionalizacion.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllRegionalizacion() {
        ofy().clear();
        List<Key<Regionalizacion>> keys = ofy().load().type(Regionalizacion.class).filter("isVersion", Boolean.FALSE).keys().list();
        ofy().delete().keys(keys);
    }

    @Override
    public Double getSumValor(Long parent) {
        // System.out.println("RegionalizacionDAOOBjectify getByParent ["+parent+"] ");
        List<Regionalizacion> list = null;
        Double sumAportes = 0.0;
        try {
            Query<Regionalizacion> query = ofy().load().type(Regionalizacion.class)
                    .ancestor(Key.create(Contrato.class, parent)).project("valor");
            list = query.list();
            System.out.println("RegionalizacionDAOOBjectify getSumValor [" + parent
                    + "] full load size: " + list.size());
            for (Regionalizacion a : list) sumAportes = sumAportes + a.getValor();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sumAportes;
    }

    @Override
    public Double getSumBeneficiario(Long parent) {
        // System.out.println("RegionalizacionDAOOBjectify getByParent ["+parent+"] ");
        List<Regionalizacion> list = null;
        Double sumAportes = 0.0;
        try {
            Query<Regionalizacion> query = ofy().load().type(Regionalizacion.class)
                    .ancestor(Key.create(Contrato.class, parent)).project("beneficiarios");
            list = query.list();
            System.out.println("RegionalizacionDAOOBjectify getSumBeneficiario [" + parent
                    + "] full load size: " + list.size());
            for (Regionalizacion a : list) sumAportes = sumAportes + a.getBeneficiarios();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sumAportes;
    }
}
