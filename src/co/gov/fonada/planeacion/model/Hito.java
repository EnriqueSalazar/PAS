package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Cache
@Entity
public class Hito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853784L;

	@Id
	private Long id;



	@Parent Key<Seguimiento> parent;
	private Long parentId;
	@Index
	private Long contrato;
	@Index
	private Date TCCpub;
	@Index
	private Date contratoSig;
	@Ignore
	private Date TCC1;
	@Ignore
	private Date sig1;
	@Ignore
	private Long deltaTCC;
	@Ignore
	private Long deltaSig;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		System.out.println(id + " setId");
		
		this.id = id;
	}

	public Key<Seguimiento> getParent() {
		return parent;
	}

	public void setParent(Key<Seguimiento> parent) {
		this.parent = parent;
	}
	
	
	/**
	 * @return the TCCpub
	 */
	public Date getTCCpub() {
		return TCCpub;
	}

	/**
	 * @param TCCpub
	 *            the TCCpub to set
	 */
	public void setTCCpub(Date TCCpub) {
		this.TCCpub = TCCpub;
	}

	/**
	 * @return the contratoSig
	 */
	public Date getContratoSig() {
		return contratoSig;
	}

	/**
	 * @param contratoSig
	 *            the contratoSig to set
	 */
	public void setContratoSig(Date contratoSig) {
		this.contratoSig = contratoSig;
	}


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getContrato() {
		return contrato;
	}

	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}

	public Date getTCC1() {
		return TCC1;
	}

	public void setTCC1(Date TCC1) {
		this.TCC1 = TCC1;
	}

	public Date getSig1() {
		return sig1;
	}

	public void setSig1(Date sig1) {
		this.sig1 = sig1;
	}

	public Long getDeltaTCC() {
		return deltaTCC;
	}

	public void setDeltaTCC(Long deltaTCC) {
		this.deltaTCC = deltaTCC;
	}

	public Long getDeltaSig() {
		return deltaSig;
	}

	public void setDeltaSig(Long deltaSig) {
		this.deltaSig = deltaSig;
	}
}
