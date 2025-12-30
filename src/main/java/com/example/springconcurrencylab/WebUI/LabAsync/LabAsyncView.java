package com.example.springconcurrencylab.WebUI.LabAsync;

import com.example.springconcurrencylab.WebUI.Layout.AdminLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "lab-async", layout = AdminLayout.class)
public class LabAsyncView extends VerticalLayout {
    public LabAsyncView() {
        add("Async 실험 화면");
    }
}
