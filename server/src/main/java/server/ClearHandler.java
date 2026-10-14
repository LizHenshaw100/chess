package server;

import dataaccess.DataAccessException;
import service.ClearService;
import io.javalin.http.Context;

public class ClearHandler {
    ClearService clearService;
    public ClearHandler(ClearService service) {
        this.clearService = service;
    }

    public void clear(Context ctx) throws DataAccessException {
        clearService.clear();
        ctx.status(200);
        ctx.result("{}");
    }
}
