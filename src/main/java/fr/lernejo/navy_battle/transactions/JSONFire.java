package fr.lernejo.navy_battle.transactions;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JSONFire implements JSONNavyObject {

    public enum possibilities {
        MISS( "miss" ), HIT( "hit" ), SUNK( "sunk" );
        private final String text;
        possibilities( final String text ) { this.text = text; }
        public String toString() { return this.text; }
    }

    private final String consequence;
    private final boolean shipLeft;

    public JSONFire( String consequence, boolean shipLeft ) {
        this.consequence = consequence;
        this.shipLeft = shipLeft;
    }

    public String getConsequence() { return this.consequence; }

    public boolean shipLeft() { return this.shipLeft; }

    @Override
    public String getJSONString() { return this.getJSON().toString(); }

    @Override
    public JSONObject getJSON() {
        JSONObject returnJSON = null;
        try ( InputStream is = this.getClass().getClassLoader().getResourceAsStream( "Fire.json" ) ) {
            JSONObject jsonSchema = new JSONObject( new JSONTokener( is ) );
            Map<String, Object> returnObject = new HashMap<>();
            returnObject.put( "consequence", this.consequence );
            returnObject.put( "shipLeft", this.shipLeft );
            returnJSON = new JSONObject( returnObject );
            SchemaLoader.load( jsonSchema ).validate( returnJSON );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return returnJSON;
    }
}
