package ar.gov.rosario.gait.frm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

@Entity
@Table(name="for_tipoFormulario")
public class TipoFormulario extends VersionableBO {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable =  false)
	private String codigo;

	@Column(nullable =  false)
	private String descripcion;

	// Constructores
	public TipoFormulario() {
		super();
	}

	// Metodos de Clase
	public static TipoFormulario getById(Long id) {
		return (TipoFormulario) FrmDAOFactory.getTipoFormularioDAO().getById(id);
	}

	public static TipoFormulario getByIdNull(Long id) {
		return (TipoFormulario) FrmDAOFactory.getTipoFormularioDAO().getByIdNull(id);
	}
	
	public static TipoFormulario getByCodigo(String codigo) {
		return FrmDAOFactory.getTipoFormularioDAO().getByCodigo(codigo);
	}

	public static List<TipoFormulario> getList() {
		return FrmDAOFactory.getTipoFormularioDAO().getList();
	}

	public static List<TipoFormulario> getListActivos() {
		return FrmDAOFactory.getTipoFormularioDAO().getListActiva();
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	// Validaciones
	public boolean validateCreate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}

		// Validaciones de Negocio

		return true;
	}

	public boolean validateUpdate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}

		// Validaciones de Negocio

		return true;
	}

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones

		if (StringUtil.isNullOrEmpty(getDescripcion())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					FrmError.TIPOFORMULARIO_DESCRIPCION);
		}
		
		if (StringUtil.isNullOrEmpty(getCodigo())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					FrmError.TIPOFORMULARIO_CODIGO);
		}
		
		if (hasError()) {
			return false;
		}

		return true;
	}
	
	
}