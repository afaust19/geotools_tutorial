package org.geotools.tutorial.quickstart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.*;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;

/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 * <p>
 * This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class Quickstart {

    /**
     * GeoTools Quickstart demo application. Prompts the user for a shapefile and displays its
     * contents on the screen in a map frame
     */
    public static void main(String[] args) throws Exception {
        // display a data store file chooser dialog for shapefiles
        File file = JFileDataStoreChooser.showOpenFile("shp", null);

        Map<String,Object> params = new HashMap<String, Object>();
        params.put( "url", file.toURI().toURL() );
        params.put( "create spatial index", false );
        params.put( "memory mapped buffer", false );
        params.put( "charset", "ISO-8859-1" );

        DataStore store = DataStoreFinder.getDataStore( params );
        SimpleFeatureSource featureSource = store.getFeatureSource( store.getTypeNames()[0] );

        // CachingFeatureSource is deprecated as experimental (not yet production ready)
        CachingFeatureSource cache = new CachingFeatureSource(featureSource);

        // Create a map content and add our shapefile to it
        MapContent map = new MapContent();
        map.setTitle("Quickstart");
        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(featureSource, style);
        map.addLayer(layer);

        // Now display the map
        JMapFrame.showMap(map);
    }

}
