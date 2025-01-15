package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.Direccion;

import java.util.List;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Embedded
    private Direccion direccion;
    private Boolean activo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    public Paciente(DatosPaciente datos) {
        this.nombre = datos.nombre();
        this.telefono = datos.telefono();
        this.email = datos.email();
        this.documento = datos.documento();
        this.direccion = new Direccion(datos.direccion());
        this.activo = true;
    }

    public void actualizar(DatosActualizarPaciente datos){
        if (datos.nombre() != null){
            this.nombre = datos.nombre();
        }

        if (datos.telefono() != null){
            this.telefono = datos.telefono();
        }

        if (datos.direccion() != null){
            this.direccion.actualizarDatos(datos.direccion());
        }
    }

    public void eliminar(){
        this.activo = false;
    }
}
