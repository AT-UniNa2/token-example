package com.github.helloiampau.token;

import com.github.helloiampau.token.utils.Properties;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * token
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 02 September 2014.
 */
public class TokenController extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {

    response.setContentType("application/json");

    try {
      String username = request.getParameter("u");
      String password = request.getParameter("p");

      if(!(username.equals("student") && password.equals("mypass")))
        throw new NullPointerException();

      JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

      JWTClaimsSet claimsSet = new JWTClaimsSet();
      claimsSet.setSubject(username);
      claimsSet.setIssueTime(new Date());

      SignedJWT signedJWT = new SignedJWT(header, claimsSet);

      JWSSigner signer = new MACSigner(Properties.SHARED_SECRET);
      signedJWT.sign(signer);

      PrintWriter writer = response.getWriter();
      writer.println(signedJWT.serialize());

      response.setStatus(HttpServletResponse.SC_OK);
    } catch (NullPointerException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } catch (IOException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (JOSEException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

  }

}
