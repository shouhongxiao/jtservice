package jtservice.register;

import com.sun.jersey.spi.scanning.AnnotationScannerListener;
import jtservice.utility.RegisterAddrAnnotation;

/**
 * Created by shouh on 2016/8/31.
 */
public class RegisterScannerListener extends AnnotationScannerListener {

    public RegisterScannerListener() {
        super(RegisterAddrAnnotation.class);
    }

    /**
     * Create a scanning listener to check for Java classes in Java
     * class files annotated with {@link RegisterAddrAnnotation} .
     *
     * @param classloader the class loader to use to load Java classes that
     *        are annotated with any one of the annotations.
     */
    public RegisterScannerListener(ClassLoader classloader) {
        super(classloader,RegisterAddrAnnotation.class);
    }
}