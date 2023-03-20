package com.miempresa.springboot.backend.apirest.controllers;

import com.miempresa.springboot.backend.apirest.models.constants.KeyConstants;
import com.miempresa.springboot.backend.apirest.models.entity.Cliente;
import com.miempresa.springboot.backend.apirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private IClienteService clienteService; //la interfaz busca la clase que esta implementada
    @GetMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    }

    //buscar por id
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Cliente cliente = null;
        Map<String,Object> response = new HashMap<String,Object>();
        try{
            cliente = clienteService.findById(id);
        }catch(DataAccessException e){
            response.put(KeyConstants.MESSAGE, KeyConstants.ERROR_CREATED);
            response.put(KeyConstants.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente == null){
            response.put(KeyConstants.MESSAGE, "El cliente ID: ".concat(id.toString().concat(KeyConstants.NOT_EXIST_IN_DB)));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
    }
    //crear cliente
    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
//        intenta guradar
        Cliente clienteNew = null;
        Map<String,Object> response = new HashMap<>();
        try{
            clienteNew = clienteService.save(cliente);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al guardar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","cliente insertado con exito!");
        response.put("Cliente",clienteNew);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
    //actualizar
    @PutMapping("/clientes/{id}")
    public  ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){

        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated = null;
        Map<String,Object> response = new HashMap<String,Object>();

        if(clienteActual == null){
            response.put("mensaje", "Error: no se puede editar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {

            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteUpdated = clienteService.save(clienteActual);
        } catch (DataAccessException e) {

            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","cliente actualizado con exito!");
        response.put("Cliente",clienteUpdated);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
    //borrar
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String,Object> response = new HashMap<String,Object>();
        try {
            clienteService.delete(id);
        } catch(DataAccessException e){
            response.put(KeyConstants.MESSAGE , KeyConstants.ERROR_DELETED );
            response.put(KeyConstants.ERROR , e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put(KeyConstants.MESSAGE , KeyConstants.CLIENT_DELETE_SUCCESS);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
    }
}
