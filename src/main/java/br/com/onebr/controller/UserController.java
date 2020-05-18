package br.com.onebr.controller;

import br.com.onebr.controller.request.PasswordRecoverReq;
import br.com.onebr.controller.request.UserReq;
import br.com.onebr.controller.response.UserRes;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.UserService;
import br.com.onebr.service.util.ApiOneBr;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/password-recover")
    public ResponseEntity passwordRecover(@Valid @RequestBody PasswordRecoverReq passwordRecoverReq) {
        userService.passwordRecover(passwordRecoverReq);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/user/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<UserRes> patchUser(@PathVariable("id") Long id, @Valid @RequestBody UserReq userReq) {
        final UserRes userRes = userService.patchUser(id, userReq);

        return ResponseEntity.ok(userRes);
    }

    @GetMapping("/admin/user/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<UserRes> getUser(@PathVariable("id") Long id) {
        final UserRes userRes = userService.getUser(id);

        return ResponseEntity.ok(userRes);
    }

    @GetMapping("/admin/user")
    @PreAuthorize("hasAuthority('" + ROLE.ADMIN + "')")
    public ResponseEntity<Page<UserRes>> getUsers(@RequestParam(value = "searchTerm", required = false) String searchTerm, Pageable page) {

        return ResponseEntity.ok(userService.findAll(searchTerm, page));
    }

    @PostMapping("/admin//user/{id}/activate")
    @PreAuthorize("hasAuthority('" + ROLE.ADMIN + "')")
    public ResponseEntity<?> activate(@PathVariable("id") Long id) {
        userService.updateActiveStatus(id, true);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/user/{id}/deactivate")
    @PreAuthorize("hasAuthority('" + ROLE.ADMIN + "')")
    public ResponseEntity<?> deactivate(@PathVariable("id") Long id) {
        userService.updateActiveStatus(id, false);

        return ResponseEntity.ok().build();
    }
}
