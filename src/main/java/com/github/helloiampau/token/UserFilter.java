package com.github.helloiampau.token;

import com.github.helloiampau.token.utils.Properties;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 02 September 2014.
 */
public class UserFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String token = request.getHeader("Authorization");

    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      JWSVerifier verifier = new MACVerifier(Properties.SHARED_SECRET);

      if(!signedJWT.verify(verifier))
        throw new Exception();

      filterChain.doFilter(servletRequest, servletResponse);
    } catch (Exception e) {
      HttpServletResponse response = (HttpServletResponse) servletResponse;
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

  }

  @Override
  public void destroy() {

  }

}
