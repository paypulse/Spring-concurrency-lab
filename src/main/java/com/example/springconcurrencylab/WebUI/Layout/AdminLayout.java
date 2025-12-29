package com.example.springconcurrencylab.WebUI.Layout;

import com.example.springconcurrencylab.WebUI.LabAsync.LabAsyncView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.RouterLink;

import static com.vaadin.flow.component.Tag.H1;

public class AdminLayout extends AppLayout {

    public AdminLayout() {
        addToNavbar(new H1("Concurrency Lab Admin"));
        addToDrawer(
                new RouterLink("Async 실험", LabAsyncView.class),
                new RouterLink("ㅣㅐ")
        );
    }
}
