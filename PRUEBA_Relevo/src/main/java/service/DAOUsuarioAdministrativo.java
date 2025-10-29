package service;

import jakarta.persistence.*;
import model.UsuarioAdministrativo;
import java.util.List;
import java.util.UUID;

public class DAOUsuarioAdministrativo implements IDAOUsuarioadministrativo {

    private EntityManager entityManager;

    public DAOUsuarioAdministrativo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UsuarioAdministrativo insert(UsuarioAdministrativo usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(usuario);
            transaction.commit();
            System.out.println("Usuario insertado: " + usuario.getNombre() + " " + usuario.getApellido() +
                    " en departamento: " + usuario.getDepartamento().getNombre());
            return usuario;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al insertar usuario: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UsuarioAdministrativo> getAll() {
        return entityManager.createNamedQuery("UsuarioAdministrativo.findAll", UsuarioAdministrativo.class)
                .getResultList();
    }

    @Override
    public UsuarioAdministrativo getById(UUID id) {
        return entityManager.find(UsuarioAdministrativo.class, id);
    }

    @Override
    public UsuarioAdministrativo update(UsuarioAdministrativo usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            UsuarioAdministrativo updated = entityManager.merge(usuario);
            transaction.commit();
            System.out.println("Usuario actualizado: " + updated.getNombre() + " " + updated.getApellido());
            return updated;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean delete(UUID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            UsuarioAdministrativo usuario = entityManager.find(UsuarioAdministrativo.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
                transaction.commit();
                System.out.println("Usuario eliminado: " + id);
                return true;
            }
            transaction.rollback();
            System.out.println("Usuario no encontrado: " + id);
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            throw e;
        }
    }

    public List<UsuarioAdministrativo> getActivos() {
        return entityManager.createNamedQuery("UsuarioAdministrativo.findActivos", UsuarioAdministrativo.class)
                .getResultList();
    }

    @Override
    public boolean desactivarUsuario(UUID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            int updated = entityManager.createNamedQuery("UsuarioAdministrativo.desactivar")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            if (updated > 0) {
                System.out.println("Usuario desactivado: " + id);
                return true;
            } else {
                System.out.println("Usuario no encontrado para desactivar: " + id);
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al desactivar usuario: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UsuarioAdministrativo> buscarPorNombre(String nombre) {
        return entityManager.createQuery(
                        "SELECT u FROM UsuarioAdministrativo u WHERE u.nombre LIKE :nombre", UsuarioAdministrativo.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    @Override
    public List<UsuarioAdministrativo> buscarPorApellido(String apellido) {
        return entityManager.createQuery(
                        "SELECT u FROM UsuarioAdministrativo u WHERE u.apellido LIKE :apellido", UsuarioAdministrativo.class)
                .setParameter("apellido", "%" + apellido + "%")
                .getResultList();
    }

    @Override
    public List<UsuarioAdministrativo> getUsuariosPorDepartamento(UUID departamentoId) {
        return entityManager.createNamedQuery("UsuarioAdministrativo.findByDepartamento", UsuarioAdministrativo.class)
                .setParameter("departamentoId", departamentoId)
                .getResultList();
    }

    public long contarActivos() {
        return entityManager.createQuery(
                        "SELECT COUNT(u) FROM UsuarioAdministrativo u WHERE u.activo = true", Long.class)
                .getSingleResult();
    }

    public boolean reactivarUsuario(UUID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            int updated = entityManager.createQuery(
                            "UPDATE UsuarioAdministrativo u SET u.activo = true WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            if (updated > 0) {
                System.out.println("Usuario reactivado: " + id);
                return true;
            } else {
                System.out.println("Usuario no encontrado para reactivar: " + id);
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al reactivar usuario: " + e.getMessage());
            throw e;
        }
    }
}