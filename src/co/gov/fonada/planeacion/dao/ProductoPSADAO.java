package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.CheckProductoPSA;
import co.gov.fonada.planeacion.model.ProductoPSA;

import java.util.List;

public interface ProductoPSADAO {


    Long save(ProductoPSA productoPSA);

    List<ProductoPSA> getAll();

    Boolean remove(ProductoPSA productoPSA);


    ProductoPSA findById(Long id);

    void deleteAllProductoPSA();

    List<ProductoPSA> getSome(String opc, String mod);

    List<ProductoPSA> getAllOrdered();

    Boolean isInFilter(String filter, String value);

    void bulkSave(List<ProductoPSA> productoPSA);

    Integer getAllCount();

    List<ProductoPSA> getRange(int startingAt, int maxPerPage, Long parentId);
    List<CheckProductoPSA> getCheckRange(int startingAt, int maxPerPage, Long parentId);
}
