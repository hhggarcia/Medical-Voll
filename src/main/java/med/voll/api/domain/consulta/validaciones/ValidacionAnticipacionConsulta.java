package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionAnticipacionConsulta implements ValidadorConsultas {
    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaMinutos = Duration.between(ahora, fechaConsulta).toMinutes();

        if (diferenciaMinutos < 30){
            throw new ValidacionException("Horario seleccionado con menos de 30 minutos de anticipacion!");
        }
    }
}
