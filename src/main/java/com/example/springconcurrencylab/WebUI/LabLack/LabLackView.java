package com.example.springconcurrencylab.WebUI.LabLack;

import com.example.springconcurrencylab.WebUI.Layout.AdminLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="lab-lack", layout = AdminLayout.class)
public class LabLackView extends VerticalLayout {
    public LabLackView() {
        add("Lab Lack View");
    }
}
