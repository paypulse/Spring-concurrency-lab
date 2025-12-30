package com.example.springconcurrencylab.WebUI.Layout;

import com.example.springconcurrencylab.WebUI.LabAsync.LabAsyncView;
import com.example.springconcurrencylab.WebUI.LabLack.LabLackView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

import static com.example.springconcurrencylab.WebUI.Layout.AppStyle.menuButton;

public class AdminLayout extends AppLayout {

    public AdminLayout() {
        //<editor-fold desc="[vaddin] Navbar  영역">
        H1 title = new H1("Concurrency Lab Admin");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                        .set("margin", "0");

        HorizontalLayout navBar = new HorizontalLayout(title);
        navBar.setWidthFull();
        navBar.setAlignItems(FlexComponent.Alignment.CENTER);
        navBar.setPadding(true);
        addToNavbar(navBar);
        //</editor-fold desc="[vaddin] Navbar  영역">

        //<editor-fold desc="[vaddin] Drawer 영역">
        addToDrawer(
                menuButton("Async 실험", LabAsyncView.class)
             //   new RouterLink("Lack 실험", LabLackView.class)
        );
        //</editor-fold desc="[vaddin] Drawer 영역">
    }


}
