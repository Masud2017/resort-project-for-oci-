package com.concretepage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.concretepage.model.Skiers;


@WebServlet(name = "PostServlet", urlPatterns = {"/skiers"})

public class ResourceServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4270566185479105328L;
	private ConcurrentHashMap<String, Skiers> skierDatabase;
    public void init() {
    	skierDatabase = new ConcurrentHashMap<String, Skiers>();
    }

	
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();

        try {
            Skiers skiers = gson.fromJson(sb.toString(), Skiers.class);
            skierDatabase.put(skiers.getskierID(), skiers);
            responseJson.add("audio", gson.toJsonTree(skiers));
			response.setStatus(HttpServletResponse.SC_CREATED);
	        responseJson.addProperty("Status", "Write successful");

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        responseJson.addProperty("Status", "Invalid inputs");

		}


        response.setContentType("application/json");
        PrintWriter postout = response.getWriter();
        postout.println(gson.toJson(responseJson));

}
}

