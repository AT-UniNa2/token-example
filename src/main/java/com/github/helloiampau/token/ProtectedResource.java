package com.github.helloiampau.token;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * token
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 02 September 2014.
 */
public class ProtectedResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("plain/text");

    try {
      PrintWriter writer = response.getWriter();
      writer.println("A super secret message");
    } catch (IOException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

}
