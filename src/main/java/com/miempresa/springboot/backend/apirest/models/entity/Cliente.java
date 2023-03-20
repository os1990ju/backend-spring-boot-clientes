package com.miempresa.springboot.backend.apirest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

// el entity lo que representa es una tabla.en bd.


@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="No puede estar vacio")
    @Size(min=4, max=12, message="El tamaño tiene que estar entre 4 y 12 caracteres")

    @Column(nullable = false)
    private String nombre;
    @NotEmpty(message="No puede estar vacio")
    private String apellido;
    @NotEmpty(message="No puede estar vacio")
    @Email(message="debe ser una dirección de email válida")
    @Column(nullable = false, unique=true)
    private String email;
    @Column(name="create_at")
    @Temporal(TemporalType.DATE) //transforma la fecha dejava a date en sql
    private Date createAt;
    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
