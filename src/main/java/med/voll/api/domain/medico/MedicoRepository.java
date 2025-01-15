package med.voll.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query(value = """
            SELECT * FROM medicos m
            WHERE m.activo = true AND m.especialidad = :especialidad
            AND m.id NOT IN(
                SELECT c.medico.id FROM consultas c
                WHERE c.fecha = :fecha
            )
            ORDER BY rand()
            LIMIT 1
        """, nativeQuery = true)
    Medico elegirMedicoAleatorioDisponibleEnLaFecha(String especialidad,
                                                    @NotNull @Future LocalDateTime fecha);

}
