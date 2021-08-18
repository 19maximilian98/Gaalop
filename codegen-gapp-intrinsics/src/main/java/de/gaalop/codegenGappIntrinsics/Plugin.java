package de.gaalop.codegenGappIntrinsics;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;

import de.gaalop.ConfigurationProperty;
import de.gaalop.codegenGappIntrinsics.riscvv.GAPPCodeGenerator;
import de.gaalop.codegenGappIntrinsics.sse.IntrinsicsGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Implements a CodegeneratorPlugin, 
 * which lists all GAPP members in a graph in a pretty-printed format
 * @author Christian Steinmetz
 */
public class Plugin implements CodeGeneratorPlugin {

    private Log log = LogFactory.getLog(Plugin.class);
    private Image icon;

    @ConfigurationProperty(type = ConfigurationProperty.Type.TEXT)
    public String intrinsicType = "sse3";

    public Plugin() {
        URL url = getClass().getResource("icon.png");
        if (url != null) {
            try {
                icon = ImageIO.read(url);
            } catch (IOException e) {
                log.error("Unable to read plugin icon " + url);
            }
        } else {
            log.warn("Unable to find plugin icon!");
        }
    }

    @Override
    public CodeGenerator createCodeGenerator() {
        if (intrinsicType.equals("sse3"))
            return new IntrinsicsGenerator();
        return new GAPPCodeGenerator();
    }

    @Override
    public String getName() { return "GAPP Intrinsics"; }

    @Override
    public String getDescription() {
        return "This plugin generates GAPP Code";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}