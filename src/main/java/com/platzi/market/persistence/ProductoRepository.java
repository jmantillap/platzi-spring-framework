package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll(){
        List<Producto> list=(List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(list);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos=this.productoCrudRepository.findByIdCategoria(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos=productoCrudRepository
                .findByCantidadStockLessThanAndEstado(quantity,true);
        return productos.map(prods-> productMapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return  productoCrudRepository
                .findById(productId)
                .map(prod->productMapper.toProduct(prod));
    }

    @Override
    public Product save(Product product) {
        return productMapper.toProduct(productoCrudRepository.save(productMapper.toProducto(product)));
    }

    @Override
    public void delete(int productId) {

            productoCrudRepository.deleteById(productId);


    }

    /*public List<Producto> getByCategory(int idCategory){
        this.productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategory);
        return this.productoCrudRepository.findByIdCategoria(idCategory);
    }

    public Optional<List<Producto>> getEscasos(int cantidadStock, boolean estado){
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(cantidadStock,estado);
    }

    public Optional<Producto> getProducto(int id){
        return productoCrudRepository.findById(id);
    }

    public Producto saveProducto(Producto producto){
        return productoCrudRepository.save(producto);
    }

    public void deleteProducto(int id){
        productoCrudRepository.deleteById(id);
    }*/

}
