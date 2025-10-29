package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Departamento;
import model.UsuarioAdministrativo;

import java.util.List;
import java.util.UUID;

public class DAODepartamento implements IDAODepartamento {

    private EntityManager entityManager;

    public DAODepartamento(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Departamento insert(Departamento departamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(departamento);
            transaction.commit();
            System.out.println("Departamento insertado: " + departamento.getNombre());
            return departamento;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al insertar departamento: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Departamento> getAll() {
        return entityManager.createNamedQuery("Departamento.findAll", Departamento.class)
                .getResultList();
    }

    @Override
    public Departamento getById(UUID id) {
        return entityManager.find(Departamento.class, id);
    }

    @Override
    public Departamento update(Departamento departamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Departamento updated = entityManager.merge(departamento);
            transaction.commit();
            System.out.println("Departamento actualizado: " + updated.getNombre());
            return updated;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar departamento: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean delete(UUID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Departamento departamento = entityManager.find(Departamento.class, id);
            if (departamento != null) {
                if (!departamento.getUsuarios().isEmpty()) {
                    System.out.println("No se puede eliminar el departamento. Tiene usuarios asignados.");
                    transaction.rollback();
                    return false;
                }
                entityManager.remove(departamento);
                transaction.commit();
                System.out.println("Departamento eliminado: " + id);
                return true;
            }
            transaction.rollback();
            System.out.println("Departamento no encontrado: " + id);
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar departamento: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Departamento> buscarPorNombre(String nombre) {
        return entityManager.createNamedQuery("Departamento.findByNombre", Departamento.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    @Override
    public boolean tieneUsuarios(UUID id) {
        Departamento departamento = getById(id);
        return departamento != null && !departamento.getUsuarios().isEmpty();
    }

    public List<UsuarioAdministrativo> getUsuariosPorDepartamento(UUID departamentoId) {
        return entityManager.createQuery(
                        "SELECT u FROM UsuarioAdministrativo u WHERE u.departamento.id = :departamentoId",
                        UsuarioAdministrativo.class)
                .setParameter("departamentoId", departamentoId)
                .getResultList();
    }
}