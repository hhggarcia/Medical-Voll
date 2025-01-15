package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.validaciones.ValidadorConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorConsultas> validadores;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){
        if (!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe el paciente seleccionado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe el medico seleccionado");
        }
        //validaciones
        validadores.forEach(v -> v.validar(datos));

        var medico = elegirMedico(datos);

        if (medico == null){
            throw new ValidacionException("No existe un medico disponible en ese horario");
        }

        var paciente = pacienteRepository.getReferenceById(datos.idPaciente());
        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad() == null){
            throw new ValidacionException("Es necesario elegir una especialidad cuando no se elige un medico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad().name(), datos.fecha());
    }

    public void cancelar(@Valid DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
