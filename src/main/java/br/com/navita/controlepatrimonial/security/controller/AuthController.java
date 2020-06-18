package br.com.navita.controlepatrimonial.security.controller;

import br.com.navita.controlepatrimonial.security.dto.JwtResponseDTO;
import br.com.navita.controlepatrimonial.security.dto.LoginDTO;
import br.com.navita.controlepatrimonial.security.dto.MessageDTO;
import br.com.navita.controlepatrimonial.security.dto.SignUpDTO;
import br.com.navita.controlepatrimonial.security.model.User;
import br.com.navita.controlepatrimonial.security.model.UserDetailsCustom;
import br.com.navita.controlepatrimonial.security.repository.UserRepository;
import br.com.navita.controlepatrimonial.security.service.JwtService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Auth controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "auth", produces="application/json", consumes ="application/json", description = "Authorization")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Authenticate user response entity.
     *
     * @param loginDTO the login dto
     * @return the response entity
     */
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponseDTO(jwt,
                userDetailsCustom.getId(),
                userDetailsCustom.getUsername(),
                userDetailsCustom.getEmail()));
    }

    /**
     * Register user response entity.
     *
     * @param signUpRequest the sign up request
     * @return the response entity
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO("Erro: Usuário já está registrado!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO("Erro: O email já está cadastrado!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(new MessageDTO("Usuário registrado com sucesso!"));
    }


    /**
     * Handle exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleException(MethodArgumentNotValidException exception) {

        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(exception.getMessage());

        return ResponseEntity.badRequest().body(errorMsg);
    }
}
