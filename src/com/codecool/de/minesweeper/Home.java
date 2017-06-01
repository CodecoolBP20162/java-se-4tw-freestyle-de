package com.codecool.de.minesweeper;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public class Home {
    public static ModelAndView renderHome(Request req, Response res) {



        return new ModelAndView(null, "home");
    }

}
