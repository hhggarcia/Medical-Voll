package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraHorarioConsulta implements ValidadorConsultas{

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioCierreClinica = fechaConsulta.getHour() > 18;

        if (domingo || horarioAperturaClinica || horarioCierreClinica){
                throw new ValidacionException("Horario seleccionado fuera del atendimiento de la clinica!");
        }
    }
}
