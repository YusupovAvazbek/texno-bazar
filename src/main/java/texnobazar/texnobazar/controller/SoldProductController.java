package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.dto.SoldProductDto;
import texnobazar.texnobazar.service.SoldProductService;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
public class SoldProductController {
    private final SoldProductService soldProductService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<SoldProductDto> addSeller(@RequestBody SoldProductDto soldProductDto){
        return soldProductService.create(soldProductDto);
    }
    @PatchMapping("/update")
    public ApiResult<SoldProductDto> updateSeller(@RequestBody SoldProductDto soldProductDto){
        return soldProductService.update(soldProductDto);
    }
    @GetMapping("/{id}")
    public ApiResult<SoldProductDto> getByIdSeller(@PathVariable Long id){
        return soldProductService.get(id);
    }
    @DeleteMapping("/delete/{id}")
    public ApiResult<Void> deleteSeller(@PathVariable Long id){
        return soldProductService.delete(id);
    }
}
