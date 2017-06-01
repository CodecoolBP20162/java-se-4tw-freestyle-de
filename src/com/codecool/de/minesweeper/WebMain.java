package com.codecool.de.minesweeper;

import spark.Request;
import spark.Response;
import spark.TemplateEngine;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class WebMain {


        public static void main(String[] args) {
            TemplateEngine templateEngine = new ThymeleafTemplateEngine();

            exception(Exception.class, (e, req, res) -> e.printStackTrace());
            staticFileLocation("/public");
            port(8888);


            get("/", (request, response) -> {
                get("/index", (Request req, Response res) ->
                        Home.renderHome(req, res), templateEngine);
                return null;
            });
        }

}
