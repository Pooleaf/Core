package net.pooleaf.core.modules.annoconfig.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigExclude;

@AllArgsConstructor
public class SimpleAnnoConfig {

  @ConfigExclude
  @Getter
  private File file;


  public SimpleAnnoConfig load() {
    AnnoConfig.load(file, this);
    return this;
  }

  public SimpleAnnoConfig save() {
    AnnoConfig.save(file, this);
    return this;
  }

}
