package co.gov.fonada.planeacion.model;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;


@Cache
@Entity
public class RegionalizacionV implements Serializable {
	
	private static final long serialVersionUID = -5521987295840853766L;

	@Id
	private Long id;
	@Parent Key<Contrato> parent;

	/**
	 * @return the parent
	 */
	public Key<Contrato> getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Key<Contrato> parent) {
		this.parent = parent;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	private Long parentId;
	private String departamento;
	private String municipio;

	public Integer getDivipola() {
		return divipola;
	}

	public void setDivipola(Integer divipola) {
		this.divipola = divipola;
	}

	private Integer divipola;
	private Float valor;	
	private Float beneficiarios;

	/**
	 * @return the beneficiarios
	 */
	public Float getBeneficiarios() {
		return beneficiarios;
	}
	/**
	 * @param beneficiarios the beneficiarios to set
	 */
	public void setBeneficiarios(Float beneficiarios) {
		this.beneficiarios = beneficiarios;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * @return the valor
	 */
	public Float getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Float valor) {
		this.valor = valor;
	}
	/**
	 * 
	 */


	

}
