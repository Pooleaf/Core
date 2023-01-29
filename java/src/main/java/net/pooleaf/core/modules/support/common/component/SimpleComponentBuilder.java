package net.pooleaf.core.modules.support.common.component;

import net.md_5.bungee.api.chat.*;

public class SimpleComponentBuilder {

    private TextComponent component;


    public SimpleComponentBuilder() {
        component = new TextComponent();
    }

    public SimpleComponentBuilder(String text) {
        component = new TextComponent(text);
    }

    public SimpleComponentBuilder text(String text) {
        component.setText(text);

        return this;
    }

    public SimpleComponentBuilder hoverEvent(HoverEvent.Action action, String text) {
        HoverEvent hoverEvent = new HoverEvent(action, new ComponentBuilder(text).create());
        component.setHoverEvent(hoverEvent);

        return this;
    }

    public SimpleComponentBuilder hoverShowText(String text) {
        return hoverEvent(HoverEvent.Action.SHOW_TEXT, text);
    }

    public SimpleComponentBuilder clickEvent(ClickEvent.Action action, String value) {
        ClickEvent clickEvent = new ClickEvent(action, value);
        component.setClickEvent(clickEvent);

        return this;
    }

    public SimpleComponentBuilder clickRunCommand(String command) {
        return clickEvent(ClickEvent.Action.RUN_COMMAND, command);
    }

    public SimpleComponentBuilder addExtra(String text) {
        component.addExtra(text);

        return this;
    }

    public SimpleComponentBuilder addExtra(BaseComponent baseComponent) {
        component.addExtra(baseComponent);

        return this;
    }

    public TextComponent build() {
        return component;
    }

}