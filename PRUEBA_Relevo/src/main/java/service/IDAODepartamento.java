package service;

import model.Departamento;
import java.util.List;
import java.util.UUID;

public interface IDAODepartamento {
    Departamento insert(Departamento departamento);
    List<Departamento> getAll();
    Departamento getById(UUID id);
    Departamento update(Departamento departamento);
    boolean delete(UUID id);
    List<Departamento> buscarPorNombre(String nombre);
    boolean tieneUsuarios(UUID id);
}