package com.example.springconcurrencylab.WebUI.Layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public final class AppStyle {

    private AppStyle() {

    }

    public static RouterLink menuButton(
            String text,
            Class<? extends Component> target
    ) {
        RouterLink link = new RouterLink("",target);
        link.addClassName("menu-link");

        Button button = new Button(text);
        button.setWidthFull();
        button.addClassName("menu-button");

        button.addThemeVariants(
                ButtonVariant.LUMO_TERTIARY,
                ButtonVariant.LUMO_SMALL
        );

        link.add(button);
        link.setHighlightCondition(HighlightConditions.sameLocation());

        return link;
    }

}
