package edu.wctc.stockpurchase.controller;

import com.github.fge.jsonpatch.JsonPatch;
import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.service.StockPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/stockpurchases")
public class StockPurchaseController {

    private StockPurchaseService service;

    @Autowired
    public StockPurchaseController(StockPurchaseService sps) {
        this.service = sps;
    }


    @PostMapping
    public StockPurchase createStockPurchase(@RequestBody StockPurchase stockPurchase){
        stockPurchase.setId(0);
        return service.save(stockPurchase);
    }

    @GetMapping
    public List<StockPurchase> getAllStockPurchases() {return service.getAllStockPurchases();}

    @GetMapping("/{stockPurchaseId}")
    public StockPurchase getStockPurchase(@PathVariable String stockPurchaseId){
        try{
            int id = Integer.parseInt(stockPurchaseId);
            return service.getStockPurchaseById(id);
            }
        catch (NumberFormatException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format: " + stockPurchaseId + " Must be a number", exception);
        }
        catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    exception.getMessage(), exception);
        }
    }

    @PutMapping
    public StockPurchase updateStockPurchase(@RequestBody StockPurchase stockPurchase){
        try{
            return service.update(stockPurchase);
        }
        catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    exception.getMessage(), exception);
        }


    }

    @PatchMapping("/{stockPurchaseId}")
    public StockPurchase patchStockPurchase(@PathVariable String stockPurchaseId, @RequestBody JsonPatch patch){
        try{
            int id = Integer.parseInt(stockPurchaseId);
            return service.patch(id, patch);
        }
        catch (NumberFormatException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format: " + stockPurchaseId + " Must be a number", exception);
        }
        catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{stockPurchaseId}")
    public String deleteStudent(@PathVariable String stockPurchaseId){
        try{
            int id = Integer.parseInt(stockPurchaseId);
            service.delete(id);
            return "Stock Purchase Deleted: " + id;
        }
        catch (NumberFormatException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format: " + stockPurchaseId + " Must be a number", exception);
        }
        catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    exception.getMessage(), exception);
        }
    }



}
