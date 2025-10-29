package service;
import model.UsuarioAdministrativo;

import java.util.List;
import java.util.UUID;

public interface IDAOUsuarioadministrativo {
    UsuarioAdministrativo insert(UsuarioAdministrativo usuario);
    List<UsuarioAdministrativo> getAll();
    UsuarioAdministrativo getById(UUID id);
    UsuarioAdministrativo update(UsuarioAdministrativo usuario);
    boolean delete(UUID id);
    boolean desactivarUsuario(UUID id);
    List<UsuarioAdministrativo> buscarPorNombre(String nombre);
    List<UsuarioAdministrativo> buscarPorApellido(String apellido);
    List<UsuarioAdministrativo> getUsuariosPorDepartamento(UUID departamentoId);
}