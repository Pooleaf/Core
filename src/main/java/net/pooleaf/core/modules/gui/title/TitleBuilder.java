package net.pooleaf.core.modules.gui.title;

public class TitleBuilder {

    private Title title = new Title();


    public TitleBuilder title(String title) {
        this.title.setTitle(title);

        return this;
    }

    public TitleBuilder subtitle(String subtitle) {
        title.setSubTitle(subtitle);

        return this;
    }

    public TitleBuilder fadeIn(int fadeIn) {
        title.setFadeIn(fadeIn);

        return this;
    }

    public TitleBuilder stay(int stay) {
        title.setStay(stay);

        return this;
    }

    public TitleBuilder fadeOut(int fadeOut) {
        title.setFadeOut(fadeOut);

        return this;
    }

    public Title build() {
        return title;
    }

}
