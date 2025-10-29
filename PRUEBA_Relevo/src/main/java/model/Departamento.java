package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "departamento")
@NamedQueries({
        @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
        @NamedQuery(name = "Departamento.findByNombre", query = "SELECT d FROM Departamento d WHERE d.nombre = :nombre")
})
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "Nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsuarioAdministrativo> usuarios = new ArrayList<>();

    public Departamento() {}

    public Departamento(String nombre) {
        this.nombre = nombre;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<UsuarioAdministrativo> getUsuarios() { return usuarios; }
    public void setUsuarios(List<UsuarioAdministrativo> usuarios) { this.usuarios = usuarios; }

    public void addUsuario(UsuarioAdministrativo usuario) {
        usuarios.add(usuario);
        usuario.setDepartamento(this);
    }

    public void removeUsuario(UsuarioAdministrativo usuario) {
        usuarios.remove(usuario);
        usuario.setDepartamento(null);
    }

    @Override
    public String toString() {
        return "departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidadUsuarios=" + usuarios.size() +
                '}';
    }
}