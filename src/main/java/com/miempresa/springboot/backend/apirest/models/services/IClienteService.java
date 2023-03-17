package com.miempresa.springboot.backend.apirest.models.services;

import com.miempresa.springboot.backend.apirest.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    public List<Cliente> findAll();
    public Cliente findById(Long id);
    public Cliente save(Cliente cliente);
    public void delete(Long id);

}
