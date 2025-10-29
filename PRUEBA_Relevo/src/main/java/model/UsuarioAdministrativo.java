package model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Usuario_Administrativo")
@NamedQueries({
        @NamedQuery(name = "UsuarioAdministrativo.findAll", query = "SELECT u FROM UsuarioAdministrativo u"),
        @NamedQuery(name = "UsuarioAdministrativo.findActivos", query = "SELECT u FROM UsuarioAdministrativo u WHERE u.activo = true"),
        @NamedQuery(name = "UsuarioAdministrativo.desactivar", query = "UPDATE UsuarioAdministrativo u SET u.activo = false WHERE u.id = :id"),
        @NamedQuery(name = "UsuarioAdministrativo.findByDepartamento", query = "SELECT u FROM UsuarioAdministrativo u WHERE u.departamento.id = :departamentoId")
})
public class UsuarioAdministrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "Activo", nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    public UsuarioAdministrativo() {}

    public UsuarioAdministrativo(String nombre, String apellido, Departamento departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;
        this.activo = true;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return "UsuarioAdministrativo{" +
                "Id=" + id +
                ", Nombre='" + nombre + '\'' +
                ", Apellido='" + apellido + '\'' +
                ", Activo=" + activo +
                ", Departamento=" + (departamento != null ? departamento.getNombre() : "Sin Departamento") +
                '}';
    }
}