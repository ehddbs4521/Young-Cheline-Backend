package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.DetailDto;
import YoungCheline.YoungCheline.service.DetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detail")
public class DetailController {

    @Value("admin.id")
    private String admin;
    private final DetailServiceImpl detailService;

    @GetMapping("/{menuId}")
    public ResponseEntity<DetailDto> getDetail(@RequestParam("menuId") Integer menuId, Authentication authentication) {
        DetailDto detail = detailService.getDetail(menuId);

        return ResponseEntity.ok(detail);
    }
}
