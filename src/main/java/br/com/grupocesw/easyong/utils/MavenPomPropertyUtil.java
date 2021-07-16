package br.com.grupocesw.easyong.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class MavenPomPropertyUtil {

    private static final String POM = "pom.xml";

    public static Model getPom() {
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            return reader.read(new FileReader(POM));
        } catch (XmlPullParserException|IOException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

}