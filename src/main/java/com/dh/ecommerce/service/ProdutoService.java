package com.dh.ecommerce.service;

import com.dh.ecommerce.entity.Produto;
import com.dh.ecommerce.entity.dto.ProdutoDTO;
import com.dh.ecommerce.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository repository;
    public List<ProdutoDTO> buscar(){
        List<Produto> listProduto = repository.findAll();
        List<ProdutoDTO> listProdutoDTO = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Produto produto : listProduto) {
            ProdutoDTO produtoDTO = mapper.convertValue(produto, ProdutoDTO.class);
            listProdutoDTO.add(produtoDTO);
        }
        return listProdutoDTO;
    }

    public ResponseEntity salvar(Produto produto){
        try{
            produto.setDataHoraCadastro(Timestamp.from(Instant.now()));
            Produto produtoSalvo = repository.save(produto);
           return new ResponseEntity( "Produto "+produtoSalvo.getNome()+" criado com sucesso", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Erro ao cadastrar produto", HttpStatus.BAD_REQUEST);
        }
    }

    public  ResponseEntity deletar(Long id){
        Optional<Produto> produto =repository.findById(id);

        if(produto.isEmpty()){
            return new ResponseEntity("Id do produto não existe", HttpStatus.BAD_REQUEST);
        }
//        repository.findById(id).orElseThrow(() -> new RuntimeException());
        repository.deleteById(id);
        return new ResponseEntity("Excluido com sucesso", HttpStatus.OK);
    }
}
