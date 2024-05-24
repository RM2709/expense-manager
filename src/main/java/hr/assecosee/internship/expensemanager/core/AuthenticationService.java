package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.dto.ClientDto;
import io.jsonwebtoken.Jwts;
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

    @Value("${keystoreAlias}")
    private String keystoreAlias;

    @Value("${p12file}")
    private String p12file;

    KeyStore keystore;

    public String authorize(String authorizationHeader) {
        try {
            Jwts.parserBuilder().setSigningKey(getPublicRsaKey()).build().parse(authorizationHeader.split(" ")[1]);
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String authenticate(ClientDto clientDto) throws AuthenticationException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException, NoSuchAlgorithmException {
        if(retrieveClientsInfo().contains(clientDto)){
            return generateJwtToken(clientDto);
        } else{
            throw new AuthenticationException("Client id and secret combination not present in configuration file!");
        }
    }

    private String generateJwtToken(ClientDto clientDto) throws KeyStoreException, UnrecoverableKeyException, CertificateException, IOException, NoSuchAlgorithmException {
        String jwtToken = Jwts.builder()
                .setSubject(clientDto.getClientId().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES)))
                .signWith(getPrivateRsaKey())
                .compact();
        return jwtToken;
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
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream(p12file), keystorePassword.toCharArray());
        PrivateKey key = (PrivateKey)keystore.getKey(keystoreAlias, keystorePassword.toCharArray());
        return key;
    }

    private PublicKey getPublicRsaKey() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream(p12file), keystorePassword.toCharArray());
        PublicKey key = keystore.getCertificate(keystoreAlias).getPublicKey();
        return key;
    }
}
