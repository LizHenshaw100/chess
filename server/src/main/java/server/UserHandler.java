package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import service.RequestResultClasses.*;
import service.UserService;

public class UserHandler {
    UserService userService;
    Gson gson = new Gson();

    public UserHandler(UserService service) {
        this.userService = service;

    }

    public void register(Context ctx) throws Exception{
        var request = gson.fromJson(ctx.body(), RegisterRequest.class);
        RegisterResult result = userService.register(request);
        ctx.status(200);
        ctx.result(gson.toJson(result));
    }

    public void login(Context ctx) throws Exception {
        var request = gson.fromJson(ctx.body(), LoginRequest.class);
        LoginResult result = userService.login(request);
        ctx.status(200);
        ctx.result(gson.toJson(result));
    }

    public void logout(Context ctx) throws Exception {
        var request = new LogoutRequest(ctx.header("authorization"));
        userService.logout(request);
        ctx.status(200);
        ctx.result("{}");
    }
}
