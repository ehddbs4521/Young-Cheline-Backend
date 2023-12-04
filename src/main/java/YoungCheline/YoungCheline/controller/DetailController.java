package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.DetailDto;
import YoungCheline.YoungCheline.service.DetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/detail")
public class DetailController {

    private final DetailServiceImpl detailService;

    @GetMapping("/{menuId}")
    public ResponseEntity<DetailDto> getDetail(@RequestParam("menuId") Integer menuId) {
        DetailDto detail = detailService.getDetail(menuId);

        return ResponseEntity.ok(detail);
    }
}
