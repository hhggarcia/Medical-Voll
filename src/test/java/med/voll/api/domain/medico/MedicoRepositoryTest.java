package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.MotivoCancelacion;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Null si el medico existe pero no tiene disponible la fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario1() {
        // given o arrange
        var lunesSig10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1", "medico1@gmail.com", "098765", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Paciente1", "paciente1@gmail.com", "12345");
        registrarConsulta(medico, paciente, lunesSig10);

        // when o act
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA.name(),
               lunesSig10);

        // then o assert
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Medico si el medico existe disponible en la fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario2() {

        var lunesSig10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("Medico1", "medico1@gmail.com", "098765", Especialidad.CARDIOLOGIA);

        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA.name(),
                lunesSig10);

        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(null, medico, paciente, fecha, MotivoCancelacion.OTROS));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento){
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosMedico(
                nombre,
                email,
                documento,
                "01234567",
                especialidad,
                datosDireccion()
        );
    }

    private DatosPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosPaciente(
                nombre,
                email,
                "0897653412",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "calle x",
                "distrito y",
                "ciudad f",
                "123",
                "a"
        );
    }
}