package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.ProductosPSA;

import java.util.List;
import java.util.Map;

public interface ProductosPSADAO {

	
	
	Long save(ProductosPSA productosPSA);

	List<ProductosPSA> getAll();

	Boolean remove(ProductosPSA productosPSA);
	

	ProductosPSA findById(Long id);

	void deleteAllProductosPSA();

	List<ProductosPSA> getSome(String opc, String mod);

	List<ProductosPSA> getAllOrdered();

	Boolean isInFilter(String filter, String value);

	void bulkSave(List<ProductosPSA> productosPSA);

	Integer getAllCount();

	List<ProductosPSA> getRange(int startingAt, int maxPerPage, Map<String, Object> filters);

}
