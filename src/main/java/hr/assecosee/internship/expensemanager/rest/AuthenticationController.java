package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.AuthenticationService;
import hr.assecosee.internship.expensemanager.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * REST mapping concerning users.
 */
@RestController
@Log4j
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    /**
     * Creates a JWT token for a client.
     *
     * @param clientInfo JSON object which contains the id and secret of the client creating a JWT token.
     * @return Status code, a message describing the outcome of the operation, and basic JWT token in response header if successful.
     */
    @PostMapping("/clientSecret")
    @Operation(summary = "Checks the correctness of a client's secret.",
            description = "Consumes a client id and client secret and checks if the id:secret pairing is registered. If yes, returns a JWT token. Otherwise, throws an error.")
    public ResponseEntity<StatusDto> getJwtToken(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Client id and client secret of the client attempting to create a JWT token.",
            content = @Content(schema =@Schema(implementation = ClientDto.class))) @RequestBody ClientDto clientInfo) throws AuthenticationException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException, NoSuchAlgorithmException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", authenticationService.authenticate(clientInfo).trim());
        return new ResponseEntity<>(new StatusDto(0, "No error!"), responseHeaders, HttpStatusCode.valueOf(200));
    }
}

