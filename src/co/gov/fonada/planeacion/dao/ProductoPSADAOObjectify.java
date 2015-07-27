package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.*;
import com.google.appengine.repackaged.com.google.api.client.util.ArrayMap;
import com.googlecode.objectify.Key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ProductoPSADAOObjectify implements Serializable, ProductoPSADAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(ProductoPSA productoPSA) {
        ofy().save().entity(productoPSA);
        return productoPSA.getId();
    }

    @Override
    public List<ProductoPSA> getAll() {

        return ofy().load().type(ProductoPSA.class).list();
    }

    @Override
    public List<ProductoPSA> getAllOrdered() {
        return ofy().load().type(ProductoPSA.class).orderKey(true).list();
    }

    @Override
    public Boolean remove(ProductoPSA productoPSA) {
        ofy().delete().entity(productoPSA).now();
        return true;
    }

    @Override
    public ProductoPSA findById(Long id) {
        Key<ProductoPSA> k = Key.create(ProductoPSA.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllProductoPSA() {
        ofy().clear();
        List<Key<ProductoPSA>> keys = ofy().load().type(ProductoPSA.class).keys().list();
        System.out
                .println("ProductoPSADAOObjectify deleteAllProductoPSA keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllProductoPSA();

        }
    }

    @Override
    public List<ProductoPSA> getSome(String opc, String mod) {
        List<ProductoPSA> toReturn = new ArrayList<ProductoPSA>();
        List<ProductoPSA> toReturn2 = new ArrayList<ProductoPSA>();

        toReturn = ofy().load().type(ProductoPSA.class).filter("opcion", opc).filter("modalidad", mod).project("productoPSAs", "duracion").list();

/*		System.out.println("ProductoPSADAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public Boolean isInFilter(String filter, String value) {
        return ofy().load().type(ProductoPSA.class).filter(filter, value).count() != 0;
    }

    @Override
    public void bulkSave(List<ProductoPSA> productoPSA) {
        try {
            System.out.println("ProductoPSADAOObjectify save success " + ofy().save().entities(productoPSA).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("ProductoPSADAOOBjectify getAllCount()");
        return ofy().load().type(ProductoPSA.class).count();
    }

    /*   @Override
       public List<ProductoPSA> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
           System.out.println("ProductoPSADAOOBjectify getRange()");
           Query<ProductoPSA> query = ofy().load().type(ProductoPSA.class);
           for (Map.Entry<String, Object> entry : filters.entrySet()) {
               query = query.filter(entry.getKey(), entry.getValue());
           }
           List<ProductoPSA> range = query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
           return range;
       }*/
    @Override
    public List<ProductoPSA> getRange(int startingAt, int maxPerPage, Long parentId) {
        System.out.println("ProductoPSADAOOBjectify getRange()");
        List<ProductoPSA> range = ofy().load().type(ProductoPSA.class).ancestor(Key.create(ProductosPSA.class, parentId)).limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }


    @Override
    public List<CheckProductoPSA> getCheckRange(int startingAt, int maxPerPage, Long parentId) {
        ListaDAO listaDAO = new ListaDAOObjectify();
        Key<ProductosPSA> parentKey = Key.create(ProductosPSA.class, parentId);
        ProductosPSA productosPSA = ofy().load().key(parentKey).now();
        System.out.println("ProductoPSADAOOBjectify getRange()");
        List<ProductoPSA> base = ofy().load().type(ProductoPSA.class).ancestor(Key.create(ProductosPSA.class, parentId)).project("contrato", "producto", "divipola", "intervencion").list();
        Boolean isUnique;
        Boolean true1;
        Boolean true2;
        Boolean true3;
        Boolean true4;
        List<CheckProductoPSA> listCheck = new ArrayList<>();
        CheckProductoPSA checkP;
        Map<Key<ProductoPSA>, ProductoPSA> mapPSA = new ArrayMap<>();
        String stringProductoPAS;
        String tempString;
        List<Key<ProductoPSA>> tempList = new ArrayList<>();
        Accion accionTemp;
        List<CheckProductoPSA> range = new ArrayList<>();
        for (ProductoPSA p : base) {
            checkP = new CheckProductoPSA();
            isUnique = Boolean.TRUE;

            stringProductoPAS = listaDAO.getItem("producto", "productoPSA", p.getProducto(), "productoPND").getProductoPND();
            for (CheckProductoPSA cp : listCheck) {
                if (stringProductoPAS.equals(cp.getProductoPAS())) true1 = Boolean.TRUE;
                else true1 = Boolean.FALSE;
                if (p.getIntervencion().equals(cp.getIntervencion())) true2 = Boolean.TRUE;
                else true2 = Boolean.FALSE;
                if (p.getContrato().longValue() == cp.getContrato().longValue()) true3 = Boolean.TRUE;
                else true3 = Boolean.FALSE;
                if (p.getDivipola().intValue() == cp.getDivipola().intValue()) true4 = Boolean.TRUE;
                else true4 = Boolean.FALSE;
                if (true1 && true2 && true3 && true4) {
                    isUnique = Boolean.FALSE;
                    tempString = cp.getProductosPSA();
                    cp.setProductosPSA(tempString + "|" + p.getProducto());
                    tempList = cp.getKeys();
                    tempList.add(Key.create(parentKey, ProductoPSA.class, p.getId()));
                    cp.setKeys(tempList);
                    tempList = new ArrayList<>();
                    break;
                }
            }
            if (isUnique) {
                checkP.setProductoPAS(stringProductoPAS);
                checkP.setIntervencion(p.getIntervencion());
                checkP.setContrato(p.getContrato());
                checkP.setDivipola(p.getDivipola());
                checkP.setProductosPSA(p.getProducto());
                checkP.setVigencia(productosPSA.getVigencia());

                checkP.setEnero(0.0);
                checkP.setFebrero(0.0);
                checkP.setMarzo(0.0);
                checkP.setAbril(0.0);
                checkP.setMayo(0.0);
                checkP.setJunio(0.0);
                checkP.setJulio(0.0);
                checkP.setAgosto(0.0);
                checkP.setSeptiembre(0.0);
                checkP.setOctubre(0.0);
                checkP.setNoviembre(0.0);
                checkP.setDiciembre(0.0);


                checkP.setEnero0(0.0);
                checkP.setFebrero0(0.0);
                checkP.setMarzo0(0.0);
                checkP.setAbril0(0.0);
                checkP.setMayo0(0.0);
                checkP.setJunio0(0.0);
                checkP.setJulio0(0.0);
                checkP.setAgosto0(0.0);
                checkP.setSeptiembre0(0.0);
                checkP.setOctubre0(0.0);
                checkP.setNoviembre0(0.0);
                checkP.setDiciembre0(0.0);


                checkP.setEneroDelta(0.0);
                checkP.setFebreroDelta(0.0);
                checkP.setMarzoDelta(0.0);
                checkP.setAbrilDelta(0.0);
                checkP.setMayoDelta(0.0);
                checkP.setJunioDelta(0.0);
                checkP.setJulioDelta(0.0);
                checkP.setAgostoDelta(0.0);
                checkP.setSeptiembreDelta(0.0);
                checkP.setOctubreDelta(0.0);
                checkP.setNoviembreDelta(0.0);
                checkP.setDiciembreDelta(0.0);


                tempList.add(Key.create(parentKey, ProductoPSA.class, p.getId()));
                checkP.setKeys(tempList);
                tempList = new ArrayList<>();
                listCheck.add(checkP);
            }
        }
        /*ya tengo la lista de productos PND,
                falta poblarla con la suma de los productos que se repiten
                en el PSA*/
        Integer endingAt;
        if ((startingAt+maxPerPage)>listCheck.size()){
            endingAt=listCheck.size();
        }else{
            endingAt=startingAt+maxPerPage;
        }
        for (int i = startingAt; i < endingAt; i++) {
            checkP = listCheck.get(i);
/*            Collection<ProductoPSA> pPSA = ofy().load().keys(checkP.getKeys()).values();
            for (Key<ProductoPSA> keyPSA : checkP.getKeys()) {
                Key<ProductoPSA> bigKey=Key.create(parentKey,ProductoPSA.class,keyPSA.getId());
//                ProductoPSA prodPSA= ofy().load().key(Key.create(Key.create(ProductosPSA.class, parentId), ProductoPSA.class, keyPSA)).now();
//                Food food = ofy().cache(false).load().key(Key.create(Key.create(TableParent.class, parent.getId()), Food.class, lng)).now();
                ProductoPSA prodPSA= ofy().load().type(ProductoPSA.class).id(keyPSA.getId()).now();
//                ProductoPSA prodPSA= ofy().load().key(Key.create(ProductoPSA.class,8423)).now();
                List<Key<ProductoPSA>> kpp= ofy().load().type(ProductoPSA.class).keys().list();
                String s=prodPSA.toString();
                System.out.println("ProductoPSADAOObjectify save success " + s);
            }*/
            mapPSA = ofy().load().keys(checkP.getKeys());
            System.out.println("ProductoPSADAOObjectify save success " + mapPSA.size());

            for (Map.Entry<Key<ProductoPSA>, ProductoPSA> entry : mapPSA.entrySet()) {
                checkP.setEnero(checkP.getEnero() + entry.getValue().getEnero());
                checkP.setFebrero(checkP.getFebrero() + entry.getValue().getFebrero());
                checkP.setMarzo(checkP.getMarzo() + entry.getValue().getMarzo());
                checkP.setAbril(checkP.getAbril() + entry.getValue().getAbril());
                checkP.setMayo(checkP.getMayo() + entry.getValue().getMayo());
                checkP.setJunio(checkP.getJunio() + entry.getValue().getJunio());
                checkP.setJulio(checkP.getJulio() + entry.getValue().getJulio());
                checkP.setAgosto(checkP.getAgosto() + entry.getValue().getAgosto());
                checkP.setSeptiembre(checkP.getSeptiembre() + entry.getValue().getSeptiembre());
                checkP.setOctubre(checkP.getOctubre() + entry.getValue().getOctubre());
                checkP.setNoviembre(checkP.getNoviembre() + entry.getValue().getNoviembre());
                checkP.setDiciembre(checkP.getDiciembre() + entry.getValue().getDiciembre());
            }
            List<Key<Accion>> kpp= ofy().load().type(Accion.class).keys().list();

            accionTemp = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, checkP.getContrato())).first().now();
       /*     accionTemp = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, checkP.getContrato())).filter("producto", checkP.getProductoPAS()).first().now();
            accionTemp = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, checkP.getContrato())).filter("producto", checkP.getProductoPAS()).filter("intervencion", checkP.getIntervencion()).first().now();
            accionTemp = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, checkP.getContrato())).filter("producto", checkP.getProductoPAS()).filter("intervencion", checkP.getIntervencion()).filter("divipola", checkP.getDivipola()).first().now();
            accionTemp = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, checkP.getContrato())).filter("producto", checkP.getProductoPAS()).filter("intervencion", checkP.getIntervencion()).filter("divipola", checkP.getDivipola()).project("enero" + checkP.getVigencia(), "febrero" + checkP.getVigencia(), "marzo" + checkP.getVigencia(), "abril" + checkP.getVigencia(), "mayo" + checkP.getVigencia(), "junio" + checkP.getVigencia(), "julio" + checkP.getVigencia(), "agosto" + checkP.getVigencia(), "septiembre" + checkP.getVigencia(), "octubre" + checkP.getVigencia(), "noviembre" + checkP.getVigencia(), "diciembre" + checkP.getVigencia()).first().now();

            accionTemp = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, checkP.getContrato())).filter("producto", checkP.getProductoPAS()).filter("intervencion", checkP.getIntervencion()).filter("divipola", checkP.getDivipola()).project("enero" + checkP.getVigencia(), "febrero" + checkP.getVigencia(), "marzo" + checkP.getVigencia(), "abril" + checkP.getVigencia(), "mayo" + checkP.getVigencia(), "junio" + checkP.getVigencia(), "julio" + checkP.getVigencia(), "agosto" + checkP.getVigencia(), "septiembre" + checkP.getVigencia(), "octubre" + checkP.getVigencia(), "noviembre" + checkP.getVigencia(), "diciembre" + checkP.getVigencia()).first().now();
  */
            switch (checkP.getVigencia()) {
                case 2015:
                    checkP.setEnero0(accionTemp.getEnero2015().doubleValue());
                    checkP.setFebrero0(accionTemp.getFebrero2015().doubleValue());
                    checkP.setMarzo0(accionTemp.getMarzo2015().doubleValue());
                    checkP.setAbril0(accionTemp.getAbril2015().doubleValue());
                    checkP.setMayo0(accionTemp.getMayo2015().doubleValue());
                    checkP.setJunio0(accionTemp.getJunio2015().doubleValue());
                    checkP.setJulio0(accionTemp.getJulio2015().doubleValue());
                    checkP.setAgosto0(accionTemp.getAgosto2015().doubleValue());
                    checkP.setSeptiembre0(accionTemp.getSeptiembre2015().doubleValue());
                    checkP.setOctubre0(accionTemp.getOctubre2015().doubleValue());
                    checkP.setNoviembre0(accionTemp.getNoviembre2015().doubleValue());
                    checkP.setDiciembre0(accionTemp.getDiciembre2015().doubleValue());
                    break;
                case 2016:
                    checkP.setEnero0(accionTemp.getEnero2016().doubleValue());
                    checkP.setFebrero0(accionTemp.getFebrero2016().doubleValue());
                    checkP.setMarzo0(accionTemp.getMarzo2016().doubleValue());
                    checkP.setAbril0(accionTemp.getAbril2016().doubleValue());
                    checkP.setMayo0(accionTemp.getMayo2016().doubleValue());
                    checkP.setJunio0(accionTemp.getJunio2016().doubleValue());
                    checkP.setJulio0(accionTemp.getJulio2016().doubleValue());
                    checkP.setAgosto0(accionTemp.getAgosto2016().doubleValue());
                    checkP.setSeptiembre0(accionTemp.getSeptiembre2016().doubleValue());
                    checkP.setOctubre0(accionTemp.getOctubre2016().doubleValue());
                    checkP.setNoviembre0(accionTemp.getNoviembre2016().doubleValue());
                    checkP.setDiciembre0(accionTemp.getDiciembre2016().doubleValue());
                    break;
                case 2017:
                    checkP.setEnero0(accionTemp.getEnero2017().doubleValue());
                    checkP.setFebrero0(accionTemp.getFebrero2017().doubleValue());
                    checkP.setMarzo0(accionTemp.getMarzo2017().doubleValue());
                    checkP.setAbril0(accionTemp.getAbril2017().doubleValue());
                    checkP.setMayo0(accionTemp.getMayo2017().doubleValue());
                    checkP.setJunio0(accionTemp.getJunio2017().doubleValue());
                    checkP.setJulio0(accionTemp.getJulio2017().doubleValue());
                    checkP.setAgosto0(accionTemp.getAgosto2017().doubleValue());
                    checkP.setSeptiembre0(accionTemp.getSeptiembre2017().doubleValue());
                    checkP.setOctubre0(accionTemp.getOctubre2017().doubleValue());
                    checkP.setNoviembre0(accionTemp.getNoviembre2017().doubleValue());
                    checkP.setDiciembre0(accionTemp.getDiciembre2017().doubleValue());
                    break;
                case 2018:
                    checkP.setEnero0(accionTemp.getEnero2018().doubleValue());
                    checkP.setFebrero0(accionTemp.getFebrero2018().doubleValue());
                    checkP.setMarzo0(accionTemp.getMarzo2018().doubleValue());
                    checkP.setAbril0(accionTemp.getAbril2018().doubleValue());
                    checkP.setMayo0(accionTemp.getMayo2018().doubleValue());
                    checkP.setJunio0(accionTemp.getJunio2018().doubleValue());
                    checkP.setJulio0(accionTemp.getJulio2018().doubleValue());
                    checkP.setAgosto0(accionTemp.getAgosto2018().doubleValue());
                    checkP.setSeptiembre0(accionTemp.getSeptiembre2018().doubleValue());
                    checkP.setOctubre0(accionTemp.getOctubre2018().doubleValue());
                    checkP.setNoviembre0(accionTemp.getNoviembre2018().doubleValue());
                    checkP.setDiciembre0(accionTemp.getDiciembre2018().doubleValue());
                    break;
            }
            checkP.setEneroDelta(checkP.getEnero() - checkP.getEnero0());
            checkP.setFebreroDelta(checkP.getFebrero() - checkP.getFebrero0());
            checkP.setMarzoDelta(checkP.getMarzo() - checkP.getMarzo0());
            checkP.setAbrilDelta(checkP.getAbril() - checkP.getAbril0());
            checkP.setMayoDelta(checkP.getMayo() - checkP.getMayo0());
            checkP.setJunioDelta(checkP.getJunio() - checkP.getJunio0());
            checkP.setJulioDelta(checkP.getJulio() - checkP.getJulio0());
            checkP.setAgostoDelta(checkP.getAgosto() - checkP.getAgosto0());
            checkP.setSeptiembreDelta(checkP.getSeptiembre() - checkP.getSeptiembre0());
            checkP.setOctubreDelta(checkP.getOctubre() - checkP.getOctubre0());
            checkP.setNoviembreDelta(checkP.getNoviembre() - checkP.getNoviembre0());
            checkP.setDiciembreDelta(checkP.getDiciembre() - checkP.getDiciembre0());
            range.add(checkP);
        }


        return range;
    }


}
