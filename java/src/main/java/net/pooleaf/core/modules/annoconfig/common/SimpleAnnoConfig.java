package net.pooleaf.core.modules.annoconfig.common;

import net.pooleaf.core.modules.annoconfig.common.anno.ConfigExclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import lombok.Setter;

@AllArgsConstructor
public class SimpleAnnoConfig {

  @ConfigExclude
  @Setter
  @Getter
  private File file;


  public SimpleAnnoConfig load() {
    AnnoConfig.load(file, this);
    onLoaded();
    return this;
  }

  public SimpleAnnoConfig save() {
    AnnoConfig.save(file, this);
    onSaved();
    return this;
  }

  public void onLoaded() {}

  public void onSaved() {}

}
