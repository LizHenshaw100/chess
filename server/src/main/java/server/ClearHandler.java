package server;

import service.ClearService;

public class ClearHandler {
    ClearService clearService;
    public ClearHandler(ClearService service) {
        this.clearService = service;
    }

    public void clear() {
        clearService.clear();
    }
}
