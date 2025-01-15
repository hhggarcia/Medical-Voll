package med.voll.api.domain.paciente;

public record DatosListadoPaciente(Long id,
                                   String nombre,
                                   String email,
                                   String documento) {
    public DatosListadoPaciente(Paciente datos){
        this(datos.getId(), datos.getNombre(), datos.getEmail(), datos.getDocumento());
    }
}
