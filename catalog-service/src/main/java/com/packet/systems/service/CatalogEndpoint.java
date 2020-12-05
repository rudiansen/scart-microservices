package com.packet.systems.service;

import com.packet.systems.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CatalogEndpoint {

    @Autowired
    private CatalogService catalogService;

    @ResponseBody
    @GetMapping("/products")
    @CrossOrigin
    public ResponseEntity<List<Product>> readAll() {
        return new ResponseEntity<List<Product>>(catalogService.readAll(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/product/{id}")
    @CrossOrigin
    public ResponseEntity<Product> read(@PathVariable("id") String id) {
        return new ResponseEntity<Product>(catalogService.read(id), HttpStatus.OK);
    }
}
