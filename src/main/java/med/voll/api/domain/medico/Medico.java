package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.Direccion;

import java.util.List;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    public Medico(DatosMedico datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.documento = datos.documento();
        this.telefono = datos.telefono();
        this.especialidad = datos.especialidad();
        this.direccion = new Direccion(datos.datosDireccion());
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarMedico actualizarMedico) {
        if (actualizarMedico.nombre() != null){
            this.nombre = actualizarMedico.nombre();
        }
        if (actualizarMedico.documento() != null){
            this.documento = actualizarMedico.documento();
        }
        if (actualizarMedico.direccion() != null){
            this.direccion = direccion.actualizarDatos(actualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
