package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.service.SellerService;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<SellerDto> addSeller(@RequestBody SellerDto sellerDto){
        return sellerService.create(sellerDto);
    }
    @PatchMapping("/update")
    public ApiResult<SellerDto> updateSeller(@RequestBody SellerDto sellerDto){
        return sellerService.update(sellerDto);
    }
    @GetMapping("/{id}")
    public ApiResult<SellerDto> getByIdSeller(@PathVariable Long id){
        return sellerService.get(id);
    }
    @DeleteMapping("/delete/{id}")
    public ApiResult<Void> deleteSeller(@PathVariable Long id){
        return sellerService.delete(id);
    }
}
