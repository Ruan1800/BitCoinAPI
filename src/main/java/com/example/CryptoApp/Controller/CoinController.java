package com.example.CryptoApp.Controller;

import com.example.CryptoApp.Entity.Coin;
import com.example.CryptoApp.Repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;


        @GetMapping()
        public ResponseEntity get() {
            return new ResponseEntity<>(coinRepository.getAll(), HttpStatus.OK);
        }

        @GetMapping("/name")
        public ResponseEntity get(@PathVariable String name) {
            try{
                return new ResponseEntity<>(coinRepository.getByName(name),HttpStatus.OK);
            } catch (Exception error){
                return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping()
        public ResponseEntity post(@RequestBody Coin coin) {
            try{
                coin.setDateTime(new Timestamp(System.currentTimeMillis()));
                return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);
            } catch (Exception error) {
                return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }

        @PutMapping
        public ResponseEntity put(@RequestBody Coin coin) {
           try{
                coin.setDateTime(new Timestamp(System.currentTimeMillis()));
                return new ResponseEntity(coinRepository.update(coin), HttpStatus.OK);
           } catch (Exception error){
               return new ResponseEntity<>(error.getMessage(),HttpStatus.NO_CONTENT);
           }
        }

          /*logica para a api de deletargi*/
        @DeleteMapping("/{id}")
        public ResponseEntity delete(@PathVariable int id) {
            boolean response = false;
          try{
              response = coinRepository.remove(id);
             return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
    }

}
