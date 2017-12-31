package org.ho.yaml.tests;

import java.io.File;
import java.util.Map;

import org.ho.yaml.Yaml;

public class ShowReleaseNotes {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        Map notes = (Map)Yaml.load(new File("RELEASE_NOTES"));
        String title = (String)notes.keySet().toArray()[0];
        System.out.println(notes);
    }

}
