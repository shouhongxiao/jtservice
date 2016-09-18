package jtservice.utility;

import com.sun.jersey.api.core.ScanningResourceConfig;
import com.sun.jersey.core.spi.scanning.PackageNamesScanner;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by shouh on 2016/9/14.
 */
public class MyResourceConfig extends ScanningResourceConfig {
    public MyResourceConfig(String... packages)
    {
        this.init(new PackageNamesScanner(checkNotNull(packages)));
    }
}
