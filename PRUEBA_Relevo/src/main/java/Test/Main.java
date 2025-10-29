package Test;

import jakarta.persistence.EntityManager;
import model.UsuarioAdministrativo;
import model.Departamento;
import service.DAOUsuarioAdministrativo;
import service.DAODepartamento;
import service.EntityManagerUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        DAOUsuarioAdministrativo usuarioDao = new DAOUsuarioAdministrativo(entityManager);
        DAODepartamento departamentoDao = new DAODepartamento(entityManager);

        try {
            Departamento deptoVentas = new Departamento("Ventas");
            Departamento deptoIT = new Departamento("Tecnologia");
            Departamento deptoRH = new Departamento("RRHH");

            departamentoDao.insert(deptoVentas);
            departamentoDao.insert(deptoIT);
            departamentoDao.insert(deptoRH);

            System.out.println(" Departamentos Creados ");
            List<Departamento> departamentos = departamentoDao.getAll();
            departamentos.forEach(System.out::println);

            UsuarioAdministrativo usuario1 = new UsuarioAdministrativo("Diego", "Fonseca", deptoVentas);
            UsuarioAdministrativo usuario2 = new UsuarioAdministrativo("Mario", "García", deptoIT);
            UsuarioAdministrativo usuario3 = new UsuarioAdministrativo("Cristopher", "Arauz", deptoVentas);
            UsuarioAdministrativo usuario4 = new UsuarioAdministrativo("Slles", "Rodríguez", deptoRH);

            usuarioDao.insert(usuario1);
            usuarioDao.insert(usuario2);
            usuarioDao.insert(usuario3);
            usuarioDao.insert(usuario4);

            deptoVentas.addUsuario(usuario1);
            deptoVentas.addUsuario(usuario3);
            deptoIT.addUsuario(usuario2);
            deptoRH.addUsuario(usuario4);

            System.out.println("\n Todos Los Usuarios ");
            List<UsuarioAdministrativo> usuarios = usuarioDao.getAll();
            usuarios.forEach(System.out::println);

            System.out.println("\n Usuarios Por Departamento (Ventas) ");
            List<UsuarioAdministrativo> usuariosVentas = usuarioDao.getUsuariosPorDepartamento(deptoVentas.getId());
            usuariosVentas.forEach(System.out::println);

            System.out.println("\n Buscar Por Nombre 'Juan' ");
            List<UsuarioAdministrativo> porNombre = usuarioDao.buscarPorNombre("Juan");
            porNombre.forEach(System.out::println);

            System.out.println("\n Usuarios Activos ");
            List<UsuarioAdministrativo> activos = usuarioDao.getActivos();
            activos.forEach(System.out::println);

            System.out.println("\n Contar Activos ");
            long totalActivos = usuarioDao.contarActivos();
            System.out.println("Total de usuarios activos: " + totalActivos);

            System.out.println("\n Cambiar Departamento De Usuario ");
            usuario2.setDepartamento(deptoRH);
            usuarioDao.update(usuario2);

            System.out.println("Usuario actualizado: " + usuario2);
            System.out.println("\n Intentar Eliminar Con Usuarios ");

            boolean eliminado = departamentoDao.delete(deptoVentas.getId());
            System.out.println("Departamento eliminado: " + eliminado);

            Departamento deptoSinUsuarios = new Departamento("Marketing");
            departamentoDao.insert(deptoSinUsuarios);

            System.out.println("\n Eliminar Departamento Sin Usuarios ");
            eliminado = departamentoDao.delete(deptoSinUsuarios.getId());
            System.out.println("Departamento eliminado: " + eliminado);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            EntityManagerUtil.close();
        }
    }
}