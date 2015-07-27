package co.gov.fonada.planeacion.mb;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;


import co.gov.fonada.planeacion.dao.VersionesDAO;
import co.gov.fonada.planeacion.dao.VersionesDAOObjectify;
import co.gov.fonada.planeacion.model.Versiones;

@ManagedBean
@ViewScoped
public class VersionesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149277260790273226L;

	

	private VersionesDAO dao;
	
	private Versiones contrato;

	private Long idSelecionado;
	

	private LinkedHashMap<Long, Versiones> contratos;
	
	public VersionesMB() {
		dao = new VersionesDAOObjectify();
		fillContratos();
	}
	
	

	public void actualizar() {
		fillContratos();
	}	

	private void fillContratos() {
		try {
			List<Versiones> qryContratos = new ArrayList<Versiones>(dao.getAll());
			contratos = new LinkedHashMap<Long, Versiones>();
			for (Versiones a: qryContratos) {
				contratos.put(a.getId(), a);
			}
			
		} catch(Exception ex) {
			addMessage(getMessageFromI18N("msg.erro.listar.contrato"), ex.getMessage());
		}
	}


	public VersionesDAO getDao() {
		return dao;
	}

	public void setDao(VersionesDAO dao) {
		this.dao = dao;
	}

	public Versiones getContrato() {
		return contrato;
	}

	public void setContrato(Versiones contrato) {
		this.contrato = contrato;
	}

	public Long getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	public Map<Long, Versiones> getContratos() {
		return contratos;
	}

	public void setContratos(Map<Long, Versiones> contratos) {
		this.contratos = (LinkedHashMap<Long, Versiones>) contratos;
	}
	
	
	public DataModel<Versiones> getDataContratos() {
		return new ListDataModel<Versiones>(new ArrayList<Versiones>(contratos.values()));
	}

	public void reset() {
		contrato = null;
		idSelecionado = null;
	}
	
	public void agregar(){
		contrato = new Versiones();
	}
	
	public void editar() {
		if (idSelecionado == null) {
			return;
		}
		contrato = contratos.get(idSelecionado);
	}
	
	
	public String guardar() {
		try {
			dao.save(contrato);
			contratos.put(contrato.getId(), contrato);
		} catch(Exception ex) {
			addMessage(getMessageFromI18N("msg.error.guardar.contrato"), ex.getMessage());
			return "";
		}
		return "listaContratos";
	}
		
	
	public String eliminar() {
		try {
			dao.remove(contrato);
			contratos.remove(contrato.getId());
		} catch(Exception ex) {
			addMessage(getMessageFromI18N("msg.error.eliminar.contrato"), ex.getMessage());
			return "";
		}
		return "listaContratos";
	}
		
	
	private String getMessageFromI18N(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages_labels", getCurrentInstance().getViewRoot().getLocale());
		return bundle.getString(key);
	}
	

	private void addMessage(String summary, String detail) {
		getCurrentInstance().addMessage(null, new FacesMessage(summary, summary.concat("<br/>").concat(detail)));
	}
	
}
