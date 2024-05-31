package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.dto.ClientDto;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {

    @Value("${clients}")
    private String clientInfoString;

    @Value("${keystorePassword}")
    private String keystorePassword;

    @Value("${keystoreRsaAlias}")
    private String keystoreRsaAlias;

    @Value("${p12file}")
    private String p12file;

    KeyStore keystore;

    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    /**
     *
     * @param authorizationHeader Authorization header of the incoming request.
     * @return true if authorized
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public boolean authorize(String authorizationHeader) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        logger.info("Method authorize called for the following JWT token: " + authorizationHeader.split(" ")[1].trim());
        Jwts.parserBuilder().setSigningKey(getPublicRsaKey()).build().parse(authorizationHeader.split(" ")[1].trim());
        logger.info("User authorized.");
        return true;

    }

    /**
     *
     * @param clientDto object containing id and secret of the client requesting a JWT token.
     * @return JWT token if client secret and id correct.
     * @throws AuthenticationException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws CertificateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public String authenticate(ClientDto clientDto) throws AuthenticationException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException, NoSuchAlgorithmException {
        logger.info("Method authenticate called.");
        if(retrieveClientsInfo().contains(clientDto)){
            logger.info("User authenticated successfully. JWT token generated.");
            return generateJwtToken(clientDto);
        } else{
            logger.info("User not authenticated successfully. Error thrown.");
            throw new AuthenticationException("Client id and secret combination not present in configuration file!");
        }
    }

    private String generateJwtToken(ClientDto clientDto) throws KeyStoreException, UnrecoverableKeyException, CertificateException, IOException, NoSuchAlgorithmException {
        logger.info("Method generateJwtToken called.");
        return Jwts.builder()
                .setSubject(clientDto.getClientId().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES)))
                .signWith(getPrivateRsaKey())
                .compact();
    }

    private List<ClientDto> retrieveClientsInfo(){
        String[] clientInfoStringList = clientInfoString.split(",");
        List<ClientDto> clientInfoList = new ArrayList<>();
        for(String clientInfo : clientInfoStringList){
            clientInfoList.add(new ClientDto(Integer.parseInt(clientInfo.split(":")[0]), clientInfo.split(":")[1]));
        }
        return clientInfoList;
    }

    private PrivateKey getPrivateRsaKey() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        logger.info("Method getPrivateRsaKey called.");
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream(p12file), keystorePassword.toCharArray());
        return (PrivateKey)keystore.getKey(keystoreRsaAlias, keystorePassword.toCharArray());
    }

    private PublicKey getPublicRsaKey() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        logger.info("Method getPublicRsaKey called.");
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream(p12file), keystorePassword.toCharArray());
        return keystore.getCertificate(keystoreRsaAlias).getPublicKey();
    }
}
