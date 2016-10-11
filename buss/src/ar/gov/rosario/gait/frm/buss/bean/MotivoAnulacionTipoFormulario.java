package ar.gov.rosario.gait.frm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

@Entity
@Table(name="for_motivoAnulacionTipoFormulario")
public class MotivoAnulacionTipoFormulario extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column(nullable =  false)
	private String descripcion;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idTipoFormulario")
	private TipoFormulario tipoFormulario;

	// Constructores
	public MotivoAnulacionTipoFormulario() {
		super();
	}

	// Metodos de Clase
	public static MotivoAnulacionTipoFormulario getById(Long id) {
		return (MotivoAnulacionTipoFormulario) FrmDAOFactory.getMotivoAnulacionTipoFormularioDAO().getById(id);
	}

	public static MotivoAnulacionTipoFormulario getByIdNull(Long id) {
		return (MotivoAnulacionTipoFormulario) FrmDAOFactory.getMotivoAnulacionTipoFormularioDAO().getByIdNull(id);
	}

	public static List<MotivoAnulacionTipoFormulario> getList() {
		return FrmDAOFactory.getMotivoAnulacionTipoFormularioDAO().getList();
	}

	public static List<MotivoAnulacionTipoFormulario> getListActivos() {
		return FrmDAOFactory.getMotivoAnulacionTipoFormularioDAO().getListActiva();
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
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
					FrmError.MOTIVOANULACIONTIPOFORMULARIO_DESCRIPCION);
		}
		if (getTipoFormulario()==null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					FrmError.MOTIVOCIERRETIPOFORMULARIO_TIPOFORMULARIO);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}