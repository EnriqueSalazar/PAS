package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.DivipolaDAO;
import co.gov.fonada.planeacion.dao.DivipolaDAOObjectify;
import co.gov.fonada.planeacion.dao.ProductoPSADAO;
import co.gov.fonada.planeacion.dao.ProductoPSADAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class ProductoPSALazyList extends LazyDataModel<ProductoPSA> {

    private static final long serialVersionUID = 1L;
    private List<ProductoPSA> productoPSAs;
    private ProductoPSADAO dao;
    private Long parentId;

    public ProductoPSALazyList(Long parentId){
        this.parentId=parentId;
    }

    @Override
    public List<ProductoPSA> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new ProductoPSADAOObjectify();
        DivipolaDAO divipolaDAO = new DivipolaDAOObjectify();
        Divipola divipolaTemp;
        try {
            System.out.println("ProductoPSALazyList startingAt " + startingAt);
            System.out.println("ProductoPSALazyList maxPerPage " + maxPerPage);
            productoPSAs = dao.getRange(startingAt, maxPerPage, parentId);

            System.out.println("ProductoPSALazyList getRange " + productoPSAs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ProductoPSA a : productoPSAs) {
            try {
                divipolaTemp = divipolaDAO.getByDivipola(a.getDivipola());
                a.setDepartamento(divipolaTemp.getDepartamento());
                a.setMunicipio(divipolaTemp.getMunicipio());
            } catch (Exception e) {
                System.out.println("catch " + e.getMessage());
            }
        }
     /*   for (ProductoPSA a : productoPSAs) {
            try {
                a.setParentId(a.getParent().getId());
            } catch (Exception e) {
                e.printStackTrace();

            }
        }*/
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return productoPSAs;
    }

    @Override
    public Object getRowKey(ProductoPSA productoPSA) {
        return productoPSA.getId();
    }

    @Override
    public ProductoPSA getRowData(String productoPSAId) {
        Integer id = Integer.valueOf(productoPSAId);
        for (ProductoPSA productoPSA : productoPSAs) {
            if (id.equals(productoPSA.getId())) {
                return productoPSA;
            }
        }
        return null;
    }
}