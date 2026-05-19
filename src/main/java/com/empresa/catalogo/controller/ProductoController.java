package com.empresa.catalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
 import com.empresa.catalogo.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD del catálogo")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "400", description = 
    "Datos de entrada inválidos")
@PostMapping
@ResponseStatus(HttpStatus.CREATED)

    public ProductoResponseDTO crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return service.crear(dto);
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description =  "Producto encontrado")
@ApiResponse(responseCode 

    = "404", description = "Producto no encontrado")
@GetMapping("/{id}")
    public ProductoResponseDTO buscar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
