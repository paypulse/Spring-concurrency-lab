package com.example.springconcurrencylab.WebUI.Layout;

import com.example.springconcurrencylab.WebUI.LabAsync.LabAsyncView;
import com.example.springconcurrencylab.WebUI.LabLack.LabLackView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.RouterLink;

public class AdminLayout extends AppLayout {

    public AdminLayout() {
        addToNavbar(new H1("Concurrency Lab Admin"));
        addToDrawer(
                new RouterLink("Async 실험", LabAsyncView.class),
                new RouterLink("Lack 실험", LabLackView.class)
        );
    }
}
