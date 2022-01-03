package net.pooleaf.core.annoconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@AllArgsConstructor
public class SimpleAnnoConfig {

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
