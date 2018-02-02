package org.magneto.mutantDetector.hk2;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class Hk2Feature implements Feature {

  public boolean configure(FeatureContext context) {
    context.register(new HK2Binder());
    return true;
  }
}