package edu.wctc.stockpurchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.repo.StockPurchaseRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StockPurchaseService {
    private StockPurchaseRepository repo;

    @Autowired
    public StockPurchaseService(StockPurchaseRepository spr) {
        this.repo = spr;
    }

    @Autowired
    private ObjectMapper objectMapper;

    public List<StockPurchase> getAllStockPurchases(){
        List<StockPurchase> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    };

    public StockPurchase getStockPurchaseById(int stockPurchaseId) {
        return repo.findById(stockPurchaseId).orElseThrow();
    }

    public StockPurchase save(StockPurchase stockPurchase){
        return repo.save(stockPurchase);
    }

    public StockPurchase update(StockPurchase stockPurchase) throws Exception {
        if(!repo.existsById((stockPurchase.getId()))){
            return repo.save(stockPurchase);

        }else{
            throw new Exception("Not Found");
        }
    }

    public StockPurchase patch(int id, JsonPatch patch) throws

        JsonPatchException, JsonProcessingException {
            StockPurchase existingStockPurchase = getStockPurchaseById(id);
            JsonNode patched = patch.apply(objectMapper.convertValue(
                    existingStockPurchase,
                    JsonNode.class));
            StockPurchase patchedStockPurchase = objectMapper.treeToValue(patched, StockPurchase.class);

            return save(patchedStockPurchase);
    }

    public void delete(int id) throws Exception{
        if(repo.existsById(id)){
            repo.deleteById(id);
        }
        else
        {
            throw new Exception("Not Found");
        }
    }

}
