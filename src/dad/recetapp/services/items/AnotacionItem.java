package dad.recetapp.services.items;

public class AnotacionItem {
	
	private Long id;
	private String anotaciones;
	private TipoAnotacionItem tipo;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAnotaciones() {
		return anotaciones;
	}
	
	public void setAnotaciones(String descripcion) {
		this.anotaciones = descripcion;
	}
	
	public TipoAnotacionItem getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoAnotacionItem tipo) {
		this.tipo = tipo;
	}
	
	
}
